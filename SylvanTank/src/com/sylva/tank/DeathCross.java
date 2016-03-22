package com.sylva.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;



public class DeathCross extends Tank {
	public DeathCross(Point tankCenter, Tank.camp tankCamp, BattleField bf,
			Color ped, Color pla, Color cab, Color can, direction d) {
		super(tankCenter.x, tankCenter.y, tankCamp, bf, ped, pla, cab, can, d,
				true);
		this.cannonH/=2;
	}

	public void draw(Graphics g) {
		super.draw(g);
		Color c = g.getColor();
		
		g.fill3DRect(tankCenter.x - cannonW / 2, tankCenter.y - cannonH,
				cannonW, cannonH, true);
		g.fill3DRect(tankCenter.x - cannonW / 2, tankCenter.y, cannonW,
				cannonH, true);
		g.fill3DRect(tankCenter.x - cannonH, tankCenter.y - cannonW / 2,
				cannonH, cannonW, true);
		g.fill3DRect(tankCenter.x, tankCenter.y - cannonW / 2, cannonH,
				cannonW, true);
		g.setColor(Color.RED);
		g.fillOval(tankCenter.x - 10, tankCenter.y - 10, 20, 20);
		g.setColor(c);
	}

	public LinkedList<Missile> fires() {
		coolDown = true;
		LinkedList<Missile> ms = new LinkedList<Missile>();
		Point mc1 = null, mc2 = null, mc3 = null, mc4 = null;

		mc1 = new Point(tankCenter.x, tankCenter.y - cannonH);

		mc2 = new Point(tankCenter.x, tankCenter.y + cannonH);

		mc3 = new Point(tankCenter.x - cannonH, tankCenter.y);

		mc4 = new Point(tankCenter.x + cannonH, tankCenter.y);

		ms.add(new Missile(this, mc1, direction.U, shotSpeed, bf,
				this.tankCamp == camp.FRIEND ? Color.RED : Color.BLUE));
		ms.add(new Missile(this, mc2,direction.D, shotSpeed, bf,
				this.tankCamp == camp.FRIEND ? Color.RED : Color.BLUE));
		ms.add(new Missile(this, mc3, direction.L, shotSpeed, bf,
				this.tankCamp == camp.FRIEND ? Color.RED : Color.BLUE));
		ms.add(new Missile(this, mc4, direction.R, shotSpeed, bf,
				this.tankCamp == camp.FRIEND ? Color.RED : Color.BLUE));
		return ms;
	}

	@Override
	public void autoDrive() {
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
		switch((int)(Math.random()*10)){
		case 1:
			bf.missiles.addAll(this.fires());
		}
	}

}
