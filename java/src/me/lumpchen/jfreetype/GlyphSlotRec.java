package me.lumpchen.jfreetype;

import java.awt.Color;
import java.awt.image.BufferedImage;

import me.lumpchen.jfreetype.JFTLibrary.FTGlyphBitmap;
import me.lumpchen.jfreetype.JFTLibrary.FTGlyphMetrics;

public class GlyphSlotRec {

	private char c;
	private int gid;
	private GlyphBitmap bitmap;
	private GlyphMetrics metrics;

	public GlyphSlotRec(char c, FTGlyphBitmap bitmap, FTGlyphMetrics ftMetrics, AffineTransform at) {
		this.c = c;
		this.bitmap = new GlyphBitmap(bitmap);
		this.metrics = new GlyphMetrics(ftMetrics, at);
	}

	public char getChar() {
		return this.c;
	}
	
	public int getGlyph() {
		return this.gid;
	}
	
	public void setGlyph(int gid) {
		this.gid = gid;
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
