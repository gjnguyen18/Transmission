package utility;

import entity.Mob;

public class VectorHandler {

  Mob m;

  static double g = -9.8;
  
  private double xAcceleration;
  private double xVelocity;
  private double yAcceleration;
  private double yVelocity;

  private double x;
  private double y;
  
  private boolean grounded = false;

  public VectorHandler(Mob m) {
    this.m = m;

    xAcceleration = 0;
    xVelocity = 0;

    yAcceleration = 0;
    yVelocity = 0;

    x = m.getX();
    y = m.getY();

  }



  public void update() {
	  
	x = m.getX();
	y = m.getY();
 
    xVelocity += (xAcceleration) / 60; 
    yVelocity -= (yAcceleration + g) / 60;

      if (!m.getHitbox().tileXCollide((int)xVelocity)) {
        x += xVelocity;
      } else {
        xVelocity = 0;
      }
      
      if (!m.getHitbox().tileYCollide((int)yVelocity)) {
        grounded = false;
        y += yVelocity;
        xAcceleration = 0;
      } else {
        grounded = true;
        yVelocity = 0;
      }
    
    m.setX((int)x);
    m.setY((int)y);
    
  }

  public void resetMomentum() {
    xVelocity = 0;
    yVelocity = 0;
  }

  public void resetMovement() {
    xAcceleration = 0;
    yAcceleration = 0;
    resetMomentum();
  }

  // Getters and Setters
  public double getyTotalAccel() {
    return yAcceleration + g;
  }
  
  
  public double getxAcceleration() {
    return xAcceleration;
  }

  public void setxAcceleration(double xAcceleration) {
    this.xAcceleration = xAcceleration;
  }

  public double getxVelocity() {
    return xVelocity;
  }

  public void setxVelocity(double xVelocity) {
    this.xVelocity = xVelocity;
  }

  public double getyAcceleration() {
    return yAcceleration;
  }

  public void setyAcceleration(double yAcceleration) {
    this.yAcceleration = yAcceleration;
  }

  public double getyVelocity() {
    return -yVelocity;
  }

  public void setyVelocity(double yVelocity) {
    this.yVelocity = -yVelocity;
  }
  
  public static void setGravity(double gravity) {
    g = gravity;
  }
  
  public boolean isGrounded() {
    return grounded;
  }

}
