package clienttool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.JTextPane;

import config.ClientConfig;
import entity.GameJPanel;
import entity.JTextPaneUP;
import entity.agrement.CommandID;
import entity.agrement.ICommand;
import entity.info.Info;
import entity.rule.agreement.ConnectCommand;
import thread.ThreadException;
import tool.JsonTools;
import tool.agreement.AgreeMentTools;
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
			sendThread.setUncaughtExceptionHandler(new ThreadException());
		}
		sendThread.start();
		return sendThread;
	}
	
	//发送数据
	public synchronized void  sendOnceMessage(ICommand iCommand,String str,JTextPaneUP jtp) {
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
	
	//接受数据
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
					ICommand icommand = AgreeMentTools.getICommand(data);
					jtp.addString("["+clientName+"r"+"]:"+new String(icommand.body));
					Info info = (Info) JsonTools.parseJson(new String(icommand.body));
					String headInfo = info.getHeadInfo();
					String dataInfo = info.getDataInfo();
					System.out.println("headInfo="+headInfo+",dataInfo="+dataInfo);
					if(icommand.header.id == CommandID.Connect) {//连接协议
						if("连接成功".equals(headInfo)) {
							System.out.println("----");
							GameJPanel.callBack(ClientConfig.connectSuccess,dataInfo);
						}
					}else if (icommand.header.id == CommandID.Login) {//登录协议
						if("登录成功".equals(headInfo)) {
							GameJPanel.callBack(ClientConfig.loginInSuccess);
						}else if("登录失败".equals(headInfo)) {
							GameJPanel.callBack(ClientConfig.loginInError);
						}
					}else if(icommand.header.id == CommandID.LoginOut) { //退出登录协议
						if("退出成功".equals(headInfo)) {
							GameJPanel.callBack(ClientConfig.loginOutSuccess);
						}else if("退出失败".equals(headInfo)) {
							GameJPanel.callBack(ClientConfig.loginOutError);
						}
					}else if(icommand.header.id == CommandID.Register) {//注册协议
						if("注册成功".equals(headInfo)) {
							GameJPanel.callBack(ClientConfig.registerSuccess);
						}else if("注册失败".equals(headInfo)) {
							GameJPanel.callBack(ClientConfig.registerError, dataInfo);
						}
					}else if(icommand.header.id == CommandID.GuestLogin) {//游客登录协议
						//拿到服务器给的游客名字,存放到本地,下次客户端先从本地拿取游客名字
						GameJPanel.callBack(ClientConfig.guestNameSuccess,dataInfo);
					}else if(icommand.header.id == CommandID.GeneralInformation) {//普通信息协议
						GameJPanel.callBack(ClientConfig.common,dataInfo);
					}else if(icommand.header.id == CommandID.GamePreparingError) {//游戏准备错误协议
						GameJPanel.callBack(ClientConfig.gamePreparingError,dataInfo);
					}else if(icommand.header.id == CommandID.VerifyStateErr) {//验证错误协议
						GameJPanel.callBack(ClientConfig.verifyStateErr,dataInfo);
					}else if(icommand.header.id == CommandID.VerifyState) {//验证协议
						GameJPanel.callBack(ClientConfig.verifyState,dataInfo);
					}else if(icommand.header.id == CommandID.WaitOtherPeople) {//等待其他玩家
						GameJPanel.callBack(ClientConfig.waitOtherPeople,headInfo);
					}else if(icommand.header.id == CommandID.GameStart) {//游戏开始
						GameJPanel.callBack(ClientConfig.gameStart,headInfo);
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
	
	/**
	 * 获得游客名字
	 */
	public static String getGuestPeopleName() {
		//字母数组(包含a~z的字母和数字)
		char[] str = new char [26*2+10];
		for(int i=0;i<26;i++) {
			str[i] = (char)(65+i);
		}
		for(int i=0;i<26;i++) {
			str[26+i] = (char)(97+i);
		}
		for(int i=0;i<10;i++) {
			str[52+i] = (i+"").charAt(0);
		}
//		for(char s :str) {
//			System.out.println(s);
//		}
		Random rand = new Random();
		char[] name = new char[8];
		String GuestName;
		while(true){
			int t;
			for(int i=0;i<8;i++) {
				t=rand.nextInt(62);
				name[i] =str[t]; 
			}
			GuestName = new String(name);
			//判断这个是否在数据库中存在,待完善
			break;
		}
		return GuestName;
	}
}
