package entity.game;

/**
 * ש���е�����
 */
public class BoardProps {
	public String propsId; 	//����id
	public String propsName;//��������
	public String propsInfo;//������Ϣ
	
	public BoardProps() {
		
	}
	public BoardProps(String propsId,String propsName,String propsInfo) {
		this.propsId = propsId;
		this.propsName = propsName;
		this.propsInfo = propsInfo;
	}
	public String getPropsId() {
		return propsId;
	}
	public void setPropsId(String propsId) {
		this.propsId = propsId;
	}
	public String getPropsName() {
		return propsName;
	}
	public void setPropsName(String propsName) {
		this.propsName = propsName;
	}
	public String getPropsInfo() {
		return propsInfo;
	}
	public void setPropsInfo(String propsInfo) {
		this.propsInfo = propsInfo;
	}
	
}