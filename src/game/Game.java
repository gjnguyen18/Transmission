package game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import runtime.GameHandler;

public class Game implements Runnable {

  public static double GAME_SCALE = 4;
  
  private Screen screen;
  private boolean running;
  
  BufferStrategy bs;
  Graphics g;
  
  Thread t;
  
  public static int FPS = 60;
  
  GameHandler handler;
  public static final long NANO = 1000000000;
  public static long delta = NANO / FPS;
  private long songPlayedTime = System.nanoTime();
  
  public Game() {
    screen = new Screen();
    handler = new GameHandler();
    screen.setListeners(handler.getMouse(), handler.getKeys());
    Assets.startMusic();
  }
  
  public void run() {

    long currentTime = System.nanoTime();
    long lastTime = currentTime;

    long fpsTimer = currentTime;
    int fps = 0;

    while (running) {
      currentTime = System.nanoTime();

      if (currentTime - lastTime >= delta) {
        lastTime = currentTime;
        fps++;

        for(int i=0; i<60/FPS; i++ ) {
        	update();
        }
        render();

        if (currentTime - fpsTimer >= NANO) {
          fpsTimer = currentTime;
          System.out.println("FPS: " + fps);
          fps = 0;
        }
      }
    }
  }
  
  public synchronized void start() {
    running = true;
    t = new Thread(this);
    t.start();
  }
  
  public synchronized void stop() {
    running = false;
    try {
      t.join();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public void render() {
    //BufferStrategy setup for drawing smoothly
    bs = screen.getPanel().getBufferStrategy();
    if(bs == null){
        screen.getPanel().createBufferStrategy(3);
        return;
    }
    g = bs.getDrawGraphics();
    //

    // Clear previous screen then draw updated info
    g.clearRect(0, 0, screen.getPanel().getWidth(), screen.getPanel().getHeight());
    handler.render(g);
    //
    
    // displays the prepared BufferStrategy then dumps the previous graphics object to free up data
    bs.show();
    g.dispose();
    // 
  }
  
  public void update() {
    handler.update();
  }
  
  private static void setFpsCap(int a) {
  	FPS = a;
  }
}
