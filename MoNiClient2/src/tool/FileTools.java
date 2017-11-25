package tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileTools {
	public File file;
	
	public FileTools() {
		
	}
	
	public FileTools(String str) {
		file = new File(str);
		String path = null;
//	    try {
//	       path = directory.getCanonicalPath();//��ȡ��ǰ·��
//	    } catch (IOException e) {
//	        // TODO Auto-generated catch block
//	        e.printStackTrace();
//	    } 
//	    path +="\\Test";
		if(!file.exists()) {//����ļ�������
			try {
				file.createNewFile();
				System.out.println("�����ɹ�");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("����ʧ��");
			}
		}
	}
	
	public boolean writeFile(String str) {
		FileOutputStream fos = null;
	    try {
			fos = new FileOutputStream(file,true);
			//ת��Ϊbyte����
			byte[] b = str.getBytes();
			fos.write(b);
			fos.flush();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("�ļ�δ�ҵ�");
			return false;
		} catch (IOException e) {
			System.out.println("IO����");
			e.printStackTrace();
			return false;
		}finally {
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        
	}
	
	public String readFile() {
		FileInputStream fis = null;                 
        try {
			fis = new FileInputStream(file);
			//��ȡ���ݣ�������ȡ�������ݴ洢��������
			byte[] data = new byte[1024]; //���ݴ洢������
		    int i = 0; //��ǰ�±�
            //��ȡ���еĵ�һ���ֽ�����
            int n = fis.read();
            //���ζ�ȡ����������
            while(n != -1){ //δ��������ĩβ
                //����Ч���ݴ洢��������
                data[i] = (byte)n;
                //�±�����
                i++;
                //��ȡ��һ���ֽڵ�����
                n = fis.read();
            }
           
            //��������
            String s = new String(data,0,i);
            //����ַ���
            System.out.println(s);
            return s;
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("�ļ�δ�ҵ�");
			return "";
		} catch (IOException e) {
			System.out.println("IO����");
			e.printStackTrace();
			return "";
		}finally{
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
