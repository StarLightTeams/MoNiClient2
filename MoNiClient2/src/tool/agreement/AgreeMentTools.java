package tool.agreement;


import entity.agrement.CommandID;
import entity.agrement.ICommand;
import entity.rule.agreement.ConnectCommand;
import entity.rule.agreement.DisconnectCommand;
import entity.rule.agreement.GameDataBoardCommand;
import entity.rule.agreement.GameDataCommand;
import entity.rule.agreement.GameLoadingCommand;
import entity.rule.agreement.GamePreparingCommand;
import entity.rule.agreement.GamePreparingErrorCommand;
import entity.rule.agreement.GameStartCommand;
import entity.rule.agreement.GeneralInformationCommand;
import entity.rule.agreement.GuestLoginCommand;
import entity.rule.agreement.HeartCommand;
import entity.rule.agreement.LoginCommand;
import entity.rule.agreement.LoginOutCommand;
import entity.rule.agreement.RegisterCommand;
import entity.rule.agreement.UnknownCommand;
import entity.rule.agreement.VerifyStateCommand;
import entity.rule.agreement.VerifyStateErrCommand;
import entity.rule.agreement.WaitOtherPeopleCommand;

/**
 * 协议工具
 */
public class AgreeMentTools {
	/**
	 * 判断协议类型,返回该协议实例
	 */
	public static ICommand judgeICommand(int id){
		if(id == CommandID.Connect){//连接协议
			return new ConnectCommand(id);
		}else if(id == CommandID.GuestLogin){//游客协议
			return new GuestLoginCommand(id);
		}else if(id == CommandID.Heart) {//心跳协议
			return new HeartCommand(id);
		}else if(id == CommandID.Login) {//登录协议
			return new LoginCommand(id);
		}else if(id == CommandID.Unknown){//未知数据协议
			return new UnknownCommand(id);
		}else if(id == CommandID.LoginOut) {//退出登录协议
			return new LoginOutCommand(id);
		}else if(id == CommandID.Register) {//注册协议
			return new RegisterCommand(id);
		}else if(id == CommandID.GeneralInformation) {//普通信息协议
			return new GeneralInformationCommand(id);
		}else if(id == CommandID.VerifyState) {//验证全部玩家状态协议
			return new VerifyStateCommand(id);
		}else if(id == CommandID.VerifyStateErr) {//验证全部玩家状态错误协议
			return new VerifyStateErrCommand(id);
		}else if(id == CommandID.GamePreparing){//游戏准备协议
			return new GamePreparingCommand(id);
		}else if(id == CommandID.GamePreparingError){//游戏准备错误协议
			return new GamePreparingErrorCommand(id);
		}else if(id == CommandID.GameLoading){//游戏加载协议
			return new GameLoadingCommand(id);
		}else if(id == CommandID.WaitOtherPeople) {//等待其他玩家协议
			return new WaitOtherPeopleCommand(id);
		}else if(id == CommandID.GameStart){//游戏开始协议
			return new GameStartCommand(id);
		}else if(id == CommandID.DisConnnect){//断开连接协议
			return new DisconnectCommand(id);
		}else if(id == CommandID.GameData){//游戏数据
			return new GameDataCommand(id);
		}else if(id == CommandID.GameDataBoard){
			return new GameDataBoardCommand(id);
		}else {
			//返回错误协议数据
			return new ICommand();
		}
	}
	
	/**
	 * 判断协议类型,返回该协议实例
	 */
	public static int judgeICommand(ICommand iCommand){
		if(iCommand.getClass().equals(ConnectCommand.class)){//连接协议
			return CommandID.Connect;
		}else if(iCommand.getClass().equals(GuestLoginCommand.class)){//游客协议
			return CommandID.GuestLogin;
		}else if(iCommand.getClass().equals(HeartCommand.class)) {//心跳协议
			return CommandID.Heart;
		}else if(iCommand.getClass().equals(LoginCommand.class)) {//登录协议
			return CommandID.Login;
		}else if(iCommand.getClass().equals(UnknownCommand.class)){//未知数据协议
			return CommandID.Unknown;
		}else if(iCommand.getClass().equals(LoginOutCommand.class)){//退出登录协议
			return CommandID.LoginOut;
		}else if(iCommand.getClass().equals(RegisterCommand.class)){//注册协议
			return CommandID.Register;
		}else if(iCommand.getClass().equals(UnknownCommand.class)){//普通信息协议
			return CommandID.GeneralInformation;
		}else if(iCommand.getClass().equals(GamePreparingCommand.class)) {//游戏准备协议
			return CommandID.GamePreparing;
		}else if(iCommand.getClass().equals(GamePreparingErrorCommand.class)) {//游戏准备错误协议
			return CommandID.GamePreparingError;
		}else if(iCommand.getClass().equals(VerifyStateCommand.class)) {//验证全部玩家状态协议
			return CommandID.VerifyState;
		}else if(iCommand.getClass().equals(VerifyStateErrCommand.class)) {//验证全部玩家状态错误协议
			return CommandID.VerifyStateErr;
		}else if(iCommand.getClass().equals(GameLoadingCommand.class)){//游戏加载协议
			return CommandID.GameLoading;
		}else if(iCommand.getClass().equals(WaitOtherPeopleCommand.class)){//等待其他玩家协议
			return CommandID.WaitOtherPeople;
		}else if(iCommand.getClass().equals(GameStartCommand.class)){//游戏开始协议
			return CommandID.GameStart;
		}else if(iCommand.getClass().equals(DisconnectCommand.class)){//断开连接协议
			return CommandID.DisConnnect;
		}else if(iCommand.getClass().equals(GameDataCommand.class)){//游戏数据协议
			return CommandID.GameData;
		}else if(iCommand.getClass().equals(GameDataBoardCommand.class)){
			return CommandID.GameDataBoard;
		}else {
			//返回错误协议数据
			return 0;
		}
	}
	
	/**
	 * 获得协议数据
	 */
	public static ICommand getICommand(DataBuffer data) {
		ICommand iCommand = new ICommand();
		iCommand.ReadBufferIp(data);
		ICommand c = judgeICommand(iCommand.header.id);
		c.ReadFromBufferBody(data);
		return c; 
	}
	
}
