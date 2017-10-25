package config;

import java.awt.Toolkit;

public class ClientConfig {
	public static String serviceHost = "127.0.0.1";
	public static int servicePort = 10020; 
	
	//屏幕宽度
	public static final int WIDTH = 550;
	//屏幕高度
	public static final int HEIGHT = 500;	
	//屏幕初始出现位置
	public static final int STARTX = 10;
	public static final int STARTY = 10;
	
	//登录成功
	public static final int loginInSuccess = 1;
	//登录失败
	public static final int loginInError = 2;
	
	//退出登录成功
	public static final int loginOutSuccess = 3;
	//退出登录失败
	public static final int loginOutError = 4;

}

