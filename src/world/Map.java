package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import game.Game;
import game.Screen;
import gfx.Loader;
import gfx.SpriteSheet;
import runtime.GameHandler;

public class Map {
  
  private static final int FILTER_A = -8454017;
  private static final int FILTER_B = -65281;
  public static final int COLLISION_MARK = -65536;
  private static final int COLLISION_FACTOR = 8;
  
  
  private BufferedImage colMap;
  private BufferedImage mapImage;
  private boolean[][] colArray;
  private ArrayList<Parallax> parallaxes;
  private GameHandler handler;
  
  public Map(String mapFolder, int parallaxCount, GameHandler handler) {
    BufferedImage imageBuffer;
    parallaxes = new ArrayList<Parallax>();
    this.handler = handler;
    
    
    // import the collision map

      imageBuffer = Loader.loadImage("/world/maps/" + mapFolder + "/collision.png");
      colMap = new BufferedImage(imageBuffer.getWidth(), imageBuffer.getHeight(), BufferedImage.TYPE_INT_ARGB);
      for (int x = 0; x < colMap.getWidth(); x++) {
        for (int y = 0; y < colMap.getHeight(); y++) {
          if(imageBuffer.getRGB(x, y) != FILTER_A && imageBuffer.getRGB(x, y) != FILTER_B) {
            colMap.setRGB(x, y, imageBuffer.getRGB(x, y));
          }
        }
      }
  
    // convert the collision map to an array of collisions
    colArray = new boolean[colMap.getWidth()][colMap.getHeight()];
    for (int x = 0; x < colMap.getWidth(); x++) {
      for (int y = 0; y < colMap.getHeight(); y++) {
        if(colMap.getRGB(x, y) == COLLISION_MARK) {
          colArray[x][y] = true; 
        } else {
          colArray[x][y] = false;
        }
      }
    }

    // import the map

    imageBuffer = Loader.loadImage("/world/maps/" + mapFolder + "/map.png");
    mapImage = new BufferedImage(imageBuffer.getWidth(), imageBuffer.getHeight(), BufferedImage.TYPE_INT_ARGB);
    for (int x = 0; x < mapImage.getWidth(); x++) {
      for (int y = 0; y < mapImage.getHeight(); y++) {
        if(imageBuffer.getRGB(x, y) != FILTER_A && imageBuffer.getRGB(x, y) != FILTER_B) {
          mapImage.setRGB(x, y, imageBuffer.getRGB(x, y));
        }
      }
    }
  
  for (int i = parallaxCount; i > 0; i--) {
    double shift = 1/(i+1.0);
    if (i == parallaxCount) shift = 0;
    parallaxes.add(new Parallax(mapFolder + "/plx" + (parallaxCount-i) +".png", shift, handler));
  }
  
  
  }
  
  
  public void render(Graphics g) {
    BufferedImage b = mapImage.getSubimage((int)(handler.getState().getCamera().xOffset()/Game.GAME_SCALE), 
        (int)(handler.getState().getCamera().yOffset()/Game.GAME_SCALE),
        (int)(Screen.screen.getWidth()/Game.GAME_SCALE), 
        (int)(Screen.screen.getHeight()/Game.GAME_SCALE));
    for (Parallax p : parallaxes) {
      p.render(g);
    }
    g.drawImage(b, 0, 0, (int)(b.getWidth() * Game.GAME_SCALE), (int)(b.getHeight() * Game.GAME_SCALE), null);
    
  }
  
  public boolean isSolid(int x, int y) {
    try {
    return colArray[x / (int)(Game.GAME_SCALE * COLLISION_FACTOR)][y / (int)(Game.GAME_SCALE * COLLISION_FACTOR)];
    } catch (Exception e) {
      return true;
    }
  }
  
  public int getWidth() {
    return (int)(mapImage.getWidth() * Game.GAME_SCALE);
  }
  
  public int getHeight() {
    return (int)(mapImage.getHeight() * Game.GAME_SCALE);
  }
}
