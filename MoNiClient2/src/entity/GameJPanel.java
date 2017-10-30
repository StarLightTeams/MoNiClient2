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
import java.util.logging.LoggingMXBean;

import javax.swing.JButton;
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
import entity.player.Player;
import entity.rule.agreement.ConnectCommand;
import entity.rule.agreement.GuestLoginCommand;
import entity.rule.agreement.LoginCommand;
import entity.rule.agreement.LoginOutCommand;
import entity.rule.agreement.RegisterCommand;
import entity.rule.agreement.UnknownCommand;
import tool.FileTools;
import tool.JsonTools;

public class GameJPanel extends JPanel{
	
	//连接
	JTextField sIp;
	JTextField sPort;
	JButton conBtn;
	JLabel conLab;

	//发送
	JTextField text;
	JButton sendBtn;
	JButton sendConBtn;
	JButton stopBtn;
	
	//中间文本框
	static JTextPaneUP jtp;
	JScrollPane jsp;
	JButton delBtn;
	JButton filtHeartBtn; 
	
	//登录
	static String GuestName;
	static JTextField userId;
	JTextField userPassword;
	JButton registerBtn;
	static int loginType;
	static JButton loginBtn;
	static JButton GuestLoginBtn;
	static JButton qqLoginBtn;
	static JButton weCharLoginBtn;
	static JLabel loginInfo;
	
	//连接标志
	boolean conFlag = false;
	//登录标志
	static boolean loginFlag = false;
	Socket socket = null;
	
	ClientTools clientTools = null;
	String nameFlag = "2";
	String clientName;
	Thread sendThread = null;
	
	static FileTools fileTools;
	
	public GameJPanel() {
		//加载界面(初始化服务器ip和端口)
		loadUI();
		doThing();
	}
	
	public void doThing() {
		
		final String ip = sIp.getText().trim();
		final int port = Integer.parseInt(sPort.getText().trim());
		
		//连接按钮监听
		conBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					//不完整 ,有待完善关闭客户端
					try {
						socket.close();
						conBtn.setText("连接");
						conLab.setText("");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else {
					if(serviceConnect(ip,port)){
						conLab.setText("连接成功");
						conFlag = true;
						conBtn.setText("断开");
						clientName = socket.getInetAddress().toString().substring(1)+":"+socket.getPort()+":"+nameFlag; 
						clientTools = new ClientTools(socket,clientName);
						fileTools = new FileTools("D://GuestName.txt");
						//接受信息
						clientTools.receiveMessage(jtp);
						//如果本地没有游客用户则发送游客名给服务器
						GuestName = fileTools.readFile();
						int k=0;
						if("".equals(GuestName)) {
							System.out.println("k="+k);
							clientTools.sendOnceMessage(new GuestLoginCommand(),"", jtp);
							k++;
						}else {
							userId.setText(GuestName);
						}
					}else {
						conLab.setText("连接失败");
					}
				}
			}
		});
		
		
		//清空按钮监听
		delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtp.clear();
				if(!conFlag){
					conLab.setText("");
				}
			}
		});
		
		//发送按钮监听
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					String str = text.getText();
					clientTools.sendOnceMessage(new ConnectCommand(),str,jtp);
				}else {
					jtp.addErrString("先进行服务器连接");
				}
			}
		});
		
		//连续发送监听
		sendConBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					String str = text.getText();
					sendThread = clientTools.sendMessage(new ConnectCommand(),str,jtp);
				}else {
					jtp.addErrString("先进行服务器连接");
				}
			}
		});
		
		//不在发送按钮监听
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					clientTools.sendThread = null;
					sendThread.stop();
				}else {
					jtp.addErrString("先进行服务器连接");
				}
			}
		});
		
		//登录监听
		loginBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					if(!loginFlag) {
						//判断用户名密码是否为空
						String userName = userId.getText().trim();
						String password = userPassword.getText().trim();
						if(!userName.equals("")&&!password.equals("")) {
							loginType = ClientConfig.login;
							//发送登录协议信息
							clientTools.sendOnceMessage(new LoginCommand(), JsonTools.getString(new Player(userName,password,loginType)), jtp);
						}else{
							jtp.addErrString("用户名或密码不能为空");
						}
					}else {
						//发送断开协议信息
						clientTools.sendOnceMessage(new LoginOutCommand(),"退出登录", jtp);
					}
				}else {
					jtp.addErrString("先进行服务器连接");
				}
			}
		});
		
		//qq登录监听
		qqLoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					if(!loginFlag) {
						//判断用户名密码是否为空
						String userName = userId.getText().trim();
						String password = userPassword.getText().trim();
						if(!userName.equals("")&&!password.equals("")) {
							loginType = ClientConfig.QQ;
							//发送登录协议信息
							clientTools.sendOnceMessage(new LoginCommand(), JsonTools.getString(new Player(userName,password,loginType)), jtp);
						}else{
							jtp.addErrString("用户名或密码不能为空");
						}
					}else {
						//发送断开协议信息
						clientTools.sendOnceMessage(new LoginOutCommand(),"退出登录", jtp);
					}
				}else {
					jtp.addErrString("先进行服务器连接");
				}
			}
		});
		
		weCharLoginBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					if(!loginFlag) {
						//判断用户名密码是否为空
						String userName = userId.getText().trim();
						String password = userPassword.getText().trim();
						if(!userName.equals("")&&!password.equals("")) {
							loginType = ClientConfig.weChat;
							//发送登录协议信息
							clientTools.sendOnceMessage(new LoginCommand(), JsonTools.getString(new Player(userName,password,loginType)), jtp);
						}else{
							jtp.addErrString("用户名或密码不能为空");
						}
					}else {
						//发送断开协议信息
						clientTools.sendOnceMessage(new LoginOutCommand(),"退出登录", jtp);
					}
				}else {
					jtp.addErrString("先进行服务器连接");
				}
			}
		});
		
		//游客登录
		GuestLoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					userId.setText(GuestName);
					if(!loginFlag) {
						//判断用户名密码是否为空
						String userName = userId.getText().trim();
						if(!userName.equals("")) {
							loginType = ClientConfig.Guest;
							//发送登录协议信息
							clientTools.sendOnceMessage(new LoginCommand(), JsonTools.getString(new Player(userName,"1",loginType)), jtp);
						}else{
							jtp.addErrString("用户名或密码不能为空");
						}
					}else {
						//发送断开协议信息
						clientTools.sendOnceMessage(new LoginOutCommand(),"退出登录", jtp);
					}
				}else {
					jtp.addErrString("先进行服务器连接");
				}
			}
		});
		
		//注册
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conFlag) {
					//判断用户名密码是否为空
					String userName = userId.getText().trim();
					String password = userPassword.getText().trim();
					if(!userName.equals("")&&!password.equals("")) {
						//发送注册信息给服务器(比如玩家的一些信息Player)
						clientTools.sendOnceMessage(new RegisterCommand() ,JsonTools.getString(new Player(userName,password)), jtp);
					}else{
						jtp.addErrString("用户名或密码不能为空");
					}
				}else {
					jtp.addErrString("先进行服务器连接");
				}
			}
		});
	}
	
	//接受回调函数
	public static void callBack(int type) {
		if(type==ClientConfig.loginInSuccess) {
			loginInfo.setText("登录成功");
			switch(loginType) {
			case ClientConfig.QQ:
				qqLoginBtn.setText("退出");
				break;
			case ClientConfig.weChat:
				weCharLoginBtn.setText("退出");
				break;
			case ClientConfig.Guest:
				GuestLoginBtn.setText("退出");
				break;
			case ClientConfig.login:
				loginBtn.setText("退出");
				break;
			}
			loginFlag = true;
		}else if (type==ClientConfig.loginInError){
			loginInfo.setText("登录失败");
		}else if(type == ClientConfig.loginOutSuccess) {
			loginInfo.setText("退出登录");
			switch(loginType) {
			case ClientConfig.QQ:
				qqLoginBtn.setText("登录");
				break;
			case ClientConfig.weChat:
				weCharLoginBtn.setText("登录");
				break;
			case ClientConfig.Guest:
				GuestLoginBtn.setText("登录");
				break;
			case ClientConfig.login:
				loginBtn.setText("登录");
				break;
			}
			loginFlag = false;
		}else if(type == ClientConfig.loginOutError) {
			jtp.addErrString("退出登录失败");
		}else if(type == ClientConfig.registerSuccess) {
			loginInfo.setText("注册成功");
		}
	}
	//接受回调函数
	public static void callBack(int type,String data) {
		if(type == ClientConfig.guestNameSuccess) {
			//赋值保存到客户端本地
			fileTools.writeFile(data);
			userId.setText(data);
			GuestName = data;
		}else if(type == ClientConfig.registerError) {
			jtp.addErrString(data);
		}else if(type == ClientConfig.common) {
			jtp.addString(data,Color.YELLOW);
		}
	}
	
	/**
	 * 连接服务器
	 */
	public boolean serviceConnect(String ip,int port) {
		try {
			socket = new Socket(ip,port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			jtp.addErrString("未找到主机名ip/port"+e.getMessage());
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			jtp.addErrString("连接失败:"+e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * 加载控件
	 */
	public void loadUI() {
		//设置为空布局(也就是相对布局)
		this.setLayout(null);
		
		JLabel lab = new JLabel("服务器ip:");
		this.add(lab);
		lab.setBounds(10,10,70,30);
		
		sIp = new JTextField(10);
		sIp.setText(ClientConfig.serviceHost);
		this.add(sIp);
		sIp.setBounds(70,10,100,30);
		
		lab = new JLabel("服务器端口:");
		this.add(lab);
		lab.setBounds(180,10,70,30); 
		
		sPort = new JTextField(10);
		sPort.setText(ClientConfig.servicePort+"");
		this.add(sPort);
		sPort.setBounds(260,10,100,30);
		
		conBtn = new JButton("连接");
		this.add(conBtn);
		conBtn.setBounds(370,10,60,30);
		
		conLab = new JLabel();
		this.add(conLab);
		conLab.setBounds(460,10,80,30);
	
		lab = new JLabel("用户名id:");
		this.add(lab);
		lab.setBounds(10, 50, 70, 30);
		
		userId= new JTextField(10);
		this.add(userId);
		userId.setBounds(70,50,100,30);
		
		lab = new JLabel("用户名密码:");
		this.add(lab);
		lab.setBounds(180,50,70,30); 
		
		userPassword = new JTextField(10);
		this.add(userPassword);
		userPassword.setBounds(260,50,100,30);
		
		registerBtn = new JButton("注册");
		this.add(registerBtn);
		registerBtn.setBounds(370, 50, 60, 30);
		
		loginBtn = new JButton("登录");
		this.add(loginBtn);
		loginBtn.setBounds(440, 50, 60, 30);
		
		qqLoginBtn = new JButton("QQ登入");
		this.add(qqLoginBtn);
		qqLoginBtn.setBounds(510,50,90,30);
		
		weCharLoginBtn = new JButton("微信登录");
		this.add(weCharLoginBtn);
		weCharLoginBtn.setBounds(610,50,90,30);
		
		GuestLoginBtn = new JButton("游客登录");
		this.add(GuestLoginBtn);
		GuestLoginBtn.setBounds(710,50,90,30);
		
		loginInfo = new JLabel();
		this.add(loginInfo);
		loginInfo.setBounds(810,50,80,30);
		
		jtp = new JTextPaneUP();
		jtp.setEditable(false);//不可编辑
		//jtp垂直滚动条一直在下面
		DefaultCaret caret = (DefaultCaret) jtp.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//		jtp.setPreferredSize(new Dimension(450, 180));//只有这个方法设置其大小
		jsp = new JScrollPane(jtp);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp);
		jsp.setBounds(10,90,420,280);
		
		delBtn = new JButton("清空数据");
		this.add(delBtn);
		delBtn.setBounds(440,90,90,30);
		
		filtHeartBtn = new JButton("过滤心跳");
		this.add(filtHeartBtn);
		filtHeartBtn.setBounds(440,130,90,30);

		text = new JTextField(25);
		this.add(text);
		text.setBounds(10,375,260,30);
		
		sendBtn = new JButton("发送");
		this.add(sendBtn);
		sendBtn.setBounds(280,375,60,30);
		
		sendConBtn = new JButton("连续发送");
		this.add(sendConBtn);
		sendConBtn.setBounds(350,375,90,30);
		
		stopBtn = new JButton("不再发送");
		this.add(stopBtn);
		stopBtn.setBounds(450, 375, 90, 30);
	}
	
}
