package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;

public class Screen {

  private JFrame frame;
  private Canvas panel;
  public static final Dimension screen = new Dimension(1440,900);//Toolkit.getDefaultToolkit().getScreenSize();
  // literally why is this scalable while the game screen isnt?
  
  public Screen() {
    
    frame = new JFrame("Transmission");
    frame.setSize(screen);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    
    panel = new Canvas();
    panel.setPreferredSize(screen);
    panel.setMaximumSize(screen);
    panel.setMinimumSize(screen);
    panel.setFocusable(false);
    
    frame.add(panel);
    frame.pack();
  }
  
  public Graphics getGraphics() {
    return panel.getGraphics();  
  }
  
  public Canvas getPanel() {
    return this.panel;
  }
  
  public void setListeners(MouseListener m, KeyListener k) {
    frame.addKeyListener(k);
    frame.addMouseListener(m);
    frame.addMouseMotionListener((MouseMotionListener) m);
    panel.addMouseListener(m);
    panel.addMouseMotionListener((MouseMotionListener) m);
  }
}
