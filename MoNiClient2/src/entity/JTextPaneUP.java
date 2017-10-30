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
	 * ���Ӻ�ɫ���ַ���
	 */
	public void addErrString(String str) {
		//�õ��༭��������
		Document doc = this.getDocument();
		//�������������,������������
		SimpleAttributeSet set = new SimpleAttributeSet();
		//�������Է�������
		StyleConstants.setForeground(set,Color.red);
		try {
			doc.insertString(doc.getLength(), str+"\n", set);
		} catch (BadLocationException e) {
			e.printStackTrace();
			System.out.println("д���ַ���ʧ��");
		}
	}
	
	/**
	 * �����ַ���
	 */
	public void addString(String str) {
		//�õ��༭��������
		Document doc = this.getDocument();
		//�������������,������������
		SimpleAttributeSet set = new SimpleAttributeSet();
		try {
			doc.insertString(doc.getLength(), str+"\n",set);
		} catch (BadLocationException e) {
			e.printStackTrace();
			System.out.println("д���ַ���ʧ��");
		}
	}
	
	/**
	 * ���Ӵ�����ɫ���ַ���
	 * @param str
	 * @param c
	 */
	public void addString(String str,Color c) {
		//�õ��༭��������
		Document doc = this.getDocument();
		//�������������,������������
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, c);
		try {
			doc.insertString(doc.getLength(), str+"\n",set);
		} catch (BadLocationException e) {
			e.printStackTrace();
			System.out.println("д���ַ���ʧ��");
		}
	}
	
	
	/**
	 * ���JTextPane�е���ʾ
	 */
	public void clear() {
		//�õ��༭��������
		Document doc = this.getDocument();
		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
			System.out.println("���ʧ��");
		}
	}
}
