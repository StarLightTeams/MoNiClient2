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
	
	public static BfbBrick conver_brick(Brick brick){
		BfbBrick bfbbrick = new BfbBrick();
		bfbbrick.bg_height = YueFenTool.yueFen(brick.height*1.00/ClientConfig.GAMEHEIGHT);//高
		bfbbrick.bg_width = YueFenTool.yueFen(brick.width*1.00/ClientConfig.GAMEWIDTH);//宽
		bfbbrick.bg_locX  = YueFenTool.yueFen(brick.locX*1.00/ClientConfig.GAMEWIDTH);//x
		bfbbrick.bg_locY  = YueFenTool.yueFen(brick.locY*1.00/ClientConfig.GAMEHEIGHT);//y
		bfbbrick.bPropsId = brick.bPropsId;//砖块道具id
		bfbbrick.hardness = brick.hardness;//硬度
		return bfbbrick;
	}
	public static BfbBall conver_ball(Ball ball){
		BfbBall bfbball =new BfbBall();
		bfbball.bf_d = YueFenTool.yueFen(ball.d*1.00/ClientConfig.GAMEHEIGHT);//高
		bfbball.bf_bx= YueFenTool.yueFen(ball.bx*1.00/ClientConfig.GAMEWIDTH);
		bfbball.bf_by= YueFenTool.yueFen(ball.by*1.00/ClientConfig.GAMEHEIGHT);
		bfbball.ySpeed = ball.ySpeed;//宽
		bfbball.xSpeed  = ball.xSpeed;//x
		bfbball.xA  = ball.xA;//y
		bfbball.yA = ball.yA;//砖块道具id
		bfbball.degree = ball.degree;//硬度
		return bfbball;
	}
	public static BfbBoard conver_board(Board board){
		BfbBoard bfbvoard =new BfbBoard();
		bfbvoard.bf_width = YueFenTool.yueFen(board.width/ClientConfig.GAMEWIDTH); //宽
		bfbvoard.bf_height = YueFenTool.yueFen(board.height/ClientConfig.GAMEHEIGHT);//高
		bfbvoard.bf_locX = YueFenTool.yueFen(board.locX/ClientConfig.GAMEWIDTH);  //位置x
		bfbvoard.bf_locY = YueFenTool.yueFen(board.locY/ClientConfig.GAMEHEIGHT);  //位置y
		bfbvoard.ySpeed = board.ySpeed;//移动速度
		bfbvoard.yA = board.yA;	  //移动加速度
		return bfbvoard;
	}
}
