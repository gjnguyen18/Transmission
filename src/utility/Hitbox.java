package utility;

import runtime.GameHandler;
import runtime.GameState;
import runtime.WorldState;

public class Hitbox {

  private int x, y, width, height, xOff, yOff;
  GameHandler handler;

  public Hitbox(int x, int y, int xOff, int yOff, int width, int height, GameHandler handler) {
    this.handler = handler;
    this.x = x;
    this.y = y;
    this.xOff = xOff;
    this.yOff = yOff;
    this.width = width;
    this. height = height;
  }

  public void update(int x, int y) {
    this.x = x + xOff;
    this.y = y + yOff;
  }

  public boolean contains(Hitbox h) {
    int[] thisBound = this.getBounds();
    int[] newBound = h.getBounds();
    
    if ((newBound[0] > thisBound[0] && newBound[0] < thisBound[1]) || 
        (newBound[1] >thisBound[0] && newBound[1] < thisBound[1])) {
      if((newBound[2] > thisBound[2] && newBound[2] < thisBound[3]) || 
        (newBound[3] >thisBound[2] && newBound[3] < thisBound[3]))
        return true;
    }
    return false;
  }
  
  public boolean containsMouse() {
    int mouseX = handler.getMouse().getMouseX();
    int mouseY = handler.getMouse().getMouseY();
    int[] bound = this.getBounds();
    
    if (mouseX > bound[0] && mouseX < bound[1] &&
        mouseY > bound[2] && mouseY < bound[3]) return true;
    
    return false;
  }

  public boolean tileXCollide(int xMove) {
    GameState currentState = handler.getState();
    if (currentState instanceof WorldState) {
      if (((WorldState)currentState).getWorld().getMap().isSolid(x + xMove, y) || 
          ((WorldState)currentState).getWorld().getMap().isSolid(x + xMove + width, y) ||
          ((WorldState)currentState).getWorld().getMap().isSolid(x + xMove, y + height -1) || 
          ((WorldState)currentState).getWorld().getMap().isSolid(x + xMove + width, y + height -1)) {
        return true;
      }
    }
    return false;
  }

  public boolean tileYCollide(int yMove) {
    GameState currentState = handler.getState();
    if (currentState instanceof WorldState) {
      if (((WorldState)currentState).getWorld().getMap().isSolid(x, y + yMove) || 
          ((WorldState)currentState).getWorld().getMap().isSolid(x, y + yMove + height) ||
          ((WorldState)currentState).getWorld().getMap().isSolid(x+width, y + yMove) || 
          ((WorldState)currentState).getWorld().getMap().isSolid(x+width, y + yMove + height)) {
        return true;
      }
    }
    return false;
}
  
  public int[] getBounds() {
    int[] bounds = {
        x + xOff,
        x + width + xOff,
        y + yOff,
        y + height + yOff
    };
    return bounds;
  }
  

}
