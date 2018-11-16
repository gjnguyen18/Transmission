package wavematcher; 
//pushing again

import java.awt.Graphics;
import game.Assets;
import game.Game;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import game.Screen;
import gfx.Loader;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import input.Button;
import input.KeyHandler;
import runtime.GameHandler;
import runtime.GameState;

public class WaveMatcher extends GameState{
	
	private ArrayList<WaveGraphic> waveGraphics;
	private ArrayList<Wave> waves, wavesCopy;
	public static Rectangle box, waveBox;
	private KeyHandler keys;
	private int selection = 1;
	private boolean keyHeld = false;
	private Stack<Modifier> moves;
	private ArrayList<Button> buttons;
	private int waveType=1;
	
	private Button exit; //add a button
	private Button undo;
	private Button reverse;
	private Button reset;
	private Button add20;
	private Button sub20;
	private Button mul2;
	private Button div2;
	private Button switchType;
	
	private int[] colors = {-65536,-16601207,-5052494,-6818592,-4344897,-5852002};
	
	private ArrayList<Button> lockButtons;
	
	private double waveSpeed = 4.0/60.0;
	private int numMods = 10;
	private int mainWaveAmplitude = 120;
	private int mainWaveFrequency = 60;
	
	private int numWaves = 0;
	private int scrambleIterations = 0;	
	//private Image bg;
	private BufferedReader fileToScan;
	private int movesRemaining;
	private int timeRemaining = 0;
	
	private int level = 0;
	private static int WIDTH = 1200;
	private static int HEIGHT = 800;
	
	public WaveMatcher(GameHandler h, int level) {
		super(h);
		
		WaveGraphic.setTimeInc(waveSpeed);
		keys = h.getKeys();
		box = new Rectangle((int)((Screen.screen.getWidth()-WIDTH)/2), 
				(int)((Screen.screen.getHeight()-HEIGHT)/2), WIDTH, HEIGHT);
		waveBox = new Rectangle((int)box.getX()+400, (int)box.getY()+40, 760, 360);
		waveGraphics = new ArrayList<WaveGraphic>();
		waves = new ArrayList<Wave>();
		wavesCopy = new ArrayList<Wave>();
		buttons = new ArrayList<Button>();
		moves = new Stack<Modifier>();
		
		exit = new Button(h, (int)box.getX()+260*4, (int)box.getY()+156*4, 
				Assets.red, Assets.redHover, Assets.redPress, "EXIT", 36, 2);
		reset = new Button(h, (int)box.getX()+260*4, (int)box.getY()+110*4, 
				Assets.red, Assets.redHover, Assets.redPress, "RESET", 35, 2);
		switchType = new Button(h, (int)box.getX()+105*4, (int)box.getY()+115*4, 
				Assets.swapAmp, Assets.swapAmpHover, Assets.swapAmpPress, "MODE: AMPLITUDE", 40, 2);
		add20 = new Button(h, (int)(box.getX())+155*4, (int)(box.getY())+130*4, 
				Assets.mod, Assets.modHover, Assets.modPress, "+20", 60, 2); 
		sub20 = new Button(h, (int)(box.getX())+105*4, (int)(box.getY())+130*4, 
				Assets.mod, Assets.modHover, Assets.modPress, "-20", 60, 2); 
	  mul2 = new Button(h, (int)(box.getX())+155*4, (int)(box.getY())+161*4, 
	  		Assets.mod, Assets.modHover, Assets.modPress, "x2", 60, 2); 
		div2 = new Button(h, (int)(box.getX())+105*4,(int)(box.getY())+161*4, 
				Assets.mod, Assets.modHover, Assets.modPress, "/2", 60, 2); 
		reverse = new Button(h, (int)(box.getX())+205*4, (int)(box.getY())+130*4, 
				Assets.mod, Assets.modHover, Assets.modPress, "*-1", 60, 2); 
		undo = new Button(h, (int)(box.getX())+205*4, (int)(box.getY())+161*4, 
				Assets.mod, Assets.modHover, Assets.modPress, "UNDO", 60, 2); 

		//initialize the button (Gamehandler, x, y, button sprite, hovered sprite, hovered sprite)
		
		this.level = level;
		fileToScan =  Loader.loadText("/WaveMatcherFiles/wavePuzzle"+ level);

		this.readFile();
		lockButtons = new ArrayList<Button>();
		for(int i=0; i<waves.size()-1; i++) {
			lockButtons.add(new Button(h, (int)(box.getX()+11*Game.GAME_SCALE), (int)(box.getY()+50*Game.GAME_SCALE+(i*Game.GAME_SCALE*31)), 
					Assets.unlocked, Assets.unlockedHover, Assets.unlockedPress)); 
			buttons.add(lockButtons.get(i));
		}
		for(Button b:lockButtons) {
			b.setButtonImageIdle(Assets.unlocked);
			b.setButtonImageHover(Assets.unlockedHover);
			b.setButtonImagePressed(Assets.unlockedPress);
		}
		lockButtons.get(selection-1).setButtonImageIdle(Assets.locked);
		lockButtons.get(selection-1).setButtonImageHover(Assets.lockedHover);
		lockButtons.get(selection-1).setButtonImagePressed(Assets.lockedPress);
		
		buttons.add(exit);
		buttons.add(undo);
		buttons.add(reverse);
		buttons.add(reset);
		buttons.add(switchType);
		buttons.add(add20);
		buttons.add(sub20);
		buttons.add(mul2);
		buttons.add(div2);
		//scrambleWaves();
	}
	
	
	public void scrambleWaves() {
		boolean[] hasBeenLocked = new boolean[numWaves];
		for(int a=0; a<scrambleIterations; a++) {
			
			boolean reset = true;
			for(boolean b:hasBeenLocked) {
				if(!b)
					reset = false;
			}
			if(reset) {
				for(int i=0; i<hasBeenLocked.length; i++) {
					hasBeenLocked[i] = false;
				}
			}
			int lockedWave = (int)(Math.random()*numWaves)+1;
			while(hasBeenLocked[lockedWave-1]) {
				lockedWave = (int)(Math.random()*numWaves)+1;
			}
			hasBeenLocked[lockedWave-1]= true;
			
			int randomizer = 0;
			while(randomizer==0) {
				randomizer = (int)(Math.random()*3)-1;
			}
			int randomizer2 = 0;
			while(randomizer2==0) {
				randomizer2 = (int)(Math.random()*3)-1;
			}
			waveType = waveType*randomizer;
			int modType = (int)(Math.random()*4)+1;
			for(Wave w:waves) {
				w.setLock(false);
				if(w==waves.get(lockedWave)) {
					w.setLock(true);
				}
				if(modType==1) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()+(20*randomizer2));
					else
						w.setFrequency(w.getFrequency()+(20*randomizer2));
				}
				if(modType==2) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()*2);
					else
						w.setFrequency(w.getFrequency()*2);
				}
				if(modType==3) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()/2);
					else
						w.setFrequency(w.getFrequency()/2);
				}
				if(modType==4) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()*(-1));
					else
						w.setFrequency(w.getFrequency()*(-1));
				}
			}
		}
		for(int i=0; i<waves.size(); i++) {
			wavesCopy.add(new Wave(waves.get(i).getAmplitude(),waves.get(i).getFrequency()));
		}
	}
	
	public void displayWavesValues(Graphics g) {
		//main wave
		g.setColor(Color.RED);
		g.setFont(new Font("DS-Digital", Font.PLAIN, 27));
		g.drawString("Main Wave", (int)(box.getX()+11*Game.GAME_SCALE)+10, (int)(box.getY()+11*Game.GAME_SCALE)+g.getFont().getSize()*1);
		g.drawString("Amplitude: " + waves.get(0).getAmplitude(), (int)(box.getX()+11*Game.GAME_SCALE)+10, (int)(box.getY()+11*Game.GAME_SCALE)+g.getFont().getSize()*2);
		g.drawString("Frequency: " + waves.get(0).getFrequency(), (int)(box.getX()+11*Game.GAME_SCALE)+10, (int)(box.getY()+11*Game.GAME_SCALE)+g.getFont().getSize()*3);
		g.drawString("Moves Remaining: " + movesRemaining, (int)(box.getX()+11*Game.GAME_SCALE)+10, (int)(box.getY()+11*Game.GAME_SCALE)+g.getFont().getSize()*4);
		
		//rest of the waves
		for(int i=1; i<waves.size(); i++) {
			g.setColor(waveGraphics.get(i).getColor());
			g.setFont(new Font("DS-Digital", Font.PLAIN, 24));
			g.drawString("Wave: " + i, (int)(box.getX()+25*Game.GAME_SCALE)+10, 
					(int)(box.getY()+14*Game.GAME_SCALE)+g.getFont().getSize()*1+(int)(i*Game.GAME_SCALE*31));
			g.drawString("Amplitude: " + waves.get(i).getAmplitude(), (int)(box.getX()+25*Game.GAME_SCALE)+10, 
					(int)(box.getY()+14*Game.GAME_SCALE)+g.getFont().getSize()*2+(int)(i*Game.GAME_SCALE*31));
			g.drawString("Frequency: " + waves.get(i).getFrequency(), (int)(box.getX()+25*Game.GAME_SCALE)+10, 
					(int)(box.getY()+14*Game.GAME_SCALE)+g.getFont().getSize()*3+(int)(i*Game.GAME_SCALE*31));
		}
	}
	public void displayWaves(Graphics g) {
		for(WaveGraphic w:waveGraphics) {
			if(w.getColor().equals(Color.RED)) {
				((Graphics2D)g).setStroke(new BasicStroke(5));
			}
			else {
				((Graphics2D)g).setStroke(new BasicStroke(2));
			}
			for(int i=0; i<w.getWavePoints().size()-1; i++) {
				g.setColor(w.getColor());
				int separation = 15;//(int)(800*(1/w.getWave().getFrequency())-(1.0/w.getWave().getAmplitude()));
				int squareSize = 8;
				if(i%separation==0)
					g.fillRect((int)w.getWavePoints().get(i).getX()-squareSize/2, 
							(int)w.getWavePoints().get(i).getY()-squareSize/2, squareSize, squareSize);
				g.drawLine((int)w.getWavePoints().get(i).getX(), (int)w.getWavePoints().get(i).getY(), 
						(int)w.getWavePoints().get(i+1).getX(), (int)w.getWavePoints().get(i+1).getY());
			}
		}
	}
	
	public void createFile() {
		try {
			File outFile = new File("./res/WaveMatcherFiles/wavePuzzle");
			PrintWriter pw = new PrintWriter(outFile);
			pw.write("" + (-1)*movesRemaining + " " + timeRemaining + "\n");
			for(WaveGraphic w:waveGraphics) {
				Color c = w.getColor();
				pw.write(w.getWave().getAmplitude() + " " + w.getWave().getFrequency() + " " +
						c.getRGB() + "\n");
			}
			pw.close();
		} catch(FileNotFoundException e) {
			System.out.println("filenotfound");
		}
	}
	
	public void readFile() {
		try {
			Scanner sc = new Scanner(fileToScan);
			int line = 0;
			while(sc.hasNextLine()) {
				numWaves++;
				String s = sc.nextLine();
				String[] parts = s.split(" ");
		    double[] waveInfo = new double[parts.length];
		    try {
		    for (int i = 0; i < parts.length; i++) {
		      waveInfo[i] = Double.parseDouble(parts[i]);
		    }
		    } catch(NumberFormatException e) {
		    	System.out.println("parseDouble failed");
		    }
		    if(line==0) {
		    	movesRemaining = (int)waveInfo[0];
		    	timeRemaining = (int)waveInfo[1];
		    }
		    else {
			    Wave w = new Wave(waveInfo[0], waveInfo[1]);
			    waves.add(w);
			    wavesCopy.add(new Wave(w.getAmplitude(), w.getFrequency()));
			    if(line==1) {
			    	w.setMain(true);
			    	mainWaveAmplitude = (int)w.getAmplitude();
			    	mainWaveFrequency = (int)w.getFrequency();
			    }
			    Color c = new Color(colors[waves.size()-1]);
			    waveGraphics.add(new WaveGraphic(waveBox, w, c));
		    }
		    line++;
			}
			sc.close();
			System.out.println("fileclosed: " + level);
		} catch(NullPointerException e) {
			System.out.println("filenotfound");
		}
	}
	
	@Override
	public void update() {
		if(running) {
			WaveGraphic.updateTime();
			keys.update();
			for(int i=0; i<waves.size(); i++) {
				waveGraphics.get(i).update(waves.get(i));
				waveGraphics.get(i).updatePoints();
			}
			
			for(Wave w:waves) {
				w.setLock(false);
			}
			if(selection>=waves.size()) {
				selection = waves.size()-1;
			}
			waves.get(selection).setLock(true);

			if(!(keys.up || keys.down || keys.e || keys.q || keys.left || keys.right 
					|| keys.shift || keys.space || keys.p || keys.i || keys.o)) {
				keyHeld = false;
			}
			for(int i=0; i<lockButtons.size(); i++) {
				if(lockButtons.get(i).isButtonPressed()) {
					for(Button b:lockButtons) {
						b.setButtonImageIdle(Assets.unlocked);
						b.setButtonImageHover(Assets.unlockedHover);
						b.setButtonImagePressed(Assets.unlockedPress);
					}
					selection=i+1;
					lockButtons.get(selection-1).setButtonImageIdle(Assets.locked);
					lockButtons.get(selection-1).setButtonImageHover(Assets.lockedHover);
					lockButtons.get(selection-1).setButtonImagePressed(Assets.lockedPress);
				}
			}
			if(waveType>0) {
				switchType.setButtonImageIdle(Assets.swapAmp);
				switchType.setButtonImageHover(Assets.swapAmpHover);
				switchType.setButtonImagePressed(Assets.swapAmpPress);
				switchType.setString("MODE: AMPLITUDE");
			}
			else {
				switchType.setButtonImageIdle(Assets.swapFreq);
				switchType.setButtonImageHover(Assets.swapFreqHover);
				switchType.setButtonImagePressed(Assets.swapFreqPress);
				switchType.setString("MODE: FREQUENCY");
			}
			if(switchType.isButtonPressed()) {
				waveType = (-1)*waveType;
			}
			if(fileToScan.equals("wavePuzzle-1")) {
				modifyDefaults();
			}
			for(Button b:buttons) {
				b.update();
			}
			if(exit.isButtonPressed()) {
				if(this.isCompleted()) {
					//do something
					handler.setWaveLevel(level+1);
				}
				else {
					handler.setState(0);
				}
			}
			if(fileToScan.equals("wavePuzzle-1")){
				modify();
			}
			else if(movesRemaining!=0)
				modify();
			
			if(undo.isButtonPressed()) {
				if(!moves.isEmpty()) {
					moves.pop().undo();
					movesRemaining++;
					for(Button b:lockButtons) {
						b.setButtonImageIdle(Assets.unlocked);
						b.setButtonImageHover(Assets.unlockedHover);
						b.setButtonImagePressed(Assets.unlockedPress);
					}
					lockButtons.get(selection-1).setButtonImageIdle(Assets.locked);
					lockButtons.get(selection-1).setButtonImageHover(Assets.lockedHover);
					lockButtons.get(selection-1).setButtonImagePressed(Assets.lockedPress);
				}
			}
			if(reset.isButtonPressed()) {
				if(fileToScan.equals("wavePuzzle-1")) {
					for(Wave w:waves) {
						w.setLock(false);
						w.setAmplitude(this.mainWaveAmplitude);
						w.setFrequency(this.mainWaveFrequency);
					}
					while(!moves.isEmpty()) {
						moves.pop();
						movesRemaining++;
					}
				}
				else {
					for(int i=1; i<waves.size(); i++) {
						waves.get(i).setLock(false);
						waves.get(i).setAmplitude(wavesCopy.get(i).getAmplitude());
						waves.get(i).setFrequency(wavesCopy.get(i).getFrequency());
					}
					while(!moves.isEmpty()) {
						moves.pop();
						movesRemaining++;
					}
					for(Button b:lockButtons) {
						b.setButtonImageIdle(Assets.unlocked);
						b.setButtonImageHover(Assets.unlockedHover);
						b.setButtonImagePressed(Assets.unlockedPress);
					}
					lockButtons.get(selection-1).setButtonImageIdle(Assets.locked);
					lockButtons.get(selection-1).setButtonImageHover(Assets.lockedHover);
					lockButtons.get(selection-1).setButtonImagePressed(Assets.lockedPress);
				}
			}
		}
	}
	@Override
	public void render(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1440, 900);
		this.drawGrid(g);
		displayWaves(g);
		drawBorder(g);
		Assets.waveHud.render((int)(box.getX()), (int)(box.getY()), g);
		g.setColor(Color.BLACK);
//		g.drawRect((int)waveBox.getX(), (int)waveBox.getY(), (int)waveBox.getWidth(), (int)waveBox.getHeight());
//		g.fillRect((int)waveBox.getX(), (int)waveBox.getCenterY()-1, (int)waveBox.getWidth(), 2);
		g.setFont(new Font("DS-Digital", Font.PLAIN, (int)(box.getHeight()*.03))); 
		displayWavesValues(g);
		g.setColor(Color.BLACK);
//		if(waveType>0)
//			g.drawString("Mode: Amplitude", (int)box.getX()+(int)(box.width*.02), (int)box.getY()+(int)(box.height*.3));
//		else
//			g.drawString("Mode: Frequency", (int)box.getX()+(int)(box.width*.02), (int)box.getY()+(int)(box.height*.3));
		for(Button b:buttons) {
			b.render(g);
		}
		for(int i=waves.size()-1; i<5; i++) {
			g.setColor(Color.BLACK);
			g.fillRect((int)box.getX()+10*4, (int)box.getY()+45*4+(int)(i*Game.GAME_SCALE*31), 80, 84);
		}
		//g.drawString("Moves Remaining: " + movesRemaining, (int)box.getX()+(int)(box.width*.01), (int)box.getY()+(int)(box.height*.05));
		//g.drawString("Time Remaining: " + timeRemaining, (int)box.getX()+(int)(box.width*.01), (int)box.getY()+(int)(box.height*.15));
		if(this.isCompleted()) {
			g.setColor(Color.BLUE);
			g.drawString("Wave Synchronized", (int)(waveBox.getCenterX()-(g.getFont().getSize()*9*.44)), 
					(int)(waveBox.getY())+(int)(waveBox.getHeight()*.9));
		}
	}
	
	public void drawGrid(Graphics g) {
		int squareLength = 70;
		g.setColor(Color.WHITE);
		for(int i=0;i<waveBox.getWidth()+squareLength; i+=squareLength) {
			for(int j=0; j<waveBox.getHeight()/2+squareLength; j+=squareLength) {
				g.drawRect((int)(waveBox.getX()+i), (int)(waveBox.getY())+(int)(waveBox.getHeight()/2)-j, squareLength, squareLength);
				g.drawRect((int)(waveBox.getX()+i), (int)(waveBox.getY())+(int)(waveBox.getHeight()/2)+j, squareLength, squareLength);
			}
		}
		g.setColor(Color.YELLOW);
		g.fillRect((int)waveBox.getX(), (int)waveBox.getCenterY()-1, (int)waveBox.getWidth(), 2);
	}
	
	
	public void drawBorder(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int)Screen.screen.getWidth(), (int)box.getY());
		g.fillRect(0, 0, (int)(box.getX()), (int)Screen.screen.getHeight());
		g.fillRect((int)(box.getX()+box.getWidth()), (int)(box.getY()), (int)(box.getX()), (int)(Screen.screen.getHeight()-box.getY()));
		g.fillRect((int)(box.getX()), (int)(box.getY()+box.getHeight()), (int)(Screen.screen.getWidth()-box.getX()), (int)(box.getY()));
		box = new Rectangle((int)((Screen.screen.getWidth()-WIDTH)/2), 
				(int)((Screen.screen.getHeight()-HEIGHT)/2), WIDTH, HEIGHT);
	}
	public void modifyDefaults() {
		if(keys.e) {
			if(!keyHeld) {
				waves.get(0).setMain(false);
				for(Wave w:waves) {
					w.setLock(false);
					if(waveType>0) {
						w.setAmplitude(w.getAmplitude()+20);
						this.mainWaveAmplitude=(int)waves.get(0).getAmplitude();
					}
					else {
						w.setFrequency(w.getFrequency()+20);
						this.mainWaveFrequency=(int)waves.get(0).getFrequency();
					}
				}
				waves.get(0).setMain(true);
				waves.get(selection).setLock(true);
			}
			keyHeld = true;
		}if(keys.q) {
			if(!keyHeld) {
				waves.get(0).setMain(false);
				for(Wave w:waves) {
					w.setLock(false);
					if(waveType>0) {
						w.setAmplitude(w.getAmplitude()-20);
						this.mainWaveAmplitude=(int)waves.get(0).getAmplitude();
					}
					else {
						w.setFrequency(w.getFrequency()-20);
						this.mainWaveFrequency=(int)waves.get(0).getFrequency();
					}
				}
				waves.get(0).setMain(true);
				waves.get(selection).setLock(true);
			}
			keyHeld = true;
		}
		if(keys.right) {
			if(!keyHeld) {
				waves.get(0).setMain(false);
				for(Wave w:waves) {
					w.setLock(false);
					if(waveType>0) {
						w.setAmplitude(w.getAmplitude()*2);
						this.mainWaveAmplitude=(int)waves.get(0).getAmplitude();
					}
					else {
						w.setFrequency(w.getFrequency()*2);
						this.mainWaveFrequency=(int)waves.get(0).getFrequency();
					}
				}
				waves.get(0).setMain(true);
				waves.get(selection).setLock(true);
			}
			keyHeld = true;
		}
		if(keys.left) {
			if(!keyHeld) {
				waves.get(0).setMain(false);
				for(Wave w:waves) {
					w.setLock(false);
					if(waveType>0) {
						w.setAmplitude(w.getAmplitude()/2);
						this.mainWaveAmplitude=(int)waves.get(0).getAmplitude();
					}
					else {
						w.setFrequency(w.getFrequency()/2);
						this.mainWaveFrequency=(int)waves.get(0).getFrequency();
					}
				}
				waves.get(0).setMain(true);
				waves.get(selection).setLock(true);
			}
			keyHeld = true;
		}
		if(keys.p) {
			if(!keyHeld) 
				createFile();
			keyHeld = true;
		}
		if(keys.o) {
			if(!keyHeld) {
				if(waves.size()<=5){
					Wave w = new Wave(mainWaveAmplitude, mainWaveFrequency);
					waves.add(w);
					wavesCopy.add(w);
					//Color c = new Color(80+(waves.size()-1)*20, 125+25*(waves.size()-1), 80+(waves.size()-1)*35);
					Color c = new Color(colors[waves.size()-1]);
					waveGraphics.add(new WaveGraphic(waveBox,w,c));
					Button b = new Button(handler, (int)(box.getX()+11*Game.GAME_SCALE), 
							(int)(box.getY()+50*Game.GAME_SCALE+(lockButtons.size()*Game.GAME_SCALE*31)), 
							Assets.unlocked, Assets.unlockedHover, Assets.unlockedPress); 
					lockButtons.add(b);
					buttons.add(b);
				}
			}
			keyHeld = true;
		}
		if(keys.i) {
			if(!keyHeld) {
				if(waves.size()>2) {
					waveGraphics.remove(waveGraphics.size()-1);
					waves.remove(waves.size()-1);
					wavesCopy.remove(wavesCopy.size()-1);
					numWaves--;
					buttons.remove(lockButtons.remove(lockButtons.size()-1));
				}
			}
			keyHeld = true;
		}
		if(keys.space) {
			if(!keyHeld) {
				waves.get(0).setMain(false);
				for(Wave w:waves) {
					w.setLock(false);
					if(waveType>0) {
						w.setAmplitude(w.getAmplitude()*(-1));
						this.mainWaveAmplitude=(int)waves.get(0).getAmplitude();
					}
					else {
						w.setFrequency(w.getFrequency()*(-1));
						this.mainWaveFrequency=(int)waves.get(0).getFrequency();
					}
				}
				waves.get(0).setMain(true);
				waves.get(selection).setLock(true);
			}
			keyHeld = true;
		}
	}
	
	public void modify() {
		if(add20.isButtonPressed()) {
			Modifier m = new Modifier(1,this);
			m.modify();
			movesRemaining--;
			moves.push(m);
		}
		if(sub20.isButtonPressed()) {
			Modifier m = new Modifier(2,this);
			m.modify();
			movesRemaining--;
			moves.push(m);
		}
		if(mul2.isButtonPressed()) {
			Modifier m = new Modifier(3,this);
			m.modify();
			movesRemaining--;
			moves.push(m);
		}
		if(div2.isButtonPressed()) {
			Modifier m = new Modifier(4,this);
			m.modify();
			movesRemaining--;
			moves.push(m);
		}
		if(reverse.isButtonPressed()) {
			Modifier m = new Modifier(5,this);
			m.modify();
			movesRemaining--;
			moves.push(m);
		}
	}
	public ArrayList<Wave> getWaves() {
		return waves;
	}
	public int getWaveType() {
		return waveType;
	}
	public int getLockedNum() {
		return selection;
	}
	public void setSelector(int a) {
		selection = a;
	}
	public boolean isCompleted() {
		boolean amp = true;
		boolean freq = true;
		for(Wave w:waves) {
			if((int)w.getAmplitude()!=(int)this.mainWaveAmplitude)
				amp = false;
			if((int)w.getFrequency()!=(int)this.mainWaveFrequency)
				freq = false;
		}
		return (amp&&freq);
	}
}

