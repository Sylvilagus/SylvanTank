package com.sylva.tank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Base {
	private final Point BASE_CENTER = new Point(388, 578);
	private final Dimension BASE_SIZE = new Dimension(48, 48);
	private boolean live = true;

	public Base() {

	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public void draw(Graphics g) {
		if (!live)			
			return;
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fill3DRect(BASE_CENTER.x - BASE_SIZE.width / 6, BASE_CENTER.y
				- BASE_SIZE.height / 2, BASE_SIZE.width * 2 / 3,
				BASE_SIZE.height / 3, true);
		g.setColor(Color.YELLOW);
		g.fill3DRect(BASE_CENTER.x - BASE_SIZE.width / 2, BASE_CENTER.y
				- BASE_SIZE.height / 2, BASE_SIZE.width  / 3,
				BASE_SIZE.height*2 / 3, true);
		g.setColor(Color.PINK);
		g.fill3DRect(BASE_CENTER.x - BASE_SIZE.width / 6, BASE_CENTER.y
				- BASE_SIZE.width / 6, BASE_SIZE.width  / 3,
				BASE_SIZE.height / 3, true);
		g.setColor(Color.ORANGE);
		g.fill3DRect(BASE_CENTER.x + BASE_SIZE.width / 6, BASE_CENTER.y
				- BASE_SIZE.width / 6, BASE_SIZE.width / 3,
				BASE_SIZE.height *2/ 3, true);
		g.setColor(Color.CYAN);
		g.fill3DRect(BASE_CENTER.x - BASE_SIZE.width / 2, BASE_CENTER.y
				+ BASE_SIZE.height /6, BASE_SIZE.width * 2 / 3,
				BASE_SIZE.height / 3, true);
		g.setColor(c);
	}
	public Rectangle getRect(){
		return new Rectangle(BASE_CENTER.x-BASE_SIZE.width/2,BASE_CENTER.y-BASE_SIZE.height/2,BASE_SIZE.width,BASE_SIZE.height);
	}
}
