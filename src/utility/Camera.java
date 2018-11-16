package utility;

import entity.Entity;
import game.Screen;
import runtime.GameHandler;
import runtime.WorldState;

public class Camera {

  private static final int WIDTH = (int) Screen.screen.getWidth();
  private static final int HEIGHT = (int) Screen.screen.getHeight();
  
  private float xOffset, yOffset;
  private GameHandler handler;
  
  public Camera(GameHandler handler) { 
   this.xOffset = 0;
   this.yOffset = 0;
   
   this.handler = handler;
  }
  
  public void centerOnEntity(Entity e) {
    xOffset = e.getX() - WIDTH/2;

    if (xOffset > ((WorldState)handler.getState()).getWorld().getMap().getWidth() - WIDTH) 
      xOffset = ((WorldState)handler.getState()).getWorld().getMap().getWidth() - WIDTH;
    if (xOffset < 0) xOffset = 0;
    yOffset = e.getY() - HEIGHT/2;
    if (yOffset > ((WorldState)handler.getState()).getWorld().getMap().getHeight() - HEIGHT) 
      yOffset = ((WorldState)handler.getState()).getWorld().getMap().getHeight() - HEIGHT;
    if (yOffset < 0) yOffset = 0;
  }
  
  public float xOffset() {
    return xOffset;
  }
  
  public float yOffset() {
    return yOffset;
  }
  
}
