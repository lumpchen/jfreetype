
#ifndef _JFREETYPENATIVE_H_
#define _JFREETYPENATIVE_H_

#include <ft2build.h>
#include FT_FREETYPE_H
#include FT_STROKER_H

#include <iostream>

#define J_FT_EXPORT(x) extern "C" __declspec(dllexport) x

J_FT_EXPORT(FT_Library) j_FT_Init_FreeType();
FT_Library j_FT_Init_FreeType() 
{
	FT_Library lib;
	FT_Init_FreeType(&lib);
	return lib;
}

J_FT_EXPORT(int) j_FT_Done_FreeType(void*);
int j_FT_Done_FreeType(void* p_library)
{
	FT_Library lib = (FT_Library) p_library;
	return FT_Done_FreeType(lib);
}

J_FT_EXPORT(void*) j_FT_Open_Face(void*, const char*, int);
void* j_FT_Open_Face(void* p_library, const char* path, int index)
{
	FT_Library lib = (FT_Library) p_library;

	FT_Face face;
	FT_New_Face(lib, path, index, &face);

	return face;
}

J_FT_EXPORT(void*) j_FT_New_Memory_Face(void*, void*, int, int);
void* j_FT_New_Memory_Face(void* p_library, void* bytes, int size, int index)
{
	FT_Library lib = (FT_Library) p_library;

	FT_Face face;
	FT_New_Memory_Face(lib, (FT_Byte *)bytes, size, index, &face);

	return face;
}

J_FT_EXPORT(int) j_FT_Done_Face(void*);
int j_FT_Done_Face(void* p_face) 
{
	FT_Face face = (FT_Face) p_face;
	return FT_Done_Face(face);
}

J_FT_EXPORT(int) j_num_glyphs(void* p_face);
int j_num_glyphs(void* p_face) 
{
	FT_Face face = (FT_Face) p_face;
	return face->num_glyphs;
}

J_FT_EXPORT(int) j_FT_Set_Char_Size(void* , int, int, int, int);
int j_FT_Set_Char_Size(void* p_face, 
						int char_width, 
						int char_height,
						int horz_resolution,
						int vert_resolution) 
{
	FT_Face face = (FT_Face) p_face;
	FT_Error error = FT_Set_Char_Size(face, 
					char_width, 
					char_height, 
					horz_resolution, 
					vert_resolution);

	return error;
}

J_FT_EXPORT(int) j_FT_Get_Char_Index(void* , int);
int j_FT_Get_Char_Index(void* p_face, int c) 
{
	FT_Face face = (FT_Face) p_face;

	FT_Select_Charmap(face , ft_encoding_unicode);

	int glyph_index = FT_Get_Char_Index(face, c);

	return glyph_index;
}

J_FT_EXPORT(int) j_FT_Load_Glyph(void* , int, int);
int j_FT_Load_Glyph(void* p_face,
                 int glyph_index,
				 int load_flags ) 
{
	FT_Face face = (FT_Face) p_face;

	return FT_Load_Glyph(face, glyph_index, load_flags);
}

J_FT_EXPORT(int) j_FT_Render_Glyph(void* , int);
int j_FT_Render_Glyph(void* p_face, int render_mode ) 
{
	FT_Face face = (FT_Face) p_face;
	FT_GlyphSlot slot = face->glyph;

	return FT_Render_Glyph(face->glyph, FT_RENDER_MODE_NORMAL);
}


J_FT_EXPORT(FT_GlyphSlot) j_FT_Get_GlyphSlot(void*);
FT_GlyphSlot j_FT_Get_GlyphSlot(void* p_face) 
{
	FT_Face face = (FT_Face) p_face;
	FT_GlyphSlot slot = face->glyph;

	return slot;
}

J_FT_EXPORT(FT_Bitmap) j_FT_Get_Glyph_Bitmap(void*);
FT_Bitmap j_FT_Get_Glyph_Bitmap(void* p_face) 
{
	FT_Face face = (FT_Face) p_face;
	FT_GlyphSlot slot = face->glyph;

	FT_Bitmap bitmap = slot->bitmap;

	return bitmap;
}

J_FT_EXPORT(int) j_FT_Get_Units_Per_EM(void*);
int j_FT_Get_Units_Per_EM(void* p_face) 
{
	FT_Face face = (FT_Face) p_face;
	return face->units_per_EM;
}

J_FT_EXPORT(FT_BBox) j_FT_Glyph_Get_BBox(void*);
FT_BBox j_FT_Glyph_Get_BBox(void* p_face) 
{
	FT_Face face = (FT_Face) p_face;
	return face->bbox;
}

J_FT_EXPORT(FT_Stroker) j_FT_Stroker_Set(void*, int, int, int, int);
FT_Stroker j_FT_Stroker_Set(void* p_library, int radius, int line_cap, int line_join, int miter_limit) 
{
	FT_Library lib = (FT_Library) p_library;
	FT_Stroker stroker;
	FT_Stroker_New(lib, &stroker);
	
	FT_Stroker_Set(stroker, radius, FT_STROKER_LINECAP_ROUND, FT_STROKER_LINEJOIN_ROUND, 0);
	return stroker;
}

J_FT_EXPORT(int) j_FT_Glyph_Stroke(void*, void*, bool);
int j_FT_Glyph_Stroke(void* p_face, void* p_stroker, bool destroy)
{
	FT_Face face = (FT_Face) p_face;

	FT_Glyph glyph;
	FT_GlyphSlot slot = (FT_GlyphSlot)face->glyph;
	FT_Get_Glyph(slot, &glyph);
	FT_Stroker stroker = (FT_Stroker) p_stroker;

	return FT_Glyph_Stroke(&glyph, stroker, destroy);
}

J_FT_EXPORT(int) j_FT_Outline_Embolden(void*, int);
int j_FT_Outline_Embolden(void* p_face, int strength )
{
	FT_Face face = (FT_Face) p_face;

	FT_Glyph glyph;
	FT_GlyphSlot slot = (FT_GlyphSlot)face->glyph;
	FT_Outline* outline = &slot->outline;

	return FT_Outline_Embolden(outline, strength);
}

#endif //_JFREETYPENATIVE_H_