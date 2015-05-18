
#ifndef _JFREETYPENATIVE_H_
#define _JFREETYPENATIVE_H_

#include <ft2build.h>
#include FT_FREETYPE_H
#include FT_STROKER_H

#include <iostream>

#define J_FT_EXPORT(x) extern "C" __declspec(dllexport) x

  /********************FreeType description*********************************/
  /*                                                                       */
  /* <Function>                                                            */
  /*    FT_Init_FreeType                                                   */
  /*                                                                       */
  /* <Description>                                                         */
  /*    Initialize a new FreeType library object.  The set of modules      */
  /*    that are registered by this function is determined at build time.  */
  /*                                                                       */
  /* <Output>                                                              */
  /*    alibrary :: A handle to a new library object.                      */
  /*                                                                       */
  /* <Return>                                                              */
  /*    FreeType error code.  0~means success.                             */
  /*                                                                       */
  /* <Note>                                                                */
  /*    In case you want to provide your own memory allocating routines,   */
  /*    use @FT_New_Library instead, followed by a call to                 */
  /*    @FT_Add_Default_Modules (or a series of calls to @FT_Add_Module).  */
  /*                                                                       */
  /*    For multi-threading applications each thread should have its own   */
  /*    FT_Library object.                                                 */
  /*                                                                       */
  /*    If you need reference-counting (cf. @FT_Reference_Library), use    */
  /*    @FT_New_Library and @FT_Done_Library.                              */
  /*                                                                       */
J_FT_EXPORT(FT_Library) j_FT_Init_FreeType();
FT_Library j_FT_Init_FreeType() 
{
	FT_Library lib;
	FT_Init_FreeType(&lib);
	return lib;
}

  /********************FreeType description*********************************/
  /*                                                                       */
  /* <Function>                                                            */
  /*    FT_Done_FreeType                                                   */
  /*                                                                       */
  /* <Description>                                                         */
  /*    Destroy a given FreeType library object and all of its children,   */
  /*    including resources, drivers, faces, sizes, etc.                   */
  /*                                                                       */
  /* <Input>                                                               */
  /*    library :: A handle to the target library object.                  */
  /*                                                                       */
  /* <Return>                                                              */
  /*    FreeType error code.  0~means success.                             */
  /*                                                                       */
J_FT_EXPORT(int) j_FT_Done_FreeType(void*);
int j_FT_Done_FreeType(void* p_library)
{
	FT_Library lib = (FT_Library) p_library;
	return FT_Done_FreeType(lib);
}

  /********************FreeType description*********************************/
  /*                                                                       */
  /* <Function>                                                            */
  /*    FT_New_Face                                                        */
  /*                                                                       */
  /* <Description>                                                         */
  /*    This function calls @FT_Open_Face to open a font by its pathname.  */
  /*                                                                       */
  /* <InOut>                                                               */
  /*    library    :: A handle to the library resource.                    */
  /*                                                                       */
  /* <Input>                                                               */
  /*    pathname   :: A path to the font file.                             */
  /*                                                                       */
  /*    face_index :: The index of the face within the font.  The first    */
  /*                  face has index~0.                                    */
  /*                                                                       */
  /* <Output>                                                              */
  /*    aface      :: A handle to a new face object.  If `face_index' is   */
  /*                  greater than or equal to zero, it must be non-NULL.  */
  /*                  See @FT_Open_Face for more details.                  */
  /*                                                                       */
  /* <Return>                                                              */
  /*    FreeType error code.  0~means success.                             */
  /*                                                                       */
  /* <Note>                                                                */
  /*    Use @FT_Done_Face to destroy the created @FT_Face object (along    */
  /*    with its slot and sizes).                                          */
  /*                                                                       */
J_FT_EXPORT(void*) j_FT_Open_Face(void*, const char*, int);
void* j_FT_Open_Face(void* p_library, const char* path, int index)
{
	FT_Library lib = (FT_Library) p_library;

	FT_Face face;
	FT_New_Face(lib, path, index, &face);

	FT_Select_Charmap(face , ft_encoding_unicode);
	return face;
}

  /********************FreeType description*********************************/
  /*                                                                       */
  /* <Function>                                                            */
  /*    FT_New_Memory_Face                                                 */
  /*                                                                       */
  /* <Description>                                                         */
  /*    This function calls @FT_Open_Face to open a font that has been     */
  /*    loaded into memory.                                                */
  /*                                                                       */
  /* <InOut>                                                               */
  /*    library    :: A handle to the library resource.                    */
  /*                                                                       */
  /* <Input>                                                               */
  /*    file_base  :: A pointer to the beginning of the font data.         */
  /*                                                                       */
  /*    file_size  :: The size of the memory chunk used by the font data.  */
  /*                                                                       */
  /*    face_index :: The index of the face within the font.  The first    */
  /*                  face has index~0.                                    */
  /*                                                                       */
  /* <Output>                                                              */
  /*    aface      :: A handle to a new face object.  If `face_index' is   */
  /*                  greater than or equal to zero, it must be non-NULL.  */
  /*                  See @FT_Open_Face for more details.                  */
  /*                                                                       */
  /* <Return>                                                              */
  /*    FreeType error code.  0~means success.                             */
  /*                                                                       */
  /* <Note>                                                                */
  /*    You must not deallocate the memory before calling @FT_Done_Face.   */
  /*                                                                       */
J_FT_EXPORT(void*) j_FT_New_Memory_Face(void*, void*, int, int);
void* j_FT_New_Memory_Face(void* p_library, void* bytes, int size, int index)
{
	FT_Library lib = (FT_Library) p_library;

	FT_Face face;
	FT_New_Memory_Face(lib, (FT_Byte *)bytes, size, index, &face);

	FT_Select_Charmap(face , ft_encoding_unicode);

	return face;
}

  /********************FreeType description*********************************/
  /*                                                                       */
  /* <Function>                                                            */
  /*    FT_Done_Face                                                       */
  /*                                                                       */
  /* <Description>                                                         */
  /*    Discard a given face object, as well as all of its child slots and */
  /*    sizes.                                                             */
  /*                                                                       */
  /* <Input>                                                               */
  /*    face :: A handle to a target face object.                          */
  /*                                                                       */
  /* <Return>                                                              */
  /*    FreeType error code.  0~means success.                             */
  /*                                                                       */
  /* <Note>                                                                */
  /*    See the discussion of reference counters in the description of     */
  /*    @FT_Reference_Face.                                                */
  /*                                                                       */
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

J_FT_EXPORT(FT_String*) j_family_name(void* p_face);
FT_String* j_family_name(void* p_face) 
{
	FT_Face face = (FT_Face) p_face;
	return face->family_name;
}

J_FT_EXPORT(FT_String*) j_style_name(void* p_face);
FT_String* j_style_name(void* p_face) 
{
	FT_Face face = (FT_Face) p_face;
	return face->style_name;
}

  /********************FreeType description*********************************/
  /*                                                                       */
  /* <Function>                                                            */
  /*    FT_Set_Char_Size                                                   */
  /*                                                                       */
  /* <Description>                                                         */
  /*    This function calls @FT_Request_Size to request the nominal size   */
  /*    (in points).                                                       */
  /*                                                                       */
  /* <InOut>                                                               */
  /*    face            :: A handle to a target face object.               */
  /*                                                                       */
  /* <Input>                                                               */
  /*    char_width      :: The nominal width, in 26.6 fractional points.   */
  /*                                                                       */
  /*    char_height     :: The nominal height, in 26.6 fractional points.  */
  /*                                                                       */
  /*    horz_resolution :: The horizontal resolution in dpi.               */
  /*                                                                       */
  /*    vert_resolution :: The vertical resolution in dpi.                 */
  /*                                                                       */
  /* <Return>                                                              */
  /*    FreeType error code.  0~means success.                             */
  /*                                                                       */
  /* <Note>                                                                */
  /*    If either the character width or height is zero, it is set equal   */
  /*    to the other value.                                                */
  /*                                                                       */
  /*    If either the horizontal or vertical resolution is zero, it is set */
  /*    equal to the other value.                                          */
  /*                                                                       */
  /*    A character width or height smaller than 1pt is set to 1pt; if     */
  /*    both resolution values are zero, they are set to 72dpi.            */
  /*                                                                       */
  /*    Don't use this function if you are using the FreeType cache API.   */
  /*                                                                       */
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

  /********************FreeType description*********************************/
  /*                                                                       */
  /* <Function>                                                            */
  /*    FT_Get_Char_Index                                                  */
  /*                                                                       */
  /* <Description>                                                         */
  /*    Return the glyph index of a given character code.  This function   */
  /*    uses a charmap object to do the mapping.                           */
  /*                                                                       */
  /* <Input>                                                               */
  /*    face     :: A handle to the source face object.                    */
  /*                                                                       */
  /*    charcode :: The character code.                                    */
  /*                                                                       */
  /* <Return>                                                              */
  /*    The glyph index.  0~means `undefined character code'.              */
  /*                                                                       */
  /* <Note>                                                                */
  /*    If you use FreeType to manipulate the contents of font files       */
  /*    directly, be aware that the glyph index returned by this function  */
  /*    doesn't always correspond to the internal indices used within the  */
  /*    file.  This is done to ensure that value~0 always corresponds to   */
  /*    the `missing glyph'.  If the first glyph is not named `.notdef',   */
  /*    then for Type~1 and Type~42 fonts, `.notdef' will be moved into    */
  /*    the glyph ID~0 position, and whatever was there will be moved to   */
  /*    the position `.notdef' had.  For Type~1 fonts, if there is no      */
  /*    `.notdef' glyph at all, then one will be created at index~0 and    */
  /*    whatever was there will be moved to the last index -- Type~42      */
  /*    fonts are considered invalid under this condition.                 */
  /*                                                                       */
J_FT_EXPORT(int) j_FT_Get_Char_Index(void* , int);
int j_FT_Get_Char_Index(void* p_face, int c) 
{
	FT_Face face = (FT_Face) p_face;
	int glyph_index = FT_Get_Char_Index(face, c);

	return glyph_index;
}

  /********************FreeType description*********************************/
  /*                                                                       */
  /* <Function>                                                            */
  /*    FT_Load_Glyph                                                      */
  /*                                                                       */
  /* <Description>                                                         */
  /*    A function used to load a single glyph into the glyph slot of a    */
  /*    face object.                                                       */
  /*                                                                       */
  /* <InOut>                                                               */
  /*    face        :: A handle to the target face object where the glyph  */
  /*                   is loaded.                                          */
  /*                                                                       */
  /* <Input>                                                               */
  /*    glyph_index :: The index of the glyph in the font file.  For       */
  /*                   CID-keyed fonts (either in PS or in CFF format)     */
  /*                   this argument specifies the CID value.              */
  /*                                                                       */
  /*    load_flags  :: A flag indicating what to load for this glyph.  The */
  /*                   @FT_LOAD_XXX constants can be used to control the   */
  /*                   glyph loading process (e.g., whether the outline    */
  /*                   should be scaled, whether to load bitmaps or not,   */
  /*                   whether to hint the outline, etc).                  */
  /*                                                                       */
  /* <Return>                                                              */
  /*    FreeType error code.  0~means success.                             */
  /*                                                                       */
  /* <Note>                                                                */
  /*    The loaded glyph may be transformed.  See @FT_Set_Transform for    */
  /*    the details.                                                       */
  /*                                                                       */
  /*    For subsetted CID-keyed fonts, `FT_Err_Invalid_Argument' is        */
  /*    returned for invalid CID values (this is, for CID values that      */
  /*    don't have a corresponding glyph in the font).  See the discussion */
  /*    of the @FT_FACE_FLAG_CID_KEYED flag for more details.              */
  /*                                                                       */
J_FT_EXPORT(int) j_FT_Load_Glyph(void* , int, int);
int j_FT_Load_Glyph(void* p_face,
                 int glyph_index,
				 int load_flags ) 
{
	FT_Face face = (FT_Face) p_face;

	return FT_Load_Glyph(face, glyph_index, load_flags);
}

  /********************FreeType description*********************************/
  /*                                                                       */
  /* <Function>                                                            */
  /*    FT_Render_Glyph                                                    */
  /*                                                                       */
  /* <Description>                                                         */
  /*    Convert a given glyph image to a bitmap.  It does so by inspecting */
  /*    the glyph image format, finding the relevant renderer, and         */
  /*    invoking it.                                                       */
  /*                                                                       */
  /* <InOut>                                                               */
  /*    slot        :: A handle to the glyph slot containing the image to  */
  /*                   convert.                                            */
  /*                                                                       */
  /* <Input>                                                               */
  /*    render_mode :: This is the render mode used to render the glyph    */
  /*                   image into a bitmap.  See @FT_Render_Mode for a     */
  /*                   list of possible values.                            */
  /*                                                                       */
  /* <Return>                                                              */
  /*    FreeType error code.  0~means success.                             */
  /*                                                                       */
  /* <Note>                                                                */
  /*    To get meaningful results, font scaling values must be set with    */
  /*    functions like @FT_Set_Char_Size before calling FT_Render_Glyph.   */
  /*                                                                       */
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

  /********************FreeType description*********************************/
  /*                                                                       */
  /* <Function>                                                            */
  /*    FT_Set_Transform                                                   */
  /*                                                                       */
  /* <Description>                                                         */
  /*    A function used to set the transformation that is applied to glyph */
  /*    images when they are loaded into a glyph slot through              */
  /*    @FT_Load_Glyph.                                                    */
  /*                                                                       */
  /* <InOut>                                                               */
  /*    face   :: A handle to the source face object.                      */
  /*                                                                       */
  /* <Input>                                                               */
  /*    matrix :: A pointer to the transformation's 2x2 matrix.  Use~0 for */
  /*              the identity matrix.                                     */
  /*    delta  :: A pointer to the translation vector.  Use~0 for the null */
  /*              vector.                                                  */
  /*                                                                       */
  /* <Note>                                                                */
  /*    The transformation is only applied to scalable image formats after */
  /*    the glyph has been loaded.  It means that hinting is unaltered by  */
  /*    the transformation and is performed on the character size given in */
  /*    the last call to @FT_Set_Char_Size or @FT_Set_Pixel_Sizes.         */
  /*                                                                       */
  /*    Note that this also transforms the `face.glyph.advance' field, but */
  /*    *not* the values in `face.glyph.metrics'.                          */
  /*                                                                       */
J_FT_EXPORT(void) j_FT_Set_Transform(void* p_face, long m00, long m01, long m10, long m11);
void j_FT_Set_Transform(void* p_face, long m00, long m01, long m10, long m11)
{
	FT_Face face = (FT_Face) p_face;

	FT_Matrix matrix;
	matrix.xx = m00;
	matrix.xy = m01;
	matrix.yx = m10;
	matrix.yy = m11;

	FT_Set_Transform(face, &matrix, 0);
}


#endif //_JFREETYPENATIVE_H_