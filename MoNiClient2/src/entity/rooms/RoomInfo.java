package entity.rooms;

import java.io.Serializable;
import java.util.Date;

public class RoomInfo {
	//����� 
	public String roomId;
	//���䴴��ʱ��
	public Date createRoomTime;
	//�����淨
	public String roomType;
	//��������
	public int RoomPeopleCount;
	//����״̬λ��1.�����С�2.��ʼ�С�3.�ر��С���
	public int RoomState =1 ;
	//�������1�ֽ���ʱ��
	public Date RoomCreateTime;
	//��������ȼ�(�뷿�������ҹ�,�����(����������-1),�����������ȼ�Ϊ-1)
	public int roomPLevel = 0;
	//��Ϸ����������(���ڷ���������ʽ��ʼ��Ϸ)
	public int endOfLoadingGame = 0;
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public Date getCreateRoomTime() {
		return createRoomTime;
	}
	public void setCreateRoomTime(Date createRoomTime) {
		this.createRoomTime = createRoomTime;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public int getRoomPeopleCount() {
		return RoomPeopleCount;
	}
	public void setRoomPeopleCount(int roomPeopleCount) {
		RoomPeopleCount = roomPeopleCount;
	}
	public int getRoomState() {
		return RoomState;
	}
	public void setRoomState(int roomState) {
		RoomState = roomState;
	}
	public Date getRoomCreateTime() {
		return RoomCreateTime;
	}
	public void setRoomCreateTime(Date roomCreateTime) {
		RoomCreateTime = roomCreateTime;
	}
	public int getRoomPLevel() {
		return roomPLevel;
	}
	public void setRoomPLevel(int roomPLevel) {
		this.roomPLevel = roomPLevel;
	}
	public int getEndOfLoadingGame() {
		return endOfLoadingGame;
	}
	public void setEndOfLoadingGame(int endOfLoadingGame) {
		this.endOfLoadingGame = endOfLoadingGame;
	}
	
	
}
