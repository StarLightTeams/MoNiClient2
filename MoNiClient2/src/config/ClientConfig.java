package config;

import java.awt.Toolkit;

public class ClientConfig {
	public static String serviceHost = "127.0.0.1";
	public static int servicePort = 10020; 
	
	//屏幕宽度
	public static final int WIDTH = 900;
	//屏幕高度
	public static final int HEIGHT = 500;	
	//屏幕初始出现位置
	public static final int STARTX = 10;
	public static final int STARTY = 10;
	
	//连接成功
	public static final int connectSuccess = 12;
	
	//登录成功
	public static final int loginInSuccess = 1;
	//登录失败
	public static final int loginInError = 2;
	
	//退出登录成功
	public static final int loginOutSuccess = 3;
	//退出登录失败
	public static final int loginOutError = 4;
	
	//注册成功
	public static final int registerSuccess = 5;
	//注册失败
	public static final int registerError = 6;
	
	//玩家登录类型(0游客  1QQ 2微信)
	public static final int login = 4;
	public static final int weChat = 2;
	public static final int QQ = 1;
	public static final int Guest = 0;
	
	//游客获取名字
	public static final int guestNameSuccess = 7;
	
	//普通协议
	public static final int common = 8;
	
	//游戏准备失败
	public static final int gamePreparingError = 9;
	
	//验证全部玩家状态失败
	public static final int verifyStateErr = 10;
	
	//验证全部玩家
	public static final int verifyState = 11;
	
	//等待其他玩家
	public static final int waitOtherPeople = 13;
	
	//游戏正式开始
	public static final int gameStart = 14;
	

}

