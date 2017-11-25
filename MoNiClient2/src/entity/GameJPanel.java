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
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import clienttool.ClientTools;
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
import entity.info.Info;
import entity.rule.agreement.GameDataBoardCommand;
import entity.rule.agreement.GameDataCommand;
import tool.ConverTool;
import tool.FileTools;
import tool.GameTools;
import tool.JsonTools;

public class GameJPanel extends JPanel implements MouseMotionListener{
	
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
	Game game;
	
	public Map<String,String> maps = new HashMap<String, String>();
	String roomType;
	String roomId;
	ClientTools clientTools;
	public boolean isRun = false;
	String clientName;
	String type ="1";
	float moveX;
	
	public GameJPanel(JTextPaneUP jtp) {
//		bricks = GameTools.createBrickList(20,0,100,100,60,jtp);
//		OBricks = GameTools.createBrickList(20,1136-300,100,100,20,jtp);
		this.jtp = jtp;
		addMouseMotionListener(this);
	}
	
	public void loadUI(Game game,String roomType,String roomId,ClientTools clientTools,String clientName) {
		this.game = game;
		this.ball_list = ConverTool.reduction_ballList(ConverTool.conver_ballList(game.ball_list));
		this.myBrickList = ConverTool.reduction_brickList(ConverTool.conver_brickList(game.myBrickList));
		this.enemyBrickList = ConverTool.reduction_brickList(ConverTool.conver_brickList(game.enemyBrickList));
		this.myborad = ConverTool.reduction_board(ConverTool.conver_board(game.myborad));
		this.enemyborad = ConverTool.reduction_board(ConverTool.conver_board(game.enemyborad));
		this.boardPropsmap = game.boardPropsmap;
		isBegin = true;
		this.roomId = roomId;
		this.roomType = roomType;
		this.clientTools = clientTools;
		isRun = true;
		maps.put("roomType", roomType);
		maps.put("roomId", roomId);
		maps.put("clientName", clientName);
		this.clientName = clientName;
	}
	
	public synchronized void refreshBoardUI(Game game,String roomType,String roomId) {
//		repaint();
	}
	
	public synchronized void refreshUI(Game game,String roomType,String roomId,String receiveType) {
		this.ball_list = ConverTool.reduction_ballList(ConverTool.conver_ballList(game.ball_list));
//		if(type.equals("1")) {
			this.myborad = ConverTool.reduction_board(ConverTool.conver_board(game.myborad));
//		}else if(type.equals("2")) {
			this.enemyborad = ConverTool.reduction_board(ConverTool.conver_board(game.enemyborad));
//			this.enemyborad.locX = ConverTool.reduction_board(ConverTool.conver_board(game.myborad)).locX;
//		}
		this.boardPropsmap = game.boardPropsmap;
		this.roomId = roomId;
		this.roomType = roomType;
		this.game = game;
		new FileTools("D://client"+clientName+".txt").writeFile(clientName+"="+"ball_list"+this.ball_list.toString()+"\n"+",enemyborad="+enemyborad.toString()+"\n"+",myboard="+myborad.toString()+"\n");
//		repaint();
	}
	
	@Override
	public synchronized void paint(Graphics g) {
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
	
	public void startGame() {
		new Thread(new Runnable() {
			public synchronized void run() {
				while(isRun) {
					List<BfbBall> BfbBalls = ConverTool.conver_ballList(ball_list,ClientConfig.GAMEWIDTH,ClientConfig.GAMEHEIGHT);
					List<Ball> bs = ConverTool.reduction_ballList(BfbBalls, ClientConfig.GAMEREALYWIDTH, ClientConfig.GAMEREALYHEIGHT);
					
					List<BfbBrick> BfbMyBricks = ConverTool.conver_brickList(myBrickList,ClientConfig.GAMEWIDTH,ClientConfig.GAMEHEIGHT);
					List<Brick> myBk = ConverTool.reduction_brickList(BfbMyBricks, ClientConfig.GAMEREALYWIDTH, ClientConfig.GAMEREALYHEIGHT);
					
					List<BfbBrick> BfbEnemyBricks = ConverTool.conver_brickList(enemyBrickList,ClientConfig.GAMEWIDTH,ClientConfig.GAMEHEIGHT);
					List<Brick> enemyBk = ConverTool.reduction_brickList(BfbEnemyBricks,ClientConfig.GAMEREALYWIDTH, ClientConfig.GAMEREALYHEIGHT);
					
					Board bb = myborad;
					bb.setLocX(moveX);
					BfbBoard BfbMyBorad = ConverTool.conver_board(bb,ClientConfig.GAMEWIDTH,ClientConfig.GAMEHEIGHT);
					Board myBd = ConverTool.reduction_board(BfbMyBorad,ClientConfig.GAMEREALYWIDTH, ClientConfig.GAMEREALYHEIGHT);
					
					BfbBoard BfbEnemyBorad = ConverTool.conver_board(enemyborad,ClientConfig.GAMEWIDTH,ClientConfig.GAMEHEIGHT);
					Board enemyBd = ConverTool.reduction_board(BfbEnemyBorad, ClientConfig.GAMEREALYWIDTH, ClientConfig.GAMEREALYHEIGHT);
					
					Game game1 = new Game(bs, myBk, enemyBk, myBd, enemyBd, boardPropsmap);
					maps.put("Game",JsonTools.getString(game1));
					clientTools.sendOnceMessage(new GameDataCommand(), JsonTools.getString(new Info("游戏数据",JsonTools.getData(maps))), jtp);
					
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
		}).start();
	}


	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public synchronized void mouseMoved(MouseEvent e) {
			//鼠标控制移动myBoard,范围不超过左边框和右边框
			moveX = e.getX();
			if(moveX<=myborad.width/2){
				moveX = myborad.width/2;
			}else if(moveX >=ClientConfig.GAMEWIDTH-myborad.width/2){
				moveX = ClientConfig.GAMEWIDTH-myborad.width/2;
			}
//			if(isRun) {
//				List<BfbBall> BfbBalls = ConverTool.conver_ballList(ball_list,ClientConfig.GAMEWIDTH,ClientConfig.GAMEHEIGHT);
//				List<Ball> bs = ConverTool.reduction_ballList(BfbBalls, ClientConfig.GAMEREALYWIDTH, ClientConfig.GAMEREALYHEIGHT);
//				
//				List<BfbBrick> BfbMyBricks = ConverTool.conver_brickList(myBrickList,ClientConfig.GAMEWIDTH,ClientConfig.GAMEHEIGHT);
//				List<Brick> myBk = ConverTool.reduction_brickList(BfbMyBricks, ClientConfig.GAMEREALYWIDTH, ClientConfig.GAMEREALYHEIGHT);
//				
//				List<BfbBrick> BfbEnemyBricks = ConverTool.conver_brickList(enemyBrickList,ClientConfig.GAMEWIDTH,ClientConfig.GAMEHEIGHT);
//				List<Brick> enemyBk = ConverTool.reduction_brickList(BfbEnemyBricks,ClientConfig.GAMEREALYWIDTH, ClientConfig.GAMEREALYHEIGHT);
//				
//				Board bb = myborad;
//				bb.setLocX(moveX);
//				BfbBoard BfbMyBorad = ConverTool.conver_board(bb,ClientConfig.GAMEWIDTH,ClientConfig.GAMEHEIGHT);
//				Board myBd = ConverTool.reduction_board(BfbMyBorad,ClientConfig.GAMEREALYWIDTH, ClientConfig.GAMEREALYHEIGHT);
//				
//				BfbBoard BfbEnemyBorad = ConverTool.conver_board(enemyborad,ClientConfig.GAMEWIDTH,ClientConfig.GAMEHEIGHT);
//				Board enemyBd = ConverTool.reduction_board(BfbEnemyBorad, ClientConfig.GAMEREALYWIDTH, ClientConfig.GAMEREALYHEIGHT);
////			enemyBd.locX = ConverTool.reduction_board(ConverTool.conver_board(myborad)).locX;
////			Game game = new Game(ball_list, myBrickList, enemyBrickList, myborad, enemyborad, boardPropsmap);
//				Game gg = new Game();
//				gg.myborad = myBd;
//				gg.enemyborad = enemyBd;
//				gg.ball_list = bs; 
//				new FileTools("D://server"+clientName+".txt").writeFile(clientName+"="+",enemyborad="+enemyBd.toString()+"\n"+",myboard="+myBd.toString()+"\n");
//				maps.put("Game",JsonTools.getString(gg));
//				clientTools.sendOnceMessage(new GameDataCommand(), JsonTools.getString(new Info("游戏数据",JsonTools.getData(maps))), jtp);
//			
//		}
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
