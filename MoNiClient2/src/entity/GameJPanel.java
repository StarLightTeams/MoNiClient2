package entity;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import entity.game.Brick;
import tool.ConverTool;
import tool.GameTools;

public class GameJPanel extends JPanel {
	
	public JTextPaneUP jtp;
	
	public ArrayList<Brick> bricks;
	public ArrayList<Brick> OBricks;
	
	
	public GameJPanel(JTextPaneUP jtp) {
		bricks = GameTools.createBrickList(20,0,100,100,60,jtp);
		OBricks = GameTools.createBrickList(20,1136-300,100,100,20,jtp);
		this.jtp = jtp;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//640*1136
		for(int i=0;i<bricks.size();i++) {
			Brick brick = bricks.get(i);
			brick.draw(g);
		}
		
		for(int i=0;i<OBricks.size();i++) {
			Brick brick = OBricks.get(i);
			brick.draw(g);
		}
	}
}
