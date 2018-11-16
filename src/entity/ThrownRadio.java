package entity;

import java.awt.Graphics;
import game.Assets;
import runtime.GameHandler;


public class ThrownRadio extends Mob {

  public ThrownRadio(int x, int y, GameHandler handler) {
    super(x, y, handler);
    this.activeSprite = Assets.throwRadio;
    this.initHitbox(0, 0, 10, 10, handler);
    this.vector.setxVelocity((handler.getMouse().getMouseX() - (this.x- handler.getState().getCamera().xOffset())) / 50);
    this.vector.setyVelocity(((this.y - handler.getState().getCamera().yOffset()) - handler.getMouse().getMouseY()) / 50);
  }

  @Override
  public void move() {
    this.vector.update();
    if (vector.isGrounded()) vector.setxVelocity(0);
  }

  @Override
  public void render(Graphics g) {
    activeSprite.render((int)(x- handler.getState().getCamera().xOffset()), (int)(y- handler.getState().getCamera().yOffset()), g);
    
  }

  @Override
  public void update() {
    move();
    this.hitbox.update(x, y);
    
  }

}
