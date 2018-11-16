package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener{
  
  private boolean left, right;
  private int x, y;

  public MouseHandler() {
    
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
    
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON1) {
      left = true;
    }
    
    if (e.getButton() == MouseEvent.BUTTON3) {
      right = true;
    }
    
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON1) {
      left = false;
    }
    
    if (e.getButton() == MouseEvent.BUTTON3) {
      right = false;
    }
    
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    x = e.getX();
    y = e.getY();
  }
  
  //Getters and Setters
  
  public int getMouseX() {
    return x;
  }
  
  public int getMouseY() {
    return y;
  }
  
  public boolean getLeft() {
    return left;
  }

  public boolean getRight() {
    return right;
  }

}
