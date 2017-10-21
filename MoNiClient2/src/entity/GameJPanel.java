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
	JTextPaneUP jtp;
	JScrollPane jsp;
	JButton delBtn;
	JButton filtHeartBtn; 
	
	//��¼
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
				if(loginFlag) {
					//������ ,�д����ƹرտͻ���
					try {
						socket.close();
						conBtn.setText("����");
						conLab.setText("");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else {
					if(serviceConnect(ip,port)){
						conLab.setText("���ӳɹ�");
						loginFlag = true;
						conBtn.setText("�Ͽ�");
						clientName = socket.getInetAddress().toString().substring(1)+":"+socket.getPort()+":"+nameFlag; 
						clientTools = new ClientTools(socket,clientName);
						//������Ϣ
						clientTools.receiveMessage(jtp);
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
				if(!loginFlag){
					conLab.setText("");
				}
			}
		});
		
		//���Ͱ�ť����
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loginFlag) {
					String str = text.getText();
					clientTools.sendOnceMessage(new UnknownCommand(),str,jtp);
				}else {
					jtp.addErrString("�Ƚ��з���������");
				}
			}
		});
		
		//�������ͼ���
		sendConBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loginFlag) {
					String str = text.getText();
					sendThread = clientTools.sendMessage(new UnknownCommand(),str,jtp);
				}else {
					jtp.addErrString("�Ƚ��з���������");
				}
			}
		});
		
		//���ڷ��Ͱ�ť����
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loginFlag) {
					clientTools.sendThread = null;
					sendThread.stop();
				}else {
					jtp.addErrString("�Ƚ��з���������");
				}
			}
		});
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
		
		loginBtn = new JButton("����");
		this.add(loginBtn);
		loginBtn.setBounds(370,50,60,30);
		
		loginInfo = new JLabel();
		this.add(loginInfo);
		loginInfo.setBounds(460,50,80,30);
		
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
		delBtn.setBounds(440,90,100,30);
		
		filtHeartBtn = new JButton("��������");
		this.add(filtHeartBtn);
		filtHeartBtn.setBounds(440,130,100,30);

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
	
	//���Ӳ���
	public void conLayout() {
		
	}
	
}
