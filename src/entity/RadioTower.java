package entity;

import java.awt.Graphics;
import game.Assets;
import runtime.GameHandler;
import gfx.Sprite;

public class RadioTower extends Interactable {

	private Sprite radioTower = Assets.radioTower;
	private int ID;
	
	public RadioTower(int x, int y, GameHandler handler, int ID) {
		super(x, y, handler);
		this.initHitbox(0, 0, 64, 96, handler);		
		this.ID = ID;
	}

	@Override
	public void onInteract(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		radioTower.render(x - (int)handler.getState().getCamera().xOffset(), y- 
	    		(int)handler.getState().getCamera().yOffset(), g);
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
