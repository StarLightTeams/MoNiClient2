package maincontroller;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.junit.runners.model.FrameworkMethod;

import clienttool.ClientTools;
import config.ClientConfig;
import entity.GameConJPanel;

public class MainController {
	
	public MainController() {
		final JFrame frame = new JFrame("¿Í»§¶Ë");
		ClientConfig clientConfig = new ClientConfig();
		frame.setBounds(clientConfig.STARTX,clientConfig.STARTY, clientConfig.WIDTH,clientConfig.HEIGHT );
		
		final GameConJPanel panel = new GameConJPanel();
		
		frame.add(panel);
		frame.addWindowListener(new WindowAdapter() {  
			public void windowClosing(WindowEvent e) {  
				super.windowClosing(e); 
				if(panel.conFlag) {
					panel.clientTools.closeClient(panel.jtp,panel.clientName);
				}else {
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			}  
		}); 
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		MainController controller = new MainController();
	}
}
