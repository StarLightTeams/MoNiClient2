package entity.game;

/**
 * 板子
 */
public class Board {
	public int width; //宽
	public int height;//高
	public int locX;  //位置x
	public int locY;  //位置y
	public int ySpeed;//移动速度
	public int yA;	  //移动加速度
	
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
