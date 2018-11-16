package gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet {

  BufferedImage b;
  static final int FILTER_A = -8454017;
  static final int FILTER_B = -65281;

  
  public SpriteSheet(String path) {

      BufferedImage imageBuffer = Loader.loadImage(path);
      b = new BufferedImage(imageBuffer.getWidth(), imageBuffer.getHeight(), BufferedImage.TYPE_INT_ARGB);
      for (int x = 0; x < b.getWidth(); x++) {
        for (int y = 0; y < b.getHeight(); y++) {
          if(imageBuffer.getRGB(x, y) != FILTER_A && imageBuffer.getRGB(x, y) != FILTER_B) {
            b.setRGB(x, y, imageBuffer.getRGB(x, y));
          }
        }
      }
    }
  
  public BufferedImage cut(int x, int y, int size) {
    return b.getSubimage(x, y, size, size);
  }
  
  public BufferedImage cut(int x, int y, int width, int height) {
    return b.getSubimage(x, y, width, height);
  }
  
  public BufferedImage reflect(BufferedImage b) {
    BufferedImage end = new BufferedImage(b.getWidth(), b.getHeight(), BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < b.getHeight(); y++) {
      for (int x = 0; x < b.getWidth(); x++) {
        end.setRGB(b.getWidth() - x - 1, y, b.getRGB(x, y));
      }
    }
    return end;
  }
  
}
