package tool;


import entity.player.Player;
import net.sf.json.JSONObject;

/**
 * jsonπ§æﬂ¿‡
 * @author Administrator
 *
 */
public class JsonTools {
	
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
			return player;
		}else {
			
			return null;
		}
		
	}
	
	public static String getClassName(Object obj) {
		String[] s = obj.getClass().getName().split("\\.");
		return s[s.length-1];
	}
	
}
