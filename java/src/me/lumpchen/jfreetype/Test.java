package me.lumpchen.jfreetype;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import me.lumpchen.jfreetype.JFTLibrary.FTVector;
import me.lumpchen.jfreetype.JFTLibrary.FTGlyphBitmap;
import me.lumpchen.jfreetype.JFTLibrary.FTGlyphSlotRec;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

public class Test {

	public static void main(String[] args) throws IOException {
//		 testBitmap();
		// testlib();
//		 testMetrics();
		testString();
	}

	static void testString() throws IOException {
		
		JFrame frame = new JFrame();

		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		GlyphPanel canvas = new GlyphPanel();

		JFreeType ft = new JFreeType();
		// ft.open("c:/temp/msyh.ttf", 0);

		File f = new File("c:/temp/msyh.ttf");
		InputStream is = new FileInputStream(f);
		byte[] stream = new byte[(int) f.length()];
		is.read(stream);
		ft.open(stream, 0);

		ft.setCharSize((int) (12 * 0.75), 96, 96);
		
		String s = "中文";

		GlyphSlotRec[] glyphs = ft.getGlyphSlots(s, Color.RED);
		canvas.drawString(glyphs);
		
		ft.close();

		frame.add(canvas, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	
	static void testBitmap() throws IOException {
		JFrame frame = new JFrame();

		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		GlyphPanel canvas = new GlyphPanel();

		JFreeType ft = new JFreeType();
		// ft.open("c:/temp/msyh.ttf", 0);

		File f = new File("c:/temp/msyh.ttf");
		InputStream is = new FileInputStream(f);
		byte[] stream = new byte[(int) f.length()];
		is.read(stream);
		ft.open(stream, 0);

		ft.setCharSize(36, 72, 72);

		char c = '成';
		FTGlyphBitmap.ByValue bitmap = ft.getCharBitmap(c);

		show(bitmap);

		canvas.drawGlyphBitmap(bitmap, ft.getBBox());

		ft.close();

		frame.add(canvas, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	static void testMetrics() throws IOException {
		JFreeType ft = new JFreeType();

		File f = new File("c:/temp/msyh.ttf");
		InputStream is = new FileInputStream(f);
		byte[] stream = new byte[(int) f.length()];
		is.read(stream);
		ft.open(stream, 0);

		ft.setCharSize(22, 96, 96);

		char c = 'W';

		System.err.println(ft.getBBox());

		GlyphMetrics metrics = ft.getGlyphMetrics(c);
		System.out.println(metrics);
	}

	static void testlib() throws IOException {
		JFreeType ft = new JFreeType();

		File f = new File("c:/temp/msyh.ttf");
		InputStream is = new FileInputStream(f);
		byte[] stream = new byte[(int) f.length()];
		is.read(stream);
		ft.open(stream, 0);

		ft.setCharSize(16, 72, 72);

		char c = '成';
		// GlyphBitmap.ByValue bitmap = ft.getCharBitmap(c);

		// show(bitmap);

		FTGlyphSlotRec slot = ft.getGlyphSlot(c);
		System.out.println(slot.metrics.vertAdvance);

		int n = slot.outline.n_points;

		System.out.println(slot.outline.n_points);

		FTVector[] pVec = slot.outline.getPoints();
		// for (FTVector p : pVec) {
		// System.err.println(p.x + " " + p.y);
		// }

		short[] tags = slot.outline.getContours();
		for (short p : tags) {
			System.err.println("" + (int) p);
		}

		ft.close();
	}

	static void show(FTGlyphBitmap.ByValue bitmap) {
		int w = bitmap.width;
		int h = bitmap.rows;
		byte[][] data = new byte[h][w];
		for (int j = 0; j < h; j++) {
			for (int i = 0; i < w; i++) {
				data[j][i] = (byte) (0xff & bitmap.buffer.getChar((i + j * w)
						* Native.getNativeSize(Byte.TYPE)));
			}
		}

		for (int j = 0; j < h; j++) {
			for (int i = 0; i < w; i++) {
				byte b = data[j][i];
				char c = (int) (data[j][i] & 0xff) == 0 ? ' ' : '+';
				System.err.print(c);
			}
			System.err.println();
		}
	}
}
