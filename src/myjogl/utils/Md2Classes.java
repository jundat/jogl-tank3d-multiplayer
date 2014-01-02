/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import java.io.IOException;

/**
 *
 * @author TIEUNUN
 */

class md2_header_t {
    int ident;              // magic number. must be equal to "IDP2"
    int version;            // md2 version. must be equal to 8

    int skinwidth;          // width of the texture
    int skinheight;         // height of the texture
    int framesize;          // size of one frame in bytes

    int num_skins;          // number of textures
    int num_xyz;            // number of vertices
    int num_st;             // number of texture coordinates
    int num_tris;           // number of triangles
    int num_glcmds;         // number of opengl commands
    int num_frames;         // total number of frames

    int ofs_skins;          // offset to skin names (64 bytes each)
    int ofs_st;             // offset to s-t texture coordinates
    int ofs_tris;           // offset to triangles
    int ofs_frames;         // offset to frame data
    int ofs_glcmds;         // offset to opengl commands
    int ofs_end;            // offset to end of file
    
    public md2_header_t() {    
    }
    
     public boolean LoadHeader(LittleEndianDataInputStream in) throws IOException {
        ident = in.readInt();
        version = in.readInt();
        skinwidth = in.readInt();
        skinheight = in.readInt();
        framesize = in.readInt();
        num_skins = in.readInt();
        num_xyz = in.readInt();
        num_st = in.readInt();
        num_tris = in.readInt();
        num_glcmds = in.readInt();
        num_frames = in.readInt();
        ofs_skins = in.readInt();
        ofs_st = in.readInt();
        ofs_tris = in.readInt();
        ofs_frames = in.readInt();
        ofs_glcmds = in.readInt();
        ofs_end = in.readInt();
        return true;
    }
}

class vec3_t {
    public float [] v;
    public vec3_t() {
        v = new float[3];
    }
    
    public vec3_t(float x,float y,float z)    {
        v = new float[3];
        v[0] = x;
        v[1] = y;
        v[2] = z;
    }
}

// vertex
class vertex_t {
    public float [] v;              // compressed vertex (x, y, z) coordinates
    public int normalIndex;         // index to a normal vector for the lighting
    
    public vertex_t() {
        v = new float[3];
    }
}

// frame
class frame_t {
    public float [] scale;          // scale values
    public float [] translate;      // translation vector
    public String name;             // frame name
    vertex_t[] verts;               // first vertex of this frame
    
    public frame_t() {
        scale = new float[3];
        translate = new float[3];
    }
}

// triangle
class triangle_t {
    public int[] index_xyz;         // indexes to triangle's vertices
    public int[] index_st;          // indexes to vertices' texture coorinates
    
    public triangle_t() {
        index_xyz = new int[3];
        index_st = new int[3];
    }
}

class texCoord_t {
    int    s;
    int    t;
}