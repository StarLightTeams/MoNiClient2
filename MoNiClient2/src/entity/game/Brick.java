package entity.game;

import java.awt.Graphics;

/**
 * ×©¿é
 */
public class Brick {
	public int height;//¸ß
	public int width;//¿í
	public int locX;//x
	public int locY;//y
	public String bPropsId;//×©¿éµÀ¾ßid
	public int hardness;//Ó²¶È
	
	public Brick() {
		
	}
	
	public Brick(int x,int y,int width,int height,String bPropsId,int hardness) {
		this.locX = x;
		this.locY = y;
		this.width = width;
		this.height = height;
		this.bPropsId = bPropsId;
		this.hardness = hardness;
	}
	
	/**
	 * »­×©¿é
	 * @param g
	 */
	public void draw(Graphics g) {
		g.drawRect(locX, locY, width, height);
	}
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
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
	public String getbPropsId() {
		return bPropsId;
	}
	public void setbPropsId(String bPropsId) {
		this.bPropsId = bPropsId;
	}
	public int getHardness() {
		return hardness;
	}
	public void setHardness(int hardness) {
		this.hardness = hardness;
	}
	
	
}
