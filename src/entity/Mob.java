package entity;

import runtime.GameHandler;
import utility.VectorHandler;

public abstract class Mob extends Entity {

  protected GameHandler handler;
  protected VectorHandler vector;
  
  public Mob(int x, int y, GameHandler handler) {
    super(x, y);
    this.handler = handler;
    this.vector = new VectorHandler(this);
  }
  
  public abstract void move();

}
