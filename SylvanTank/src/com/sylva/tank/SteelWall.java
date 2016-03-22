package com.sylva.tank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public class SteelWall extends Wall {

	public SteelWall(Point wCenter, Dimension wSize, BattleField bf) {
		super(wCenter, wSize, bf,20);
	}

	@Override
	public void draw(Graphics g) {
		if(!live){
			bf.walls.remove(this);
			return;
		}
		Color c=g.getColor();
		g.setColor(new Color(211-(20-life)*5,211-(20-life)*5,211-(20-life)*5));
		g.fill3DRect(wCenter.x-wSize.width/2, wCenter.y-wSize.height/2, wSize.width/2, wSize.height/2, true);
		g.fill3DRect(wCenter.x, wCenter.y-wSize.height/2, wSize.width/2, wSize.height/2, true);
		g.fill3DRect(wCenter.x-wSize.width/2, wCenter.y, wSize.width/2, wSize.height/2, true);
		g.fill3DRect(wCenter.x, wCenter.y, wSize.width/2, wSize.height/2, true);
		g.setColor(c);
	}

}
