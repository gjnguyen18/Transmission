package entity;

import java.awt.Graphics;

import game.Assets;
import runtime.GameHandler;
import runtime.WorldState;
import gfx.Sprite;

//Editing Stuff
public class RadioBox extends Interactable {

	private Sprite switchBox = Assets.switchBox;
	private int ID;
	
	public RadioBox(int x, int y, GameHandler handler, int ID) {
		super(x, y, handler);
		this.initHitbox(0, 0, 32, 32, handler);		
		this.ID = ID;
	}

	@Override
	public void onInteract(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		switchBox.render(x - (int)handler.getState().getCamera().xOffset(), y- 
	    		(int)handler.getState().getCamera().yOffset(), g);
		
	}

	@Override
	public void update() {
		hitbox.update(x, y);
		if (this.hitbox.contains(((WorldState) (handler.getState())).getWorld().getPlayer().getHitbox()))
		{
			if(ID == 0)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().openAlert0();
			}
			if(ID == 1)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().openAlert1();
			}
			if(ID == 2)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().openAlert2();
			}
			if(ID == 3)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().openAlert3();
			}
			if(ID == 4){
				((WorldState) (handler.getState())).getWorld().getPlayer().openAlert4();
			}
			if(ID == 5)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().openAlert5();
			}
			/*if(ID == 6)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().openAlert6();
			}
			if(ID == 7)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().openAlert7();
			}
			if(ID == 8)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().openAlert8();
			}*/
			if(ID == 6)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().openAlert6();
			}
		}
		else
		{
			if(ID == 0)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().closeAlert0();
			}
			if(ID == 1)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().closeAlert1();
			}
			if(ID == 2)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().closeAlert2();
			}
			if(ID == 3)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().closeAlert3();
			}
			if(ID == 4)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().closeAlert4();
			}
			if(ID == 5)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().closeAlert5();
			}
			/*if(ID == 6)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().closeAlert6();
			}
			if(ID == 7)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().closeAlert7();
			}
			if(ID == 8)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().closeAlert8();
			}*/
			if(ID == 6)
			{
				((WorldState) (handler.getState())).getWorld().getPlayer().closeAlert6();
			}
		}
	}
}
