package me.lumpchen.jfreetype;

import me.lumpchen.jfreetype.JFTLibrary.FTGlyphMetrics;

public class GlyphMetrics {

	public double width;
	public double height;

	public double horiBearingX;
	public double horiBearingY;
	public double horiAdvance;

	public double vertBearingX;
	public double vertBearingY;
	public double vertAdvance;

	public GlyphMetrics(FTGlyphMetrics metrics) {
		double scale = 1 / 64.0d;
		this.width = metrics.width.intValue() * scale;
		this.height = metrics.height.intValue() * scale;
		
		this.horiBearingX = metrics.horiBearingX.intValue() * scale;
		this.horiBearingY = metrics.horiBearingY.intValue() * scale;
		this.horiAdvance = metrics.horiAdvance.intValue() * scale;
		
		this.vertBearingX = metrics.vertBearingX.intValue() * scale;
		this.vertBearingY = metrics.vertBearingY.intValue() * scale;
		this.vertAdvance = metrics.vertAdvance.intValue() * scale;
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		buf.append("width " + this.width);
		buf.append("\n");
		buf.append("height " + this.height);
		buf.append("\n");
		
		buf.append("horiBearingX " + this.horiBearingX);
		buf.append("\n");
		buf.append("horiBearingY " + this.horiBearingY);
		buf.append("\n");
		buf.append("horiAdvance " + this.horiAdvance);
		buf.append("\n");

		buf.append("vertBearingX " + this.vertBearingX);
		buf.append("\n");
		buf.append("vertBearingY " + this.vertBearingY);
		buf.append("\n");
		buf.append("vertAdvance " + this.vertAdvance);
		buf.append("\n");
		
		return buf.toString();
	}
}
