package entity;

import java.awt.GridBagConstraints;
/**
 * 作废(但可以参考)
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
	
	//对齐方式  
	public GridBag setAnchor(int anchor)  {  
	    this.anchor = anchor;  
	    return this;  
	}  
	  
	//是否拉伸及拉伸方向  
	public GridBag setFill(int fill)  {  
	    this.fill = fill;  
	    return this;  
	}  
}
