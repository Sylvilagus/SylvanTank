package com.sylva.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

public class BattleField extends Frame {

	public Point initLoc = new Point(100, 100);
	public Dimension initSiz = new Dimension(800, 600);
	public boolean stop = true;
	private Image bufferImage = null;
	Tank myTank = new Tank(300, 600, Tank.camp.FRIEND, this);
	public LinkedList<Tank> tanks = new LinkedList<Tank>();
	public LinkedList<Explode> explodes = new LinkedList<Explode>();
	public LinkedList<Missile> missiles = new LinkedList<Missile>();
	public LinkedList<Wall> walls = new LinkedList<Wall>();
	public LinkedList<PowerUp> powerUps = new LinkedList<PowerUp>();
	public Base b = new Base();
	public int level = 1;
	public int score = 0;
	public boolean boss = true;

	@Override
	public void paint(Graphics g) {
		// try {
		if (!missiles.isEmpty()) {
			for(Iterator<Missile> iter=missiles.iterator();iter.hasNext();){
				Missile m=iter.next();
				if (!m.isLive())
					iter.remove();
				else
					m.draw(g);
			}
		}
		if (!explodes.isEmpty()) {
			for (Iterator<Explode> iter=explodes.iterator();iter.hasNext();) {
				Explode e=iter.next();
				if (!e.isLive()) {
					iter.remove();
				} else
					e.draw(g);
			}
		}
		if (!tanks.isEmpty()) {
			for (Iterator<Tank> iter=tanks.iterator();iter.hasNext();) {
				Tank ta=iter.next();
				if (!ta.isLive()) {
					iter.remove();
				} else
					ta.draw(g);
			}
		}
		if (!walls.isEmpty()) {
			for (Iterator<Wall> iter=walls.iterator();iter.hasNext();) {
				Wall w=iter.next();
				if (!w.isLive()) {
					iter.remove();
				} else
					w.draw(g);
			}
		}
		if (!powerUps.isEmpty()) {
			for (Iterator<PowerUp> iter=powerUps.iterator();iter.hasNext();) {
				PowerUp p=iter.next();
				if (!p.isLive()) {
					iter.remove();
				} else
					p.draw(g);
			}
		}
		// } catch (Exception e) {
		// }
		g.drawString("生命值：" + myTank.getLife(), 20, 480);
		g.drawString("速度：" + myTank.getSpeed(), 20, 500);
		g.drawString("攻击力：" + myTank.getDamage(), 20, 520);
		g.drawString("得分：" + score, 20, 540);
		g.drawString("关卡：" + level, 20, 560);
		g.drawString("特殊攻击：" + myTank.specialShot, 20, 580);
		if (tanks.size() == 1
				&& tanks.getFirst().getTankCamp() == Tank.camp.FRIEND) {
			Color c1 = new Color((int) (Math.random() * 255), (int) (Math
					.random() * 255), (int) (Math.random() * 255));
			Color c2 = new Color((int) (Math.random() * 255), (int) (Math
					.random() * 255), (int) (Math.random() * 255));
			Color c3 = new Color((int) (Math.random() * 255), (int) (Math
					.random() * 255), (int) (Math.random() * 255));
			Color c4 = new Color((int) (Math.random() * 255), (int) (Math
					.random() * 255), (int) (Math.random() * 255));
			if (!boss) {
				boss = true;
				for (int i = 0; i < 11; i++) {
					Tank t = new Tank(50 + 60 * i, 50, Tank.camp.FOE, this, c1,
							c2, c3, c4);
					t.setLife(t.getLife() + level);
					t.setSpeed(level);
					t.setDamage(level);

					tanks.add(t);

				}
				if (walls.size() < 6)
					createStructures();
			} else {
				boss = false;
				Tank t = null;
				if (level < 4) {
					t = new Tank(300, 300, Tank.camp.FOE, this, c1, c2, c3, c4,
							Tank.direction.D, true);
				} else if (level < 7) {
					t = new DeathCross(new Point(300, 300), Tank.camp.FOE,
							this, c1, c2, c3, c4, Tank.direction.D);
					t.setAttackRate(10 + (6 - level));
				} else {
					t = new CrazyLaser(new Point(300, 300), Tank.camp.FOE,
							this, c1, c2, c3, c4, Tank.direction.D);
					t.setAttackRate(5);
				}
				t.setLife(level * 10);
				t.setSpeed(level * 3);
				t.setDamage(level * 3);
				tanks.add(t);
			}
			if (boss)
				level++;
		}
		if (b.isLive())
			b.draw(g);

	}

	@Override
	public void update(Graphics g) {
		if (bufferImage == null) {
			bufferImage = this.createImage(initSiz.width, initSiz.height);
		}
		Graphics bg = bufferImage.getGraphics();
		Color c = this.getBackground();
		Color oc = bg.getColor();
		bg.setColor(c);
		bg.fillRect(0, 0, initSiz.width, initSiz.height);
		bg.setColor(oc);
		paint(bg);
		g.drawImage(bufferImage, 0, 0, null);

	}

	public void launchFrame() {
		// Color c1 = new Color((int) (Math.random() * 255), (int) (Math
		// .random() * 255), (int) (Math.random() * 255));
		// Color c2 = new Color((int) (Math.random() * 255), (int) (Math
		// .random() * 255), (int) (Math.random() * 255));
		// Color c3 = new Color((int) (Math.random() * 255), (int) (Math
		// .random() * 255), (int) (Math.random() * 255));
		// Color c4 = new Color((int) (Math.random() * 255), (int) (Math
		// .random() * 255), (int) (Math.random() * 255));
		// Tank tt = new CrazyLaser(new Point(300, 300), Tank.camp.FOE, this,
		// c1, c2, c3,
		// c4, Tank.direction.D);
		// tanks.add(tt);
		this.setBackground(new Color(245, 222, 179));
		this.setLocation(initLoc);
		this.setSize(initSiz);
		this.setResizable(false);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}

		});
		this.addKeyListener(new keyMonitor());
		Thread th = new Thread(new TankPainter());
		th.start();
		tanks.add(myTank);
		for (int i = 0; i < 11; i++) {
			Tank t = new Tank(50 + 60 * i, 50, Tank.camp.FOE, this, new Color(
					186, 85, 211), new Color(218, 112, 214), new Color(148, 0,
					211), new Color(165, 42, 42));
			t.setLife(3);
			tanks.add(t);
		}
		createStructures();

	}

	public void createStructures() {
		for (int i = 0; i < 11; i++) {
			BrickWall w;
			if (i == 0)
				w = new BrickWall(new Point(150 + 48 * i, 100), new Dimension(
						48, 48), this, true);
			else
				w = new BrickWall(new Point(150 + 48 * i, 100), new Dimension(
						48, 48), this);
			walls.add(w);
			if (i != 10) {
				SteelWall sw = new SteelWall(
						new Point(150 + 48 * (i + 1), 360), new Dimension(48,
								48), this);
				walls.add(sw);
			}
		}
		for (int i = 0; i < 8; i++) {
			BrickWall w;
			if (i == 0)
				w = new BrickWall(new Point(48 * i, 200),
						new Dimension(48, 48), this, true);
			else
				w = new BrickWall(new Point(48 * i, 200),
						new Dimension(48, 48), this);
			walls.add(w);
			BrickWall wr;
			if (i == 0)
				wr = new BrickWall(new Point(9 * 48 + 48 * i, 200),
						new Dimension(48, 48), this, true);
			else
				wr = new BrickWall(new Point(9 * 48 + 48 * i, 200),
						new Dimension(48, 48), this);
			walls.add(wr);
			SteelWall sw = new SteelWall(new Point(150 + 48 * (i + 2), 460),
					new Dimension(48, 48), this);
			walls.add(sw);
		}
		walls.add(new SteelWall(new Point(350 - 10, 578),
				new Dimension(48, 48), this));
		walls.add(new SteelWall(new Point(350 - 10, 530),
				new Dimension(48, 48), this));
		walls.add(new SteelWall(new Point(398 - 10, 530),
				new Dimension(48, 48), this));
		walls.add(new SteelWall(new Point(446 - 10, 530),
				new Dimension(48, 48), this));
		walls.add(new SteelWall(new Point(446 - 10, 578),
				new Dimension(48, 48), this));
	}

	public static void main(String[] args) {
		BattleField bf = new BattleField();
		bf.launchFrame();
	}

	private class TankPainter implements Runnable {

		public void run() {

			while (true) {
				repaint();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private class keyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

	}
}