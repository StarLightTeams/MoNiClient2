package entity.game;

/**
 * ����
 */
public class Board {
	public int width; //��
	public int height;//��
	public int locX;  //λ��x
	public int locY;  //λ��y
	public int ySpeed;//�ƶ��ٶ�
	public int yA;	  //�ƶ����ٶ�
	
	public Board() {
		
	}
	
	public Board(int width, int height, int locX, int locY, int ySpeed, int yA) {
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
	public int getLocX() {
		return locX;
	}
	public void setLocX(int locX) {
		this.locX = locX;
	}
	public int getLocY() {
		return locY;
	}
	public void setLocY(int locY) {
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
	
}
