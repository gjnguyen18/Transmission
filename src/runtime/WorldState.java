package runtime;

import java.awt.Graphics;
import world.World;

public class WorldState extends GameState{

  
  World world;
  
  public WorldState(GameHandler handler) {
    super(handler);
    world = new World(handler);
  }
  
  
  @Override
  public void render(Graphics g) {
    world.render(g);
    
  }

  @Override
  public void update() {
    if (this.running) {
    camera.centerOnEntity(world.getPlayer());
    world.update();
    }
  }

  //Getters and Setters
  
  public World getWorld() {
    return world;
  }
  
}
