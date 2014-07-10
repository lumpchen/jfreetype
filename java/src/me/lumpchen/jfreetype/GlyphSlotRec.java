package me.lumpchen.jfreetype;

import java.awt.Color;
import java.awt.image.BufferedImage;

import me.lumpchen.jfreetype.JFTLibrary.FTGlyphBitmap;
import me.lumpchen.jfreetype.JFTLibrary.FTGlyphMetrics;

import com.sun.jna.Native;

public class GlyphSlotRec {
	
	public FTGlyphBitmap bitmap;
	private GlyphMetrics metrics;
	private BufferedImage glyphImg;
	
	public GlyphSlotRec(FTGlyphBitmap bitmap, FTGlyphMetrics ftMetrics, double pxpeu, Color color) {
		this.bitmap = bitmap;
		this.metrics = new GlyphMetrics(ftMetrics);
		
		int h = bitmap.rows;
		int w = bitmap.width;
		
		int rgb = color.getRGB();
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();

		this.glyphImg = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);

		byte[][] data = new byte[h][w];
		for (int j = 0; j < h; j++) {
			for (int i = 0; i < w; i++) {
				data[j][i] = (byte) (0xff & bitmap.buffer.getChar((i + j * w)
						* Native.getNativeSize(Byte.TYPE)));

				int gray = (int) (data[j][i] & 0xff);
				
				System.err.println(gray);
				if (gray == 255) {
					this.glyphImg.setRGB(i, j, rgb);
				} if (gray == 0) {
					this.glyphImg.setRGB(i, j, 0);
				} else {
					Color c = new Color(r, g, b, gray);
					this.glyphImg.setRGB(i, j, c.getRGB());
				}
			}
		}
	}
	
	public BufferedImage getGlyphBufferImage(Color color) {
		return glyphImg;
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
