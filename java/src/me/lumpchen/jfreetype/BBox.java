package me.lumpchen.jfreetype;

import me.lumpchen.jfreetype.JFTLibrary.FTBBox;


public class BBox {
	
	private double xMin;
	private double yMin;

	private double xMax;
	private double yMax;
	
	public BBox(FTBBox bbox, double pxpeu) {
		this.xMin = bbox.xMin.longValue() * pxpeu;
		this.yMin = bbox.yMin.longValue() * pxpeu;
		this.xMax = bbox.xMax.longValue() * pxpeu;
		this.yMax = bbox.yMax.longValue() * pxpeu;
	}
	
	public String toString() {
		StringBuilder buf = new StringBuilder();
		
		buf.append("xMin " + this.xMin);
		buf.append("\n");
		buf.append("yMin " + this.yMin);
		buf.append("\n");
		buf.append("xMax " + this.xMax);
		buf.append("\n");
		buf.append("yMax " + this.yMax);
		buf.append("\n");
		return buf.toString();
	}
}
