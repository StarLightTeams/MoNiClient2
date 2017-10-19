package maincontroller;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import config.ClientConfig;
import entity.GameJPanel;

public class MainController {
	
	public MainController() {
		JFrame frame = new JFrame("�ͻ���");
		ClientConfig clientConfig = new ClientConfig();
		frame.setBounds(clientConfig.STARTX,clientConfig.STARTY, clientConfig.WIDTH,clientConfig.HEIGHT );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GameJPanel panel = new GameJPanel();
		
		frame.add(panel);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		MainController controller = new MainController();
	}
}
