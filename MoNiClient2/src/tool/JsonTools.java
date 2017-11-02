package tool;


import java.util.HashMap;
import java.util.Map;

import entity.info.Info;
import entity.player.Player;
import net.sf.json.JSONObject;

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
	
	public static String  getString(Object obj) {
		JSONObject jObject = new JSONObject();
		jObject.put("className",getClassName(obj));
		jObject.put("data", obj);
		return jObject.toString();
	}
	
	public static Object parseJson(String str) {
		JSONObject jObject = JSONObject.fromObject(str);
		String className = jObject.getString("className");
		if("Player".equals(className)) {
			Player player = new Player();
			player.setPlayerName(jObject.getJSONObject("data").getString("playerName"));
			player.setPassword(jObject.getJSONObject("data").getString("password"));
			player.setLoginState(Integer.parseInt(jObject.getJSONObject("data").getString("loginState")));
			player.setClientId(jObject.getJSONObject("data").getString("clientId"));
			return player;
		}else if("Info".equals(className)){
			Info info = new Info();
			info.setHeadInfo(jObject.getJSONObject("data").getString("headInfo"));
			info.setDataInfo(jObject.getJSONObject("data").getString("dataInfo"));
			return info;
		}else {
			return null;
		}
		
	}
	
	public static String getClassName(Object obj) {
		String[] s = obj.getClass().getName().split("\\.");
		return s[s.length-1];
	}
	
}
