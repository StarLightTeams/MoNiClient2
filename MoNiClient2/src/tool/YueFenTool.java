package tool;

import java.text.DecimalFormat;

import org.junit.Test;

public class YueFenTool {

	public static double yueFen(double a){
		DecimalFormat df=new DecimalFormat("#.#####"); 
		return new Double(df.format(a));
	}
	@Test
	public void test(){
		System.out.println(yueFen(20.0));
	}
}