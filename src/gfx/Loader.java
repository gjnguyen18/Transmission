package gfx;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Loader {

  public static BufferedImage loadImage(String path) {
    try {
      return ImageIO.read(Loader.class.getResourceAsStream(path));
    } catch (Exception e) {
      return null;
    }
  }
  
  public static BufferedReader loadText(String path) {
    try {
      return new BufferedReader(new InputStreamReader(Loader.class.getResourceAsStream(path)));
    } catch (Exception e) {
      return null;
    }
  }
  
  public static InputStream loadStream(String path) {
    try {
      return Loader.class.getResourceAsStream(path);
    } catch (Exception e) {
      return null;
    }
  }
  
  public static AudioInputStream loadURLAudio(String path) {
    URL url  = Loader.class.getResource(path);
    try {
      return AudioSystem.getAudioInputStream(url);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    
  }
}
