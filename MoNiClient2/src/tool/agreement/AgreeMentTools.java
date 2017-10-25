package tool.agreement;


import entity.agrement.CommandID;
import entity.agrement.ICommand;
import entity.rule.agreement.ConnectCommand;
import entity.rule.agreement.GuestLoginCommand;
import entity.rule.agreement.HeartCommand;
import entity.rule.agreement.LoginCommand;
import entity.rule.agreement.LoginOutCommand;
import entity.rule.agreement.UnknownCommand;

/**
 * Э�鹤��
 */
public class AgreeMentTools {
	/**
	 * �ж�Э������,���ظ�Э��ʵ��
	 */
	public static ICommand judgeICommand(int id){
		if(id == CommandID.Connect){//����Э��
			return new ConnectCommand(id);
		}else if(id == CommandID.GuestLogin){//�ο�Э��
			return new GuestLoginCommand(id);
		}else if(id == CommandID.Heart) {//����Э��
			return new HeartCommand(id);
		}else if(id == CommandID.Login) {//��¼Э��
			return new LoginCommand(id);
		}else if(id == CommandID.Unknown){//δ֪����Э��
			return new UnknownCommand(id);
		}else if(id == CommandID.LoginOut) {//�˳���¼Э��
			return new LoginOutCommand(id);
		}else {
			//���ش���Э������
			return new ICommand();
		}
	}
	
	/**
	 * �ж�Э������,���ظ�Э��ʵ��
	 */
	public static int judgeICommand(ICommand iCommand){
		if(iCommand.getClass().equals(ConnectCommand.class)){//����Э��
			return CommandID.Connect;
		}else if(iCommand.getClass().equals(GuestLoginCommand.class)){//�ο�Э��
			return CommandID.GuestLogin;
		}else if(iCommand.getClass().equals(HeartCommand.class)) {//����Э��
			return CommandID.Heart;
		}else if(iCommand.getClass().equals(LoginCommand.class)) {//��¼Э��
			return CommandID.Login;
		}else if(iCommand.getClass().equals(UnknownCommand.class)){//δ֪����Э��
			return CommandID.Unknown;
		}else if(iCommand.getClass().equals(UnknownCommand.class)){//�˳���¼Э��
			return CommandID.LoginOut;
		}else {
			//���ش���Э������
			return 0;
		}
	}
	
	/**
	 * ���Э������
	 */
	public static ICommand getICommand(DataBuffer data) {
		ICommand iCommand = new ICommand();
		iCommand.ReadBufferIp(data);
		ICommand c = judgeICommand(iCommand.header.id);
		c.ReadFromBufferBody(data);
		return c; 
	}
	
}
