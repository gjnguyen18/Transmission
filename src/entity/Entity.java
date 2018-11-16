package entity;

import java.awt.Graphics;
import game.Game;
import gfx.Sprite;
import runtime.GameHandler;
import utility.Hitbox;

public abstract class Entity {
  
  protected Sprite activeSprite;
  
  protected int x, y;
  protected int width, height;
  protected Hitbox hitbox;
  
  public Entity(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  protected void initHitbox(int xOff, int yOff, int width, int height, GameHandler handler) {
    hitbox = new Hitbox(x, y, (int)(xOff  * Game.GAME_SCALE), (int)(yOff  * Game.GAME_SCALE),
        (int)(width  * Game.GAME_SCALE), (int)(height  * Game.GAME_SCALE), handler);
  }
  
  public abstract void render(Graphics g);
  
  public abstract void update();

  //Getters and Setters
  
  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
  
  public Hitbox getHitbox() {
    return this.hitbox;
  }

}
