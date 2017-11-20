package tool;

import java.util.Iterator;
import java.util.Map;

import config.GameConfig;


/**
 * ͨ�ù���
 */
public class CommonTools {
	
	/**
	 * ����map
	 */
	public static void listMaps(Map map) {
		Iterator entries = map.entrySet().iterator();
		while(entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			System.out.println("key="+entry.getKey()+",value="+entry.getValue());
		}
	}
	
	/**
	 * ѡ�����Ϸ���� (����+:+c/s)
	 */
	public static String selectGameType(String str) {
		String[] types = {"˫����ͨ��Ϸ","˫��������Ϸ","������ͨ��Ϸ","����������Ϸ"};
		if("˫����ͨ��Ϸ".equals(str)) {
			return createRoomType(2,GameConfig.doubleCommonGame);
		}else if("˫��������Ϸ".equals(str)) {
			return createRoomType(2,GameConfig.doubleSpecialGame);
		}else if("������ͨ��Ϸ".equals(str)) {
			return createRoomType(4,GameConfig.fourCommonGame);
		}else if("����������Ϸ".equals(str)) {
			return createRoomType(4,GameConfig.fourSpecialGame);
		}else {
			return null;
		}
	}
	
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
		return peoplecount+"-"+roomtype;
	}
}