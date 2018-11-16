package runtime;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import game.Assets;
import game.Game;
import gfx.ScreenTint;
import input.Button;
import input.KeyHandler;
import input.MouseHandler;
import wavematcher.WaveMatcher;

public class GameHandler {

  private KeyHandler k;
  private MouseHandler m;
  private int waveLevel = 0;
  
  ArrayList<GameState> states;
  GameState state;
  ScreenTint tint;
  
  private Button specs;
  
  public GameHandler() {
    k = new KeyHandler();
    m = new MouseHandler();
    
    tint = new ScreenTint("/world/screenTint.png");
    specs = new Button(this, 20, 20, Assets.fpsCap, Assets.fpsCapHover, Assets.fpsCapPress,
    		"FPS CAP: 60", 16, 1);
    
    states = new ArrayList<GameState>();
    states.add(new WorldState(this));
    states.add(new WaveMatcher(this,waveLevel));
    //assign state to a starting gamestate
    state = states.get(0);
    state.start();
  }
  
  public void render(Graphics g) {
    state.render(g);
    tint.render(g);
    g.setColor(Color.WHITE);
    g.setFont(new Font("DS-Digital", Font.PLAIN, 24));
    specs.render(g);
  }
  
  public void update() {
    k.update();
    state.update();
    specs.update();
    if(specs.isButtonPressed()) {
    	if(Game.FPS==60) {
    		Game.FPS=30;
    	}
    	else
    		Game.FPS=60;
    	Game.delta = Game.NANO/Game.FPS;
    	specs.setString("FPS CAP: " + Game.FPS);
    }
  }
  
  //Getters and Setters
  
  public MouseHandler getMouse() {
    return m;
  }
  
  public KeyHandler getKeys() {
    return k;
  }
  
  public GameState getState() {
    return state;
  }
  
  public void setState(int index) {
    for(GameState i:states) {
    	i.pause();
    }
    state = states.get(index);
    state.start();
    Assets.songClip1.stop();
    Assets.songClip2.stop();
    if(index==0) {
    	Assets.songClip1.loop(1000000);
    }
    else {
    	Assets.songClip2.loop(1000000);
  	}
  }
  
  //only called in WaveMatcher class
  public void setWaveLevel(int l) {
  	waveLevel = l;
  	states.add(new WaveMatcher(this,waveLevel));
  	this.setState(0);
  	states.get(1).pause();
  	states.remove(1);
  }
  public int getLevel() {
  	return waveLevel;
  }
}
