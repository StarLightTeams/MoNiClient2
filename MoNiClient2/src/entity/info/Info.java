package entity.info;

/**
 * ������(����������Ϣ)
 */
public class Info {
	public String headInfo;
	public String dataInfo;
	
	public Info() {
	}
	
	public Info(String headInfo) {
		this.headInfo = headInfo;
	}
	
	public Info(String headInfo, String dataInfo) {
		this.headInfo = headInfo;
		this.dataInfo = dataInfo;
	}
	public String getHeadInfo() {
		return headInfo;
	}
	public void setHeadInfo(String headInfo) {
		this.headInfo = headInfo;
	}
	public String getDataInfo() {
		return dataInfo;
	}
	public void setDataInfo(String dataInfo) {
		this.dataInfo = dataInfo;
	}
	@Override
	public String toString() {
		return "ErrorInfo [headInfo=" + headInfo + ", dataInfo=" + dataInfo + "]";
	}
	
	
}	
