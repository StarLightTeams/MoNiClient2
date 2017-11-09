package entity;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import entity.game.Brick;
import tool.GameTools;

public class GameJPanel extends JPanel {
	
	public ArrayList<Brick> bricks;
	
	public GameJPanel() {
		bricks = GameTools.createBrickList();
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawLine(100,100 , 120, 120);
		//640*1136
//		for(int i=0;i<bricks.size();i++) {
//			Brick brick = bricks.get(i);
//			brick.draw(g);
//		}
	}
}
