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
	
}
