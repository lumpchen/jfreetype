package me.lumpchen.jfreetype;

import java.awt.Color;
import java.awt.image.BufferedImage;

import me.lumpchen.jfreetype.JFTLibrary.FTGlyphBitmap;
import me.lumpchen.jfreetype.JFTLibrary.FTGlyphMetrics;

public class GlyphSlotRec {
	
	public GlyphBitmap bitmap;
	private GlyphMetrics metrics;
	
	public GlyphSlotRec(FTGlyphBitmap bitmap, FTGlyphMetrics ftMetrics, double pxpeu) {
		this.bitmap = new GlyphBitmap(bitmap);
		this.metrics = new GlyphMetrics(ftMetrics);
	}
	
	public BufferedImage getGlyphBitmap(Color color) {
		return this.bitmap.getBitmap(color);
	}
	
	public BufferedImage getGlyphBitmap() {
		return this.bitmap.getBitmap();
	}
	
	public double getHAdvance() {
		return this.metrics.horiAdvance;
	}
	
	public double getHeight() {
		return this.metrics.height;
	}
	
	public double getBearingY() {
		return this.metrics.horiBearingY;
	}
	
}
