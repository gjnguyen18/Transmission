package entity;

import java.awt.Graphics;

import game.Assets;
import runtime.GameHandler;
import runtime.WorldState;
import gfx.Sprite;

public class ThrowableRadio extends Interactable {

	private Sprite radio = Assets.grabRadio;
	
	public ThrowableRadio(int x, int y, GameHandler handler) {
		super(x, y, handler);
		this.initHitbox(0, 0, 32, 32, handler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onInteract(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		radio.render(x - (int)handler.getState().getCamera().xOffset(), y- 
	    		(int)handler.getState().getCamera().yOffset(), g);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		hitbox.update(x, y);
		if (this.hitbox.contains(((WorldState) (handler.getState())).getWorld().getPlayer().getHitbox()))
		{
			((WorldState) (handler.getState())).getWorld().getPlayer().gotRadio();
			x = 0;
			y = 0;
		}
	}

}
