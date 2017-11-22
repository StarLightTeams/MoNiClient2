package entity.game.bfbData;

import java.awt.Graphics;

/**
 * �ٷֱ�ש
 */
public class BfbBrick {

	public double bg_height;//��
	public double bg_width;//��
	public double bg_locX;//x
	public double bg_locY;//y
	public String bPropsId;//ש�����id
	public int hardness;//Ӳ��
	
	public void draw(Graphics g) {
		g.drawRect((int)bg_locX,(int)bg_locY, (int)bg_width, (int)bg_height);
	}
	
	public BfbBrick() {
		super();
	}

	public BfbBrick(double bg_height, double bg_width, double bg_locX,
			double bg_locY, String bPropsId, int hardness) {
		super();
		this.bg_height = bg_height;
		this.bg_width = bg_width;
		this.bg_locX = bg_locX;
		this.bg_locY = bg_locY;
		this.bPropsId = bPropsId;
		this.hardness = hardness;
	}

	public double getBg_height() {
		return bg_height;
	}

	public void setBg_height(double bg_height) {
		this.bg_height = bg_height;
	}

	public double getBg_width() {
		return bg_width;
	}

	public void setBg_width(double bg_width) {
		this.bg_width = bg_width;
	}

	public double getBg_locX() {
		return bg_locX;
	}

	public void setBg_locX(double bg_locX) {
		this.bg_locX = bg_locX;
	}

	public double getBg_locY() {
		return bg_locY;
	}

	public void setBg_locY(double bg_locY) {
		this.bg_locY = bg_locY;
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
