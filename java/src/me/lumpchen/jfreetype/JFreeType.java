package me.lumpchen.jfreetype;

import me.lumpchen.jfreetype.JFTLibrary.FTBBox;
import me.lumpchen.jfreetype.JFTLibrary.FTGlyphBitmap;
import me.lumpchen.jfreetype.JFTLibrary.FTGlyphMetrics;
import me.lumpchen.jfreetype.JFTLibrary.FTGlyphSlotRec;
import me.lumpchen.jfreetype.JFTLibrary.FTLoadFlag;
import me.lumpchen.jfreetype.JFTLibrary.FTRenderMode;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class JFreeType {

	private JFTLibrary library = JFTLibrary.INSTANCE;

	private Pointer pFreeType = Pointer.NULL;
	private Pointer face = Pointer.NULL;

	private Pointer stroker = Pointer.NULL;

	private double unitsPerEM = 0;
	private double pxpeu = 0;

	private int hRes;
	private int vRes;

	private Pointer nativeStream;
	private AffineTransform at = new AffineTransform();

	public JFreeType() {
		this.pFreeType = library.j_FT_Init_FreeType();
	}

	public boolean open(String path, int faceIndex) {
		if (this.face != Pointer.NULL) {
			this.close();
		}

		this.face = this.library.j_FT_Open_Face(this.pFreeType, path, faceIndex);
		this.unitsPerEM = this.library.j_FT_Get_Units_Per_EM(this.face);
		return true;
	}

	public boolean open(byte[] stream, int faceIndex) {
		if (this.face != Pointer.NULL) {
			this.close();
		}

		Pointer nativeStream = new Memory(stream.length);
		nativeStream.write(0, stream, 0, stream.length);

		this.face = this.library.j_FT_New_Memory_Face(this.pFreeType, nativeStream, stream.length,
				faceIndex);

		this.unitsPerEM = this.library.j_FT_Get_Units_Per_EM(this.face);
		return true;
	}

	public void close() {
		this.library.j_FT_Done_Face(this.face);
		this.face = Pointer.NULL;
		this.library.j_FT_Done_FreeType(this.pFreeType);
		this.pFreeType = Pointer.NULL;
	}

	public String getFamilyName() {
		return this.library.j_family_name(this.face);
	}

	public String getStyleName() {
		return this.library.j_style_name(this.face);
	}

	public double getUnitPerEM() {
		return this.unitsPerEM;
	}

	public void setMatrix(double scaleX, double scaleY, double angel) {
//		this.at.setToIdentity();
//		double r = Math.toRadians(15);
//		this.at.shear(r, 0);
//		int m00 = (int) Math.round(at.m00 * 0x10000);
//		int m10 = (int) Math.round(at.m10 * 0x10000);
//		int m01 = (int) Math.round(at.m01 * 0x10000);
//		int m11 = (int) Math.round(at.m11 * 0x10000);

		
		double r = Math.toRadians(angel);
		int m00 = (int) Math.round(Math.cos(r) * scaleX * 0x10000);
		int m10 = (int) Math.round(Math.sin(r) * 0x10000);
		int m01 = -m10;
		int m11 = (int) Math.round(Math.cos(r) * scaleY * 0x10000);

		double tan = Math.tan(Math.toRadians(15));
		m01 += tan * m00;
		m11 += tan * m10;
		

		this.library.j_FT_Set_Transform(this.face, m00, m01, m10, m11);
	}

	public void setCharSize(double pt, int hRes, int vRes) {
		this.hRes = hRes;
		this.vRes = vRes;

		int w = (int) Math.round(pt * 64);
		int h = (int) Math.round(pt * 64);
		this.library.j_FT_Set_Char_Size(this.face, w, h, hRes, vRes);
	}
	
	public void setCharSize(double charWidth, double charHeight, int hRes, int vRes) {
		this.hRes = hRes;
		this.vRes = vRes;

		int w = (int) Math.round(charWidth * 64);
		int h = (int) Math.round(charHeight * 64);
		this.library.j_FT_Set_Char_Size(this.face, w, h, hRes, vRes);
	}

	public FTGlyphSlotRec getGlyphSlot(char c) {
		int glyph = this.library.j_FT_Get_Char_Index(face, (int) (c & 0xffff));
		if (glyph == 0) {

		}
		this.library.j_FT_Load_Glyph(this.face, glyph, FTLoadFlag.DEFAULT);
		FTGlyphSlotRec slot = this.library.j_FT_Get_GlyphSlot(this.face);

		return slot;
	}

	public FTGlyphBitmap.ByValue getCharBitmap(char c) {
		int glyph = this.library.j_FT_Get_Char_Index(face, (int) (c & 0xffff));
		if (glyph == 0) {

		}
		this.library.j_FT_Load_Glyph(this.face, glyph, FTLoadFlag.DEFAULT);

		this.library.j_FT_Render_Glyph(this.face, FTRenderMode.NORMAL);
		FTGlyphBitmap.ByValue bitmap = this.library.j_FT_Get_Glyph_Bitmap(this.face);

		return bitmap;
	}

	public GlyphMetrics getGlyphMetrics(char c) {
		FTGlyphSlotRec slot = this.library.j_FT_Get_GlyphSlot(this.face);
		return new GlyphMetrics(slot.metrics, this.at);
	}

	public BBox getBBox() {
		FTBBox box = this.library.j_FT_Glyph_Get_BBox(this.face);
		return new BBox(box, this.pxpeu);
	}

	private FTGlyphBitmap bitmap;
	private FTGlyphMetrics metrics;

	private void beatCharBitmap(char c) {
		int gid = this.library.j_FT_Get_Char_Index(face, (int) (c));
		if (gid == 0) {
			System.err.println("not found glyph: " + c + "(" + (int) c + ")");
		}

		this.beatGlyphBitmap(gid);
	}

	private void beatGlyphBitmap(int gid) {
		this.library.j_FT_Load_Glyph(this.face, gid, FTLoadFlag.DEFAULT);
		FTGlyphSlotRec slot = this.library.j_FT_Get_GlyphSlot(this.face);
		this.metrics = slot.metrics;

		this.library.j_FT_Render_Glyph(this.face, FTRenderMode.NORMAL);
		this.bitmap = this.library.j_FT_Get_Glyph_Bitmap(this.face);
	}

	public GlyphSlotRec[] getGlyphSlots(String s) {
		GlyphSlotRec[] glyphSlots = new GlyphSlotRec[s.length()];
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			this.beatCharBitmap(c);
			glyphSlots[i] = new GlyphSlotRec(c, this.bitmap, this.metrics, this.at);
		}

		return glyphSlots;
	}

	public GlyphSlotRec[] getGlyphSlots(char[] gid) {
		GlyphSlotRec[] glyphSlots = new GlyphSlotRec[gid.length];
		for (int i = 0; i < gid.length; i++) {
			this.beatGlyphBitmap(gid[i]);
			glyphSlots[i] = new GlyphSlotRec(gid[i], this.bitmap, this.metrics, this.at);
		}

		return glyphSlots;
	}
}
