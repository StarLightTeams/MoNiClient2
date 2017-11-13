package tool;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.junit.Test;

import config.ClientConfig;
import config.GameConfig;
import entity.JTextPaneUP;
import entity.game.Brick;

public class GameTools {
	
	/**
	 * 生成砖块列表
	 */
	public static ArrayList<Brick> createBrickList(double startX,double startY,int bX,int bY,int bricksNum,JTextPaneUP jtp){
		
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		ConverTool cTools = new ConverTool();
		startX = cTools.exChangePoint*startX;
		startY = cTools.exChangePoint*startY;
		//待完善,数据
		Map<String, Double> bprobability = new HashMap<String, Double>();
		Map<Integer,Double> probability = new HashMap<Integer, Double>();
		probability.put(1, 0.7);
		probability.put(2, 0.2);
		probability.put(3, 0.1);
		bY = (int)(cTools.exChangePoint*bY);
		bX = (int)(cTools.exChangePoint*bX);
		System.out.println("bx="+bX+",by="+bY+",startX="+startX+",startY="+startY+",startY+cTools.exChangePoint*300="+(startY+cTools.exChangePoint*300));
		double sX = startX;
		double sY = startY;
		for(int i=0;i<bricksNum;i++) {
			String bPropsId = getRandomBProps(bprobability);
			int hardness = getRandomHardness(probability);
			if(startX+bX>ClientConfig.GAMEWIDTH-sX) {
				startX = sX;
				if(startY+bY>=(sY+cTools.exChangePoint*300)){
					jtp.addString("上方生成砖块宽度已经超过限定300,减少砖块数量", Color.BLUE);
					break;
				}
				if(startY+bY>=ClientConfig.GAMEHEIGHT) {
					jtp.addString("下方生成砖块宽度已经超过限定300,减少砖块数量", Color.BLUE);
					break;
				}
				startY+=bY;
			}
			Brick brick = new Brick(bY,bX,startX,startY,bPropsId,hardness);
			bricks.add(brick);
			startX+=bX;
		}
		for(int i=0;i<bricks.size();i++) {
			System.out.println(bricks.get(i).toString());
		}
		return bricks;
	}
	
	/**
	 * 根据概率生成道具
	 * @param probability
	 * @return
	 */
	public static String getRandomBProps(Map<String,Double> bprobability) {
		Random rand = new Random();
		double randNum = rand.nextDouble();
		List<Map.Entry<String,Double>> list = new ArrayList<Map.Entry<String,Double>>(bprobability.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<String,Double>>() {
			//升序排序
			public int compare(Entry<String,Double> o1, Entry<String,Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
	    }); 
		double b=0,e=0;
		String bPropsId = "";
		for(int i=0;i<list.size();i++){
			e += list.get(i).getValue();
			if(randNum>=b && randNum <=e) {
				bPropsId = list.get(i).getKey();
				break;
			}
			b = e;
        } 
		return bPropsId;
	}
	
	/**
	 * 根据概率生敲击硬度
	 * @param probability 等级,概率
	 * @return
	 */
	public static int getRandomHardness(Map<Integer,Double> probability) {
		Random rand = new Random();
		double randNum = rand.nextDouble();
		List<Map.Entry<Integer,Double>> list = new ArrayList<Map.Entry<Integer,Double>>(probability.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<Integer,Double>>() {
			//升序排序
			public int compare(Entry<Integer,Double> o1, Entry<Integer,Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
	    }); 
		double b=0,e=0;
		int hardNum = 0;
		for(int i=0;i<list.size();i++){
			e += list.get(i).getValue();
			if(randNum>=b && randNum <=e) {
				hardNum = list.get(i).getKey();
				break;
			}
			b = e;
        } 
		return hardNum;
	}
	
	@Test
	public void test() {
		Map<Integer,Double> probability = new HashMap<Integer, Double>();
		probability.put(2,0.3);
		probability.put(1,0.3);
		probability.put(3,0.3);
		probability.put(4,0.3);
		
		for(int i=0;i<10;i++) {
			System.out.println(getRandomHardness(probability));
		}
	}
}
