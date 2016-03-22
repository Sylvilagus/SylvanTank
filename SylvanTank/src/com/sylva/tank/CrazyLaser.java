package com.sylva.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;



public class CrazyLaser extends Tank {
	public CrazyLaser(Point tankCenter, Tank.camp tankCamp, BattleField bf,
			Color ped, Color pla, Color cab, Color can, direction d) {
		super(tankCenter.x, tankCenter.y, tankCamp, bf, ped, pla, cab, can, d,
				true);
		this.cannonH/=2;
		this.cannonW*=2;
	}

	@Override
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

	@Override
	public Missile fire() {
		coolDown = true;
		Point mc = null;
		switch (curDir) {
		case U:
			mc = new Point(tankCenter.x, tankCenter.y - cannonH-(damage*3+30));
			break;
		case D:
			mc = new Point(tankCenter.x, tankCenter.y + cannonH+(damage*3+30));
			break;
		case L:
			mc = new Point(tankCenter.x - cannonH-(damage*3+30), tankCenter.y);
			break;
		case R:
			mc = new Point(tankCenter.x + cannonH+(damage*3+30), tankCenter.y);
			break;
		}
		
		return new Laser(this,mc, curDir, shotSpeed,bf,this.tankCamp==camp.FRIEND?Color.RED:Color.BLUE);
	}
}
