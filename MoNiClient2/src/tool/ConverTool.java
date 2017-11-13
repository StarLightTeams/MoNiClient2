package tool;

import config.ClientConfig;
import entity.game.Ball;
import entity.game.Board;
import entity.game.Brick;
import entity.game.bfbData.BfbBall;
import entity.game.bfbData.BfbBoard;
import entity.game.bfbData.BfbBrick;

public class ConverTool {
	
	/*
	 * ת����
	 */
	public static double exChangePoint ;
	
	public ConverTool() {
		this.exChangePoint = ClientConfig.GAMEHEIGHT*1.0/ClientConfig.GAMEREALYHEIGHT;
	}
	
	/**
	 * ����  ʵ����Ϸ����/ʵ����Ϸ��Ļ
	 * @param x ʵ����Ϸĳ�����x
	 * @return
	 */
	public static double exchageXValueReal(int x) {
		return YueFenTool.yueFen(x*1.0/ClientConfig.GAMEREALYWIDTH);
	}
	
	/**
	 * ����  ʵ����Ϸ����/ʵ����Ϸ��Ļ
	 * @param y ʵ����Ϸĳ�����y
	 * @return
	 */
	public static double exchageYValueReal(int y) {
		return YueFenTool.yueFen(y*1.0/ClientConfig.GAMEREALYHEIGHT);
	}
	
	/**
	 * ���� ��ǰ��Ϸ����/��ǰ��Ϸ��Ļ
	 * @param x ��ǰ��Ϸĳ�����x
	 * @return
	 */
	public static double exchageXValue(int x) {
		return YueFenTool.yueFen(x*1.0/ClientConfig.GAMEWIDTH);
	}
	
	/**
	 *  ���� ��ǰ��Ϸ����/��ǰ��Ϸ��Ļ
	 * @param y ��ǰ��Ϸĳ�����y
	 * @return
	 */
	public static double exchageYValue(int y) {
		return YueFenTool.yueFen(y*1.0/ClientConfig.GAMEHEIGHT);
	}
	
	/**
	 * ���� ��ǰ��Ϸ����ʵ�ʿ��
	 * @param x
	 * @return
	 */
	public static double reductionXValueCurrent(double x){
		return x*ClientConfig.GAMEWIDTH;
	}
	
	/**
	 * ���� ��ǰ��Ϸ����ʵ�ʸ߶�
	 * @param y
	 * @return
	 */
	public static double reductionYValueCurrent(double y){
		return y*ClientConfig.GAMEHEIGHT;
	}
	
	/**
	 * ���� ʵ����Ϸ�����ʵ�ʿ��
	 * @param x
	 * @return
	 */
	public static double reductionXValue(double x){
		return x*ClientConfig.GAMEREALYWIDTH;
	}
	
	/**
	 * ���� ʵ����Ϸ�����ʵ�ʸ߶�
	 * @param y
	 * @return
	 */
	public static double reductionYValue(double y){
		return y*ClientConfig.GAMEREALYHEIGHT;
	}
	
	public static BfbBrick conver_brick(Brick brick){
		BfbBrick bfbbrick = new BfbBrick();
		bfbbrick.bg_height = YueFenTool.yueFen(brick.height*1.00/ClientConfig.GAMEHEIGHT);//��
		bfbbrick.bg_width = YueFenTool.yueFen(brick.width*1.00/ClientConfig.GAMEWIDTH);//��
		bfbbrick.bg_locX  = YueFenTool.yueFen(brick.locX*1.00/ClientConfig.GAMEWIDTH);//x
		bfbbrick.bg_locY  = YueFenTool.yueFen(brick.locY*1.00/ClientConfig.GAMEHEIGHT);//y
		bfbbrick.bPropsId = brick.bPropsId;//ש�����id
		bfbbrick.hardness = brick.hardness;//Ӳ��
		return bfbbrick;
	}
	public static BfbBall conver_ball(Ball ball){
		BfbBall bfbball =new BfbBall();
		bfbball.bf_d = YueFenTool.yueFen(ball.d*1.00/ClientConfig.GAMEHEIGHT);//��
		bfbball.bf_bx= YueFenTool.yueFen(ball.bx*1.00/ClientConfig.GAMEWIDTH);
		bfbball.bf_by= YueFenTool.yueFen(ball.by*1.00/ClientConfig.GAMEHEIGHT);
		bfbball.ySpeed = ball.ySpeed;//��
		bfbball.xSpeed  = ball.xSpeed;//x
		bfbball.xA  = ball.xA;//y
		bfbball.yA = ball.yA;//ש�����id
		bfbball.degree = ball.degree;//Ӳ��
		return bfbball;
	}
	public static BfbBoard conver_board(Board board){
		BfbBoard bfbvoard =new BfbBoard();
		bfbvoard.bf_width = YueFenTool.yueFen(board.width/ClientConfig.GAMEWIDTH); //��
		bfbvoard.bf_height = YueFenTool.yueFen(board.height/ClientConfig.GAMEHEIGHT);//��
		bfbvoard.bf_locX = YueFenTool.yueFen(board.locX/ClientConfig.GAMEWIDTH);  //λ��x
		bfbvoard.bf_locY = YueFenTool.yueFen(board.locY/ClientConfig.GAMEHEIGHT);  //λ��y
		bfbvoard.ySpeed = board.ySpeed;//�ƶ��ٶ�
		bfbvoard.yA = board.yA;	  //�ƶ����ٶ�
		return bfbvoard;
	}
}
