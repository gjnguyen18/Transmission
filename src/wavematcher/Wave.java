package wavematcher;

public class Wave{
	
	private double amplitude;
	private double frequency;
	private boolean locked = false;
	private boolean main = false;
	private static int waveNum = 0;
	private int waveID;
	private double speed;
	
	public Wave(double amp, double fre, double speed) {
		amplitude = amp;
		frequency = fre;
		this.speed = speed;
		waveID = waveNum;
		waveNum++;
	}
	public Wave(double amp, double fre) {
		this(amp, fre, -10);
	}
	
	public double getAmplitude() {
		return amplitude;
	}
	
	public double getFrequency() {
		return frequency;
	}
	
	public void setAmplitude(double a) {
		if(!locked && !main)
			amplitude = a;
	}
	
	public void setFrequency(double a) {
		if(!locked && !main)
			frequency = a;
	}
	
	public boolean getLock() {
		return locked;
	}

	public void setLock(boolean a) {
		locked = a;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double s) {
		speed = s;
	}
	
	public boolean getIsMain() {
		return main;
	}
	
	public void setMain(boolean a) {
		main = a;
	}
	
	public String toString() {
		return "Wave Number: " + waveID + ", Amplitude: " + amplitude + 
				", Frequency: " + frequency;
	}
}
