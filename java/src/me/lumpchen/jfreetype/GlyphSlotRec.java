package me.lumpchen.jfreetype;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

import me.lumpchen.jfreetype.JFTLibrary.FTGlyphBitmap;
import me.lumpchen.jfreetype.JFTLibrary.FTGlyphMetrics;
import me.lumpchen.jfreetype.JFTLibrary.FTVector;

public class GlyphSlotRec {
	
	private char c;
	private GlyphBitmap bitmap;
	private GlyphMetrics metrics;
	
	public GlyphSlotRec(char c, FTGlyphBitmap bitmap, FTGlyphMetrics ftMetrics, 
			AffineTransform at, double pxpeu) {
		this.c = c;
		this.bitmap = new GlyphBitmap(bitmap);
		this.metrics = new GlyphMetrics(ftMetrics, at);
	}
	
	public char getChar() {
		return this.c;
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
