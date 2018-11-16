package world;

import java.awt.Graphics;
import java.util.ArrayList;
import entity.Player;
import entity.RadioBox;
import entity.RadioTower;
import entity.ThrowableRadio;
import runtime.GameHandler;

public class World {

	private ArrayList<Map> maps;
	private Map activeMap;
	private Player p;
	private RadioBox r0;
	private RadioBox r1;
	private RadioBox r2;
	private RadioBox r3;
	private RadioBox r4;
	private RadioBox r5;
	/*private RadioBox r6;
	private RadioBox r7;
	private RadioBox r8;*/
	private RadioBox r6;
	private RadioTower t0;
	private RadioTower t1;
	private RadioTower t2;
	private RadioTower t3;
	private RadioTower t4;
	private RadioTower t5;
	/*private RadioTower t6;
	private RadioTower t7;
	private RadioTower t8;*/
	private RadioTower t6;
	private ThrowableRadio tr;

	GameHandler handler;

	public World(GameHandler handler) {

		this.handler = handler;

		maps = new ArrayList<Map>();
		maps.add(new Map("testMap", 3, handler));
		activeMap = maps.get(0);

		p = new Player(600, 3800, handler);
		r0 = new RadioBox(820, 3810, handler, 0); 
		r1 = new RadioBox(1321, 2593, handler, 1);
		r2 = new RadioBox(2464, 2912, handler, 2);
		r3 = new RadioBox(3230, 2690, handler, 3);
		r4 = new RadioBox(4434, 2660, handler, 4);
		r5 = new RadioBox(6040, 2500, handler, 5);
		/*r6 = new RadioBox(8699, 3810, handler, 6);
		r7 = new RadioBox(9385, 2595, handler, 7);
		r8 = new RadioBox(10679, 3810, handler, 8);*/
		r6 = new RadioBox(11281, 3810, handler, 6);
		t0 = new RadioTower(960, 2337, handler, 0);
		t1 = new RadioTower(1533, 2337, handler, 1);
		t2 = new RadioTower(2684, 2432, handler, 2);
		t3 = new RadioTower(3786, 2400, handler, 3);
		t4 = new RadioTower(4740, 2270, handler, 4);
		t5 = new RadioTower(6554, 3296, handler, 5);
		/*t6 = new RadioTower(8953, 2335, handler, 6);
		t7 = new RadioTower(9522, 3555, handler, 7);
		t8 = new RadioTower(10942, 3555, handler, 8);*/
		t6 = new RadioTower(11000, 2552, handler, 6);
		tr = new ThrowableRadio(7100, 3550, handler);
	}

	public void render(Graphics g) {
		
		activeMap.render(g);
		tr.render(g);
		r0.render(g);
		r1.render(g);
		r2.render(g);
		r3.render(g);
		r4.render(g);
		r5.render(g);
		/*r6.render(g);
		r7.render(g);
		r8.render(g);*/
		r6.render(g);
		t0.render(g);
		t1.render(g);
		t2.render(g);
		t3.render(g);
		t4.render(g);
		t5.render(g);
		/*t6.render(g);
		t7.render(g);
		t8.render(g);*/
		t6.render(g);
		p.render(g);
	}

	public void update() {
		tr.update();
		r0.update();
		r1.update();
		r2.update();
		r3.update();
		r4.update();
		r5.update();
		/*r6.update();
		r7.update();
		r8.update();*/
		r6.update();
		t0.update();
		t1.update();
		t2.update();
		t3.update();
		t4.update();
		t5.update();
		/*t6.update();
		t7.update();
		t8.update();*/
		t6.update();
		p.update();
	}

	// Getters and Setters

	public void setMap(int index) {
		activeMap = maps.get(index);
	}

	public Map getMap() {
		return activeMap;
	}

	public Player getPlayer() {
		return p;
	}

}
