package entity.game;

import java.awt.Graphics;

public class Ball {

	/**
	 * ��
	 */
	public int d;//�뾶
	public double bx;//���x����
	public double by;//���y����
	public double ySpeed;//���y���ٶ�
	public double xSpeed;//���x���ٶ�
	public double xA;//���x����ٶ�
	public double yA;//���y����ٵ�
	public double degree;//��ĽǶ�
	
	public void draw(Graphics g) {
		g.drawOval((int)(bx-d/2), (int)(by-d/2), d, d);
	}
	
	public Ball() {
		super();
	}
	public Ball(int d, double bx, double by, double ySpeed, double xSpeed,
			double xA, double yA, double degree) {
		super();
		this.d = d;
		this.bx = bx;
		this.by = by;
		this.ySpeed = ySpeed;
		this.xSpeed = xSpeed;
		this.xA = xA;
		this.yA = yA;
		this.degree = degree;
	}
	public int getD() {
		return d;
	}
	public void setD(int d) {
		this.d = d;
	}
	public double getBx() {
		return bx;
	}
	public void setBx(double bx) {
		this.bx = bx;
	}
	public double getBy() {
		return by;
	}
	public void setBy(double by) {
		this.by = by;
	}
	public double getySpeed() {
		return ySpeed;
	}
	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}
	public double getxSpeed() {
		return xSpeed;
	}
	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
	public double getxA() {
		return xA;
	}
	public void setxA(double xA) {
		this.xA = xA;
	}
	public double getyA() {
		return yA;
	}
	public void setyA(double yA) {
		this.yA = yA;
	}
	public double getDegree() {
		return degree;
	}
	public void setDegree(double degree) {
		this.degree = degree;
	}
	@Override
	public String toString() {
		return "Ball [d=" + d + ", bx=" + bx + ", by=" + by + ", ySpeed=" + ySpeed + ", xSpeed=" + xSpeed + ", xA=" + xA
				+ ", yA=" + yA + ", degree=" + degree + "]";
	}	
	
	
}
