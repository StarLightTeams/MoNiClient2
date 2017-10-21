package entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.AcceptPendingException;

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
import entity.rule.agreement.UnknownCommand;

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
	JTextPaneUP jtp;
	JScrollPane jsp;
	JButton delBtn;
	JButton filtHeartBtn; 
	
	//登录
	JTextField userId;
	JTextField userPassword;
	JButton loginBtn;
	JLabel loginInfo;
	
	boolean loginFlag = false;
	boolean loopFlag = true;
	Socket socket = null;
	
	ClientTools clientTools = null;
	String nameFlag = "2";
	String clientName;
	Thread sendThread = null;
	
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
				if(loginFlag) {
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
						loginFlag = true;
						conBtn.setText("断开");
						clientName = socket.getInetAddress().toString().substring(1)+":"+socket.getPort()+":"+nameFlag; 
						clientTools = new ClientTools(socket,clientName);
						//接受信息
						clientTools.receiveMessage(jtp);
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
				if(!loginFlag){
					conLab.setText("");
				}
			}
		});
		
		//发送按钮监听
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loginFlag) {
					String str = text.getText();
					clientTools.sendOnceMessage(new UnknownCommand(),str,jtp);
				}else {
					jtp.addErrString("先进行服务器连接");
				}
			}
		});
		
		//连续发送监听
		sendConBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loginFlag) {
					String str = text.getText();
					sendThread = clientTools.sendMessage(new UnknownCommand(),str,jtp);
				}else {
					jtp.addErrString("先进行服务器连接");
				}
			}
		});
		
		//不在发送按钮监听
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loginFlag) {
					clientTools.sendThread = null;
					sendThread.stop();
				}else {
					jtp.addErrString("先进行服务器连接");
				}
			}
		});
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
		
		loginBtn = new JButton("登入");
		this.add(loginBtn);
		loginBtn.setBounds(370,50,60,30);
		
		loginInfo = new JLabel();
		this.add(loginInfo);
		loginInfo.setBounds(460,50,80,30);
		
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
		delBtn.setBounds(440,90,100,30);
		
		filtHeartBtn = new JButton("过滤心跳");
		this.add(filtHeartBtn);
		filtHeartBtn.setBounds(440,130,100,30);

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
	
	//连接布局
	public void conLayout() {
		
	}
	
}
