package entity.game;

import java.awt.Graphics;

public class Board {

	public int width; //��
	public int height;//��
	public double locX;  //λ��x
	public double locY;  //λ��y
	public int ySpeed;//�ƶ��ٶ�
	public int yA;	  //�ƶ����ٶ�
	
	public void draw(Graphics g) {
		g.drawRect((int)(locX-width/2), (int)(locY-height/2), (int)(width), (int)(height));
	}
	
	public Board() {
		super();
	}

	public Board(int width, int height, double locX, double locY, int ySpeed,
			int yA) {
		super();
		this.width = width;
		this.height = height;
		this.locX = locX;
		this.locY = locY;
		this.ySpeed = ySpeed;
		this.yA = yA;
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

	public double getLocX() {
		return locX;
	}

	public void setLocX(double locX) {
		this.locX = locX;
	}

	public double getLocY() {
		return locY;
	}

	public void setLocY(double locY) {
		this.locY = locY;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public int getyA() {
		return yA;
	}

	public void setyA(int yA) {
		this.yA = yA;
	}

	@Override
	public String toString() {
		return "Board [width=" + width + ", height=" + height + ", locX=" + locX + ", locY=" + locY + ", ySpeed="
				+ ySpeed + ", yA=" + yA + "]";
	}
	
}
