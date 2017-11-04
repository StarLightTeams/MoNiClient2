package entity;

import java.awt.Graphics;

import javax.swing.JPanel;

public class GameJPanel extends JPanel {
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawLine(100,100 , 120, 120);
		//640*1136
	}
}
