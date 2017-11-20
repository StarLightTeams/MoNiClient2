package tool;

import java.util.Iterator;
import java.util.Map;

import config.GameConfig;


/**
 * 通用工具
 */
public class CommonTools {
	
	/**
	 * 遍历map
	 */
	public static void listMaps(Map map) {
		Iterator entries = map.entrySet().iterator();
		while(entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			System.out.println("key="+entry.getKey()+",value="+entry.getValue());
		}
	}
	
	/**
	 * 选择出游戏类型 (人数+:+c/s)
	 */
	public static String selectGameType(String str) {
		String[] types = {"双人普通游戏","双人特殊游戏","四人普通游戏","四人特殊游戏"};
		if("双人普通游戏".equals(str)) {
			return createRoomType(2,GameConfig.doubleCommonGame);
		}else if("双人特殊游戏".equals(str)) {
			return createRoomType(2,GameConfig.doubleSpecialGame);
		}else if("四人普通游戏".equals(str)) {
			return createRoomType(4,GameConfig.fourCommonGame);
		}else if("四人特殊游戏".equals(str)) {
			return createRoomType(4,GameConfig.fourSpecialGame);
		}else {
			return null;
		}
	}
	
	/**
	 * 通过房间类型获得房间的最大人数
	 * @param roomType
	 * @return
	 */
	public static int getRoomPeopleNumByRoomType(String roomType) {
		return Integer.parseInt(roomType.split(":")[0]);
	}
	
	//创建房间的类型
	public static String createRoomType(int peoplecount,int roomtype) {
		return peoplecount+"-"+roomtype;
	}
}