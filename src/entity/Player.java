package entity;

import java.awt.Graphics;
import java.util.ArrayList;
import game.Assets;
import gfx.Sprite;
import runtime.GameHandler;

public class Player extends Mob {

  private Sprite walk = Assets.player_walk;
  private Sprite still = Assets.player_still;
  private Sprite warp = Assets.player_warp;
  private Sprite warpBack = Assets.player_warpback;

  private int updateCount = 0;
  private ArrayList<Integer> stationsEarned = new ArrayList<Integer>();
  private int currentStation = -1;
  private int currentStationIndex = -1;

  private boolean updatingWarp = false;
  private boolean finishingWarp = false;

  private Sprite stam = Assets.stamina;
  private Sprite stamContainer = Assets.staminaContainer;
  private Sprite tuner = Assets.radioTuner;

  private Sprite interactableThing = Assets.interactableNear;

  private static Sprite station0 = Assets.station0;
  private static Sprite station1 = Assets.station1;
  private static Sprite station2 = Assets.station2;
  private static Sprite station3 = Assets.station3;
  private static Sprite station4 = Assets.station4;
  private static Sprite station5 = Assets.station5;
  private static Sprite station6 = Assets.station6;
  /*
   * private static Sprite station7 = Assets.station7; private static Sprite station8 =
   * Assets.station8; private static Sprite station9 = Assets.station9;
   */
  private static Sprite noStation = Assets.noStation;
  private int tunedStation;

  private int stamina;
  private boolean exhausted;
  private boolean tunerOn;
  private boolean tunerRel = true;
  private boolean qRel = true;
  private boolean eRel = true;
  private boolean fRel = true;
  private boolean nRel = true;
  private boolean cRel = true;

  private boolean firstTune = true;
  private boolean firstQ = true;
  private boolean firstE = true;
  private boolean firstC = true;
  
  private boolean alertOn0 = false;
  private boolean alertOn1 = false;
  private boolean alertOn2 = false;
  private boolean alertOn3 = false;
  private boolean alertOn4 = false;
  private boolean alertOn5 = false;
  private boolean alertOn6 = false;
  /*
   * private boolean alertOn7 = false; private boolean alertOn8 = false; private boolean alertOn9 =
   * false;
   */

  private boolean completed0 = false;
  private boolean completed1 = false;
  private boolean completed2 = false;
  private boolean completed3 = false;
  private boolean completed4 = false;
  private boolean completed5 = false;
  private boolean completed6 = false;

  private ThrownRadio personalRadio;
  private boolean radioThrown = false;
  /*
   * private boolean completed7 = false; private boolean completed8 = false; private boolean
   * completed9 = false;
   */

  // private int stationNum = 0;
  // private boolean puzzleOpen = false;

  private boolean gotRadio = false;
  private boolean jumpingForward = false;

  public Player(int x, int y, GameHandler handler) {
    super(x, y, handler);
    this.initHitbox(12, 3, 9, 29, handler);
    this.activeSprite = still;
    stamina = 180;
    exhausted = false;
  }

  @Override
  public void render(Graphics g) {
    if (radioThrown)
      personalRadio.render(g);
    activeSprite.render((int) (x - handler.getState().getCamera().xOffset()),
        (int) (y - handler.getState().getCamera().yOffset()), g);
    renderHud(g);

  }

  @Override
  public void update() {
    if (handler.getLevel() == 1 && !completed0) {
      completed0 = true;
      stationsEarned.add(0);
    }
    if (handler.getLevel() == 2 && !completed1) {
      // System.out.println(handler.getLevel());
      completed1 = true;
      stationsEarned.add(1);
      // System.out.println(stationsEarned.get(1));
    }
    if (handler.getLevel() == 3 && !completed2) {
      completed2 = true;
      stationsEarned.add(2);
    }
    if (handler.getLevel() == 4 && !completed3) {
      completed3 = true;
      stationsEarned.add(3);
    }
    if (handler.getLevel() == 5 && !completed4) {
      completed4 = true;
      stationsEarned.add(4);
    }
    if (handler.getLevel() == 6 && !completed5) {
      completed5 = true;
      stationsEarned.add(5);
    }
    if (handler.getLevel() == 7 && !completed6) {
      completed6 = true;
      stationsEarned.add(6);
    }
    /*
     * if(handler.getLevel() == 8 && !completed7) { completed7 = true; stationsEarned.add(7); }
     * if(handler.getLevel() == 9 && !completed8) { completed8 = true; stationsEarned.add(8); }
     * if(handler.getLevel() == 10 && !completed9) { completed9 = true; stationsEarned.add(9); }
     */
    // System.out.println(getX());
    if (stationsEarned.size() == 1) {
      currentStationIndex = 0;
      currentStation = stationsEarned.get(0);
    }
    // for(int)
    // System.out.println(stationsEarned.size());
    /*
     * if(getX() > 1920 && getX() < 1960) { openAlert(); } else { alertOn = false; }
     */
    hitbox.update(x, y);
    move();
    if (updatingWarp) {
      updateCount++;
    } else if (finishingWarp) {
      updateCount--;
    }
    input();
    vector.update();
    if (radioThrown)
      personalRadio.update();
  }

  @Override
  public void move() {

    if (handler.getKeys().right || handler.getKeys().left) {
      activeSprite = walk;
      if (handler.getKeys().right) {
        reflectSprites(1);
        if (handler.getKeys().shift && !exhausted) {
          vector.setxAcceleration(5);
          stamina--;
        } else {
          if (vector.getxVelocity() < 4)
            vector.setxVelocity(4);
          else {
            vector.setxVelocity(vector.getxVelocity() / 1.02);
            vector.setxAcceleration(0);
          }
          stamina++;
        }
      }
      if (handler.getKeys().left) {
        reflectSprites(-1);
        if (handler.getKeys().shift && !exhausted) {
          vector.setxAcceleration(-5);
          stamina--;
        } else {
          if (vector.getxVelocity() > -4) {
            vector.setxVelocity(-4);
          } else {
            vector.setxVelocity(vector.getxVelocity() / 1.02);
            vector.setxAcceleration(0);
          }
          stamina++;
        }
      }
    } else {
      stamina++;
      activeSprite = still;
      if (vector.isGrounded())
        vector.setxVelocity(0);
      vector.setxAcceleration(0);
    }
    // jump
    if (handler.getKeys().space && vector.isGrounded()) {
      this.vector.setyVelocity(4);
    }

    // stamina management
    if (stamina <= 0) {
      exhausted = true;
    }
    if (stamina >= 180) {
      exhausted = false;
      stamina = 180;
    }

  }

  private void input() {
    /*
     * 
     * if (handler.getKeys().z) { Game.GAME_SCALE += 0.05; }
     * 
     * if (handler.getKeys().x) { Game.GAME_SCALE -= 0.05; }
     * 
     * if (handler.getKeys().c) { Game.GAME_SCALE = 4; }
     * 
     */


    if (tunerOn) {
      if (handler.getKeys().c) {
       if(firstC && cRel)
       {
         if (gotRadio) {
           if (radioThrown) {
             jumpingForward = true;
             if (!updatingWarp & !finishingWarp && jumpingForward) {
               if (handler.getKeys().c) {
                 updatingWarp = true;
               }
             }
           } else {
             personalRadio = new ThrownRadio(this.x, this.y, handler);
             radioThrown = true;
             // System.out.println(jumpingForward);
           }
         }
         firstC = false;
       }
       else if(cRel)
       {
         if (gotRadio) {
           if (radioThrown) {
             jumpingForward = true;
             if (!updatingWarp & !finishingWarp && jumpingForward) {
               if (handler.getKeys().c) {
                 updatingWarp = true;
               }
             }
           } else {
             System.out.println("radio thrown!");
             personalRadio = new ThrownRadio(this.x, this.y, handler);
             radioThrown = true;
             // System.out.println(jumpingForward);
           }
         }
       }
      }

      cRel = handler.getKeys().cRel;

      if (updateCount > 0 && updateCount < 27 && updatingWarp) {
        activeSprite = warp;
      } else if (updateCount == 27) {
        updatingWarp = false;
        finishingWarp = true;
        if (currentStation == 0 && !jumpingForward) {
          setX(1088);
          setY(2561);
        } else if (currentStation == 1 && !jumpingForward) {
          setX(1655);
          setY(2561);
        } else if (currentStation == 2 && !jumpingForward) {
          setX(2814);
          setY(2660);
        } else if (currentStation == 3 && !jumpingForward) {
          setX(3912);
          setY(2628);
        } else if (currentStation == 4 && !jumpingForward) {
          setX(4876);
          setY(2464);
        } else if (currentStation == 5 && !jumpingForward) {
          setX(6688);
          setY(3450);
        } /*
           * else if (currentStation == 6) { setX(9079); setY(2508); } else if (currentStation == 7)
           * { setX(9650); setY(3716); } else if (currentStation == 8) { setX(11074); setY(3712); }
           */ else if (currentStation == 6 && !jumpingForward) {
          setX(11126);
          setY(2756);
        }
        if (jumpingForward && radioThrown) {
          setX(personalRadio.getX());
          setY(personalRadio.getY() - 96);
          this.personalRadio = null;
          radioThrown = false;
          jumpingForward = false;
        }
      }
      if (updateCount > 0 && updateCount < 27 && finishingWarp) {
        activeSprite = warpBack;
      } else if (updateCount == 0 && finishingWarp) {
        finishingWarp = false;
      }
      if (handler.getKeys().q) {
        if (firstQ) {
          /*
           * System.out.println(currentStation); System.out.println(stationsEarned.get(0));
           */
          // System.out.println(currentStationIndex);
          if (currentStationIndex != -1) {
            if (currentStation != stationsEarned.get(0)) {
              currentStation = stationsEarned.get(currentStationIndex - 1);
              currentStationIndex--;
            }
          }
          firstQ = false;
        }
        if (qRel) {
          // System.out.println(currentStationIndex);
          /*
           * System.out.println(currentStation); System.out.println(stationsEarned.get(0));
           */
          if (currentStationIndex != -1) {
            if (currentStation != stationsEarned.get(0)) {
              currentStation = stationsEarned.get(currentStationIndex - 1);
              currentStationIndex--;
            }
          }
        }
      }
      qRel = handler.getKeys().qRel;
      if (handler.getKeys().e) {
        /*
         * System.out.println("X: "+getX()); System.out.println("Y: "+getY());
         */
        // System.out.println(currentStation);
        if (firstE) {
          // System.out.println(currentStationIndex);
          if (currentStationIndex != stationsEarned.size() - 1 && currentStationIndex != -1) {
            currentStation = stationsEarned.get(currentStationIndex + 1);
            currentStationIndex++;
          }
          firstE = false;
        }
        if (eRel) {
          // System.out.println(currentStationIndex);
          /*
           * System.out.println(stationsEarned.get(0)); System.out.println(stationsEarned.get(1));
           */
          if (currentStationIndex != stationsEarned.size() - 1 && currentStationIndex != -1) {
            currentStation = stationsEarned.get(currentStationIndex + 1);
            currentStationIndex++;
          }
        }
      }
      eRel = handler.getKeys().eRel;
      if (!updatingWarp & !finishingWarp && !jumpingForward) {
        if (handler.getKeys().f) {
          updatingWarp = true;
        }
        fRel = handler.getKeys().fRel;
      }

    }

    if (handler.getKeys().t) {
      openTuner();
      // System.out.println(getX());
      // System.out.println(getY());
    }
    tunerRel = handler.getKeys().tRel;
    if (alertOn0) {
      if (handler.getKeys().f) {
        if (!completed0)
          handler.setState(1);
        // openPuzzle();

      }
      fRel = handler.getKeys().fRel;
    }
    if (alertOn1) {
      if (handler.getKeys().f) {
        if (!completed1)
          handler.setState(1);
        // openPuzzle();
      }
      fRel = handler.getKeys().fRel;
    }
    if (alertOn2) {
      if (handler.getKeys().f) {
        if (!completed2)
          handler.setState(1);
        // openPuzzle();
      }
      fRel = handler.getKeys().fRel;
    }
    if (alertOn3) {
      if (handler.getKeys().f) {
        if (!completed3)
          handler.setState(1);
        // openPuzzle();
      }
      fRel = handler.getKeys().fRel;
    }
    if (alertOn4) {
      if (handler.getKeys().f) {
        if (!completed4)
          handler.setState(1);
        // openPuzzle();
      }
      fRel = handler.getKeys().fRel;
    }
    if (alertOn5) {
      if (handler.getKeys().f) {
        if (!completed5)
          handler.setState(1);
        // openPuzzle();
      }
      fRel = handler.getKeys().fRel;
    }
    if (alertOn6) {
      if (handler.getKeys().f) {
        if (!completed6)
          handler.setState(1);
        // openPuzzle();
      }
      fRel = handler.getKeys().fRel;
    }

    /*
     * if (alertOn7) { if (handler.getKeys().f) { handler.setState(1); // openPuzzle(); } fRel =
     * handler.getKeys().fRel; } if (alertOn8) { if (handler.getKeys().f) { handler.setState(1); //
     * openPuzzle(); } fRel = handler.getKeys().fRel; } if (alertOn9) { if (handler.getKeys().f) {
     * handler.setState(1); // openPuzzle(); } fRel = handler.getKeys().fRel; }
     */
  }

  /*
   * private void openPuzzle() {
   * 
   * }
   */

  private void openTuner() {
    if (firstTune) {
      if (!(alertOn0 || alertOn1 || alertOn2 || alertOn3 || alertOn4 || alertOn5 || alertOn6)) {
        tunerOn = !tunerOn;
        firstTune = false;
      }

    }
    if (tunerRel) {
      if (!(alertOn0 || alertOn1 || alertOn2 || alertOn3 || alertOn4 || alertOn5 || alertOn6)) {
        tunerOn = !tunerOn;
      }
    }
  }

  protected void openAlert0() {
    if (!tunerOn) {
      if (!completed0)
        alertOn0 = true;
    }
  }

  protected void closeAlert0() {
    alertOn0 = false;
  }

  protected void openAlert1() {
    if (!tunerOn) {
      if (!completed1)
        alertOn1 = true;
    }
  }

  protected void closeAlert1() {
    alertOn1 = false;
  }

  protected void openAlert2() {
    if (!tunerOn) {
      if (!completed2)
        alertOn2 = true;
    }
  }

  protected void closeAlert2() {
    alertOn2 = false;
  }

  protected void openAlert3() {
    if (!tunerOn) {
      if (!completed3)
        alertOn3 = true;
    }
  }

  protected void closeAlert3() {
    alertOn3 = false;
  }

  protected void openAlert4() {
    if (!tunerOn) {
      if (!completed4)
        alertOn4 = true;
    }
  }

  protected void closeAlert4() {
    alertOn4 = false;
  }

  protected void openAlert5() {
    if (!tunerOn) {
      if (!completed5)
        alertOn5 = true;
    }
  }

  protected void closeAlert5() {
    alertOn5 = false;
  }

  protected void openAlert6() {
    if (!tunerOn) {
      if (!completed6)
        alertOn6 = true;
    }
  }

  protected void closeAlert6() {
    alertOn6 = false;
  }

  /*
   * protected void openAlert7() { if (!tunerOn) { alertOn7 = true; } }
   * 
   * protected void closeAlert7() { alertOn7 = false; }
   * 
   * protected void openAlert8() { if (!tunerOn) { alertOn8 = true; } }
   * 
   * protected void closeAlert8() { alertOn8 = false; }
   * 
   * protected void openAlert9() { if (!tunerOn) { alertOn9 = true; } }
   * 
   * protected void closeAlert9() { alertOn9 = false; }
   */

  protected void gotRadio() {
    gotRadio = true;
  }

  private void renderHud(Graphics g) {
    if ((alertOn0 || alertOn1 || alertOn2 || alertOn3 || alertOn4 || alertOn5 || alertOn6)
        && !tunerOn) {
      interactableThing.render((int) (x - handler.getState().getCamera().xOffset() + 60),
          (int) (y - handler.getState().getCamera().yOffset() - 30), g);
    }
    // Stamina rendering
    if (stamina < 180) {
      for (int i = 0; i < 5; i++) {
        stamContainer.render((int) (x - 18 - handler.getState().getCamera().xOffset()),
            (int) (y + 24 * (4 - i) - 5 - handler.getState().getCamera().yOffset()), g);
      }

      for (int i = 0; i < (int) Math.ceil(stamina / 36); i++) {
        stam.render((int) (x - 18 - handler.getState().getCamera().xOffset()),
            (int) (y + 24 * (4 - i) - 5 - handler.getState().getCamera().yOffset()), g);
      }
    }

    if (tunerOn
        && !(alertOn0 || alertOn1 || alertOn2 || alertOn3 || alertOn4 || alertOn5 || alertOn6)) {
      tuner.render((int) (x - handler.getState().getCamera().xOffset() + 4),
          (int) (y - handler.getState().getCamera().yOffset() - 125), g);
    }
    int yChange = 106;
    int xChange = 55;
    if (tunerOn
        && !(alertOn0 || alertOn1 || alertOn2 || alertOn3 || alertOn4 || alertOn5 || alertOn6)) {
      if (currentStation == 0) {
        if (completed0)
          station0.render((int) (x - handler.getState().getCamera().xOffset() + xChange),
              (int) (y - handler.getState().getCamera().yOffset() - yChange), g);
      }
      if (currentStation == 1) {
        if (completed1)
          station1.render((int) (x - handler.getState().getCamera().xOffset() + xChange),
              (int) (y - handler.getState().getCamera().yOffset() - yChange), g);
      }
      if (currentStation == 2) {
        if (completed2)
          station2.render((int) (x - handler.getState().getCamera().xOffset() + xChange),
              (int) (y - handler.getState().getCamera().yOffset() - yChange), g);
      }
      if (currentStation == 3) {
        if (completed3)
          station3.render((int) (x - handler.getState().getCamera().xOffset() + xChange),
              (int) (y - handler.getState().getCamera().yOffset() - yChange), g);
      }
      if (currentStation == 4) {
        if (completed4)
          station4.render((int) (x - handler.getState().getCamera().xOffset() + xChange),
              (int) (y - handler.getState().getCamera().yOffset() - yChange), g);
      }
      if (currentStation == 5) {
        if (completed5)
          station5.render((int) (x - handler.getState().getCamera().xOffset() + xChange),
              (int) (y - handler.getState().getCamera().yOffset() - yChange), g);
      }
      if (currentStation == 6) {
        if (completed6)
          station6.render((int) (x - handler.getState().getCamera().xOffset() + xChange),
              (int) (y - handler.getState().getCamera().yOffset() - yChange), g);
      }
      if (currentStation == -1) {
        noStation.render((int) (x - handler.getState().getCamera().xOffset() + xChange - 1),
            (int) (y - handler.getState().getCamera().yOffset() - yChange + 1), g);
      }

    }
  }

  private void reflectSprites(int direction) {
    walk.reflect(direction);
    still.reflect(direction);
    warp.reflect(direction);
    warpBack.reflect(direction);
  }

}
