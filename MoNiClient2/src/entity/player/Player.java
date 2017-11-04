package entity.player;

import java.util.Map;

/*
 * Íæ¼ÒÀà
 */
public class Player {
	//Íæ¼ÒµÄip+:+port
	public String clientId;
	//Íæ¼ÒÐòºÅ
	public int playerNo;
	//Íæ¼ÒÃû×Ö
	public String playerName;
	//Íæ¼ÒµÇÂ½ÃÜÂë
	public String password;
	//Íæ¼Ò·¿¿¨
	public int playerCard;
	//Íæ¼ÒµÀ¾ß
	public Map<String,Integer> djmap;
	//Íæ¼ÒµÇÂ¼×´Ì¬¡¾0.Î´µÇÂ¼ 1.ÓÎ¿ÍµÇÂ¼ 2.qqµÇÂ¼ 3.Î¢ÐÅµÇÂ¼¡¿
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

