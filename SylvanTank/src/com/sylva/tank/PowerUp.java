package com.sylva.tank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;


public class PowerUp {
	protected int typeID;
	protected final Dimension pSize=new Dimension(48,48);
	protected boolean live=true;
	protected Point pCenter;
	protected Tank evenOwner;
	protected int ampPoint;
	public PowerUp(Tank evenOwner){
		this.pCenter=new Point((int)(Math.random()*10)*48,(int)(Math.random()*10)*48);
		this.typeID=(int)(Math.random()*4);
		this.evenOwner=evenOwner;
		switch(typeID){
		case 0:
			this.ampPoint=(int)Math.ceil((evenOwner.getDamage()*Math.random()));
			break;
		case 1:
			this.ampPoint=(int)Math.ceil((evenOwner.getSpeed()*Math.random()));
			break;
		case 2:
			this.ampPoint=(int)Math.ceil(((evenOwner.getDamage()+evenOwner.getSpeed())*Math.random()));
			break;
		case 3:
			this.ampPoint=(int)(Math.random()*20+1);
			break;
		}
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public void draw(Graphics g){
		if(!live){
			return;
		}
		Color c=g.getColor();
		g.setColor(Color.YELLOW);
		g.draw3DRect(pCenter.x-24, pCenter.y-24, 48, 48, true);
		g.setColor(Color.MAGENTA);
		g.draw3DRect(pCenter.x-22, pCenter.y-22, 44, 44, true);		
		switch(typeID){
		case 0:
			g.setColor(Color.RED);
			g.drawString("攻击+"+this.ampPoint, pCenter.x-String.valueOf(this.ampPoint).length()*5-5, pCenter.y);
			break;
		case 1:
			g.setColor(Color.CYAN);
			g.drawString("速度+"+this.ampPoint, pCenter.x-String.valueOf(this.ampPoint).length()*5-5, pCenter.y);
			break;
		case 2:
			g.setColor(Color.GREEN);
			g.drawString("生命+"+this.ampPoint, pCenter.x-String.valueOf(this.ampPoint).length()*5-5, pCenter.y);
			break;
		case 3:
			g.setColor(Color.YELLOW);
			g.drawString("激光+"+this.ampPoint, pCenter.x-String.valueOf(this.ampPoint).length()*5-10, pCenter.y);
			break;
		}
		g.setColor(c);
	}
	public Rectangle getRect(){
		return new Rectangle(pCenter.x-pSize.width/2,pCenter.y-pSize.height/2,pSize.width,pSize.height);
	}
	public void amplify(Tank t){
		switch(typeID){
		case 0:
			t.setDamage(t.getDamage()+ampPoint);
			break;
		case 1:
			t.setSpeed(t.getSpeed()+ampPoint);
			if(t.getSpeed()>8)t.setSpeed(8);
			break;
		case 2:
			t.setLife(t.getLife()+ampPoint);
			break;
		case 3:
			t.setSpecialShot(t.getSpecialShot()+this.ampPoint);
			break;
		}
	}
}
