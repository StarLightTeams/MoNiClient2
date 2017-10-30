package entity;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class JTextPaneUP extends JTextPane {
	
	/**
	 * 增加红色的字符串
	 */
	public void addErrString(String str) {
		//得到编辑器关联类
		Document doc = this.getDocument();
		//属性类的容器类,用来放置属性
		SimpleAttributeSet set = new SimpleAttributeSet();
		//设置属性放入容器
		StyleConstants.setForeground(set,Color.red);
		try {
			doc.insertString(doc.getLength(), str+"\n", set);
		} catch (BadLocationException e) {
			e.printStackTrace();
			System.out.println("写入字符串失败");
		}
	}
	
	/**
	 * 增加字符串
	 */
	public void addString(String str) {
		//得到编辑器关联类
		Document doc = this.getDocument();
		//属性类的容器类,用来放置属性
		SimpleAttributeSet set = new SimpleAttributeSet();
		try {
			doc.insertString(doc.getLength(), str+"\n",set);
		} catch (BadLocationException e) {
			e.printStackTrace();
			System.out.println("写入字符串失败");
		}
	}
	
	/**
	 * 增加带有颜色的字符串
	 * @param str
	 * @param c
	 */
	public void addString(String str,Color c) {
		//得到编辑器关联类
		Document doc = this.getDocument();
		//属性类的容器类,用来放置属性
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, c);
		try {
			doc.insertString(doc.getLength(), str+"\n",set);
		} catch (BadLocationException e) {
			e.printStackTrace();
			System.out.println("写入字符串失败");
		}
	}
	
	
	/**
	 * 清空JTextPane中的显示
	 */
	public void clear() {
		//得到编辑器关联类
		Document doc = this.getDocument();
		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
			System.out.println("清空失败");
		}
	}
}
