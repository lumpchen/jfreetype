package me.lumpchen.jfreetype;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public interface JFTLibrary extends Library {

	public static JFTLibrary INSTANCE = (JFTLibrary) Native.loadLibrary("jfreetypeNative",
			JFTLibrary.class);

	public Pointer j_FT_Init_FreeType();
	public int j_FT_Done_FreeType(Pointer library);

	public Pointer j_FT_Open_Face(Pointer library, String path, int faceIndex);
	public Pointer j_FT_New_Memory_Face(Pointer library, Pointer byteStream, int size, int faceIndex);
	public int j_FT_Done_Face(Pointer face);
	
	public int j_FT_Get_Units_Per_EM(Pointer face);
	public int j_num_glyphs(Pointer face);

	public int j_FT_Set_Char_Size(Pointer face, int charWidth, int charHeight,
			int horzResolution, int vertResolution);
	public int j_FT_Get_Char_Index(Pointer face, int c);
	public int j_FT_Load_Glyph(Pointer face, int glyphIndex, int loadFlags);
	public int j_FT_Render_Glyph(Pointer face, int renderMode);

	public FTGlyphSlotRec j_FT_Get_GlyphSlot(Pointer face);
	
	public FTGlyphBitmap.ByValue j_FT_Get_Glyph_Bitmap(Pointer face);
	
	public FTBBox.ByValue j_FT_Glyph_Get_BBox(Pointer face);
	
	public static interface FTLoadFlag {
		public static final int DEFAULT 					 = 0x0;
		public static final int NO_SCALE 					 = 1 << 0 ;
		public static final int NO_HINTING 					 = 1 << 1;
		public static final int RENDER 						 = 1 << 2;
		public static final int NO_BITMAP 					 = 1 << 3;
		public static final int VERTICAL_LAYOUT              = 1 << 4;
		public static final int FORCE_AUTOHINT               = 1 << 5;
		public static final int CROP_BITMAP                  = 1 << 6;
		public static final int PEDANTIC                     = 1 << 7;
		public static final int IGNORE_GLOBAL_ADVANCE_WIDTH  = 1 << 9;
		public static final int NO_RECURSE                   = 1 << 10;
		public static final int IGNORE_TRANSFORM             = 1 << 11;
		public static final int MONOCHROME                   = 1 << 12;
		public static final int LINEAR_DESIGN                = 1 << 13;
		public static final int NO_AUTOHINT                  = 1 << 15;
		  /* Bits 16..19 are used by `FT_LOAD_TARGET_' */
		public static final int FT_LOAD_COLOR                = 1 << 20;
	}
	
	public static interface FTRenderMode {
		public static final int NORMAL  = 0;
		public static final int LIGHT 	= 1;
		public static final int MONO 	= 2;
		public static final int LCD 	= 3;
		public static final int LCD_V 	= 4;
		public static final int MAX 	= 5;
	}
	
	public static class FTEncoding {
		public static final int NONE 		= 0;

		public static final int MS_SYMBOL 	= FTUtil.FT_ENC_TAG('s', 'y', 'm', 'b');
		public static final int UNICODE 	= FTUtil.FT_ENC_TAG('u', 'n', 'i', 'c');
		public static final int SJIS 		= FTUtil.FT_ENC_TAG('s', 'j', 'i', 's');
		public static final int GB2312 		= FTUtil.FT_ENC_TAG('g', 'b', ' ', ' ');
		public static final int BIG5 		= FTUtil.FT_ENC_TAG('b', 'i', 'g', '5');
		public static final int WANSUNG 	= FTUtil.FT_ENC_TAG('w', 'a', 'n', 's');
		public static final int JOHAB 		= FTUtil.FT_ENC_TAG('j', 'o', 'h', 'a');

		/* for backwards compatibility */
		public static final int MS_SJIS 	= SJIS;
		public static final int MS_GB2312 	= GB2312;
		public static final int MS_BIG5 	= BIG5;
		public static final int MS_WANSUNG 	= WANSUNG;
		public static final int MS_JOHAB 	= JOHAB;

		public static final int ADOBE_STANDARD 	= FTUtil.FT_ENC_TAG('A', 'D', 'O', 'B');
		public static final int ADOBE_EXPERT 	= FTUtil.FT_ENC_TAG('A', 'D', 'B', 'E');
		public static final int ADOBE_CUSTOM 	= FTUtil.FT_ENC_TAG('A', 'D', 'B', 'C');
		public static final int ADOBE_LATIN_1 	= FTUtil.FT_ENC_TAG('l', 'a', 't', '1');
		public static final int OLD_LATIN_2 	= FTUtil.FT_ENC_TAG('l', 'a', 't', '2');
		public static final int APPLE_ROMAN 	= FTUtil.FT_ENC_TAG('a', 'r', 'm', 'n');

	};
	
	public static interface FTGlyphFormat {
		public static final int NONE 		= 0;
		public static final int COMPOSITE 	= FTUtil.FT_ENC_TAG('c', 'o', 'm', 'p');
		public static final int BITMAP 		= FTUtil.FT_ENC_TAG('c', 'o', 'm', 'p');
		public static final int OUTLINE 	= FTUtil.FT_ENC_TAG('o', 'u', 't', 'l');
		public static final int PLOTTER 	= FTUtil.FT_ENC_TAG('p', 'l', 'o', 't');
	}
	
	public static class FTGlyphMetrics extends Structure {
		public static class ByReference extends FTGlyphMetrics implements Structure.ByReference {
		}

		public static class ByValue extends FTGlyphMetrics implements Structure.ByValue {
		}

		public NativeLong width;
		public NativeLong height;

		public NativeLong horiBearingX;
		public NativeLong horiBearingY;
		public NativeLong horiAdvance;

		public NativeLong vertBearingX;
		public NativeLong vertBearingY;
		public NativeLong vertAdvance;

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] {
					"width",
					"height",

					"horiBearingX",
					"horiBearingY",
					"horiAdvance",

					"vertBearingX",
					"vertBearingY",
					"vertAdvance"
			});
		}
	}
	
	public static class FTBBox extends Structure {
		public static class ByReference extends FTBBox implements Structure.ByReference {
		}

		public static class ByValue extends FTBBox implements Structure.ByValue {
		}

		public NativeLong xMin;
		public NativeLong yMin;

		public NativeLong xMax;
		public NativeLong yMax;

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] {
					"xMin",
					"yMin",

					"xMax",
					"yMax",
			});
		}
	}
	
	public static class FTGeneric extends Structure {
		public static class ByReference extends FTGeneric implements Structure.ByReference {
		}

		public static class ByValue extends FTGeneric implements Structure.ByValue {
		}

		public Pointer data;
		public Pointer finalizer;
		
		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] {
					"data",
					"finalizer"
			});
		}
	}
	
	public static class FTVector extends Structure {
		public static class ByReference extends FTVector implements Structure.ByReference {
		}

		public static class ByValue extends FTVector implements Structure.ByValue {
		}

		public NativeLong x;
		public NativeLong y;
		
		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] {
					"x",
					"y"
			});
		}
		
		public void read(Pointer p) {
			this.x = p.getNativeLong(Native.getNativeSize(NativeLong.class));
			this.y = p.getNativeLong(Native.getNativeSize(NativeLong.class));
		}
	}
	
	public static class FTGlyphOutline extends Structure {
		public static class ByReference extends FTGlyphOutline implements Structure.ByReference {
		}

		public static class ByValue extends FTGlyphOutline implements Structure.ByValue {
		}
		public short n_contours;
	    public short n_points;

//	    public FTVector[] points;
//	    public byte[] tags;
//	    public short[] contours;
	    
	    public Pointer points;
	    public Pointer tags;
	    public Pointer contours;

	    public int flags;

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] {
					"n_contours",
					"n_points",
					"points",
					"tags",
					"contours",
					"flags"
			});
		}
		
		public FTVector[] getPoints() {
			FTVector[] pVec = new FTVector[n_points];
			
			for (int i = 0; i < n_points * 2; i++) {
				
				NativeLong x = points.getNativeLong(i * Native.getNativeSize(NativeLong.class));
				i++;
				NativeLong y = points.getNativeLong(i * Native.getNativeSize(NativeLong.class));
				
				pVec[i / 2] = new FTVector();
				pVec[i / 2].x = x;
				pVec[i / 2].y = y;
			}
			
			return pVec;
		}
		
		public byte[] getTags() {
			byte[] tags = new byte[n_points];
			tags = this.tags.getByteArray(0, this.n_points);
			
			return tags;
		}
		
		public short[] getContours() {
			return this.contours.getShortArray(0, this.n_contours);
		}
	}
	
	public static class FTSubGlyph extends Structure {
		public static class ByReference extends FTSubGlyph implements Structure.ByReference {
		}

		public static class ByValue extends FTSubGlyph implements Structure.ByValue {
		}

		@Override
		protected List<String> getFieldOrder() {
			return null;
		}
	}
	
	public static class FTSlotInternal extends Structure {
		public static class ByReference extends FTSlotInternal implements Structure.ByReference {
		}

		public static class ByValue extends FTSlotInternal implements Structure.ByValue {
		}

		@Override
		protected List<String> getFieldOrder() {
			return null;
		}
	}
	
	
	public static class FTGlyphSlotRec extends Structure {
		public static class ByReference extends FTGlyphSlotRec implements Structure.ByReference {
		}

		public static class ByValue extends FTGlyphSlotRec implements Structure.ByValue {
		}
		
		public Pointer library;
	    public Pointer face;
	    public Pointer next;
	    public int reserved;       /* retained for binary compatibility */
	    public FTGeneric generic;

	    public FTGlyphMetrics  metrics;
	    public NativeLong linearHoriAdvance;
	    public NativeLong linearVertAdvance;
	    public FTVector advance;

	    public int format;

	    public FTGlyphBitmap bitmap;
	    public int bitmap_left;
	    public int bitmap_top;

	    public FTGlyphOutline outline;
//	    public Pointer outline;

	    public int num_subglyphs;
//	    public SubGlyph subglyphs;
	    public Pointer subglyphs;

	    public Pointer control_data;
	    public NativeLong control_len;

	    public NativeLong lsb_delta;
	    public NativeLong rsb_delta;

	    public Pointer other;

//	    public SlotInternal  internal;
	    public Pointer internal;
	    
		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] { 
					"library", 
					"face", 
					"next", 
					"reserved",
					"generic",
					"metrics", 
					"linearHoriAdvance", 
					"linearVertAdvance",
					"advance", 
					"format",
					"bitmap", 
					"bitmap_left",
					"bitmap_top",
					"outline", 
					"num_subglyphs", 
					"subglyphs",
					"control_data", 
					"control_len",
					"lsb_delta", 
					"rsb_delta",
					"other",
					"internal", 
					});
		}
	}
	
	public static class FTGlyphBitmap extends Structure {
		public static class ByReference extends FTGlyphBitmap implements Structure.ByReference {
		}

		public static class ByValue extends FTGlyphBitmap implements Structure.ByValue {
		}

		public int rows;
		public int width;
		public int pitch;
		public Pointer buffer;
		public byte num_grays;
		public byte pixel_mode;
		public byte palette_mode;
		public Pointer palette;

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList(new String[] { 
					"rows", 
					"width", 
					"pitch", 
					"buffer", 
					"num_grays",
					"pixel_mode", 
					"palette_mode", 
					"palette" });
		}
	}
	
	public static class FTUtil {
		public static int FT_ENC_TAG(char a, char b, char c, char d) {
			int value = 0;
			value |= ((int) (a & 0xff)) << 24;
			value |= ((int) (b & 0xff)) << 16;
			value |= ((int) (c & 0xff)) << 8;
			value |= ((int) (d & 0xff));
			return value;
		}		
	}
}
