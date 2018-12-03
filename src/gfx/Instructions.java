package gfx;

import java.awt.Font;
import java.awt.Graphics;

import game.Assets;
import game.Screen;
import input.Button;
import runtime.GameHandler;
import wavematcher.WaveMatcher;
 
/*
 * instructions updated
 */
public class Instructions {
	
	Button button;
	private boolean showing;
	private GameHandler handler;
	
	public Instructions(GameHandler h) {
		handler = h;
		button = new Button(h,1300, 5, Assets.button, Assets.buttonHovered, Assets.buttonPressed, "Help", 30, 2);
		showing = false;
	}
	
	public void displayInstructions(Graphics g) {
		g.setFont(new Font("DS-Digital", Font.PLAIN, 18)); 
		g.drawString("Use A and D to move left and right", 50, 100);
		g.drawString("Press Space to jump", 50, 120);
		g.drawString("Press T to open Dial", 50, 140);
		g.drawString("Press Q and E to switch between dials", 50, 160);
		g.drawString("Press F while not on station to teleport to next area", 50, 180);
	}
	
	public void displayInstructions2(Graphics g) {
		g.setFont(new Font("DS-Digital", Font.PLAIN, 16)); 
		g.drawString("Press to lock wave ->", 12, 400);
		g.drawString("Goal: Match waves", 800, 120);
		g.drawString("Press these to modify everything but the locked wave", 650, 820);
		g.drawString("Press this to change which wave property to modify", 550, 500);
	}
	public void render(Graphics g) {
		button.render(g);
		if(showing) {
			if(handler.getState() instanceof WaveMatcher)
				displayInstructions2(g);
			else
				displayInstructions(g);
		}
		g.setFont(new Font("DS-Digital", Font.PLAIN, 70)); 
	}
	
	public void update() {
		button.update();
		if(button.isButtonPressed()) {
			showing = !showing;
		}
	}
	
	public void setShowing(boolean a) {
		showing = a;
	}
}
