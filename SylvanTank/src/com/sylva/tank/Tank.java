package com.sylva.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;

import org.w3c.dom.css.Rect;

public class Tank implements Idestructible {
	protected Point tankCenter = new Point(300, 300);
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	protected int speed = 2;
	protected Color pedrail = Color.GRAY;
	protected Color plant = new Color(60, 120, 25);
	protected Color cab = new Color(40, 90, 40);
	protected Color cannon = Color.BLACK;
	protected int damage = 1;
	protected camp tankCamp;
	protected direction curDir = direction.U;
	protected boolean alter = true;
	protected int pedrailW = 5 + 2;
	protected int pedrailH = 25 + 4;
	protected int plantW = 15 + 2;
	protected int plantH = 18 + 2;
	protected int cabW = 9 + 2;
	protected int attackRate=10;
	protected int specialShot=0;
	public int getSpecialShot() {
		return specialShot;
	}

	public void setSpecialShot(int specialShot) {
		this.specialShot = specialShot;
	}

	public int getLife() {
		return life;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
		if(this.speed>8)this.speed=8;
	}

	public void setLife(int life) {
		this.life = life;
	}
	protected int cabH = 12;
	protected int cannonW = 3;
	protected int cannonH = 17;
	protected int pedrailint = 4;
	protected int shotSpeed = 4;
	protected boolean coolDown = false;
	protected int CDint = 1000;
	protected Freezer f = new Freezer();
	protected boolean left = false, right = false, up = false, down = false;
	protected BattleField bf;
	protected boolean live=true;
	protected Point oldCenter;
	protected int autoCD=0;
	protected int life=1;
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	enum direction {
		L, R, U, D
	};
	enum camp{FRIEND,FOE};
	protected boolean engine = false;

	public Tank(Point tankCenter, camp tankCamp,BattleField bf) {
		this.tankCenter = tankCenter;
		this.bf = bf;
		this.tankCamp =tankCamp;
		f.start();
	}

	public Tank(int x, int y, camp tankCamp,BattleField bf) {
		this(new Point(x, y),tankCamp, bf);
	}

	public Tank(int x, int y, camp tankCamp,BattleField bf, Color pedrail, Color plant,
			Color cab, Color cannon) {
		this(new Point(x, y),tankCamp, bf);
		this.pedrail = pedrail;
		this.plant = plant;
		this.cab = cab;
		this.cannon = cannon;
	}

	public Tank(int x, int y,camp tankCamp, BattleField bf, Color pedrail, Color plant,
			Color cab, Color cannon, direction oriDir) {
		this(x, y,tankCamp, bf, pedrail, plant, cab, cannon);
		this.curDir = oriDir;
	}
	public Tank(int x, int y,camp tankCamp, BattleField bf, Color pedrail, Color plant,
			Color cab, Color cannon, direction oriDir,boolean boss) {
		this(x, y,tankCamp, bf, pedrail, plant, cab, cannon);
		this.curDir = oriDir;
		this.pedrailH*=2;
		this.pedrailW*=2;
		this.plantH*=2;
		this.plantW*=2;
		this.cabH*=2;
		this.cabW*=2;
		this.cannonH*=2;
		this.cannonW*=2;
		this.pedrailint+=2;
	}

	public Missile fire() {
		
		coolDown = true;
		Point mc = null;
		switch (curDir) {
		case U:
			mc = new Point(tankCenter.x, specialShot==0?(tankCenter.y - cannonH):(tankCenter.y - cannonH-(damage*3+30)));
			break;
		case D:
			mc = new Point(tankCenter.x, specialShot==0?(tankCenter.y + cannonH):(tankCenter.y + cannonH+(damage*3+30)));
			break;
		case L:
			mc = new Point(specialShot==0?(tankCenter.x - cannonH):(tankCenter.x - cannonH-(damage*3+30)), tankCenter.y);
			break;
		case R:
			mc = new Point(specialShot==0?(tankCenter.x + cannonH):(tankCenter.x + cannonH+(damage*3+30)), tankCenter.y);
			break;
		}
		if(specialShot!=0){
			specialShot--;
			return new Laser(this, mc, curDir, shotSpeed, bf, Color.RED);
		}
		
		return new Missile(this,mc, curDir, shotSpeed,bf,this.tankCamp==camp.FRIEND?Color.RED:Color.BLUE);
	}

	public void draw(Graphics g) {
		if(!live){
			bf.tanks.remove(this);			
			return;
		}
		if(tankCamp==camp.FOE&&(autoCD++)%20==0)autoDrive();
		if(autoCD==1000)autoCD=0;
		Color oriForeCol = g.getColor();
		move();
		if (curDir == direction.U || curDir == direction.D) {
			g.setColor(pedrail);
			g.draw3DRect(tankCenter.x - pedrailW - (plantW / 2), tankCenter.y
					- (pedrailH / 2), pedrailW, pedrailH, true);
			g.draw3DRect(tankCenter.x + (plantW / 2), tankCenter.y
					- (pedrailH / 2), pedrailW, pedrailH, true);
			if (alter) {
				for (int i = tankCenter.y - (pedrailH / 2); i <= tankCenter.y
						+ (pedrailH / 2); i += pedrailint) {
					g.drawLine(tankCenter.x - pedrailW - (plantW / 2), i,
							tankCenter.x - plantW / 2, i);
					g.drawLine(tankCenter.x + (plantW / 2), i, tankCenter.x
							+ plantW / 2 + pedrailW, i);
				}
			} else {
				for (int i = tankCenter.y - (pedrailH / 2) + pedrailint / 2; i <= tankCenter.y
						+ (pedrailH / 2); i += pedrailint) {
					g.drawLine(tankCenter.x - pedrailW - (plantW / 2), i,
							tankCenter.x - plantW / 2, i);
					g.drawLine(tankCenter.x + (plantW / 2), i, tankCenter.x
							+ plantW / 2 + pedrailW, i);
				}
			}
			g.setColor(plant);
			g.fill3DRect(tankCenter.x - plantW / 2, tankCenter.y - plantH / 2,
					plantW, plantH, true);
			g.setColor(cab);
			g.fill3DRect(tankCenter.x - cabW / 2, tankCenter.y - cabH / 2,
					cabW, cabH, true);
			g.setColor(cannon);
			if (curDir == direction.U) {
				g.fill3DRect(tankCenter.x - cannonW / 2,
						tankCenter.y - cannonH, cannonW, cannonH, true);
			} else {
				g.fill3DRect(tankCenter.x - cannonW / 2, tankCenter.y, cannonW,
						cannonH, true);
			}
		} else if (curDir == direction.L || curDir == direction.R) {
			g.setColor(pedrail);
			g.draw3DRect(tankCenter.x - pedrailH / 2, tankCenter.y - pedrailW
					- plantW / 2, pedrailH, pedrailW, true);
			g.draw3DRect(tankCenter.x - pedrailH / 2,
					tankCenter.y + plantW / 2, pedrailH, pedrailW, true);
			if (alter) {
				for (int i = tankCenter.x - (pedrailH / 2); i <= tankCenter.x
						+ (pedrailH / 2); i += pedrailint) {
					g.drawLine(i, tankCenter.y - pedrailW - plantW / 2, i,
							tankCenter.y - plantW / 2);
					g.drawLine(i, tankCenter.y + (plantW / 2), i, tankCenter.y
							+ plantW / 2 + pedrailW);
				}
			} else {
				for (int i = tankCenter.x - (pedrailH / 2) + pedrailint / 2; i <= tankCenter.x
						+ (pedrailH / 2); i += pedrailint) {
					g.drawLine(i, tankCenter.y - pedrailW - plantW / 2, i,
							tankCenter.y - plantW / 2);
					g.drawLine(i, tankCenter.y + (plantW / 2), i, tankCenter.y
							+ plantW / 2 + pedrailW);
				}
			}
			g.setColor(plant);
			g.fill3DRect(tankCenter.x - plantH / 2, tankCenter.y - plantW / 2,
					plantH, plantW, true);
			g.setColor(cab);
			g.fill3DRect(tankCenter.x - cabH / 2, tankCenter.y - cabW / 2,
					cabH, cabW, true);
			g.setColor(cannon);
			if (curDir == direction.L) {
				g.fill3DRect(tankCenter.x - cannonH,
						tankCenter.y - cannonW / 2, cannonH, cannonW, true);

			} else {
				g.fill3DRect(tankCenter.x, tankCenter.y - cannonW / 2, cannonH,
						cannonW, true);
			}
		}
		if (engine)
			alter = !alter;
		g.setColor(oriForeCol);
	}

	public void move() {
		
		if (engine) {
			oldCenter=new Point(tankCenter.x,tankCenter.y);
			switch (curDir) {
			case U:
				tankCenter = new Point(tankCenter.x, tankCenter.y - speed);
				break;
			case D:
				tankCenter = new Point(tankCenter.x, tankCenter.y + speed);
				break;
			case L:
				tankCenter = new Point(tankCenter.x - speed, tankCenter.y);
				break;
			case R:
				tankCenter = new Point(tankCenter.x + speed, tankCenter.y);
				break;
			}
		}
		if(tankCenter.x<cannonH)
			tankCenter.x=cannonH;		
		if(tankCenter.x>bf.initSiz.width-cannonH)
			tankCenter.x=bf.initSiz.width-cannonH;		
		if(tankCenter.y<cannonH+30)
			tankCenter.y=cannonH+30;
		if(tankCenter.y>bf.initSiz.height-cannonH)
			tankCenter.y=bf.initSiz.height-cannonH;
		barrier(bf.tanks);
		barrierWall(bf.walls);
		getPowerUp(bf.powerUps);
		barrierBase(bf.b);
	}

	public void keyPressed(KeyEvent e) {
		int kc = e.getKeyCode();
		if(!this.live)return;
		switch (kc) {
		case KeyEvent.VK_W:
			up = true;
			engine = true;
			break;
		case KeyEvent.VK_S:
			down = true;
			engine = true;
			break;
		case KeyEvent.VK_A:
			left = true;
			engine = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			engine = true;
			break;
		case KeyEvent.VK_J:
			if (!coolDown)
				bf.missiles.add(fire());
			break;
		}
		turnning();
	}

	public void turnning() {
		if (up) {
			curDir = direction.U;
		} else if (down) {
			curDir = direction.D;
		} else if (left) {
			curDir = direction.L;
		} else if (right) {
			curDir = direction.R;
		}

	}

	public void keyReleased(KeyEvent e) {
		int kc = e.getKeyCode();
		switch (kc) {
		case KeyEvent.VK_W:
			up = false;
			break;
		case KeyEvent.VK_S:
			down = false;
			break;
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			break;
		}
		if (!up && !down && !left && !right)
			engine = false;
	}

	private class Freezer extends Thread {

		@Override
		public void run() {
			while (true) {
				coolDown = false;
				try {
					Thread.sleep(CDint);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	/* (non-Javadoc)
	 * @see Idestructible#getRect()
	 */
	public Rectangle getRect(){
		return new Rectangle(tankCenter.x-pedrailW-plantW/2,tankCenter.y-pedrailH/2,(pedrailW+plantW/2)*2,pedrailH);
	}

	public camp getTankCamp() {
		return tankCamp;
	}

	public void setTankCamp(camp tankCamp) {
		this.tankCamp = tankCamp;
	}
	public void autoDrive(){
		engine=true;
		switch((int)(Math.random()*4)){
		case 0:
			curDir=direction.U;
			break;
		case 1:
			curDir=direction.D;
			break;
		case 2:
			curDir=direction.L;
			break;
		case 3:
			curDir=direction.R;
			break;
		}
		switch((int)(Math.random()*attackRate)){
		case 1:
			bf.missiles.add(this.fire());
		}
	}
	public int getAttackRate() {
		return attackRate;
	}

	public void setAttackRate(int attackRate) {
		this.attackRate = attackRate;
	}

	public void barrier(LinkedList<Tank> ts){
		for(Tank t:ts){
			if(t!=this&&this.getRect().intersects(t.getRect()))
				this.tankCenter=oldCenter;
		}
		
	}
	public void barrierWall(LinkedList<Wall> ws){
		for(Wall w:ws){
			if(this.getRect().intersects(w.getRect()))
				this.tankCenter=oldCenter;
		}
		
	}
	public void barrierBase(Base b){
		if(this.getRect().intersects(b.getRect()))
			this.tankCenter=oldCenter;
	}
	public void getPowerUp(LinkedList<PowerUp> ps){
		for(PowerUp p:ps){
			if(this.getRect().intersects(p.getRect())){
				p.amplify(this);
				p.setLive(false);
			}
		}
	}

}
