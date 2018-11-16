package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

  boolean[] keys;
  boolean[] keysReleased;
  public boolean up, down, left, right, shift, space, q, e, z, x, c, t, f, n, r, i, o, p;
  public boolean upRel, downRel, rightRel, shiftRel, spaceRel, qRel, eRel, tRel, fRel, nRel, cRel;
  
  public KeyHandler() {
    keys = new boolean[256];  
    keysReleased = new boolean[256];
    }
  
  @Override
  public void keyPressed(KeyEvent e) {
    keys[e.getKeyCode()] = true;
    keysReleased[e.getKeyCode()] = false;
  }
  
  public void update() {
    up = keys[KeyEvent.VK_W];
    down = keys[KeyEvent.VK_S];
    left = keys[KeyEvent.VK_A];
    right = keys[KeyEvent.VK_D];
    shift = keys[KeyEvent.VK_SHIFT];
    space = keys[KeyEvent.VK_SPACE];
    q = keys[KeyEvent.VK_Q];
    e = keys[KeyEvent.VK_E];
    z = keys[KeyEvent.VK_Z];
    x = keys[KeyEvent.VK_X];
    c = keys[KeyEvent.VK_C];
    t = keys[KeyEvent.VK_T];
    f = keys[KeyEvent.VK_F];
    n = keys[KeyEvent.VK_N];
    r = keys[KeyEvent.VK_R];
    i = keys[KeyEvent.VK_I];
    o = keys[KeyEvent.VK_O];
    p = keys[KeyEvent.VK_P];
    
    spaceRel = keysReleased[KeyEvent.VK_SPACE];
    tRel = keysReleased[KeyEvent.VK_T];
    qRel = keysReleased[KeyEvent.VK_Q];
    eRel = keysReleased[KeyEvent.VK_E];
    fRel = keysReleased[KeyEvent.VK_F];
    nRel = keysReleased[KeyEvent.VK_N];
    cRel = keysReleased[KeyEvent.VK_C];
    
  }

  @Override
  public void keyReleased(KeyEvent e) {
    keys[e.getKeyCode()] = false;
    keysReleased[e.getKeyCode()] = true;
    
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
    
  }

}
