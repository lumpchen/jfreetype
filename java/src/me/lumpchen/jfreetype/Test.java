package me.lumpchen.jfreetype;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import me.lumpchen.jfreetype.JFTLibrary.FTGlyphBitmap;
import me.lumpchen.jfreetype.JFTLibrary.FTGlyphSlotRec;
import me.lumpchen.jfreetype.JFTLibrary.FTVector;

import com.sun.jna.Native;

public class Test {

	public static void main(String[] args) throws IOException {
		// testBitmap();
		// testlib();
		// testMetrics();
		// testString();
		 testType1();

		// testMatrix();
//		testKening();
		
//		testOutline();
	}
	
	static void testKening() throws IOException {
		JFreeType ft = new JFreeType();

		File f = new File("c:/temp/fonts/msyh.ttf");
		InputStream is = new FileInputStream(f);
		byte[] stream = new byte[(int) f.length()];
		is.read(stream);
		ft.open(stream, 0);

		ft.setCharSize(32, 96, 96);
		
		double k = ft.getKerning('A', 'W');
		System.out.println(k);
	}

	static void testMatrix() throws IOException {
		JFrame frame = new JFrame();

		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		GlyphPanel canvas = new GlyphPanel();

		JFreeType ft = new JFreeType();

		File f = new File("c:/temp/fonts/msyh.ttf");
		InputStream is = new FileInputStream(f);
		byte[] stream = new byte[(int) f.length()];
		is.read(stream);
		ft.open(stream, 0);

		// ft.setCharSize((int) (36), 96, 96);
		ft.setCharSize(16, 96, 96);
		ft.setMatrix(1, 1, 0);

		String s = "PDF 繁殖复杂Reference";

		GlyphSlotRec[] glyphs = ft.getGlyphSlots(s);
		canvas.drawString(glyphs);

		ft.close();
		frame.add(canvas, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	static void testType1() throws IOException {
		JFrame frame = new JFrame();

		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		GlyphPanel canvas = new GlyphPanel();

		JFreeType ft = new JFreeType();
		// ft.open("c:/temp/fonts//msyh.ttf", 0);

		File f = new File("c:/temp/fonts/msyh.ttf");
		InputStream is = new FileInputStream(f);
		byte[] stream = new byte[(int) f.length()];
		is.read(stream);
		ft.open(stream, 0);

		String family = ft.getFamilyName();
		System.out.println(family);
		System.out.println(ft.getStyleName());

		ft.setCharSize((int) (36), 96, 96);

		String s = "PDF Reference";

		GlyphSlotRec[] glyphs = ft.getGlyphSlots(s);
		canvas.drawString(glyphs);

		// ft.close();

		ft = new JFreeType();
		f = new File("c:/temp/fonts/t1/283222649459342");
		s = "$Adobe Systems Incorporated and its subsidiaries own a number of patents covering technology"
				+ "AND EXPRESSLY DISCLAIMS ANY AND ALL WARRANTIES OF MERCHANTABILITY, FITNESS "
				+ "FOR PARTICULAR PURPOSES, AND NONINFRINGEMENT OF THIRD-PARTY RIGHTS.";
		is = new FileInputStream(f);
		stream = new byte[(int) f.length()];
		is.read(stream);
		ft.open(stream, 0);
		ft.setCharSize((int) (12), 96, 96);
		glyphs = ft.getGlyphSlots(s);
		canvas.drawString2(glyphs);
		ft.close();

		frame.add(canvas, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	static void testString() throws IOException {

		JFrame frame = new JFrame();

		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		GlyphPanel canvas = new GlyphPanel();

		JFreeType ft = new JFreeType();
		// ft.open("c:/temp/fonts//msyh.ttf", 0);

		File f = new File("c:/temp/fonts/msyh.ttf");
		InputStream is = new FileInputStream(f);
		byte[] stream = new byte[(int) f.length()];
		is.read(stream);
		ft.open(stream, 0);

		String family = ft.getFamilyName();
		System.out.println(family);
		System.out.println(ft.getStyleName());

		ft.setCharSize((int) (36), 96, 96);

		String s = "中 文繁殖复杂的事情";

		GlyphSlotRec[] glyphs = ft.getGlyphSlots(s);
		canvas.drawString(glyphs);

		ft.close();

		ft = new JFreeType();
		f = new File("c:/temp/fonts/simhei.ttf");
		is = new FileInputStream(f);
		stream = new byte[(int) f.length()];
		is.read(stream);
		ft.open(stream, 0);
		ft.setCharSize((int) (12), 96, 96);
		glyphs = ft.getGlyphSlots(s);
		canvas.drawString2(glyphs);
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
		// ft.open("c:/temp/fonts//msyh.ttf", 0);

		File f = new File("c:/temp/fonts/msyh.ttf");
		InputStream is = new FileInputStream(f);
		byte[] stream = new byte[(int) f.length()];
		is.read(stream);
		ft.open(stream, 0);

		ft.setCharSize(36, 72, 72);

		char c = '成';
		FTGlyphBitmap.ByValue bitmap = ft.getCharBitmap(c);

		show(bitmap);

		ft.close();

		frame.add(canvas, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	static void testMetrics() throws IOException {
		JFreeType ft = new JFreeType();

		File f = new File("c:/temp/fonts/msyh.ttf");
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

	static void testOutline() throws IOException {
		JFreeType ft = new JFreeType();

		File f = new File("c:/temp/fonts/msyh.ttf");
		InputStream is = new FileInputStream(f);
		byte[] stream = new byte[(int) f.length()];
		is.read(stream);
		ft.open(stream, 0);

		ft.setCharSize(16, 72, 72);

		char c = '.';
		// GlyphBitmap.ByValue bitmap = ft.getCharBitmap(c);

		// show(bitmap);

		FTGlyphSlotRec slot = ft.getGlyphSlot(c);
		System.out.println(slot.metrics.horiAdvance);

		int n = slot.outline.n_points;

		System.out.println(slot.outline.n_points);

		FTVector[] pVec = slot.outline.getPoints();
		for (FTVector p : pVec) {
			System.err.println(p.x + " " + p.y);
		}

		byte[] tags = slot.outline.getTags();
		for (byte p : tags) {
			System.err.println("" + (int) (p & 0xff));
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
