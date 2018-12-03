package gfx;

import java.awt.Font;
import java.awt.Graphics;

import game.Assets;
import game.Screen;
import input.Button;
import runtime.GameHandler;

public class Instructions {
	
	Button button;
	private boolean showing;
	
	public Instructions(GameHandler h) {
		button = new Button(h, (int)Screen.screen.getWidth()-200, 10, Assets.button, Assets.buttonHovered, Assets.buttonPressed);
		showing = false;
	}
	
	public void displayInstructions(Graphics g) {
		g.setFont(new Font("DS-Digital", Font.PLAIN, 16)); 
		g.drawString("Press to lock wave ->", 12, 400);
		g.drawString("Goal: Match waves", 550, 50);
		g.drawString("Press these to modify everything but the locked wave", 550, 800);
		g.drawString("Press this to change which wave property to modify", 550, 500);
	}
	
	public void render(Graphics g) {
		button.render(g);
		if(showing)
			displayInstructions(g);
	}
	
	public void update() {
		button.update();
		if(button.isButtonPressed()) {
			showing = !showing;
		}
	}
}
