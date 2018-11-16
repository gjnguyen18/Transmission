/* Spritesheets are initialized with a path to the sheet
 * Sprites have 4 methods of initialization
 *      -For square still sprites, use (x, y, size, spritesheet)
 *      -For rectangular stills, use (x, y, width, height, spritesheet)
 *      -For animations
 */

package game;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import gfx.Loader;
import gfx.Sprite;
import gfx.SpriteSheet;

public class Assets {

  private static final String MOB = "/mob/";
  private static final String UI = "/ui/";
  private static final String WORLD = "/world/";
  
  //All spritesheets
  public static SpriteSheet s_player;
  public static SpriteSheet s_hud;
  public static SpriteSheet s_world;
  public static SpriteSheet s_waveHud;
  
  //All sprites
  public static Sprite player_still;
  public static Sprite player_walk;
  public static Sprite player_warp;
  public static Sprite player_warpback;
  
  public static Sprite stamina;
  public static Sprite staminaContainer;
  public static Sprite radioTuner;
  
  public static Sprite switchBox;
  public static Sprite radioTower;
  public static Sprite radio;
  public static Sprite interactableNear;
  
  public static Sprite button;
  public static Sprite buttonPressed;
  public static Sprite buttonHovered;
  public static Sprite squareButton;
  public static Sprite squareButtonPressed;
  public static Sprite squareButtonHovered;
  public static Sprite dial;
  
  public static Sprite add20;
  public static Sprite sub20;
  public static Sprite mul2;
  public static Sprite div2;
  public static Sprite add20Press;
  public static Sprite sub20Press;
  public static Sprite mul2Press;
  public static Sprite div2Press;
  
  public static Sprite station0;
  public static Sprite station1;
  public static Sprite station2;
  public static Sprite station3;
  public static Sprite station4;
  public static Sprite station5;
  public static Sprite station6;
  public static Sprite station7;
  public static Sprite station8;
  public static Sprite station9;
  public static Sprite noStation;
  
  public static Sprite grabRadio;
  public static Sprite throwRadio;
  
  public static Sprite waveHud;
  public static Sprite mod;
  public static Sprite modHover;
  public static Sprite modPress;
  
  public static Sprite unlocked;
  public static Sprite unlockedHover;
  public static Sprite unlockedPress;
  public static Sprite locked;
  public static Sprite lockedHover;
  public static Sprite lockedPress;
  public static Sprite swapAmp;
  public static Sprite swapAmpHover;
  public static Sprite swapAmpPress;
  public static Sprite swapFreq;
  public static Sprite swapFreqHover;
  public static Sprite swapFreqPress;
  public static Sprite red;
  public static Sprite redHover;
  public static Sprite redPress;
  public static Sprite blackRect;
  
  public static Sprite fpsCap;
  public static Sprite fpsCapHover;
  public static Sprite fpsCapPress;
  
  public static Clip songClip1;
  public static Clip songClip2;
  
  public static void init() {
    initSheet();
    initSprite();
    initFont();
    initSounds();
  }
  
  // initializes all of the spritesheets
  public static void initSheet() {
    s_player = new SpriteSheet(MOB + "player.png");
    s_hud = new SpriteSheet(UI + "hud.png");
    s_world = new SpriteSheet(WORLD + "worldObjects.png");
    s_waveHud = new SpriteSheet(UI + "waveHudmain.png");
  }
  // initializes all of the sprites based on the spritesheets
  public static void initSprite() {
    player_still = new Sprite(0, 0, 32, s_player);
    player_walk = new Sprite(3, 32, 10, 12, s_player, true);
    player_warp = new Sprite(4, 32, 10, 18, s_player, true);
    player_warpback = new Sprite(5, 32, 10, 18, s_player, true);
    
    
    stamina = new Sprite(8, 0, 8, s_hud);
    staminaContainer = new Sprite(0, 0, 8, s_hud);
    
    radioTuner = new Sprite(1, 32, 8, 24, s_hud, true);
    
    radio = new Sprite(1, 1, 32, s_world);
    radioTower = new Sprite(0, 0, 64, 96, s_world);
    switchBox = new Sprite(64, 0, 32, s_world);
    interactableNear = new Sprite(16, 0, 8, s_hud);
    
    station0 = new Sprite(8, 40, 8, s_hud);
    station1 = new Sprite(0, 8, 8, s_hud);
    station2 = new Sprite(8, 8, 8, s_hud);
    station3 = new Sprite(0, 16, 8, s_hud);
    station4 = new Sprite(8, 16, 8, s_hud);
    station5 = new Sprite(0, 24, 8, s_hud);
    station6 = new Sprite(8, 24, 8, s_hud);
    station7 = new Sprite(0, 32, 8, s_hud);
    station8 = new Sprite(8, 32, 8, s_hud);
    station9 = new Sprite(0, 40, 8, s_hud);
    noStation = new Sprite(16, 8, 8, s_hud);
    
    button = new Sprite(64, 96, 32, s_hud);
    buttonPressed = new Sprite(64, 32, 32, s_hud);
    buttonHovered = new Sprite(64, 64, 32, s_hud);
    squareButton = new Sprite(64, 96, 32, s_hud);
    squareButtonPressed = new Sprite(64, 128, 32, s_hud);
    squareButtonHovered = new Sprite(64, 160, 32, s_hud);
    dial = new Sprite(64, 0, 32, s_hud);
    
    add20 = new Sprite(0, 64, 32, s_hud);
    sub20 = new Sprite(0, 96, 32, s_hud);
    mul2 =  new Sprite(0, 128, 32, s_hud);
    div2 =  new Sprite(0, 160, 32, s_hud);
    add20Press = new Sprite(0, 192, 32, s_hud);
    sub20Press = new Sprite(0, 224, 32, s_hud);
    mul2Press =  new Sprite(0, 256, 32, s_hud);
    div2Press =  new Sprite(0, 288, 32, s_hud);
    grabRadio = new Sprite(3, 32, 12, 16, s_world, true);
    throwRadio = new Sprite(64, 64, 32, s_world);

  	waveHud = new Sprite(0, 0, 300, 200, s_waveHud);
  	mod = new Sprite(105, 130, 41, 26, s_waveHud);
  	modHover = new Sprite(155, 130, 41, 26, s_waveHud);
  	modPress = new Sprite(205, 130, 41, 26, s_waveHud);
  	
    unlocked = new Sprite(11, 54-4, 13+1, 11+1, s_waveHud);
    unlockedHover = new Sprite(11, 85-4, 13, 11, s_waveHud);
    unlockedPress = new Sprite(11, 116-4, 13, 11, s_waveHud);
    locked = new Sprite(11, 147-4, 13, 11, s_waveHud);
    lockedHover = new Sprite(11, 178-4, 13, 11, s_waveHud);
    lockedPress = new Sprite(105, 161, 13, 11, s_waveHud);
    
    swapAmp = new Sprite(0, 200, 140+1, 10+1, s_waveHud);
    swapAmpHover = new Sprite(0, 212, 140+1, 10+1, s_waveHud);
    swapAmpPress = new Sprite(0, 224, 140+1, 10+1, s_waveHud);
    swapFreq = new Sprite(0, 236, 140+1, 10+1, s_waveHud);
    swapFreqHover = new Sprite(0, 248, 140+1, 10+1, s_waveHud);
    swapFreqPress = new Sprite(0, 260, 140+1, 10+1, s_waveHud);
    
    red = new Sprite(260, 110, 30+1, 34+1, s_waveHud);
    redHover = new Sprite(300, 110, 30+1, 34+1, s_waveHud);
    redPress = new Sprite(332, 110, 30+1, 34+1, s_waveHud);
    blackRect = new Sprite(25,45,20,20, s_waveHud);
    
    fpsCap = new Sprite(0, 275, 22+1, 5+1, s_waveHud);
    fpsCapHover = new Sprite(0, 282, 22+1, 5+1, s_waveHud);
    fpsCapPress = new Sprite(0, 289, 22+1, 5+1, s_waveHud);
  }
  
  public static void initFont() { 
  	try {
      GraphicsEnvironment ge = 
          GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, 
      		Loader.loadStream("/fonts/DS-DIGI.TTF")));
		} catch (IOException|FontFormatException e) {
		     System.out.println("font not found");
		}
  }
  
  public static void initSounds() {
  	try{
	    songClip1 = AudioSystem.getClip();
	    songClip1.open(Loader.loadURLAudio("/sfx/sfx_radioCity.wav"));
    } catch(Exception e){System.out.println("failed");}
  	try{
	    songClip2 = AudioSystem.getClip();
	    songClip2.open(Loader.loadURLAudio("/sfx/sfx_transmission.wav"));
    } catch(Exception e){System.out.println("failed");}
  }
  
  public static void startMusic() {
  	songClip1.loop(1000000);
  }
}
