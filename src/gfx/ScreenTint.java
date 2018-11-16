package gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import game.Game;

public class ScreenTint {
  
  private static final int FILTER_A = -8454017;
  private static final int FILTER_B = -65281;
  
  private BufferedImage image;

  public ScreenTint(String path) {
    BufferedImage imageBuffer;
    // import the 
      imageBuffer = Loader.loadImage(path);
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
    g.drawImage(image, 0, 0,
        (int)(image.getWidth() * Game.GAME_SCALE), 
        (int)(image.getHeight() * Game.GAME_SCALE), null);
  }
  
}
