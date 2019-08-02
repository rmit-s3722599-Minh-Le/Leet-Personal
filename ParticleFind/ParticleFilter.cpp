
#include "ParticleFilter.h"

#include <cstdio>
#include <cstdlib>
#include <iostream>

/**THINGS TO CONSIDER AND WHAT MY TESTs IMPLEMENTS:
*1. THE ROBOT CANNOT ROTATE 180 DEGREES (SPEFICIALLY POINTING TOWARDS M3)
*2. ROBOT CANNOT BE OUT OF BOUNDS: TRHOUGH WALLS (OR BORDERS i.e '~')
*3. WHEN CREATING GRID, MAKE SURE 3x3 DOES NOT GO OUT OF BOUNDS
*4. HAVE FUN
*/

/**HOW I IMPLEMENTED PARTICLE FILTER
 * FIRST SITUATION: WHEN FIRST OBS
 * 
 *1. FINDS ALL POSSIBLE LOCATION. PARTICLES THEN GOES INTO P ARRAY
 *2. PARTICLE-FILTER WILL FORM A GRID FOR EACH PARTICLE'S LOCATION AND COMPARE WITH OBS
 *3. IF OBS ORIENT IS UNKNOWN
      *ASSUME IT IS UP, COMPARE AND THEN ROTATE 90 DEGREES. REPEAT THREE TIMES 
      *ORIENT = NO.OF ROTATIONS
 *4. FILTERED PARTICLE GOES TO P1. P1 GETS COPIED TO P
   
 * END FIRST SITUATION
 * 
 * 2ND SITUATION STARTS
 * IF THE ORIENT IS '*'
     *ASSUME ROTATION:
        *ADD IF OBS == PARTICLE GRID
        *ELSE DO NOT ADD
        *ROTATE AND REPEAT (3 MORE TIMES) 
     *ASSUME MOVEMENT:
          *PARTICLE MOVES
          *COMPARE: TRUE = ADD 
       *UPDATE P LIST       
 *
  *IF ROBOT ROTATES
     *PARTICLE LOCATION STAYS SAME
     *OVERWRITE ORIENT
     *UPDATE P LIST
 * 
  *IF ROBOT MOVES
     *PARTICLE MOVES
     *COMPARE IF TRUE, THEN ADD
     *UPDATE P LIST
 * END

*/

/**WHY IS IT EFFICIENT
 * HAVING A TEMP GRIND FOR THE PARTICLES TO COMPARE WITH THE OBS CREATES MORE FLEXIBILITY
   *ROTATION METHOD CAN BE USED AS MANY TIMES I.E. LESS REPETITION FOR ORIENT COMPARING
   *NEAT CODE COMPARING USING FOR LOOPS RATHER THEN THE LONG ONE BY ONE CODE
   *DOES NOT GO CREATE ANY NEW GRID IF THE PARTICLE MOVEMENT IS OUT OF BOUNDS

*/

// Initialise a new particle filter with a given maze of size (x,y)
ParticleFilter::ParticleFilter(char **maze, int rows, int cols)
{
   this->rows = rows;
   this->maze = maze;
   this->cols = cols;
   pL = new ParticleList();
   firstObs = false;
   outOfBounds = false;
}

// Clean-up the Particle Filter
ParticleFilter::~ParticleFilter()
{
   delete pL;
   pL = NULL;
}

// A new observation of the robot, of size 3x3
void ParticleFilter::newObservation(Grid observation)
{
   ParticleList *pL1 = new ParticleList();
   //Condition: when this is the first observation
   if (pL->getNumberParticles() == 0)
   {
      std::cout << "FirstObservation" << std::endl;
      prevOrient = getOrientation(observation);
      //adds all the possible locations to the list
      for (int i = 1; i < rows - 1; i++)
      {
         for (int j = 1; j < cols - 1; j++)
         {
            if (maze[i][j] == '.')
            {
               pL->add_back(new Particle(j, i, prevOrient));
            }
         }
      }
      //filters the particles and adds to temp List
      for (int i = 0; i < pL->getNumberParticles(); i++)
      {
         Particle *thisParticle = (pL->get(i));
         prevOrient = thisParticle->getOrientation();
         Grid tempParticleGrid = createGrid(thisParticle);
         //if:direction is known => compares both observations and adds if match
         if (observation[1][1] != '*')
         {
            directCompareandAdd(observation, tempParticleGrid, thisParticle, pL1);
         }
         //else rotates and compare and adds if match
         else
         {
            compareAndRotate(observation, tempParticleGrid, thisParticle, pL1);
         }
      }

      pLDeepCopy(pL, pL1);
      firstObs = true;
   }
   //end of First CONDITION

   else if (observation[1][1] == '*')
   {
      std::cout << "robot doesn't know it's orientation!" << std::endl;
      for (int i = 0; i < pL->getNumberParticles(); i++)
      {
         Particle *thisParticle = (pL->get(i));
         Grid tempParticleGrid1 = createGrid(thisParticle);
         //check to see if the robot has rotated
         compareAndRotate(observation, tempParticleGrid1, thisParticle, pL1);
         //check to see if it has moved in the direction
         Particle *newParticle = newParticleFromMovement(pL->get(i)->getOrientation(), thisParticle);
         if (!outOfBounds)
         {
            Grid tempParticleGrid = lookStraightAhead(createGrid(newParticle), pL->get(i)->getOrientation());
            directCompareandAdd(observation, tempParticleGrid, newParticle, pL1);
         }
      }
      pLDeepCopy(pL, pL1);
   }
   //robot has rotated (M2)
   else if (prevOrient != getOrientation(observation))
   {
      std::cout << "the robot has rotated" << std::endl;
      for (int i = 0; i < pL->getNumberParticles(); i++)
      {
         Particle *thisParticle = (pL->get(i));
         int x = thisParticle->getX();
         int y = thisParticle->getY();
         Particle *newParticle = new Particle(x, y, getOrientation(observation));
         prevOrient = getOrientation(observation);
         pL1->add_back(newParticle);
      }
      pLDeepCopy(pL, pL1);
   }
   //robot has moved! (M2)
   else if (prevOrient == getOrientation(observation))
   {
      std::cout << "Robot has moved" << std::endl;
      for (int i = 0; i < pL->getNumberParticles(); i++)
      {
         outOfBounds = false;
         Particle *thisParticle = (pL->get(i));
         Particle *newParticle = newParticleFromMovement(getOrientation(observation), thisParticle);
         Grid tempParticleGrid = createGrid(newParticle);
         directCompareandAdd(observation, tempParticleGrid, newParticle, pL1);
      }
      pLDeepCopy(pL, pL1);
   }
   delete pL1;
}

//creates a 3 x 3 Grid "view" of the particle
Grid ParticleFilter::createGrid(Particle *p)
{
   int x = (p->getX());
   int y = (p->getY());
   Grid grid = make_grid(OBS_SIZE, OBS_SIZE);
   grid[0][0] = maze[y - 1][x - 1];
   grid[0][1] = maze[y - 1][x];
   grid[0][2] = maze[y - 1][x + 1];
   grid[1][0] = maze[y][x - 1];
   grid[1][1] = maze[y][x];
   grid[1][2] = maze[y][x + 1];
   grid[2][0] = maze[y + 1][x - 1];
   grid[2][1] = maze[y + 1][x];
   grid[2][2] = maze[y + 1][x + 1];
   return grid;
}

//credits to unit_test.cpp and the author
//makes the grid
Grid ParticleFilter::make_grid(const int rows, const int cols)
{
   Grid grid = NULL;

   if (rows >= 0 && cols >= 0)
   {
      grid = new char *[rows];
      for (int i = 0; i != rows; ++i)
      {
         grid[i] = new char[cols];
      }
   }

   return grid;
}

//gets the orientation of the observation. IF no direction, it allocates the direction facing forward
Orientation ParticleFilter::getOrientation(Grid observation)
{
   if (observation[1][1] == '^')
   {
      return ORIEN_UP;
   }
   else if (observation[1][1] == 'v')
   {
      return ORIEN_DOWN;
   }
   else if (observation[1][1] == '>')
   {
      return ORIEN_RIGHT;
   }

   else if (observation[1][1] == '<')
   {
      return ORIEN_LEFT;
   }
   else
      return ORIEN_UP;
}

//checks to see if the movement goes on the border and if so, alert outofbounds
void ParticleFilter::outOfBoundsCheck(int x, int y)
{
   if (x >= cols - 1 || x <= 0)
   {
      outOfBounds = true;
   }
   if (y >= rows - 1 || y <= 0)
   {
      outOfBounds = true;
   }
   if (maze[y][x] == '=')
   {
      outOfBounds = true;
   }
}

//moves the particle one space from the direction it is facing
Particle *ParticleFilter::newParticleFromMovement(Orientation orient, Particle *p)
{
   int x = p->getX();
   int y = p->getY();
   if (orient == ORIEN_UP)
   {
      y--;
      outOfBoundsCheck(x, y);
      return new Particle(x, y, ORIEN_UP);
   }
   else if (orient == ORIEN_DOWN)
   {
      y++;
      outOfBoundsCheck(x, y);
      return new Particle(x, y, ORIEN_DOWN);
   }
   else if (orient == ORIEN_RIGHT)
   {
      x++;
      outOfBoundsCheck(x, y);
      return new Particle(x, y, ORIEN_RIGHT);
   }
   else
   {
      x--;
      outOfBoundsCheck(x, y);
      return new Particle(x, y, ORIEN_LEFT);
   }
}
//copys the temporarely filtered Particle List to the original ParticleList.
void ParticleFilter::pLDeepCopy(ParticleList *origin, ParticleList *temp)
{
   origin->clear();
   for (int i = 0; i < temp->getNumberParticles(); i++)
   {
      Particle *currentParticle = new Particle((temp->get(i))->getX(), (temp->get(i))->getY(), (temp->get(i))->getOrientation());
      origin->add_back(currentParticle);
   }
   temp->clear();
}
//shifts particle's obs to match the orientation
Grid ParticleFilter::lookStraightAhead(Grid grid, Orientation orient)
{

   if (orient == ORIEN_LEFT)
   {
      rotatePartGrid(grid);
   }
   else if (orient == ORIEN_DOWN)
   {
      rotatePartGrid(grid);
      rotatePartGrid(grid);
   }
   else if (orient == ORIEN_RIGHT)
   {
      rotatePartGrid(grid);
      rotatePartGrid(grid);
      rotatePartGrid(grid);
   }
   return grid;
}
//rotates 3 x 3 grid counter-clockwise
void ParticleFilter::rotatePartGrid(Grid &grid)
{
   Grid newGrid = make_grid(OBS_SIZE, OBS_SIZE);
   newGrid[0][0] = grid[2][0];
   newGrid[0][1] = grid[1][0];
   newGrid[0][2] = grid[0][0];
   newGrid[1][0] = grid[2][1];
   newGrid[1][2] = grid[0][1];
   newGrid[2][0] = grid[2][2];
   newGrid[2][1] = grid[1][2];
   newGrid[2][2] = grid[0][2];
   for (int i = 0; i < OBS_SIZE; i++)
   {
      for (int j = 0; j < OBS_SIZE; j++)
      {
         grid[i][j] = newGrid[i][j];
      }
   }
}

//compares the observation and the possible particle's surrounding observations
//adds the particle to the temp list if it matches
void ParticleFilter::directCompareandAdd(Grid observation, Grid tempParticleGrid, Particle *thisParticle, ParticleList *pL1)
{
   bool equal = true;
   for (int j = 0; j < OBS_SIZE; j++)
   {
      for (int k = 0; k < OBS_SIZE; k++)
      {
         if (observation[j][k] != tempParticleGrid[j][k])
         {
            if (observation[j][k] != '^' && observation[j][k] != '<' && observation[j][k] != '>' && observation[j][k] != 'v' && observation[j][k] != '*')
            {
               equal = false;
            }
         }
      }
   }
   if (equal)
   {
      pL1->add_back(thisParticle);
   }
}

//forM3: compares and rotates
void ParticleFilter::compareAndRotate(Grid obs, Grid partGrid, Particle *currentp, ParticleList *pL1)
{
   //count verifies the possible direection of the particle (anti-clockwise rotation)
   int count = ORIEN_UP;
   for (int k = 0; k < 4; k++)
   {

      bool equal = true;
      for (int i = 0; i < OBS_SIZE; i++)
      {
         for (int j = 0; j < OBS_SIZE; j++)
         {
            if (obs[i][j] != partGrid[i][j])
            {
               if (obs[i][j] != '*')
               {
                  equal = false;
               }
            }
         }
      }

      if (equal)
      {
         if (firstObs == false)
         {

            pL1->add_back(new Particle(currentp->getX(), currentp->getY(), count));
         }
         else
         {
            //adds particle if rotation is not 180 degrees
            if ((currentp->getOrientation() - count + 1) / (currentp->getOrientation() - count + 1) != 1)
            {
               pL1->add_back(new Particle(currentp->getX(), currentp->getY(), count));
            }
            else
            {
               std::cout << "cannot add particle: 180 degrees rotation" << std::endl;
            }
         }
      }
      rotatePartGrid(partGrid);
      if (count == 0)
      {
         count = 3;
      }
      else
      {
         count--;
      }
   }
}

// Return a DEEP COPY of the ParticleList of all particles representing
//    the current possible locations of the robot
ParticleList *ParticleFilter::getParticles()
{
   ParticleList *copy = new ParticleList;
   for (int i = 0; i < pL->getNumberParticles(); i++)
   {
      Particle *currentParticle = new Particle((pL->get(i))->getX(), (pL->get(i))->getY(), (pL->get(i))->getOrientation());
      copy->add_back(currentParticle);
   }
   return copy;
}
