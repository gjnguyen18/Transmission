package input;

import java.awt.Font;
import java.awt.Graphics;

import game.Game;
import gfx.Sprite;
import runtime.GameHandler;
import utility.Hitbox;

/**
 * Button has 3 states:
 * 	Idle: mouse not over button
 * 	Hover: mouse is over button
 * 	Pressed: mouse is pressing button
 * Button will display different things based on which state it is in
 * 	Method: void displayButton(Graphics g)
 * Button can also be spammable
 * 	spammable means the button will keep returning true in isButtonPressed() while held down
 * 	-if false, button will only return true the first time isButtonPressed() is called and then will
 * 	 only return true again if button is released and presssed again
 */
public class Button {
  
	private Sprite button, hover, pressed, active;
	private int x,y;
	private GameHandler handler;
	private Hitbox hitbox;
	private boolean spammable = false;
	private boolean canPress = true;
	private String label;
	private int fontSize;
	private int shift;

	public Button(GameHandler handler, int x, int y, Sprite button, Sprite hover, Sprite pressed) {
	  this.handler = handler;
	  this.x = x;
	  this.y = y;
	  this.button = button;
	  this.hover = hover;
	  this.pressed = pressed;
	  
	  active = button;
	  
	  hitbox = new Hitbox(x, y, 0, 0, button.getFWidth(), button.getFHeight(), handler);
	}
	public Button(GameHandler handler, int x, int y, Sprite button, Sprite hover, Sprite pressed,
			String s, int fontSize, int shift) {
		this(handler,x,y,button,hover,pressed);
		this.label = s;
		this.fontSize = fontSize;
		this.shift = shift;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int a) {
		x=a;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int a) {
		y=a;
	}
	
	public boolean getSpammable() {
		return spammable;
	}
	public void setSpammable(boolean a) {
		spammable=a;
	}
	public Sprite getButtonImageIdle() {
		return button;
	}
	public void setButtonImageIdle(Sprite a) {
		button=a;
	}
	public Sprite getButtonImageHover() {
		return hover;
	}
	public void setButtonImageHover(Sprite a) {
		hover = a;
	}
	public Sprite getButtonImagePressed() {
		return pressed;
	}
	public void setButtonImagePressed(Sprite a) {
		pressed = a;
	}
	public void setString(String s) {
		label = s;
	}
	
	public void render(Graphics g) {
	  active.render(x, y, g);
	  g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, fontSize));
	  if(label!=null) {
		  if(active!=pressed)
		  	g.drawString(label, x+(int)(Game.GAME_SCALE*(shift+1+(button.getWidth()/2))-fontSize*label.length()*.23),
		  		y+(int)(Game.GAME_SCALE*(button.getHeight()/2))+(int)(fontSize*.4));
		  else
		  	g.drawString(label, x+(int)(Game.GAME_SCALE*(1+(button.getWidth()/2))-fontSize*label.length()*.23),
			  	y+(int)(Game.GAME_SCALE*(button.getHeight()/2))+(int)(fontSize*.4));
	  }
	}
	
	public void update() {
	     if(!isMouseOver())
	       active = button;
       else if(isMouseOver() && handler.getMouse().getLeft()) 
           active = pressed;
       else
           active = hover;
	}
	
	public boolean isMouseOver() {
		return hitbox.containsMouse();
	}
	
	public boolean isButtonPressed() {
		if(!spammable) {
			if(hitbox.containsMouse() && handler.getMouse().getLeft()) {
				if(canPress) {
					canPress=false;
					return true;
				}
				return false;
			}
			else {
				canPress=true;
				return false;
			}
		}
		else
			return (hitbox.containsMouse() && handler.getMouse().getLeft());
	}
}
