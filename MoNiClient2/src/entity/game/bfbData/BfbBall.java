package entity.game.bfbData;

import java.awt.Graphics;

/** 
 *�ٷֱ���
 */
public class BfbBall {

	public double bf_d;//�뾶
	public double bf_bx;//���x����
	public double bf_by;//���y����
	public double ySpeed;//���y���ٶ�
	public double xSpeed;//���x���ٶ�
	public double xA;//���x����ٶ�
	public double yA;//���y����ٵ�
	public double degree;//��ĽǶ�
	
	public void draw(Graphics g) {
		g.drawOval((int)bf_bx, (int)bf_by, (int)bf_d, (int)bf_d);
	}
	
	public BfbBall() {
		super();
	}

	public BfbBall(double bf_d, double bf_bx, double bf_by, double ySpeed,
			double xSpeed, double xA, double yA, double degree) {
		super();
		this.bf_d = bf_d;
		this.bf_bx = bf_bx;
		this.bf_by = bf_by;
		this.ySpeed = ySpeed;
		this.xSpeed = xSpeed;
		this.xA = xA;
		this.yA = yA;
		this.degree = degree;
	}

	public double getBf_d() {
		return bf_d;
	}

	public void setBf_d(double bf_d) {
		this.bf_d = bf_d;
	}

	public double getBf_bx() {
		return bf_bx;
	}

	public void setBf_bx(double bf_bx) {
		this.bf_bx = bf_bx;
	}

	public double getBf_by() {
		return bf_by;
	}

	public void setBf_by(double bf_by) {
		this.bf_by = bf_by;
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
	
}
