package config;

import java.awt.Toolkit;

public class ClientConfig {
	public static String serviceHost = "127.0.0.1";
	public static int servicePort = 10020; 
	
	//��Ļ���
	public static final int WIDTH = 900;
	//��Ļ�߶�
	public static final int HEIGHT = 500;	
	//��Ļ��ʼ����λ��
	public static final int STARTX = 10;
	public static final int STARTY = 10;
	
	//��¼�ɹ�
	public static final int loginInSuccess = 1;
	//��¼ʧ��
	public static final int loginInError = 2;
	
	//�˳���¼�ɹ�
	public static final int loginOutSuccess = 3;
	//�˳���¼ʧ��
	public static final int loginOutError = 4;
	
	//ע��ɹ�
	public static final int registerSuccess = 5;
	//ע��ʧ��
	public static final int registerError = 6;
	
	//��ҵ�¼����(0�ο�  1QQ 2΢��)
	public static final int login = 4;
	public static final int weChat = 2;
	public static final int QQ = 1;
	public static final int Guest = 0;
	
	//�οͻ�ȡ����
	public static final int guestNameSuccess = 7;
	
	//��ͨЭ��
	public static final int common = 8;

}

