package entity.game;

/**
 * ��
 */
public class Ball {
	public int d;//�뾶
	public double ySpeed;//���y���ٶ�
	public double xSpeed;//���x���ٶ�
	public double xA;//���x����ٶ�
	public double yA;//���y����ٵ�
	public double degree;//��ĽǶ�
	
	public Ball() {
	}
	
	public Ball(int d, double ySpeed, double xSpeed, double xA, double yA, double degree) {
		this.d = d;
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
