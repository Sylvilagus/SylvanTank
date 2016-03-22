package com.sylva.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Laser extends Missile {
	public Laser(Tank owner, Point missileCenter, Tank.direction d, int speed,
			BattleField bf, Color mc) {
		super(owner, missileCenter, d, speed, bf, mc);
		mColor=Color.RED;
	}

	@Override
	public void draw(Graphics g) {
		if (!live) {
			bf.missiles.remove(this);
			return;
		}
		Color oriCol = g.getColor();
		g.setColor(mColor);
		switch (mDir) {
		case U:
		case D:
			g.fillRect(mCenter.x - 3, mCenter.y - (damage*3+30), 2, (damage*3+30)*2);
			g
					.setColor(new Color(
							(mColor.getRed() + (int) ((255 - mColor.getRed()) / 2)),
							(mColor.getGreen() + (int) ((255 - mColor
									.getGreen()) / 2)),
							(mColor.getBlue() + ((int) ((255 - mColor.getBlue()) / 2)))));
			g.fillRect(mCenter.x - 1, mCenter.y - (damage*3+30), 2, (damage*3+30)*2);
			g.setColor(mColor);
			g.fillRect(mCenter.x + 1, mCenter.y - (damage*3+30), 2, (damage*3+30)*2);
			break;
		case L:
		case R:
			g.fillRect(mCenter.x - (damage*3+30), mCenter.y - 3, (damage*3+30)*2, 2);
			g
					.setColor(new Color(
							(mColor.getRed() + (int) ((255 - mColor.getRed()) / 2)),
							(mColor.getGreen() + (int) ((255 - mColor
									.getGreen()) / 2)),
							(mColor.getBlue() + ((int) ((255 - mColor.getBlue()) / 2)))));
			g.fillRect(mCenter.x - (damage*3+30), mCenter.y - 1, (damage*3+30)*2, 2);
			g.setColor(mColor);
			g.fillRect(mCenter.x - (damage*3+30), mCenter.y + 1, (damage*3+30)*2, 2);
			break;
		}
		g.setColor(oriCol);
		move();
		if (mCenter.x < 0 - (damage*3+30)*2 || mCenter.y < 0 - (damage*3+30)*2
				|| mCenter.x > bf.initSiz.width + (damage*3+30)*2
				|| mCenter.y > bf.initSiz.height + (damage*3+30)*2)
			live = false;
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		Rectangle r;
		if (mDir == Tank.direction.U || mDir == Tank.direction.D) {
			r = new Rectangle(mCenter.x - 3, mCenter.y - (damage*3+30), 6,
					(damage*3+30)*2);
		} else {
			r = new Rectangle(mCenter.x - (damage*3+30), mCenter.y - 3,
					(damage*3+30)*2, 6);
		}
		return r;
	}

	@Override
	public boolean hitBase(Base b) {
		// TODO Auto-generated method stub
		try {
			return super.hitBase(b);
		} finally {
			live = true;
		}
	}

	@Override
	public boolean hitTanks(LinkedList<Tank> ts) {
		// TODO Auto-generated method stub
		try {
			return super.hitTanks(ts);
		} finally {
			live = true;
		}
	}

	@Override
	public boolean hitWall(LinkedList<Wall> ws) {
		// TODO Auto-generated method stub
		try {
			return super.hitWall(ws);
		} finally {
			live = true;
		}
	}

}
