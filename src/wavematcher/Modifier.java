package wavematcher;

public class Modifier {
	/* Types: 
	1) addition 
	2) subtraction 
	3) multiplication
	4) division
	*/
	private int type;
	private int waveType;
	private int lockedNum;
	private WaveMatcher m;
	
	public Modifier(int t, WaveMatcher m) {
		type = t;
		waveType = m.getWaveType();
		lockedNum = m.getLockedNum();
		this.m = m;
	}
	
	public void modify() {
		for(Wave w:m.getWaves()) {
			w.setLock(false);
		}
		m.getWaves().get(lockedNum).setLock(true);
		switch(type) {
			case 1:
				for(Wave w:m.getWaves()) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()+20);
					else
						w.setFrequency(w.getFrequency()+20);
				}
				break;
			case 2:
				for(Wave w:m.getWaves()) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()-20);
					else
						w.setFrequency(w.getFrequency()-20);
				}
				break;
			case 3:
				for(Wave w:m.getWaves()) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()*2);
					else
						w.setFrequency(w.getFrequency()*2);
				}
				break;
			case 4:
				for(Wave w:m.getWaves()) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()/2);
					else
						w.setFrequency(w.getFrequency()/2);
				}
				break;
			case 5:
				for(Wave w:m.getWaves()) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()*(-1));
					else
						w.setFrequency(w.getFrequency()*(-1));
				}
				break;
			default:
				System.out.println("not valid command");
				break;
		}
	}
	public void undo() {
		for(Wave w:m.getWaves()) {
			w.setLock(false);
		}
		m.getWaves().get(lockedNum).setLock(true);
		m.setSelector(lockedNum);
		switch(type) {
			case 2:
				for(Wave w:m.getWaves()) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()+20);
					else
						w.setFrequency(w.getFrequency()+20);
				}
				break;
			case 1:
				for(Wave w:m.getWaves()) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()-20);
					else
						w.setFrequency(w.getFrequency()-20);
				}
				break;
			case 4:
				for(Wave w:m.getWaves()) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()*2);
					else
						w.setFrequency(w.getFrequency()*2);
				}
				break;
			case 3:
				for(Wave w:m.getWaves()) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()/2);
					else
						w.setFrequency(w.getFrequency()/2);
				}
				break;
			case 5:
				for(Wave w:m.getWaves()) {
					if(waveType>0)
						w.setAmplitude(w.getAmplitude()*(-1));
					else
						w.setFrequency(w.getFrequency()*(-1));
				}
				break;
			default:
				System.out.println("not valid command");
				break;
		}
	}
	public int getType() {
		return type;
	}
	public int getWaveType() {
		return waveType;
	}
}
