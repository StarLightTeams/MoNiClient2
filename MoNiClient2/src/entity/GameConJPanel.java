package entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.AcceptPendingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.LoggingMXBean;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

import clienttool.ClientTools;
import config.ClientConfig;
import config.entity.Log;
import entity.info.Info;
import entity.player.Player;
import entity.rooms.Room;
import entity.rule.agreement.ConnectCommand;
import entity.rule.agreement.GameLoadingCommand;
import entity.rule.agreement.GuestLoginCommand;
import entity.rule.agreement.LoginCommand;
import entity.rule.agreement.LoginOutCommand;
import entity.rule.agreement.RegisterCommand;
import entity.rule.agreement.GamePreparingCommand;
import entity.rule.agreement.UnknownCommand;
import tool.CommonTools;
import tool.FileTools;
import tool.JsonTools;
import tool.ThreadTools;

public class GameConJPanel extends JPanel{
	
	//����
	JTextField sIp;
	JTextField sPort;
	JButton conBtn;
	JLabel conLab;

	//����
	JTextField text;
	JButton sendBtn;
	JButton sendConBtn;
	JButton stopBtn;
	
	//�м��ı���
	public static JTextPaneUP jtp;
	JScrollPane jsp;
	JButton delBtn;
	JButton filtHeartBtn; 
	
	//��¼
	static String GuestName;
	static int GuestId;
	static JTextField userId;
	JTextField userPassword;
	JButton registerBtn;
	static int loginType;
	static JButton loginBtn;
	static JButton GuestLoginBtn;
	static JButton qqLoginBtn;
	static JButton weCharLoginBtn;
	static JLabel loginInfo;
	
	//��ʼ��Ϸ
	static JButton gameStartBtn; //��¼��������ҿ�ʼ��Ϸ
	JComboBox gameType;
	static String roomId;//������Ϸ�ķ����
	
	//���ӱ�־
	public boolean conFlag = false;
	//��¼��־
	static boolean loginFlag = false;
	Socket socket = null;
	
	public static ClientTools clientTools = null;
	String nameFlag = "2";
	String serverName; //������id ip+':'+port+':'+nameFlag
	public static String clientName; //�ͻ���id ip+':'+port
	Thread sendThread = null;
	
	static FileTools fileTools;
	
	//�������Ϸ����
	static Room room;
	//��Ϸ����
	GameJPanel gameJPanel;
	
	//�ͻ��˵�ip��port
	static String cIp;
	static String cPort;
	
	public GameConJPanel() {
		//���ؽ���(��ʼ��������ip�Ͷ˿�)
		loadUI();
		doThing();
	}
	
	public void doThing() {
		
		final String ip = sIp.getText().trim();
		final int port = Integer.parseInt(sPort.getText().trim());
		
		//���Ӱ�ť����
		conBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					clientTools.closeClient(jtp,clientName);
					conBtn.setText("����");
					conLab.setText("");
					conFlag = false;
				}else {
					if(serviceConnect(ip,port)){
						conLab.setText("���ӳɹ�");
						conFlag = true;
						conBtn.setText("�Ͽ�");
						serverName = socket.getInetAddress().toString().substring(1)+":"+socket.getPort()+":"+nameFlag;
						clientTools = new ClientTools(socket,serverName,clientName);
						String path = this.getClass().getClassLoader().getResource("").getPath(); // �õ����� ClassPath�ľ���URI·��
						System.out.println(path);
						fileTools = new FileTools(path+"/GuestName.txt");
						//������Ϣ
						clientTools.receiveMessage(jtp);
						//�������û���ο��û������ο�����������
						String data= fileTools.readFile();
						if("".equals(data)) {	//Ϊ��
							clientTools.sendOnceMessage(new GuestLoginCommand(),JsonTools.getString(new Info("")), jtp);
						}else {
							Map<String,String> maps = JsonTools.parseData(data);
							GuestName = maps.get("playerName");
							GuestId = Integer.parseInt(maps.get("playerId"));
							userId.setText(GuestName);
						}
					}else {
						conLab.setText("����ʧ��");
					}
				}
			}
		});
		
		
		//��հ�ť����
		delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtp.clear();
				if(!conFlag){
					conLab.setText("");
				}
			}
		});
		
		//���Ͱ�ť����
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					String str = text.getText();
					clientTools.sendOnceMessage(new ConnectCommand(),str,jtp);
				}else {
					jtp.addErrString("�Ƚ��з���������");
				}
			}
		});
		
		//�������ͼ���
		sendConBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					String str = text.getText();
					sendThread = clientTools.sendMessage(new ConnectCommand(),str,jtp);
				}else {
					jtp.addErrString("�Ƚ��з���������");
				}
			}
		});
		
		//���ڷ��Ͱ�ť����
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
//					ThreadTools.remove(serverName);
					clientTools.sendThread = null;
					sendThread.stop();
				}else {
					jtp.addErrString("�Ƚ��з���������");
				}
			}
		});
		
		//��¼����
		loginBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					if(!loginFlag) {
						//�ж��û��������Ƿ�Ϊ��
						String userName = userId.getText().trim();
						String password = userPassword.getText().trim();
						if(!userName.equals("")&&!password.equals("")) {
							loginType = ClientConfig.login;
							//���͵�¼Э����Ϣ
							clientTools.sendOnceMessage(new LoginCommand(), JsonTools.getString(new Player(userName,password,loginType,cIp+":"+cPort)), jtp);
						}else{
							jtp.addErrString("�û��������벻��Ϊ��");
						}
					}else {
						//���ͶϿ�Э����Ϣ
						clientTools.sendOnceMessage(new LoginOutCommand(),"�˳���¼", jtp);
					}
				}else {
					jtp.addErrString("�Ƚ��з���������");
				}
			}
		});
		
		//qq��¼����
		qqLoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					if(!loginFlag) {
						//�ж��û��������Ƿ�Ϊ��
						String userName = userId.getText().trim();
						String password = userPassword.getText().trim();
						if(!userName.equals("")&&!password.equals("")) {
							loginType = ClientConfig.QQ;
							//���͵�¼Э����Ϣ
							clientTools.sendOnceMessage(new LoginCommand(), JsonTools.getString(new Player(userName,password,loginType,cIp+":"+cPort)), jtp);
						}else{
							jtp.addErrString("�û��������벻��Ϊ��");
						}
					}else {
						//���ͶϿ�Э����Ϣ
						clientTools.sendOnceMessage(new LoginOutCommand(),"�˳���¼", jtp);
					}
				}else {
					jtp.addErrString("�Ƚ��з���������");
				}
			}
		});
		
		weCharLoginBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					if(!loginFlag) {
						//�ж��û��������Ƿ�Ϊ��
						String userName = userId.getText().trim();
						String password = userPassword.getText().trim();
						if(!userName.equals("")&&!password.equals("")) {
							loginType = ClientConfig.weChat;
							//���͵�¼Э����Ϣ
							clientTools.sendOnceMessage(new LoginCommand(), JsonTools.getString(new Player(userName,password,loginType,cIp+":"+cPort)), jtp);
						}else{
							jtp.addErrString("�û��������벻��Ϊ��");
						}
					}else {
						//���ͶϿ�Э����Ϣ
						clientTools.sendOnceMessage(new LoginOutCommand(),"�˳���¼", jtp);
					}
				}else {
					jtp.addErrString("�Ƚ��з���������");
				}
			}
		});
		
		//�ο͵�¼
		GuestLoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					userId.setText(GuestName);
					if(!loginFlag) {
						//�ж��û��������Ƿ�Ϊ��
						String userName = userId.getText().trim();
						if(!userName.equals("")&&GuestId!=0) {
							loginType = ClientConfig.Guest;
							//���͵�¼Э����Ϣ
							clientTools.sendOnceMessage(new LoginCommand(), JsonTools.getString(new Player(GuestId,"1",loginType,cIp+":"+cPort)), jtp);
						}else{
							jtp.addErrString("�û��������벻��Ϊ��");
						}
					}else {
						//���ͶϿ�Э����Ϣ
						clientTools.sendOnceMessage(new LoginOutCommand(),"�˳���¼", jtp);
					}
				}else {
					jtp.addErrString("�Ƚ��з���������");
				}
			}
		});
		
		//ע��
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					//�ж��û��������Ƿ�Ϊ��
					String userName = userId.getText().trim();
					String password = userPassword.getText().trim();
					if(!userName.equals("")&&!password.equals("")) {
						//����ע����Ϣ��������(������ҵ�һЩ��ϢPlayer)
						clientTools.sendOnceMessage(new RegisterCommand() ,JsonTools.getString(new Player(userName,password,cIp+":"+cPort)), jtp);
					}else{
						jtp.addErrString("�û��������벻��Ϊ��");
					}
				}else {
					jtp.addErrString("�Ƚ��з���������");
				}
			}
		});
		
		//��ʼ��Ϸ
		gameStartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtp.addString("����ƥ�䷿��",Color.GREEN);
				String gType = (String) gameType.getSelectedItem();
				Map<String,String> map = new HashMap();
				map.put("gameType", CommonTools.selectGameType(gType));
				map.put("clientId", cIp+":"+cPort);
				//����(������)
				map.put("roomKey", 1+"");
				String data = JsonTools.getData(map);
				clientTools.sendOnceMessage(new GamePreparingCommand(),JsonTools.getString(new Info("��ʼ��Ϸ",data)), jtp);
			}
		});
		
	}
	
	//���ܻص�����
	public static void callBack(int type) {
		if(type==ClientConfig.loginInSuccess) {
			loginInfo.setText("��¼�ɹ�");
			switch(loginType) {
			case ClientConfig.QQ:
				qqLoginBtn.setText("�˳�");
				break;
			case ClientConfig.weChat:
				weCharLoginBtn.setText("�˳�");
				break;
			case ClientConfig.Guest:
				GuestLoginBtn.setText("�˳�");
				break;
			case ClientConfig.login:
				loginBtn.setText("�˳�");
				break;
			}
			loginFlag = true;
			gameStartBtn.setEnabled(true);
		}else if (type==ClientConfig.loginInError){
			loginInfo.setText("��¼ʧ��");
		}else if(type == ClientConfig.loginOutSuccess) {
			loginInfo.setText("�˳���¼");
			switch(loginType) {
			case ClientConfig.QQ:
				qqLoginBtn.setText("��¼");
				break;
			case ClientConfig.weChat:
				weCharLoginBtn.setText("��¼");
				break;
			case ClientConfig.Guest:
				GuestLoginBtn.setText("��¼");
				break;
			case ClientConfig.login:
				loginBtn.setText("��¼");
				break;
			}
			loginFlag = false;
		}else if(type == ClientConfig.loginOutError) {
			jtp.addErrString("�˳���¼ʧ��");
		}else if(type == ClientConfig.registerSuccess) {
			loginInfo.setText("ע��ɹ�");
		}
	}
	//���ܻص�����
	public static void callBack(int type,String data) {
		if(type == ClientConfig.connectSuccess) {
			Map<String,String> maps = JsonTools.parseData(data);
			cIp = maps.get("cIp");
			cPort = maps.get("cPort");
			clientName = cIp+":"+cPort;
			System.out.println("cIp="+cIp+",cPort="+cPort);
		}else if(type == ClientConfig.guestNameSuccess) {
			//��ֵ���浽�ͻ��˱���
			fileTools.writeFile(data);
			Map<String, String> maps = JsonTools.parseData(data);
			String guestName = maps.get("playerName");
			int guestId = Integer.parseInt(maps.get("playerId"));
			userId.setText(guestName);
			GuestId = guestId;
			GuestName = guestName;
		}else if(type == ClientConfig.registerError) {
			jtp.addErrString(data);
		}else if(type == ClientConfig.common) {
			jtp.addString(data,Color.YELLOW);
		}else if(type == ClientConfig.gamePreparingError) {
			jtp.addErrString(data);
		}else if(type == ClientConfig.verifyStateErr) {
			Map<String, String> maps = JsonTools.parseData(data);
			Iterator entries = maps.entrySet().iterator();
			while(entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				jtp.addErrString("�ͻ���:"+entry.getKey()+",��֤����:"+entry.getValue());
			}
		}else if(type == ClientConfig.verifyState) {
			Map<String, String> maps = JsonTools.parseData(data);
			roomId = maps.get("roomId"); 
			String roomType = maps.get("roomType");
			System.out.println("roomId="+roomId+",roomType="+roomType);
			//������Ϸ(������)
			jtp.addString("��ʼ��Ϸ");
			//���ͼ������
			clientTools.sendOnceMessage(new GameLoadingCommand(),JsonTools.getString(new Info("�������",data)),jtp);
		}else if(type == ClientConfig.waitOtherPeople) {
			Map<String,String> maps = JsonTools.pasreObjectData(data);
			String headInfo = maps.get("headInfo");
			String dataInfo = maps.get("dataInfo");
			room = (Room) JsonTools.parseJson(dataInfo);
			Log.d(room.toString());
			jtp.addString(headInfo, Color.GREEN);
		}else if(type == ClientConfig.gameStart){
			jtp.addString(data,Color.green);
		}
				
	}
	
	/**
	 * ���ӷ�����
	 */
	public boolean serviceConnect(String ip,int port) {
		try {
			socket = new Socket(ip,port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			jtp.addErrString("δ�ҵ�������ip/port"+e.getMessage());
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			jtp.addErrString("����ʧ��:"+e.getMessage());
			return false;
		}
		System.out.println("00000000");
		return true;
	}
	
	/**
	 * ���ؿؼ�
	 */
	public void loadUI() {
		//����Ϊ�ղ���(Ҳ������Բ���)
		this.setLayout(null);
		
		JLabel lab = new JLabel("������ip:");
		this.add(lab);
		lab.setBounds(10,10,70,30);
		
		sIp = new JTextField(10);
		sIp.setText(ClientConfig.serviceHost);
		this.add(sIp);
		sIp.setBounds(70,10,100,30);
		
		lab = new JLabel("�������˿�:");
		this.add(lab);
		lab.setBounds(180,10,70,30); 
		
		sPort = new JTextField(10);
		sPort.setText(ClientConfig.servicePort+"");
		this.add(sPort);
		sPort.setBounds(260,10,100,30);
		
		conBtn = new JButton("����");
		this.add(conBtn);
		conBtn.setBounds(370,10,60,30);
		
		conLab = new JLabel();
		this.add(conLab);
		conLab.setBounds(460,10,80,30);
	
		lab = new JLabel("�û���id:");
		this.add(lab);
		lab.setBounds(10, 50, 70, 30);
		
		userId= new JTextField(10);
		this.add(userId);
		userId.setBounds(70,50,100,30);
		
		lab = new JLabel("�û�������:");
		this.add(lab);
		lab.setBounds(180,50,70,30); 
		
		userPassword = new JTextField(10);
		this.add(userPassword);
		userPassword.setBounds(260,50,100,30);
		
		registerBtn = new JButton("ע��");
		this.add(registerBtn);
		registerBtn.setBounds(370, 50, 60, 30);
		
		loginBtn = new JButton("��¼");
		this.add(loginBtn);
		loginBtn.setBounds(440, 50, 60, 30);
		
		qqLoginBtn = new JButton("QQ����");
		this.add(qqLoginBtn);
		qqLoginBtn.setBounds(510,50,90,30);
		
		weCharLoginBtn = new JButton("΢�ŵ�¼");
		this.add(weCharLoginBtn);
		weCharLoginBtn.setBounds(610,50,90,30);
		
		GuestLoginBtn = new JButton("�ο͵�¼");
		this.add(GuestLoginBtn);
		GuestLoginBtn.setBounds(710,50,90,30);
		
		loginInfo = new JLabel();
		this.add(loginInfo);
		loginInfo.setBounds(810,50,80,30);
		
		jtp = new JTextPaneUP();
		jtp.setEditable(false);//���ɱ༭
		//jtp��ֱ������һֱ������
		DefaultCaret caret = (DefaultCaret) jtp.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//		jtp.setPreferredSize(new Dimension(450, 180));//ֻ����������������С
		jsp = new JScrollPane(jtp);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp);
		jsp.setBounds(10,90,420,280);
		
		delBtn = new JButton("�������");
		this.add(delBtn);
		delBtn.setBounds(440,90,90,30);
		
		filtHeartBtn = new JButton("��������");
		this.add(filtHeartBtn);
		filtHeartBtn.setBounds(440,130,90,30);
		
		gameStartBtn = new JButton("��ʼ��Ϸ");
		this.add(gameStartBtn);
		gameStartBtn.setBounds(440, 170, 90, 30);
		gameStartBtn.setEnabled(false);
		
		String[] types = {"˫����ͨ��Ϸ","˫��������Ϸ","������ͨ��Ϸ","����������Ϸ"};	
		gameType = new JComboBox(types);
		this.add(gameType);
		gameType.setBounds(540, 170, 90, 30);
		
		gameJPanel = new GameJPanel(jtp);
		this.add(gameJPanel);
		gameJPanel.setBounds(640, 90,ClientConfig.GAMEWIDTH, ClientConfig.GAMEHEIGHT);
		gameJPanel.setBackground(Color.WHITE);
		gameJPanel.setBorder(BorderFactory.createEtchedBorder());

		text = new JTextField(25);
		this.add(text);
		text.setBounds(10,375,260,30);
		
		sendBtn = new JButton("����");
		this.add(sendBtn);
		sendBtn.setBounds(280,375,60,30);
		
		sendConBtn = new JButton("��������");
		this.add(sendConBtn);
		sendConBtn.setBounds(350,375,90,30);
		
		stopBtn = new JButton("���ٷ���");
		this.add(stopBtn);
		stopBtn.setBounds(450, 375, 90, 30);
	}
	
}
