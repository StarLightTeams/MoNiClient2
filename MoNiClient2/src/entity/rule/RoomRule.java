package entity.rule;

import java.io.Serializable;
import java.util.Date;

/**
 *	��������� 
 */
public class RoomRule {
	//�������1- ,2- ,3- ��
	public int RoomXXRule;
	//�������ķ�������
	public int RoomCostCardCount;
	public int getRoomXXRule() {
		return RoomXXRule;
	}
	public void setRoomXXRule(int roomXXRule) {
		RoomXXRule = roomXXRule;
	}
	public int getRoomCostCardCount() {
		return RoomCostCardCount;
	}
	public void setRoomCostCardCount(int roomCostCardCount) {
		RoomCostCardCount = roomCostCardCount;
	}
	
	
}
