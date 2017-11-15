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
	 * 转换点
	 */
	public static double exChangePoint ;
	
	public ConverTool() {
		this.exChangePoint = ClientConfig.GAMEHEIGHT*1.0/ClientConfig.GAMEREALYHEIGHT;
	}
	
	/**
	 * 返回  实际游戏物体/实际游戏屏幕
	 * @param x 实际游戏某物体的x
	 * @return
	 */
	public static double exchageXValueReal(int x) {
		return YueFenTool.yueFen(x*1.0/ClientConfig.GAMEREALYWIDTH);
	}
	
	/**
	 * 返回  实际游戏物体/实际游戏屏幕
	 * @param y 实际游戏某物体的y
	 * @return
	 */
	public static double exchageYValueReal(int y) {
		return YueFenTool.yueFen(y*1.0/ClientConfig.GAMEREALYHEIGHT);
	}
	
	/**
	 * 返回 当前游戏物体/当前游戏屏幕
	 * @param x 当前游戏某物体的x
	 * @return
	 */
	public static double exchageXValue(int x) {
		return YueFenTool.yueFen(x*1.0/ClientConfig.GAMEWIDTH);
	}
	
	/**
	 *  返回 当前游戏物体/当前游戏屏幕
	 * @param y 当前游戏某物体的y
	 * @return
	 */
	public static double exchageYValue(int y) {
		return YueFenTool.yueFen(y*1.0/ClientConfig.GAMEHEIGHT);
	}
	
	/**
	 * 返回 当前游戏物体实际宽度
	 * @param x
	 * @return
	 */
	public static double reductionXValueCurrent(double x){
		return x*ClientConfig.GAMEWIDTH;
	}
	
	/**
	 * 返回 当前游戏物体实际高度
	 * @param y
	 * @return
	 */
	public static double reductionYValueCurrent(double y){
		return y*ClientConfig.GAMEHEIGHT;
	}
	
	/**
	 * 返回 实际游戏物体的实际宽度
	 * @param x
	 * @return
	 */
	public static double reductionXValue(double x){
		return x*ClientConfig.GAMEREALYWIDTH;
	}
	
	/**
	 * 返回 实际游戏物体的实际高度
	 * @param y
	 * @return
	 */
	public static double reductionYValue(double y){
		return y*ClientConfig.GAMEREALYHEIGHT;
	}
	
	public static Brick reduction_brick(BfbBrick bfbBrick) {
		Brick brick = new Brick();
		brick.height = (int) reductionYValueCurrent(bfbBrick.bg_height);//高
		brick.width = (int)reductionXValueCurrent(bfbBrick.bg_width);//宽
		brick.locX  =reductionXValueCurrent(bfbBrick.bg_locX);;//x
		brick.locY  = reductionYValueCurrent(bfbBrick.bg_locY);//y
		brick.bPropsId = brick.bPropsId;//砖块道具id
		brick.hardness = brick.hardness;//硬度
		return brick;
	}
	
	public static Ball reduction_ball(BfbBall bfbball){
		Ball ball =new Ball();
		ball.d = (int) reductionYValueCurrent(bfbball.bf_d);//高
		ball.bx= reductionXValueCurrent(bfbball.bf_bx);
		ball.by= reductionYValueCurrent(bfbball.bf_by);
		ball.ySpeed = reductionYValueCurrent(bfbball.ySpeed);//宽
		ball.xSpeed  = reductionXValueCurrent(bfbball.xSpeed);//x
		ball.xA  = reductionXValueCurrent(bfbball.xA);//y
		ball.yA = reductionYValueCurrent(bfbball.yA);//砖块道具id
		ball.degree = bfbball.degree;//硬度
		return ball;
	}
	
	public static Board reduction_board(BfbBoard bfbvoard){
		Board board = new Board();
		board.width = (int) reductionXValueCurrent(bfbvoard.bf_width); //宽
		board.height = (int) reductionYValueCurrent(bfbvoard.bf_height);//高
		board.locX = reductionXValueCurrent(bfbvoard.bf_locX);  //位置x
		board.locY = reductionYValueCurrent(bfbvoard.bf_locY);  //位置y
		board.ySpeed = board.ySpeed;//移动速度
		board.yA = board.yA;	  //移动加速度
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
		bfbbrick.bg_height = YueFenTool.yueFen(brick.height*1.00/ClientConfig.GAMEREALYHEIGHT);//高
		bfbbrick.bg_width = YueFenTool.yueFen(brick.width*1.00/ClientConfig.GAMEREALYWIDTH);//宽
		bfbbrick.bg_locX  = YueFenTool.yueFen(brick.locX*1.00/ClientConfig.GAMEREALYWIDTH);//x
		bfbbrick.bg_locY  = YueFenTool.yueFen(brick.locY*1.00/ClientConfig.GAMEREALYHEIGHT);//y
		bfbbrick.bPropsId = brick.bPropsId;//砖块道具id
		bfbbrick.hardness = brick.hardness;//硬度
		return bfbbrick;
	}
	public static BfbBall conver_ball(Ball ball){
		BfbBall bfbball =new BfbBall();
		bfbball.bf_d = YueFenTool.yueFen(ball.d*1.00/ClientConfig.GAMEREALYHEIGHT);//高
		bfbball.bf_bx= YueFenTool.yueFen(ball.bx*1.00/ClientConfig.GAMEREALYWIDTH);
		bfbball.bf_by= YueFenTool.yueFen(ball.by*1.00/ClientConfig.GAMEREALYHEIGHT);
		bfbball.ySpeed = ball.ySpeed;//宽
		bfbball.xSpeed  = ball.xSpeed;//x
		bfbball.xA  = ball.xA;//y
		bfbball.yA = ball.yA;//砖块道具id
		bfbball.degree = ball.degree;//硬度
		return bfbball;
	}
	public static BfbBoard conver_board(Board board){
		BfbBoard bfbvoard =new BfbBoard();
		bfbvoard.bf_width = YueFenTool.yueFen(board.width/ClientConfig.GAMEREALYWIDTH); //宽
		bfbvoard.bf_height = YueFenTool.yueFen(board.height/ClientConfig.GAMEREALYHEIGHT);//高
		bfbvoard.bf_locX = YueFenTool.yueFen(board.locX/ClientConfig.GAMEREALYWIDTH);  //位置x
		bfbvoard.bf_locY = YueFenTool.yueFen(board.locY/ClientConfig.GAMEREALYHEIGHT);  //位置y
		bfbvoard.ySpeed = board.ySpeed;//移动速度
		bfbvoard.yA = board.yA;	  //移动加速度
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
