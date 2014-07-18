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

public class GlyphPanel extends JPanel {

	private static final long serialVersionUID = -6872279873956165739L;
	private BBox bbox;

	Font f;

	private GlyphSlotRec[] glyphs;

	public GlyphPanel() {
		super();
		this.setSize(500, 500);

		try {
			this.f = Font.createFont(Font.TRUETYPE_FONT, new File("c:/temp/msyh.ttf"));
			this.f = this.f.deriveFont(36.0f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public void drawString(GlyphSlotRec[] glyphs) {
		this.glyphs = glyphs;
		this.repaint();
	}
	
	
	private GlyphSlotRec[] glyphs2;
	public void drawString2(GlyphSlotRec[] glyphs) {
		this.glyphs2 = glyphs;
		this.repaint();
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
		g2.drawString("中 文繁殖复杂的事情", baseX, baseY);

		baseY += 50;
		g2.setFont(this.f.deriveFont(Font.BOLD));
		g2.drawString("中 文繁殖复杂的事情", baseX, baseY);
		
		// g2.drawImage(this.glyphImg, baseX, baseY, null);

		baseY += 100;

		for (GlyphSlotRec glyph : this.glyphs) {
			double advance = glyph.getHAdvance();
			int y = (int) Math.round(baseY - glyph.getBearingY());
			
			BufferedImage img = glyph.getGlyphBitmap();
			if (img != null) {
				g2.drawImage(img, baseX, y, null);
			}

			baseX += advance;
		}
		
		baseY += 100;
		baseX = 60;
		for (GlyphSlotRec glyph : this.glyphs2) {
			double advance = glyph.getHAdvance();
			int y = (int) Math.round(baseY - glyph.getBearingY());
			
			BufferedImage img = glyph.getGlyphBitmap(Color.BLUE);
			if (img != null) {
				g2.drawImage(img, baseX, y, null);
			}

			baseX += advance;
		}
	}
}
