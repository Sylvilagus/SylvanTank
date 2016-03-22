package com.sylva.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class Explode {
	private Point eCenter;
	private BattleField bf;
	private boolean live=true;
	private int cur=0;
	private Color eColor=Color.ORANGE;
	private int[] radius={1,2,4,7,11,16,22,25,18,12,5,1};
	public Explode(Point eCenter,BattleField bf){
		this.eCenter=eCenter;
		this.bf=bf;
	}
	public Explode(Point eCenter,BattleField bf,Color eColor){
		this(eCenter,bf);
		this.eColor=eColor;
	}
	public void draw(Graphics g){
		
		if(cur==radius.length){
			live=false;
			cur=0;
			return;
		}
		if(!live)return;
		Color c=g.getColor();
		g.setColor(eColor);
		g.fillOval(eCenter.x-radius[cur], eCenter.y-radius[cur], radius[cur]*2, radius[cur++]*2);
		g.setColor(c);
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
}
