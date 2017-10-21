package entity;

import java.awt.GridBagConstraints;
/**
 * ����(�����Բο�)
 */

public class GridBag extends GridBagConstraints {
	
	public GridBag(int gridx,int gridy) {
		this.gridy = gridy;
		this.gridx = gridx;
	}
	
	public GridBag(int gridx,int gridy,int gridwidth,int gridheight) {
		this.gridy = gridy;
		this.gridx = gridx;
		this.gridwidth = gridwidth;
		this.gridheight = gridheight;
	}
	
	//���뷽ʽ  
	public GridBag setAnchor(int anchor)  {  
	    this.anchor = anchor;  
	    return this;  
	}  
	  
	//�Ƿ����켰���췽��  
	public GridBag setFill(int fill)  {  
	    this.fill = fill;  
	    return this;  
	}  
}
