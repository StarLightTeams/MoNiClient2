package clienttool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextPane;

import entity.JTextPaneUP;
import thread.ThreadException;

/**
 * 客户端工具
 * @author Administrator
 *
 */
public class ClientTools {
	
	public Socket s;
	public String nameFlag = "2";
	public Thread sendThread;
	public Thread receiveThread;
	public String clientName;
	
	public ClientTools(Socket s,String clientName) {
		this.s = s;
		this.clientName = clientName;
	}
	
	//接受数据
	public Thread receiveMessage(JTextPaneUP jtp) {
		if(receiveThread == null) {
			receiveThread = new Thread(new Receive(jtp),clientName+"r");
			receiveThread.setUncaughtExceptionHandler(new ThreadException());
		}
		receiveThread.start();
		return receiveThread;
	}
	
	//发送连续数据
	public Thread sendMessage(String str,JTextPaneUP jtp) {
		if(sendThread==null) {
			sendThread = new Thread(new Send(str,jtp),clientName+"s");
			receiveThread.setUncaughtExceptionHandler(new ThreadException());
		}
		sendThread.start();
		return sendThread;
	}
	
	//发送数据
	public void sendOnceMessage(String str,JTextPaneUP jtp) {
		try {
			jtp.addString("["+clientName+"s"+"]:"+str);
			OutputStream os = s.getOutputStream();
			os.write(str.getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class Receive implements Runnable{
		
		JTextPaneUP jtp;
		public Receive(JTextPaneUP jtp) {
			this.jtp = jtp;
		}

		public synchronized void run() {
			while(true) {
				try {
					byte[] b = new byte[1024];
					InputStream is = s.getInputStream();
					int len = is.read(b);
					jtp.addString("["+clientName+"r"+"]:"+new String(b));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}

	class Send implements Runnable {
		
		String str;
		JTextPaneUP jtp;
		public Send(String str,JTextPaneUP jtp) {
			this.str = str;
			this.jtp = jtp;
		}
		
		public synchronized void run() {
			while(true) {
				try {
					jtp.addString("["+clientName+"s"+"]:"+str);
					OutputStream os = s.getOutputStream();
					os.write(str.getBytes());
					os.flush();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
