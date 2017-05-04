package pong;

import java.awt.Graphics;

public class Player extends Shape {
	private int width, height, score = 0;
	public Player(int x, int y, int width, int height) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.width = width;
		this.height = height;
	}

	@Override
	public void drawShape(Graphics g) {
		// TODO Auto-generated method stub
		g.fillRect(getX(), getY(), width, height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
