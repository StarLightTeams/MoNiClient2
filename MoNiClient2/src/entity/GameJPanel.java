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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import clienttool.ClientTools;
import config.ClientConfig;

public class GameJPanel extends JPanel{
	
	JTextField sIp;
	JTextField sPort;
	JTextField text;
	JButton sendBtn;
	JButton conBtn;
	JTextPaneUP jtp;
	JScrollPane jsp;
	JButton delBtn;
	JButton sendConBtn;
	JLabel conLab;
	JButton stopBtn;
	
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
					clientTools.sendOnceMessage(str,jtp);
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
					sendThread = clientTools.sendMessage(str,jtp);
				}else {
					jtp.addErrString("先进行服务器连接");
				}
			}
		});
		
		//不在发送按钮监听
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientTools.sendThread = null;
				sendThread.stop();
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
	
	public void loadUI() {
		GridBagLayout gl = new GridBagLayout();
		this.setLayout(gl);
		
		JLabel lab = new JLabel("服务器ip:");
		this.add(lab);
		gl.setConstraints(lab, new GridBag(0, 0).setFill(GridBag.BOTH));
		
		sIp = new JTextField(10);
		sIp.setText(ClientConfig.serviceHost);
		this.add(sIp);
		gl.setConstraints(sIp, new GridBag(1,0,2,1).setFill(GridBag.BOTH));
		
		lab = new JLabel("服务器端口:");
		gl.setConstraints(lab, new GridBag(3,0).setFill(GridBag.BOTH));
		this.add(lab);
		
		sPort = new JTextField(10);
		sPort.setText(ClientConfig.servicePort+"");
		gl.setConstraints(sPort, new GridBag(4,0,2,1).setFill(GridBag.BOTH));
		this.add(sPort);
		
		conBtn = new JButton("连接");
		gl.setConstraints(conBtn, new GridBag(6,0).setFill(GridBag.BOTH));
		this.add(conBtn);
		
		conLab = new JLabel();
		gl.setConstraints(conLab, new GridBag(7,0).setFill(GridBag.BOTH));
		this.add(conLab);
		
		jtp = new JTextPaneUP();
		jtp.setEditable(false);//不可编辑
		jtp.setPreferredSize(new Dimension(450, 180));//只有这个方法设置其大小
		gl.setConstraints(jtp, new GridBag(0,1,6,1).setFill(GridBag.BOTH));
		jsp = new JScrollPane(jtp);
		this.add(jsp);
		
		delBtn = new JButton("清空");
		gl.setConstraints(delBtn, new GridBag(6,1,5,1).setFill(GridBag.BOTH));
		this.add(delBtn);

		text = new JTextField(25);
		gl.setConstraints(text, new GridBag(0,2,4,1).setFill(GridBag.BOTH));
		this.add(text);
		
		sendBtn = new JButton("发送");
		gl.setConstraints(sendBtn, new GridBag(6,2).setFill(GridBag.BOTH));
		this.add(sendBtn);
		
		sendConBtn = new JButton("连续发送");
		gl.setConstraints(sendConBtn, new GridBag(6,2).setFill(GridBag.BOTH));
		this.add(sendConBtn);
		
		stopBtn = new JButton("不再发送");
		gl.setConstraints(stopBtn, new GridBag(7,2).setFill(GridBag.BOTH));
		this.add(stopBtn);
		
	}
}
