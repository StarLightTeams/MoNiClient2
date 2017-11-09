package tool;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import config.entity.Log;
import entity.rooms.Room;

/*
 * ��������Ĺ����࣬���еĴ�����������Ĺ���
 */
public class RoomTools {
	
	/**
	 * ͨ���������ͻ�÷�����������
	 * @param roomType
	 * @return
	 */
	public static int getRoomPeopleNumByRoomType(String roomType) {
		return Integer.parseInt(roomType.split(":")[0]);
	}
	
	//�������������
	public static String createRoomType(int peoplecount,int roomtype) {
		return peoplecount+":"+roomtype;
	}

	//��������id
	public static String createRoomID(int peoplecount/*�������������������*/,int no/*�������к�*/){
		String roomid="";
		if(no<10){
			roomid = peoplecount+"00"+no;
		}else if(no>10&&no<100){
			roomid = peoplecount+"0"+no;
		}else{
			roomid = peoplecount+""+no;
		}
		return roomid;
	}
	//���������
	public static boolean insertTable(Map<String, Map<String,Room>> roommap/*�����б�����*/,String roomlx/*����*/,String roomno/*�������*/,Room room/*��������*/){
		//����
		if(roommap.get(roomlx).get(roomno)!=null){
			Log.d("��������"+roomno+"ʧ��");
			return false;
		}else{
			roommap.get(roomlx).put(roomno, room);
		}
		return true;
	}
	
	@Test
	public void test() {
		System.out.println(RoomTools.getRoomPeopleNumByRoomType("1:s"));
	}
}
