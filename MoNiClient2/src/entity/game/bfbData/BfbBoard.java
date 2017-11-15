package entity.game.bfbData;

import java.awt.Graphics;

/**
 * �ٷֱȰ�
 */
public class BfbBoard {

	public double bf_width; //��
	public double bf_height;//��
	public double bf_locX;  //λ��x
	public double bf_locY;  //λ��y
	public double ySpeed;//�ƶ��ٶ�
	public double yA;	  //�ƶ����ٶ�
	
	public void draw(Graphics g) {
		g.drawRect((int)(bf_locX), (int)(bf_locY-bf_width/2), (int)(bf_width), (int)(bf_height));
	}
	
	public BfbBoard() {
		super();
	}

	public BfbBoard(double bf_width, double bf_height, double bf_locX,
			double bf_locY, double bf_ySpeed, double bf_yA) {
		super();
		this.bf_width = bf_width;
		this.bf_height = bf_height;
		this.bf_locX = bf_locX;
		this.bf_locY = bf_locY;
		this.ySpeed = bf_ySpeed;
		this.yA = bf_yA;
	}
	
	
}
