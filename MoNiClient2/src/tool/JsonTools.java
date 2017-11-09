package tool;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import config.GameConfig;
import config.entity.Log;
import entity.info.Info;
import entity.player.Player;
import entity.rooms.DoubleRoom;
import entity.rooms.Room;
import entity.rooms.RoomInfo;
import entity.rule.RoomRule;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * json������
 */
public class JsonTools {
	
	/**
	 * ֻ�ܽ�����ʽΪ{"key":"value","key1":"value1","key2":"value2",...}
	 * @param str
	 * @return
	 */
	public static Map<String,String> parseData(String str){
		String[] strs = str.split(",");
		Map<String,String> maps = new HashMap<String, String>();
		for(int i=0;i<strs.length;i++) {
			String[] sstrs = strs[i].split("\"");
//			for(int j=0;j<sstrs.length;j++) {
//				System.out.println(sstrs[j]);
//			}
			maps.put(sstrs[1], sstrs[3]);
		}
		return maps;
	}
	
	/**
	 * ����map��key��value�õ�json��String
	 * @param mStr
	 * @return
	 */
	public static String getData(Map<String,String> mStr) {
		JSONObject jObject = new JSONObject();
		for(Map.Entry<String, String> entry :mStr.entrySet()) {
			jObject.put(entry.getKey(),entry.getValue());
		}
		return jObject.toString();
	}
	
	public static String getString(Object obj) {
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("YYYY-MM-dd hh:mm:ss"));
		JSONObject jObject = new JSONObject();
//		jObject.put("className",getClassName(obj));
//		jObject.put("superName", getSuperClassName(obj));
//		jObject.put("data", obj);
		Map maps = new HashMap<Object, Object>();
		maps.put("className",getClassName(obj));
		maps.put("superName", getSuperClassName(obj));
		maps.put("data", obj);
		jObject.putAll(maps, jsonConfig);
		return jObject.toString();
	}
	
	public static Object parseJson(String str) {
		  
		JSONObject jObject = JSONObject.fromObject(str);
		String className = jObject.getString("className");
		String superName = jObject.getString("superName");
		if("Player".equals(className)) {
			Player player = new Player();
			player.setPlayerName(jObject.getJSONObject("data").getString("playerName"));
			player.setPassword(jObject.getJSONObject("data").getString("password"));
			player.setLoginState(Integer.parseInt(jObject.getJSONObject("data").getString("loginState")));
			player.setPlayerNo(Integer.parseInt(jObject.getJSONObject("data").getString("playerNo")));
			return player;
		}else if("Info".equals(className)){
			Info info = new Info();
			info.setHeadInfo(jObject.getJSONObject("data").getString("headInfo"));
			info.setDataInfo(jObject.getJSONObject("data").getString("dataInfo"));
			return info;
		}else if("Room".equals(superName)){
			try {
				Room room = new Room();
				RoomInfo roomInfo = new RoomInfo();
				JSONObject rInfo = jObject.getJSONObject("data").getJSONObject("roomInfo");
				roomInfo.roomId = rInfo.getString("roomId");
				roomInfo.createRoomTime = new SimpleDateFormat("YYYY-DD-MM hh:mm:ss").parse(rInfo.getString("createRoomTime"));
				roomInfo.roomType = rInfo.getString("roomType");
				roomInfo.RoomPeopleCount = rInfo.getInt("roomPeopleCount");
				roomInfo.RoomState = rInfo.getInt("roomState");
				roomInfo.RoomCreateTime = !rInfo.getString("roomCreateTime").equals("")?new SimpleDateFormat("YYYY-DD-MM hh:mm:ss").parse(rInfo.getString("roomCreateTime")):null;
				roomInfo.roomPLevel = rInfo.getInt("roomPLevel");
				roomInfo.endOfLoadingGame = rInfo.getInt("endOfLoadingGame");
				room.roomInfo = roomInfo;
				JSONObject pMap = jObject.getJSONObject("data").getJSONObject("playermap");
				Map<Player,Integer> playermap = new HashMap<Player,Integer>();
				Iterator entries = pMap.entrySet().iterator();
				while(entries.hasNext()) {
					Map.Entry entry = (Map.Entry) entries.next();
					String string = (String) entry.getKey();
					String jsonStr = getJsonByString(string,"Player");
					System.out.println(jsonStr);
					JSONObject pj = JSONObject.fromObject(jsonStr);
					JSONObject playerJb = pj.getJSONObject("Player");
					Player player = new Player();
//					player.clientId = playerJb.getString("clientId");
					player.playerNo = playerJb.getInt("playerNo");
					JSONObject djmapJ = playerJb.getJSONObject("djmap");
					Iterator entries2 = djmapJ.entrySet().iterator();
					Map<String,Integer> djmap = new HashMap<String, Integer>();
					while(entries2.hasNext()) {
						Map.Entry entry2 = (Map.Entry) entries2.next();
						String pId = (String) entry.getKey();
						int pos = (Integer) entry.getValue();
						System.out.println("pId="+pId);
						djmap.put(pId,pos);
					}
					player.djmap = djmap;
					player.playerName = playerJb.getString("playerName");
					player.loginState = Integer.parseInt(playerJb.getString("loginState"));
					System.out.println(player.toString());
					int t = (Integer) entry.getValue();
					System.out.println(t);
					playermap.put(player, t);
				}
				room.playermap = playermap;
				JSONObject rRule = jObject.getJSONObject("data").getJSONObject("roomRule");
				RoomRule roomRule = new RoomRule();
				roomRule.RoomXXRule = rRule.getInt("roomXXRule");
				roomRule.RoomCostCardCount = rRule.getInt("roomCostCardCount");
				room.roomRule = roomRule;
				return room;
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("����stringת��data�쳣");
			}
			return null;
		}else {
			return null;
		}
		
	}
	
	/**
	 * �����ַ����õ�����,�ַ�����ʽ[playerNo=0, playerName=admin, password=admin, playerCard=0, djmap=null, loginState=0]
	 * @param str
	 * @param type
	 * @return
	 * "playermap":{"Player [playerNo=0, playerName=admin, password=admin, playerCard=0, djmap={"1"="2", 2=3, 4=5}, loginState=0]":1}
	 */
	public static String getJsonByString(String str,String type) {
		if("Player".equals(type)) {
			String s = str.substring(str.indexOf("[")+1,str.indexOf("]"));
			System.out.println(s);
			String[] ks = s.split("\\{");
			String[] kks = ks[1].split("\\}");
			String skt ="";
			if(!kks[0].equals("")) {
				StringBuilder sb = new StringBuilder();
				s = sb.append(ks[0]).append("{}").append(kks[1]).toString();
				skt = getMapJsonByString(kks[0]);
			}
			String[] ss = s.split("\\,");
			StringBuilder sb = new StringBuilder() ;
			sb.append("{"+'\"'+type+'\"'+":{");
			int t=0;
			String stt="";
			for(int i=0;i<ss.length;i++) {
				System.out.println(ss[i]);
				String[] sss = ss[i].split("=");
				for(int j=0;j<sss.length;j++) {
					if(j==0) {
						stt='\"'+sss[j].trim()+'\"';
				 	}else if(j==1) {
						if(" djmap".equals(sss[0])) {
							System.out.println("sss="+sss[0].trim());
							stt=skt;
						}else {
							stt='\"'+sss[1].trim()+'\"';
						}
					}
					System.out.format("ssss[%d]=%s",j,sss[j]);
					sb.append(stt);
					if(j==0) {
						sb.append(":");
					}else if(j==1&&i!=ss.length-1) {
						sb.append(",");
					}
				}
			}
			sb.append("}}");
			System.out.println(sb.toString());
			return sb.toString();
		}else {
			
			return null;
		}
	}
	
	/**
	 * {1=2, 2=3, 4=5}
	 * @param str
	 * @return
	 */
	public static String getMapJsonByString(String str) {
		String s = str;//.substring(1,str.indexOf("}"));
		if(!"".equals(s)) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			String[] ss = s.split(",");
			for(int k=0;k<ss.length;k++) {
				String[] sss = ss[k].split("=");
				for(int z=0;z<sss.length;z++) {
					sss[z] = '\"'+sss[z].trim()+'\"';
					sb.append(sss[z]);
					if(z==0) {
						sb.append(':');
					}else if(z==1&&k!=ss.length-1) {
						sb.append(',');
					}
				}
			}
			sb.append('}');
			System.out.println(sb.toString());
			return sb.toString();
		}else {
//			return '{'+'}';
			return null;
		}
	}
	
	/**
	 * �������
	 * @param obj
	 * @return
	 */
	public static String getClassName(Object obj) {
		String[] s = obj.getClass().getName().split("\\.");
		return s[s.length-1];
	}
	
	/**
	 * ��ø�����
	 * @param obj
	 * @return
	 */
	public static String getSuperClassName(Object obj) {
		String[] s = obj.getClass().getSuperclass().getName().split("\\.");
		return s[s.length-1];
	}
	
	@Test
	public void test() {
		Room room = new DoubleRoom(RoomTools.createRoomID(2, GameConfig.doubleCommonGame));
		Player player =  new Player();
		player.setPlayerName("admin");
		player.setPassword("admin");
		player.djmap.put("1",2);
		player.djmap.put("2",3);
		player.djmap.put("4",5);		
		room.playermap.put(player,1);
//		String str= getString(player);
//		System.out.println(str);
		String str = JsonTools.getString(room);
		System.out.println("str="+str);
		Room room1 = (Room) JsonTools.parseJson(str);
		System.out.println(room1.toString());
//		System.out.println(JsonTools.getJsonByString("[playerNo=0, playerName=admin, password=admin, playerCard=0, djmap={}, loginState=0]","Player"));
//		System.out.println(getMapJsonByString("{1=2,2=3,4=5}"));
	}
	
}
