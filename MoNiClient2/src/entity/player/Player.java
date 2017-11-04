package entity.player;

import java.util.Map;

/*
 * �����
 */
public class Player {
	//��ҵ�ip+:+port
	public String clientId;
	//������
	public int playerNo;
	//�������
	public String playerName;
	//��ҵ�½����
	public String password;
	//��ҷ���
	public int playerCard;
	//��ҵ���
	public Map<String,Integer> djmap;
	//��ҵ�¼״̬��0.δ��¼ 1.�ο͵�¼ 2.qq��¼ 3.΢�ŵ�¼��
	public int loginState = 0;
	
	public Player() {
		
	}
	
	public Player(int playerNo,String password,int loginState,String clientId) {
		this.playerNo = playerNo;
		this.password = password;
		this.loginState =loginState;
		this.clientId = clientId;
	}
	
	public Player(String playerName ,String password,String clientId) {
		this.playerName = playerName;
		this.password = password;
		this.clientId = this.clientId;
	}
	
	public Player(String playerName,String password,int loginState,String clientId) {
		this.playerName = playerName;
		this.password = password;
		this.loginState =loginState;
		this.clientId = clientId;
	}
	

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public int getLoginState() {
		return loginState;
	}

	public void setLoginState(int loginState) {
		this.loginState = loginState;
	}

	public int getPlayerNo() {
		return playerNo;
	}
	public void setPlayerNo(int playerNo) {
		this.playerNo = playerNo;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPlayerCard() {
		return playerCard;
	}
	public void setPlayerCard(int playerCard) {
		this.playerCard = playerCard;
	}
	public Map<String, Integer> getDjmap() {
		return djmap;
	}
	public void setDjmap(Map<String, Integer> djmap) {
		this.djmap = djmap;
	}

	@Override
	public String toString() {
		return "Player [playerNo=" + playerNo + ", playerName=" + playerName + ", password=" + password
				+ ", playerCard=" + playerCard + ", djmap=" + djmap + ", loginState=" + loginState + "]";
	}
}

