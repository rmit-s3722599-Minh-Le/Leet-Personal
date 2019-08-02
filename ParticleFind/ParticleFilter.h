
#ifndef COSC_ASS_ONE_PARTICLE_FILTER
#define COSC_ASS_ONE_PARTICLE_FILTER

#include "Particle.h"
#include "ParticleList.h"
#include "Types.h"

#define MAZE_SIZE          100
#define OBS_SIZE           3

class ParticleFilter {
public:

   /*                                           */
   /* DO NOT MOFIFY ANY CODE IN THIS SECTION    */
   /*                                           */


   // Initialise a new particle filter with a given maze of size (x,y)
   ParticleFilter(Grid maze, int rows, int cols);

   // Clean-up the Particle Filter
   ~ParticleFilter();

   // A new observation of the robot, of size 3x3
   void newObservation(Grid observation);

   // Return a DEEP COPY of the ParticleList of all particles representing
   //    the current possible locations of the robot
   ParticleList* getParticles();


   /*                                           */
   /* YOU MAY ADD YOUR MODIFICATIONS HERE       */
   /*                                           */
   private: 
      Grid maze; 
      int rows;
      int cols;
      Orientation prevOrient;
      ParticleList *pL;
      Orientation getOrientation(Grid observation);
      Grid createGrid(Particle *p);
      Grid make_grid(const int rows, const int cols);
      Particle *newParticleFromMovement(Orientation orient, Particle *p);
      void pLDeepCopy(ParticleList* origin, ParticleList* temp);
      void rotatePartGrid(Grid &grid);
      void compareAndRotate(Grid obs, Grid partGrid, Particle *currentpart, ParticleList* pL1);
      bool firstObs;
      void outOfBoundsCheck(int x, int y);
      bool outOfBounds;
      Grid lookStraightAhead(Grid grid, Orientation orient);
      void directCompareandAdd(Grid observation, Grid tempParticleGrid, Particle* thisParticle, ParticleList* pL1);

};

#endif // COSC_ASS_ONE_PARTICLE_FILTER
