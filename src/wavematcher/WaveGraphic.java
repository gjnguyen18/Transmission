package wavematcher;

import java.awt.Point;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.lang.Math;

public class WaveGraphic {
	
	private Wave wave;
	private Rectangle space;
	private ArrayList<Point> wavePoints;
	
	private static double time = 0;
	private static double timeInc = 0;
	
	private double amplitude;
	private double frequency;
	private double goalAmplitude;
	private double goalFrequency;
	//private int expandFactor;
	private double waveSpeed;
	private Color color;
	
	private double ampFactor = 2.0;
	private double freqFactor = 400.0;
	
	public WaveGraphic(Rectangle s, Wave w, Color c) {
		wavePoints = new ArrayList<Point>();
		space = s;
		wave = w;
		amplitude = w.getAmplitude()/ampFactor;
		frequency = w.getFrequency()/freqFactor;
		//expandFactor = 20;
		waveSpeed = wave.getSpeed();
		color = c;
		for(double i=0; i<space.getWidth(); i+=1) {
			double xPos = i;
			double yPos = ( (amplitude*Math.sin( ((frequency/waveSpeed)*xPos)-
					time*frequency))) + space.getCenterY();
			Point tempPoint = new Point((int)(xPos+space.getX()), (int)yPos);
			wavePoints.add(tempPoint);
			tempPoint.setLocation(tempPoint.getX()-1, tempPoint.getY());
		}
	}
	public void updatePoints() {
		for(int i=0; i<wavePoints.size(); i++) {
			double xPos = (wavePoints.get(i).getX()-space.getX());
			wavePoints.get(i).setLocation(xPos+space.getX(),
					( (amplitude*Math.sin( ((frequency/waveSpeed)*xPos)-
					time*frequency))) + space.getCenterY() );
		}
	}
	
	//-------------------------------------------------
	public ArrayList<Point> getWavePoints() {
		return wavePoints;
	}
	public void setColor(Color c) {
		color = c;
	}
	public Color getColor() {
		return color;
	}
	public void setSpeed(double a) {
		timeInc = a;
	}
	public double getSpeed() {
		return timeInc;
	}
	public Wave getWave() {
		return wave;
	}
	public void setWave(Wave w) {
		wave = w;
	}
	public static void setTimeInc(double waveSpeed2) {
		timeInc = waveSpeed2;
	}
	
	public void update(Wave w) {
		wave = w;
		goalAmplitude = w.getAmplitude()/ampFactor;
		goalFrequency = w.getFrequency()/freqFactor;
		waveSpeed = wave.getSpeed();
		adjust();
	}
	public void adjust() {
		double ampDiff = goalAmplitude-amplitude;
		double freqDiff = goalFrequency-frequency;
		
		if(Math.abs(ampDiff)>5*(1.0/ampFactor))
			amplitude += ampDiff/5.0;
		else
			amplitude = goalAmplitude;
		
		if(Math.abs(freqDiff)>5*(1.0/freqFactor))
			frequency += freqDiff/5.0;
		else
			frequency = goalFrequency;
	}
	public static void updateTime() {
		time+=timeInc;
	}
}