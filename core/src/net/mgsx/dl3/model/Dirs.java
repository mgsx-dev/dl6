package net.mgsx.dl3.model;

import com.badlogic.gdx.utils.ObjectMap;

public class Dirs {
	public static final int UP = 1;
	public static final int LEFT = 2;
	public static final int DOWN = 4;
	public static final int RIGHT = 8;
	public static final int [] ALL = {UP, LEFT, DOWN, RIGHT};
	
	public static final int [][] VECTORS = {
			null,
			{0,1},
			{-1,0},
			null,
			{0,-1},
			null,
			null,
			null,
			{1,0}
	};

	public static ObjectMap<Integer, Integer> createTileToDirs(){
		ObjectMap<Integer, Integer> tileToDirs = new ObjectMap<Integer, Integer>();
		tileToDirs.put(0, DOWN | RIGHT);
		tileToDirs.put(1, DOWN | RIGHT | LEFT);
		tileToDirs.put(2, DOWN | LEFT);
		
		tileToDirs.put(32, DOWN | UP | RIGHT);
		tileToDirs.put(33, DOWN | UP | RIGHT | LEFT);
		tileToDirs.put(34, DOWN | UP | LEFT);
		
		tileToDirs.put(64, UP | RIGHT);
		tileToDirs.put(65, UP | RIGHT | LEFT);
		tileToDirs.put(66, UP | LEFT);
		
		tileToDirs.put(96, RIGHT);
		tileToDirs.put(97, RIGHT | LEFT);
		tileToDirs.put(98, LEFT);
		
		tileToDirs.put(3,  DOWN);
		tileToDirs.put(35, DOWN | UP);
		tileToDirs.put(67, UP);
		return tileToDirs;
	}

	public static int inverse(int dir) {
		if(dir == UP) return DOWN;
		if(dir == DOWN) return UP;
		if(dir == LEFT) return RIGHT;
		if(dir == RIGHT) return LEFT;
		return 0;
	}
}
