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
					clientTools.sendOnceMessage(str,jtp);
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
					sendThread = clientTools.sendMessage(str,jtp);
				}else {
					jtp.addErrString("�Ƚ��з���������");
				}
			}
		});
		
		//���ڷ��Ͱ�ť����
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientTools.sendThread = null;
				sendThread.stop();
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
	
	public void loadUI() {
		GridBagLayout gl = new GridBagLayout();
		this.setLayout(gl);
		
		JLabel lab = new JLabel("������ip:");
		this.add(lab);
		gl.setConstraints(lab, new GridBag(0, 0).setFill(GridBag.BOTH));
		
		sIp = new JTextField(10);
		sIp.setText(ClientConfig.serviceHost);
		this.add(sIp);
		gl.setConstraints(sIp, new GridBag(1,0,2,1).setFill(GridBag.BOTH));
		
		lab = new JLabel("�������˿�:");
		gl.setConstraints(lab, new GridBag(3,0).setFill(GridBag.BOTH));
		this.add(lab);
		
		sPort = new JTextField(10);
		sPort.setText(ClientConfig.servicePort+"");
		gl.setConstraints(sPort, new GridBag(4,0,2,1).setFill(GridBag.BOTH));
		this.add(sPort);
		
		conBtn = new JButton("����");
		gl.setConstraints(conBtn, new GridBag(6,0).setFill(GridBag.BOTH));
		this.add(conBtn);
		
		conLab = new JLabel();
		gl.setConstraints(conLab, new GridBag(7,0).setFill(GridBag.BOTH));
		this.add(conLab);
		
		jtp = new JTextPaneUP();
		jtp.setEditable(false);//���ɱ༭
		jtp.setPreferredSize(new Dimension(450, 180));//ֻ����������������С
		gl.setConstraints(jtp, new GridBag(0,1,6,1).setFill(GridBag.BOTH));
		jsp = new JScrollPane(jtp);
		this.add(jsp);
		
		delBtn = new JButton("���");
		gl.setConstraints(delBtn, new GridBag(6,1,5,1).setFill(GridBag.BOTH));
		this.add(delBtn);

		text = new JTextField(25);
		gl.setConstraints(text, new GridBag(0,2,4,1).setFill(GridBag.BOTH));
		this.add(text);
		
		sendBtn = new JButton("����");
		gl.setConstraints(sendBtn, new GridBag(6,2).setFill(GridBag.BOTH));
		this.add(sendBtn);
		
		sendConBtn = new JButton("��������");
		gl.setConstraints(sendConBtn, new GridBag(6,2).setFill(GridBag.BOTH));
		this.add(sendConBtn);
		
		stopBtn = new JButton("���ٷ���");
		gl.setConstraints(stopBtn, new GridBag(7,2).setFill(GridBag.BOTH));
		this.add(stopBtn);
		
	}
}
