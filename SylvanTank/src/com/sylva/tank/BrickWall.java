package com.sylva.tank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class BrickWall extends Wall {
	private boolean left;
	public BrickWall(Point wCenter, Dimension wSize, BattleField bf) {
		super(wCenter, wSize, bf);
	}
	public BrickWall(Point wCenter, Dimension wSize, BattleField bf,boolean left) {
		super(wCenter, wSize, bf);
		this.left=left;
	}

	@Override
	public void draw(Graphics g) {
		if(!live){
			bf.walls.remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(new Color(210-(3-life)*10, 105-(3-life)*10, 30-(3-life)*10));
		int keyPx = wCenter.x - wSize.width / 2;
		int keyPy = wCenter.y - wSize.height / 2;
		g.fill3DRect(keyPx, keyPy, wSize.width / 2, wSize.height / 3, true);
		g.fill3DRect(keyPx + wSize.width / 2, keyPy, wSize.width / 2,
				wSize.height / 3, true);
		g.fill3DRect(keyPx, keyPy + wSize.height * 2 / 3, wSize.width / 2,
				wSize.height / 3, true);
		g.fill3DRect(keyPx + wSize.width / 2, keyPy + wSize.height * 2 / 3,
				wSize.width / 2, wSize.height / 3, true);
		g.fill3DRect(keyPx, keyPy + wSize.height / 3, wSize.width / 4,
				wSize.height / 3, true);
		g.fill3DRect(keyPx + wSize.width * 3 / 4, keyPy + wSize.height / 3,
				wSize.width / 4, wSize.height / 3, true);
		g.fill3DRect(keyPx + wSize.width / 4, keyPy + wSize.height / 3,
				wSize.width / 2, wSize.height / 3, true);
		if(!left)
		g.fill3DRect(keyPx-wSize.width/4, keyPy + wSize.height / 3, wSize.width / 2,
						wSize.height / 3, true);
	
		g.setColor(c);
		
	}

}
