package game;

public class Initialize {
  
  public static void main(String[] args) {
    
    Assets.init();
    Game g = new Game();
    g.start();
    
  }
  
}
