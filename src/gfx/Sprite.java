package gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import game.Game;

public class Sprite {
  
  private BufferedImage[] sprite;
  private BufferedImage[] left;
  private BufferedImage[] right;
  private BufferedImage activeSprite;
  
  
  private int width, height;
  private int fps;
  private int frames;
  private int currentFrame = 0;
  private int update = 0;
  
  private boolean anim;
  
  private Graphics draw;

  private int f_width;
  private int f_height;
  
  
  

  public Sprite(int x, int y, int size, SpriteSheet s) {
    this.fps = 0;
    this.frames = 0;
    this.anim = false;
    
    left = new BufferedImage[1];
    right = new BufferedImage[1];

    width = size;
    height = size;
    
    f_width = (int)(width * Game.GAME_SCALE);
    f_height = (int)(height * Game.GAME_SCALE);
    
    right[0] = new BufferedImage(f_width, f_height, BufferedImage.TYPE_INT_ARGB_PRE);
    draw = right[0].getGraphics();
    draw.drawImage(s.cut(x, y, size), 0, 0, f_width, f_height, null);
    draw.dispose();
    
    left[0] = new BufferedImage(f_width, f_height, BufferedImage.TYPE_INT_ARGB_PRE);
    draw = left[0].getGraphics();
    draw.drawImage(s.reflect(s.cut(x, y, size)), 0, 0, f_width, f_height, null);
    draw.dispose();
    
    sprite = right;
    activeSprite = sprite[0];
    }
  
  public Sprite(int x, int y, int width, int height, SpriteSheet s) {
    this.fps = 0;
    this.frames = 0;
    this.anim = false;
    
    left = new BufferedImage[1];
    right = new BufferedImage[1];
    
    this.width = width;
    this.height = height;
    
    f_width = (int)(width * Game.GAME_SCALE);
    f_height = (int)(height * Game.GAME_SCALE);
    
    right[0] = new BufferedImage(f_width, f_height, BufferedImage.TYPE_INT_ARGB_PRE);
    draw = right[0].getGraphics();
    draw.drawImage(s.cut(x, y, width, height), 0, 0, f_width, f_height, null);
    draw.dispose();
    
    left[0] = new BufferedImage(f_width, f_height, BufferedImage.TYPE_INT_ARGB_PRE);
    draw = left[0].getGraphics();
    draw.drawImage(s.reflect(s.cut(x, y, width, height)), 0, 0, f_width, f_height, null);
    draw.dispose();
    
    sprite = right;
    activeSprite = sprite[0];
    }
  
  public Sprite(int x, int y, int size, int frames, int fps, SpriteSheet s, boolean anim) {
    left = new BufferedImage[frames];
    right = new BufferedImage[frames];
    
    width = size;
    height = size;
    
    f_width = (int)(width * Game.GAME_SCALE);
    f_height = (int)(height * Game.GAME_SCALE);
    
    for (int i = 0; i < frames; i++) {
      right[i] = new BufferedImage(f_width, f_height, BufferedImage.TYPE_INT_ARGB_PRE);
      draw = right[i].getGraphics();
      draw.drawImage(s.cut(x, y * size * i, size), 0, 0, f_width, f_height, null);
      draw.dispose();
      
      left[i] = new BufferedImage(f_width, f_height, BufferedImage.TYPE_INT_ARGB_PRE);
      draw = left[i].getGraphics();
      draw.drawImage(s.reflect(s.cut(x, y * size * i, size)), 0, 0, f_width, f_height, null);
      draw.dispose();
      
    }
    
    sprite = right;
    activeSprite = sprite[0];
    
    this.fps = fps;
    this.frames = frames;
    this.anim = anim;
  }
  
  public Sprite(int column, int size, int frames, int fps, SpriteSheet s, boolean anim) {
    left = new BufferedImage[frames];
    right = new BufferedImage[frames];

    width = size;
    height = size;
    
    f_width = (int)(width * Game.GAME_SCALE);
    f_height = (int)(height * Game.GAME_SCALE);
    
    for (int i = 0; i < frames; i++) {
      right[i] = new BufferedImage(f_width, f_height, BufferedImage.TYPE_INT_ARGB_PRE);
      draw = right[i].getGraphics();
      draw.drawImage(s.cut(column * size, i * size, size), 0, 0, f_width, f_height, null);
      draw.dispose();
      
      left[i] = new BufferedImage(f_width, f_height, BufferedImage.TYPE_INT_ARGB_PRE);
      draw = left[i].getGraphics();
      draw.drawImage(s.reflect(s.cut(column * size, i * size, size)), 0, 0, f_width, f_height, null);
      draw.dispose();
    }
    
    sprite = right;
    activeSprite = sprite[0];
    
    this.fps = fps;
    this.frames = frames;
    this.anim = anim;
  }
  
  public void render(int x, int y, Graphics g) {

    f_width = (int)(width * Game.GAME_SCALE);
    f_height = (int)(height * Game.GAME_SCALE);
    
    if (anim) {
      if (update >= 60/fps) {
      activeSprite = sprite[currentFrame];
      update = 0;
      currentFrame++;
      if (currentFrame >= frames) currentFrame = 0;
      }
    } else {
      activeSprite = sprite[0];
    }
    g.drawImage(activeSprite, x, y, f_width, f_height, null);
    update++;
  }
  
  public void reflect(int direction) {
    if (direction == 1) sprite = right;
    if (direction == -1) sprite = left;
  }
  public int getWidth() {
  	return this.width;
  }
  public int getHeight() {
  	return this.height;
  }
  
  public int getFWidth() {
  	return this.f_width;
  }
  public int getFHeight() {
  	return this.f_height;
  }
  
  
}
