package entity.game;

public class Board {

	public int width; //��
	public int height;//��
	public double locX;  //λ��x
	public double locY;  //λ��y
	public int ySpeed;//�ƶ��ٶ�
	public int yA;	  //�ƶ����ٶ�
	
	public Board() {
		super();
	}

	public Board(int width, int height, double locX, double locY, int ySpeed,
			int yA) {
		super();
		this.width = width;
		this.height = height;
		this.locX = locX;
		this.locY = locY;
		this.ySpeed = ySpeed;
		this.yA = yA;
	}
	
	
}
