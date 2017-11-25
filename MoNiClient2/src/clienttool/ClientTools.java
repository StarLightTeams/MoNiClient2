package clienttool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JTextPane;

import config.ClientConfig;
import config.entity.Log;
import entity.GameConJPanel;
import entity.JTextPaneUP;
import entity.agrement.CommandID;
import entity.agrement.ICommand;
import entity.info.Info;
import entity.rule.agreement.ConnectCommand;
import entity.rule.agreement.DisconnectCommand;
import thread.ThreadException;
import tool.JsonTools;
import tool.ThreadTools;
import tool.agreement.AgreeMentTools;
import tool.agreement.DataBuffer;

/**
 * �ͻ��˹���
 */
public class ClientTools{
	
	public Socket s;
	public Thread sendThread;
	public Thread sendThread1;
	public Thread receiveThread;
	public String serverName;
	public String clientName;
	public boolean isLive = true;
	
	public ClientTools(Socket s,String serverName,String clientName) {
		this.s = s;
		this.serverName = serverName;
		this.clientName = clientName;
	}
	
	/*
	 * �رտͻ���
	 */
	public void closeClient(JTextPaneUP jtp,String clientName){
		Map<String,String> maps = new HashMap<String, String>();
		maps.put("clientId", clientName);
		//���ͶϿ�Э���������
		sendOnceMessage(new DisconnectCommand(), JsonTools.getString(new Info("�ͻ��˶Ͽ�����",JsonTools.getData(maps))), jtp);
	}
	
	//��������
	public Thread receiveMessage(JTextPaneUP jtp) {
		if(receiveThread == null) {
			receiveThread = new Thread(new Receive(jtp),serverName+"r");
			receiveThread.setUncaughtExceptionHandler(new ThreadException());
		}
		receiveThread.start();
		return receiveThread;
	}
	
	//������������
	public Thread sendMessage(ICommand iCommand,String str,JTextPaneUP jtp) {
		if(sendThread==null) {
			sendThread = new Thread(new Send(iCommand,str,jtp),serverName+"s");
			sendThread.setUncaughtExceptionHandler(new ThreadException());
		}
		sendThread.start();
		return sendThread;
	}
	
	//��������
	public synchronized void sendOnceMessage(ICommand iCommand,String str,JTextPaneUP jtp) {
		try {
			jtp.addString("["+serverName+"s"+"]:"+str);
			OutputStream os = s.getOutputStream();
			DataBuffer data = createAgreeMentMessage(iCommand, str);
			os.write(data.readByte());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//��������
	class Receive implements Runnable{
		
		JTextPaneUP jtp;
		public Receive(JTextPaneUP jtp) {
			this.jtp = jtp;
		}

		public synchronized void run() {
			while(isLive) {
				try {
					byte[] b = new byte[45056];
					InputStream is = s.getInputStream();
					int len = is.read(b);
//					System.out.println("len="+len);
					if(len == -1) { //֤���ڷ��������Ѿ��ѿͻ��˹ر�
						//�ر��߳�
						isLive = false;
//						s.close();
						Log.d("�رտͻ���");
					}else {
						DataBuffer data = getAgreeMentMessage(b);
						ICommand icommand = AgreeMentTools.getICommand(data);
						jtp.addString("["+serverName+"r"+"]:"+new String(icommand.body));
						Info info = (Info) JsonTools.parseJson(new String(icommand.body));
						if(info!=null) {
							String headInfo = info.getHeadInfo();
							String dataInfo = info.getDataInfo();
							System.out.println("headInfo="+headInfo+",dataInfo="+dataInfo);
							if(icommand.header.id == CommandID.Connect) {//����Э��
								if("���ӳɹ�".equals(headInfo)) {
									GameConJPanel.callBack(ClientConfig.connectSuccess,dataInfo);
								}
							}else if (icommand.header.id == CommandID.Login) {//��¼Э��
//							System.out.println("hhhhhhhhhheadInfo="+headInfo);
								if("��¼�ɹ�".equals(headInfo)) {
									GameConJPanel.callBack(ClientConfig.loginInSuccess);
								}else if("��¼ʧ��".equals(headInfo)) {
									GameConJPanel.callBack(ClientConfig.loginInError);
								}
							}else if(icommand.header.id == CommandID.LoginOut) { //�˳���¼Э��
								if("�˳��ɹ�".equals(headInfo)) {
									GameConJPanel.callBack(ClientConfig.loginOutSuccess);
								}else if("�˳�ʧ��".equals(headInfo)) {
									GameConJPanel.callBack(ClientConfig.loginOutError);
								}
							}else if(icommand.header.id == CommandID.Register) {//ע��Э��
								if("ע��ɹ�".equals(headInfo)) {
									GameConJPanel.callBack(ClientConfig.registerSuccess);
								}else if("ע��ʧ��".equals(headInfo)) {
									GameConJPanel.callBack(ClientConfig.registerError, dataInfo);
								}
							}else if(icommand.header.id == CommandID.GuestLogin) {//�ο͵�¼Э��
								//�õ������������ο�����,��ŵ�����,�´οͻ����ȴӱ�����ȡ�ο�����
								GameConJPanel.callBack(ClientConfig.guestNameSuccess,dataInfo);
							}else if(icommand.header.id == CommandID.GeneralInformation) {//��ͨ��ϢЭ��
								GameConJPanel.callBack(ClientConfig.common,dataInfo);
							}else if(icommand.header.id == CommandID.GamePreparingError) {//��Ϸ׼������Э��
								GameConJPanel.callBack(ClientConfig.gamePreparingError,dataInfo);
							}else if(icommand.header.id == CommandID.VerifyStateErr) {//��֤����Э��
								GameConJPanel.callBack(ClientConfig.verifyStateErr,dataInfo);
							}else if(icommand.header.id == CommandID.VerifyState) {//��֤Э��
								GameConJPanel.callBack(ClientConfig.verifyState,dataInfo);
							}else if(icommand.header.id == CommandID.WaitOtherPeople) {//�ȴ��������
								Map<String,String> maps = new HashMap<String,String>();
								maps.put("headInfo", headInfo);
								maps.put("dataInfo",dataInfo);
								GameConJPanel.callBack(ClientConfig.waitOtherPeople,JsonTools.getData(maps));
							}else if(icommand.header.id == CommandID.GameStart) {//��Ϸ��ʼ
								GameConJPanel.callBack(ClientConfig.gameStart,headInfo);
							}else if(icommand.header.id == CommandID.GameData) {
								GameConJPanel.callBack(ClientConfig.gameData,dataInfo);
							}else if(icommand.header.id == CommandID.GameDataBoard) {
								GameConJPanel.callBack(ClientConfig.gameDataBoard,dataInfo);
							}
						}
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
			while(isLive) {
				try {
					jtp.addString("["+serverName+"s"+"]:"+str);
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
	 * ����Э����Ϣ
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
	 * ����Э����Ϣ
	 * @param bytes
	 * @return
	 */
	public DataBuffer getAgreeMentMessage(byte[] bytes) {
		DataBuffer data = new DataBuffer();
		char[] c =data.getChars(bytes);
		return data;
	}
	
	/**
	 * ����ο�����
	 */
	public static String getGuestPeopleName() {
		//��ĸ����(����a~z����ĸ������)
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
			//�ж�����Ƿ������ݿ��д���,������
			break;
		}
		return GuestName;
	}
}
