package com.sylva.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class Missile {
	protected int damage;
	protected Point mCenter;
	protected Tank.direction mDir;
	protected Color mColor = Color.RED;
	protected int speed = 5;
	protected int radius = 2;
	protected boolean live = true;
	protected BattleField bf;
	protected Tank owner;

	public Missile(Tank owner, Point missileCenter, Tank.direction d,
			int speed, BattleField bf) {
		this.owner = owner;
		mDir = d;
		mCenter = missileCenter;
		this.speed = speed;
		this.bf = bf;
		this.damage = owner.getDamage();
		this.radius+=(int)(damage/3);
		if(this.radius>10)this.radius=10;
	}

	public Missile(Tank owner, Point missileCenter, Tank.direction d,
			int speed, BattleField bf, Color mc) {
		this(owner, missileCenter, d, speed, bf);
		mColor = mc;
	}

	public void draw(Graphics g) {
		if (!live) {
			bf.missiles.remove(this);
			return;
		}
		Color oriCol = g.getColor();
		g.setColor(mColor);
		g.fillOval(mCenter.x - radius, mCenter.y - radius, 2 * radius,
				2 * radius);
		g.setColor(oriCol);
		move();
		if (mCenter.x < 0 - radius || mCenter.y < 0 - radius
				|| mCenter.x > bf.initSiz.width + radius
				|| mCenter.y > bf.initSiz.height + radius)
			live = false;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public void move() {
		switch (mDir) {
		case U:
			mCenter = new Point(mCenter.x, mCenter.y - speed - owner.getSpeed());
			break;
		case D:
			mCenter = new Point(mCenter.x, mCenter.y + speed + owner.getSpeed());
			break;
		case L:
			mCenter = new Point(mCenter.x - speed - owner.getSpeed(), mCenter.y);
			break;
		case R:
			mCenter = new Point(mCenter.x + speed + owner.getSpeed(), mCenter.y);
			break;
		}
		hitTanks(bf.tanks);
		hitWall(bf.walls);
		hitBase(bf.b);
	}

	public Rectangle getRect() {
		return new Rectangle(mCenter.x - radius, mCenter.y - radius,
				radius * 2, radius * 2);
	}

	public boolean hit(Tank t) {
		if (this.getRect().intersects(t.getRect()) && t.isLive()
				&& (t.getTankCamp() != owner.getTankCamp())) {
			this.live = false;
			t.setLive(false);
			bf.explodes.add(new Explode(mCenter, bf));
			return true;
		}
		return false;
	}

	public boolean hitTanks(LinkedList<Tank> ts) {
		for (Tank t : ts) {
			if (this.getRect().intersects(t.getRect()) && t.isLive()
					&& (t.getTankCamp() != owner.getTankCamp())) {
				this.live = false;
				t.setLife(t.getLife() - damage);
				if (t.getLife() <= 0) {
					t.setLive(false);
					if(Math.random()<0.5)bf.powerUps.add(new PowerUp(t));
					bf.explodes.add(new Explode(new Point(mCenter.x - 10,
							mCenter.y - 10), bf, Color.RED));
					bf.explodes.add(new Explode(new Point(mCenter.x + 14,
							mCenter.y - 10), bf, Color.YELLOW));
					bf.explodes.add(new Explode(new Point(mCenter.x - 10,
							mCenter.y + 20), bf, Color.WHITE));
					bf.explodes.add(new Explode(new Point(mCenter.x + 10,
							mCenter.y + 16), bf));
					if (t.getTankCamp() == Tank.camp.FRIEND && !t.isLive()) {
						JOptionPane.showMessageDialog(bf, "你挂了-。-");
						System.exit(0);						
					}
					if(t.getTankCamp()==Tank.camp.FOE){
						if(t.getRect().width==bf.myTank.getRect().width)
						bf.score++;
						else
							bf.score+=10*bf.level;
					}
				}
				bf.explodes.add(new Explode(mCenter, bf));
				return true;
			}

		}
		return false;
	}

	public boolean hitWall(LinkedList<Wall> ws) {
		for (Wall w : ws) {
			if (this.getRect().intersects(w.getRect()) && w.isLive()) {
				this.live = false;
				w.setLife(w.getLife() - damage);
				if (w.getLife() <= 0) {
					w.setLive(false);
					bf.explodes.add(new Explode(new Point(mCenter.x - 10,
							mCenter.y - 10), bf, Color.RED));
					bf.explodes.add(new Explode(new Point(mCenter.x + 14,
							mCenter.y - 10), bf, Color.YELLOW));
					bf.explodes.add(new Explode(new Point(mCenter.x - 10,
							mCenter.y + 20), bf, Color.WHITE));
					bf.explodes.add(new Explode(new Point(mCenter.x + 10,
							mCenter.y + 16), bf));
				}
				bf.explodes.add(new Explode(mCenter, bf));
				return true;
			}
		}
		return false;
	}

	public boolean hitBase(Base b) {
		if (this.getRect().intersects(b.getRect()) && b.isLive()) {
			this.live = false;
			b.setLive(false);
			bf.explodes.add(new Explode(mCenter, bf));
			bf.explodes.add(new Explode(new Point(mCenter.x - 10,
					mCenter.y - 10), bf, Color.RED));
			bf.explodes.add(new Explode(new Point(mCenter.x + 14,
					mCenter.y - 10), bf, Color.YELLOW));
			bf.explodes.add(new Explode(new Point(mCenter.x - 10,
					mCenter.y + 20), bf, Color.WHITE));
			bf.explodes.add(new Explode(new Point(mCenter.x + 10,
					mCenter.y + 16), bf));
			JOptionPane.showMessageDialog(bf, "基地挂了，游戏结束");
			System.exit(0);
			return true;
		}
		return false;
	}
}
