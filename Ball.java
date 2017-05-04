package pong;

import java.awt.Graphics;

public class Ball extends Shape {
	private int radius;
	public Ball(int x, int y, int radius) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.radius = radius;
	}

	@Override
	public void drawShape(Graphics g) {
		// TODO Auto-generated method stub
		g.fillOval(getX() - radius, getY() - radius, radius * 2, radius * 2);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

}
