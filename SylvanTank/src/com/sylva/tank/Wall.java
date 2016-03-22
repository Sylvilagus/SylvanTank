package com.sylva.tank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;


public abstract class Wall {
	protected Point wCenter;
	protected Dimension wSize;
	protected Color wColor;
	protected int life;
	protected boolean live=true;
	protected BattleField bf;
	public Wall(Point wCenter,Dimension wSize,BattleField bf){
		this.wCenter=wCenter;
		this.wSize=wSize;
		life=3;
	}
	public Wall(Point wCenter,Dimension wSize,BattleField bf,int life){
		this(wCenter,wSize,bf);
		this.life=life;
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public abstract void draw(Graphics g);
	public Rectangle getRect(){
		return new Rectangle(wCenter.x-wSize.width/2,wCenter.y-wSize.height/2,wSize.width,wSize.height);
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
}
