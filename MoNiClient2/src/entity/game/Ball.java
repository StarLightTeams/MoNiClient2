package entity.game;

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
	
	
}
