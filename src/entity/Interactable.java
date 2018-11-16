package entity;

import runtime.GameHandler;

public abstract class Interactable extends Entity  {

  protected GameHandler handler;
  
  public Interactable(int x, int y, GameHandler handler) {
    super(x, y);
    this.handler = handler;
    
  }

  public abstract void onInteract(Entity e);
  
}
