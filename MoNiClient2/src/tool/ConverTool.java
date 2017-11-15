package tool;

import java.util.ArrayList;
import java.util.List;

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
	
	public static Brick reduction_brick(BfbBrick bfbBrick) {
		Brick brick = new Brick();
		brick.height = (int) reductionYValueCurrent(bfbBrick.bg_height);//��
		brick.width = (int)reductionXValueCurrent(bfbBrick.bg_width);//��
		brick.locX  =reductionXValueCurrent(bfbBrick.bg_locX);;//x
		brick.locY  = reductionYValueCurrent(bfbBrick.bg_locY);//y
		brick.bPropsId = brick.bPropsId;//ש�����id
		brick.hardness = brick.hardness;//Ӳ��
		return brick;
	}
	
	public static Ball reduction_ball(BfbBall bfbball){
		Ball ball =new Ball();
		ball.d = (int) reductionYValueCurrent(bfbball.bf_d);//��
		ball.bx= reductionXValueCurrent(bfbball.bf_bx);
		ball.by= reductionYValueCurrent(bfbball.bf_by);
		ball.ySpeed = reductionYValueCurrent(bfbball.ySpeed);//��
		ball.xSpeed  = reductionXValueCurrent(bfbball.xSpeed);//x
		ball.xA  = reductionXValueCurrent(bfbball.xA);//y
		ball.yA = reductionYValueCurrent(bfbball.yA);//ש�����id
		ball.degree = bfbball.degree;//Ӳ��
		return ball;
	}
	
	public static Board reduction_board(BfbBoard bfbvoard){
		Board board = new Board();
		board.width = (int) reductionXValueCurrent(bfbvoard.bf_width); //��
		board.height = (int) reductionYValueCurrent(bfbvoard.bf_height);//��
		board.locX = reductionXValueCurrent(bfbvoard.bf_locX);  //λ��x
		board.locY = reductionYValueCurrent(bfbvoard.bf_locY);  //λ��y
		board.ySpeed = board.ySpeed;//�ƶ��ٶ�
		board.yA = board.yA;	  //�ƶ����ٶ�
		return board;
	}
	
	public static List<Brick> reduction_brickList(List<BfbBrick> bfbBricks) {
		List<Brick> bricks = new ArrayList<Brick>();
		for(int i=0;i<bfbBricks.size();i++) {
			BfbBrick bfbBrick = bfbBricks.get(i);
			bricks.add(reduction_brick(bfbBrick));
		}
		return bricks;
	}
	
	public static List<Ball> reduction_ballList(List<BfbBall> bfbballs){
		List<Ball> balls = new ArrayList<Ball>();
		for(int i=0;i<bfbballs.size();i++) {
			BfbBall bfbball = bfbballs.get(i);
			balls.add(reduction_ball(bfbball));
		}
		return balls;
	}
	
	public static List<Board> reduction_boardList(List<BfbBoard> bfbBoards){
		List<Board> boards = new ArrayList<Board>();
		for(int i=0;i<bfbBoards.size();i++) {
			BfbBoard bfbBoard = bfbBoards.get(i);
			boards.add(reduction_board(bfbBoard));
		}
		return boards;
	}
	
	
	
	public static BfbBrick conver_brick(Brick brick){
		BfbBrick bfbbrick = new BfbBrick();
		bfbbrick.bg_height = YueFenTool.yueFen(brick.height*1.00/ClientConfig.GAMEREALYHEIGHT);//��
		bfbbrick.bg_width = YueFenTool.yueFen(brick.width*1.00/ClientConfig.GAMEREALYWIDTH);//��
		bfbbrick.bg_locX  = YueFenTool.yueFen(brick.locX*1.00/ClientConfig.GAMEREALYWIDTH);//x
		bfbbrick.bg_locY  = YueFenTool.yueFen(brick.locY*1.00/ClientConfig.GAMEREALYHEIGHT);//y
		bfbbrick.bPropsId = brick.bPropsId;//ש�����id
		bfbbrick.hardness = brick.hardness;//Ӳ��
		return bfbbrick;
	}
	public static BfbBall conver_ball(Ball ball){
		BfbBall bfbball =new BfbBall();
		bfbball.bf_d = YueFenTool.yueFen(ball.d*1.00/ClientConfig.GAMEREALYHEIGHT);//��
		bfbball.bf_bx= YueFenTool.yueFen(ball.bx*1.00/ClientConfig.GAMEREALYWIDTH);
		bfbball.bf_by= YueFenTool.yueFen(ball.by*1.00/ClientConfig.GAMEREALYHEIGHT);
		bfbball.ySpeed = ball.ySpeed;//��
		bfbball.xSpeed  = ball.xSpeed;//x
		bfbball.xA  = ball.xA;//y
		bfbball.yA = ball.yA;//ש�����id
		bfbball.degree = ball.degree;//Ӳ��
		return bfbball;
	}
	public static BfbBoard conver_board(Board board){
		BfbBoard bfbvoard =new BfbBoard();
		bfbvoard.bf_width = YueFenTool.yueFen(board.width/ClientConfig.GAMEREALYWIDTH); //��
		bfbvoard.bf_height = YueFenTool.yueFen(board.height/ClientConfig.GAMEREALYHEIGHT);//��
		bfbvoard.bf_locX = YueFenTool.yueFen(board.locX/ClientConfig.GAMEREALYWIDTH);  //λ��x
		bfbvoard.bf_locY = YueFenTool.yueFen(board.locY/ClientConfig.GAMEREALYHEIGHT);  //λ��y
		bfbvoard.ySpeed = board.ySpeed;//�ƶ��ٶ�
		bfbvoard.yA = board.yA;	  //�ƶ����ٶ�
		return bfbvoard;
	}
	
	public static List<BfbBoard> conver_boardList(List<Board> boards) {
		List<BfbBoard> bfbBoards = new ArrayList<BfbBoard>();
		for(int i=0;i<boards.size();i++) {
			Board board = boards.get(i);
			bfbBoards.add(conver_board(board));
		}
		return bfbBoards;
	}
	
	public static List<BfbBall> conver_ballList(List<Ball> balls){
		List<BfbBall> bfbBalls = new ArrayList<BfbBall>();
		for(int i=0;i<balls.size();i++) {
			Ball ball = balls.get(i);
			bfbBalls.add(conver_ball(ball));
		}
		return bfbBalls;
	}
	
	public static List<BfbBrick> conver_brick(List<Brick> bricks){
		List<BfbBrick> bfbBricks = new ArrayList<BfbBrick>();
		for(int i=0;i<bricks.size();i++) {
			Brick brick = bricks.get(i);
			bfbBricks.add(conver_brick(brick));
		}
		return bfbBricks;
	}
}
