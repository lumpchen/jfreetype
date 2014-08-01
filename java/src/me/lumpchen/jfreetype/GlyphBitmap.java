package me.lumpchen.jfreetype;

import java.awt.Color;
import java.awt.image.BufferedImage;

import me.lumpchen.jfreetype.JFTLibrary.FTGlyphBitmap;

import com.sun.jna.Native;

public class GlyphBitmap {

	private int width;
	private int height;
	private byte[][] data;
	private BufferedImage glyphImg;

	public GlyphBitmap(FTGlyphBitmap bitmap) {
		this.height = bitmap.rows;
		this.width = bitmap.width;

		this.data = new byte[this.height][this.width];
		for (int j = 0; j < this.height; j++) {
			for (int i = 0; i < this.width; i++) {
				data[j][i] = (byte) (0xff & bitmap.buffer.getChar((i + j * this.width)
						* Native.getNativeSize(Byte.TYPE)));
			}
		}
	}

	public BufferedImage getBitmap(Color color) {
		if (this.glyphImg != null) {
			return this.glyphImg;
		}

		if (this.width == 0) {
			return null;
		}
		
		int rgb = color.getRGB();
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();

		this.glyphImg = new BufferedImage(this.width, this.height, BufferedImage.TYPE_4BYTE_ABGR);

		for (int j = 0; j < this.height; j++) {
			for (int i = 0; i < this.width; i++) {
				int gray = (int) (data[j][i] & 0xff);

				if (gray == 255) {
					this.glyphImg.setRGB(i, j, rgb);
				}
				if (gray == 0) {
					this.glyphImg.setRGB(i, j, 0xFFFFFF);
				} else {
					Color c = new Color(r, g, b, gray);
					this.glyphImg.setRGB(i, j, c.getRGB());
				}
			}
		}

		return this.glyphImg;
	}
	
	public BufferedImage getBitmap() {
		return getBitmap(Color.black);
		
//		if (this.glyphImg != null) {
//			return this.glyphImg;
//		}
//		
//		if (this.width == 0) {
//			return null;
//		}
//
//		this.glyphImg = new BufferedImage(this.width, this.height, BufferedImage.TYPE_BYTE_GRAY);
//
//		for (int j = 0; j < this.height; j++) {
//			for (int i = 0; i < this.width; i++) {
//				int gray = (int) (data[j][i] & 0xff);
//
//				System.out.println(gray);
//				if (gray == 255) {
//					this.glyphImg.setRGB(i, j, 0);
//				}
//				if (gray == 0) {
//					this.glyphImg.setRGB(i, j, 0xFFFFFF);
//				} else {
//					gray = 255 - gray;
//					
//			        int rgb = ((0 & 0xFF) << 24) |
//			               ((gray & 0xFF) << 16) |
//			               ((gray & 0xFF) << 8)  |
//			               ((gray & 0xFF) << 0);
//					this.glyphImg.setRGB(i, j, rgb);
//				}
//			}
//		}
//
//		return this.glyphImg;
	}

}
