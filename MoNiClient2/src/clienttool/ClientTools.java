package clienttool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextPane;

import entity.JTextPaneUP;
import entity.agrement.CommandID;
import entity.agrement.ICommand;
import entity.rule.agreement.ConnectCommand;
import thread.ThreadException;
import tool.agreement.DataBuffer;

/**
 * 客户端工具
 */
public class ClientTools{
	
	public Socket s;
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
	public Thread sendMessage(ICommand iCommand,String str,JTextPaneUP jtp) {
		if(sendThread==null) {
			sendThread = new Thread(new Send(iCommand,str,jtp),clientName+"s");
			receiveThread.setUncaughtExceptionHandler(new ThreadException());
		}
		sendThread.start();
		return sendThread;
	}
	
	//发送数据
	public void sendOnceMessage(ICommand iCommand,String str,JTextPaneUP jtp) {
		try {
			jtp.addString("["+clientName+"s"+"]:"+str);
			OutputStream os = s.getOutputStream();
			DataBuffer data = createAgreeMentMessage(iCommand, str);
			os.write(data.readByte());
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
					byte[] b = new byte[45056];
					InputStream is = s.getInputStream();
					int len = is.read(b);
					DataBuffer data = getAgreeMentMessage(b);
					ICommand icommand = new ICommand();
					icommand.ReadBufferIp(data);
					System.out.println(icommand.header.id);
					System.out.println(icommand.header.length);
					if (icommand.header.id == CommandID.Connect) {
						ConnectCommand conect2 = new ConnectCommand();
						conect2.header.id = icommand.header.id;
						conect2.header.length = icommand.header.length;
						conect2.ReadFromBufferBody(data);
						jtp.addString("["+clientName+"r"+"]:"+new String(conect2.body));
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}

	class Send implements Runnable {
		String str;
		JTextPaneUP jtp;
		ICommand iCommand;
		public Send(ICommand iCommand,String str,JTextPaneUP jtp) {
			this.str = str;
			this.jtp = jtp;
			this.iCommand = iCommand;
		}
		
		public synchronized void run() {
			while(true) {
				try {
					jtp.addString("["+clientName+"s"+"]:"+str);
					OutputStream os = s.getOutputStream();
					DataBuffer data = createAgreeMentMessage(iCommand, str);
					os.write(data.readByte());
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

	/**
	 * 创建协议信息
	 * @param iCommand
	 * @param str
	 * @return
	 */
	public DataBuffer createAgreeMentMessage(ICommand iCommand,String str){
		DataBuffer data = new DataBuffer();
		iCommand.WriteToBuffer(data,str);
		return data;
	}
	
	/**
	 * 接受协议信息
	 * @param bytes
	 * @return
	 */
	public DataBuffer getAgreeMentMessage(byte[] bytes) {
		DataBuffer data = new DataBuffer();
		char[] c =data.getChars(bytes);
		return data;
	}
}
