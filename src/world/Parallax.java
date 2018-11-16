package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import game.Game;
import game.Screen;
import gfx.Loader;
import gfx.SpriteSheet;
import runtime.GameHandler;

public class Parallax {

  private static final int FILTER_A = -8454017;
  private static final int FILTER_B = -65281;
  
  private BufferedImage image;
  private GameHandler handler;
  private double shift;
  
  public Parallax(String path, double shift, GameHandler handler) {
    this.shift = shift;
    this.handler = handler;
    
    BufferedImage imageBuffer;
    // import the parallax image
      imageBuffer = Loader.loadImage("/world/maps/" + path);
      image = new BufferedImage(imageBuffer.getWidth(), imageBuffer.getHeight(), BufferedImage.TYPE_INT_ARGB);
      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          if(imageBuffer.getRGB(x, y) != FILTER_A && imageBuffer.getRGB(x, y) != FILTER_B) {
            image.setRGB(x, y, imageBuffer.getRGB(x, y));
          }
        }
      }
    }
  
  public void render(Graphics g) {
    BufferedImage b = image.getSubimage((int)(handler.getState().getCamera().xOffset()*shift/Game.GAME_SCALE), 
        (int)(handler.getState().getCamera().yOffset()/Game.GAME_SCALE),
        (int)(Screen.screen.getWidth()/Game.GAME_SCALE), 
        (int)(Screen.screen.getHeight()/Game.GAME_SCALE));
    g.drawImage(b, 0,0,
        (int)(b.getWidth() * Game.GAME_SCALE), 
        (int)(b.getHeight() * Game.GAME_SCALE), null);
  }
}
