package me.lumpchen.jfreetype;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

import me.lumpchen.jfreetype.JFTLibrary.FTGlyphBitmap;

import com.sun.jna.Native;

public class GlyphPanel extends JPanel {

	private static final long serialVersionUID = -6872279873956165739L;

	private BufferedImage glyphImg;
	private BBox bbox;
	
	Font f;

	private GlyphSlotRec[] glyphs;

	public GlyphPanel() {
		super();
		this.setSize(500, 500);
		
		try {
			this.f = Font.createFont(Font.TRUETYPE_FONT, new File("c:/temp/msyh.ttf"));
			this.f = this.f.deriveFont(12.0f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public void drawString(GlyphSlotRec[] glyphs) {
		this.glyphs = glyphs;
		this.repaint();
		
	}

	public void drawGlyphBitmap(FTGlyphBitmap bitmap, BBox bbox) {
		this.bbox = bbox;
		int h = bitmap.rows;
		int w = bitmap.width;

		this.glyphImg = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);

		byte[][] data = new byte[h][w];
		for (int j = 0; j < h; j++) {
			for (int i = 0; i < w; i++) {
				data[j][i] = (byte) (0xff & bitmap.buffer.getChar((i + j * w)
						* Native.getNativeSize(Byte.TYPE)));

				byte b = data[j][i];
				int g = (int) (b & 0xff);

				if (g == 0) {
					this.glyphImg.setRGB(i, j, b);
				} else {
					g = 255 - g;
					this.glyphImg.setRGB(i, j, (new Color(g, g, g)).getRGB());
				}
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (this.bbox != null) {

		}

		int baseX = 60;
		int baseY = 160;
		g2.setColor(Color.RED);
		g2.drawLine(baseX, baseY, baseX + this.getWidth(), baseY);

		g2.setFont(this.f);
		g2.drawString("abcdefg中国城市", baseX, baseY);

//		g2.drawImage(this.glyphImg, baseX, baseY, null);
		
		baseY += 100;
		
		for (GlyphSlotRec glyph : this.glyphs) {
			BufferedImage img = glyph.getGlyphBufferImage(Color.RED);
			double advance = glyph.getHAdvance();

			int y = (int) Math.round(baseY - glyph.getBearingY());
			g2.drawImage(img, baseX, y, null);
			baseX += advance;
		}
	}
}
