
#include "Particle.h"

Particle::Particle(int x, int y, int o) {
   x_cord = x;
   y_cord = y;
   orient = o;
}

// x-co-ordinate of the particle
int Particle::getX() {
   return x_cord;
}

// y-co-ordinate of the particle
int Particle::getY() {
   return y_cord;
}

// Orientation of the particle
Orientation Particle::getOrientation() {
   return orient;
}
