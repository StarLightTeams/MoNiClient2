package entity;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import config.ClientConfig;
import config.entity.Log;
import entity.game.Ball;
import entity.game.Board;
import entity.game.BoardProps;
import entity.game.Brick;
import entity.game.Game;
import entity.game.bfbData.BfbBall;
import entity.game.bfbData.BfbBoard;
import entity.game.bfbData.BfbBrick;
import tool.ConverTool;
import tool.GameTools;

public class GameJPanel extends JPanel implements Runnable,MouseMotionListener{
	
	public JTextPaneUP jtp;
	//球的列表
	public  List<Ball> ball_list =new ArrayList<Ball>();
	//自己砖的列表
	public  List<Brick> myBrickList =new ArrayList<Brick>();
	//敌方砖的列表
	public  List<Brick> enemyBrickList =new ArrayList<Brick>();
	//自己的板
	public  Board myborad =new Board();
	//敌人的板
	public  Board enemyborad =new Board();
	//敌方的道具列表
	public  Map<Integer,BoardProps> boardPropsmap =new HashMap<Integer,BoardProps>();
	public boolean isBegin = false;
	//鼠标的位置
	public int pX;
	public int pY;
	
	
	public GameJPanel(JTextPaneUP jtp) {
//		bricks = GameTools.createBrickList(20,0,100,100,60,jtp);
//		OBricks = GameTools.createBrickList(20,1136-300,100,100,20,jtp);
		this.jtp = jtp;
		addMouseMotionListener(this);
	}
	
	public void loadUI(Game game) {
		this.ball_list = ConverTool.reduction_ballList(ConverTool.conver_ballList(game.ball_list));
		this.myBrickList = ConverTool.reduction_brickList(ConverTool.conver_brick(game.myBrickList));
		this.enemyBrickList = ConverTool.reduction_brickList(ConverTool.conver_brick(game.enemyBrickList));
		this.myborad = ConverTool.reduction_board(ConverTool.conver_board(game.myborad));
		this.enemyborad = ConverTool.reduction_board(ConverTool.conver_board(game.enemyborad));
		this.boardPropsmap = boardPropsmap;
		isBegin = true;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(isBegin) {
			//640*1136
			for(int i=0;i<ball_list.size();i++) {
				Ball ball = ball_list.get(i);
				ball.draw(g);
			}
			
			for(int i=0;i<myBrickList.size();i++) {
				Brick brick = myBrickList.get(i);
				brick.draw(g);
			}
			
			for(int i=0;i<enemyBrickList.size();i++) {
				Brick brick = enemyBrickList.get(i);
				brick.draw(g);
			}
			
			myborad.draw(g);
			
			enemyborad.draw(g);
			
		}
	}

	public void run() {
		while(true) {
			repaint();
			//休眠
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("休眠失败");
			}
		}
		
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		//鼠标控制移动myBoard,范围不超过左边框和右边框
		float moveX = e.getX();
		if(moveX<=myborad.width/2){
			moveX = myborad.width/2;
		}else if(moveX >=ClientConfig.GAMEWIDTH-myborad.width/2){
			moveX = ClientConfig.GAMEWIDTH-myborad.width/2;
		}
		myborad.setLocX(moveX);
		
	}
	
	/**
	 * 把鼠标设置不可见
	 */
	public void setCursorStyleNull(){
		URL url = this.getClass().getResource("");
		Image img = Toolkit.getDefaultToolkit().getImage(url);
		Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0,0),"cursor");
		setCursor(c);
	}
	
}
