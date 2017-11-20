package entity.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
	//����б�
	public  List<Ball> ball_list =new ArrayList<Ball>();
	//�Լ�ש���б�
	public  List<Brick> myBrickList =new ArrayList<Brick>();
	//�з�ש���б�
	public  List<Brick> enemyBrickList =new ArrayList<Brick>();
	//�Լ��İ�
	public  Board myborad =new Board();
	//���˵İ�
	public  Board enemyborad =new Board();
	//�з��ĵ����б�
	public  Map<Integer,BoardProps> boardPropsmap =new HashMap<Integer,BoardProps>();
	
	
	
	public Game() {
		super();
	}
	public Game(List<Ball> ball_list, List<Brick> myBrickList, List<Brick> enemyBrickList, Board myborad,
			Board enemyborad, Map<Integer, BoardProps> boardPropsmap) {
		super();
		this.ball_list = ball_list;
		this.myBrickList = myBrickList;
		this.enemyBrickList = enemyBrickList;
		this.myborad = myborad;
		this.enemyborad = enemyborad;
		this.boardPropsmap = boardPropsmap;
	}
	public List<Ball> getBall_list() {
		return ball_list;
	}
	public void setBall_list(List<Ball> ball_list) {
		this.ball_list = ball_list;
	}
	public List<Brick> getMyBrickList() {
		return myBrickList;
	}
	public void setMyBrickList(List<Brick> myBrickList) {
		this.myBrickList = myBrickList;
	}
	public List<Brick> getEnemyBrickList() {
		return enemyBrickList;
	}
	public void setEnemyBrickList(List<Brick> enemyBrickList) {
		this.enemyBrickList = enemyBrickList;
	}
	public Board getMyborad() {
		return myborad;
	}
	public void setMyborad(Board myborad) {
		this.myborad = myborad;
	}
	public Board getEnemyborad() {
		return enemyborad;
	}
	public void setEnemyborad(Board enemyborad) {
		this.enemyborad = enemyborad;
	}
	public Map<Integer, BoardProps> getBoardPropsmap() {
		return boardPropsmap;
	}
	public void setBoardPropsmap(Map<Integer, BoardProps> boardPropsmap) {
		this.boardPropsmap = boardPropsmap;
	}
	@Override
	public String toString() {
		return "Game [ball_list=" + ball_list + ", myBrickList=" + myBrickList + ", enemyBrickList=" + enemyBrickList
				+ ", myborad=" + myborad + ", enemyborad=" + enemyborad + ", boardPropsmap=" +null + "]";
	}
}
