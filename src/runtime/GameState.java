package runtime;

import java.awt.Graphics;
import utility.Camera;

public abstract class GameState {
  
  protected GameHandler handler;  
  protected boolean running = false;
  protected Camera camera;
  
  public GameState(GameHandler handler) {
    this.handler = handler;
    camera = new Camera(handler);
  }
  
  public void pause() {
    running = false;
  }
  
  public void start() {
    running = true;
  }
  
  public abstract void render(Graphics g);
  
  public abstract void update();
  
  public Camera getCamera() {
    return camera;
  }
}
