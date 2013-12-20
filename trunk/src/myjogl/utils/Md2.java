/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.swing.JOptionPane;
import myjogl.utils.LittleEndianDataInputStream;

/**
 *
 * @author TIEUNUN
 */
public class Md2 {

    private float scale;
    private float interpol;             // time counter
    private int currentFrame;
    private int nextFrame;              // next frame # in animation
    
    /**
     * current frame's vertices (array of vertex)
     */
    private vec3_t[] currentVList;             // current frame vertices 
    
    /**
     * next frame's vertices (array of vertex)
     */
    private vec3_t[] nextVList;         // next frame vertices
    
    /**
     * list các ??nh ?ã ???c tính toán l?i
     * các ??nh này m?i ???c dùng ?? v?
     */
    private vec3_t[] vertlist;          // list interpolated vertices
    
    public byte[] buffer;              // Md2 data
    public int num_frames;             // number of frames
    public int num_xyz;                // number of vertices in 1 frame
    public int num_glcmds;             // number of opengl commands
    public md2_header_t m_header;      // file header
    public triangle_t[] m_triangles;   // array of triagle
    public frame_t[] m_frames;         // array of frame
    public vec3_t[] m_vertices;        // vertex array, all vertices in all frame
    public int[] m_glcmds;             // opengl command array
    public texCoord_t[] m_texCoord;    // texture coord array
    Texture m_texture;

    public Md2() {
        interpol = 0.0f;
        currentFrame = 0;
        nextFrame = 1;
        scale = 1.0f;
    }

    private void ReadFile(String filename) {

        InputStream inputStr = getClass().getResourceAsStream(filename);

        try {
            buffer = new byte[inputStr.available()];
            inputStr.read(buffer, 0, buffer.length);
        } catch (IOException ex) {
            System.out.println("md2.Readfile.buffer.available(): can not read!");
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStr.close();
            } catch (IOException ex) {
                Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Boolean LoadModel(String filename) {
        ReadFile(filename);

        //read header from buffer array
        InputStream bis = new ByteArrayInputStream(buffer);
        LittleEndianDataInputStream din = new LittleEndianDataInputStream(new DataInputStream(bis));
        
        try {
            m_header = new md2_header_t();
            m_header.LoadHeader(din);
            din.close();
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        }

        num_frames = m_header.num_frames;
        num_xyz = m_header.num_xyz;
        num_glcmds = m_header.num_glcmds;
        
        //number of all vertices = num_frame * num_xyz (in 1 frame)
        m_vertices = new vec3_t[num_frames * num_xyz];
        
        ///////////////////////////////////////////////////

        try {
            din.reset();
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        }

        //load st coord array
        m_texCoord = new texCoord_t[m_header.num_st];
        
        try {
            din.skip(m_header.ofs_st);
            
            for (int i = 0; i < m_header.num_st; i++) {
                m_texCoord[i] = new texCoord_t();
                
                m_texCoord[i].s = din.readUnsignedShort();
                m_texCoord[i].t = din.readUnsignedShort();
            }
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        }

        ///////////////////////////////////////////////////
        
        // Load triangle array initialization with index vertex and index texture coord
        try {
            din.reset();
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        }

        m_triangles = new triangle_t[m_header.num_tris];
        
        try {
            din.skip(m_header.ofs_tris);
            
            for (int i = 0; i < m_header.num_tris; i++) {
                m_triangles[i] = new triangle_t();
                
                m_triangles[i].index_xyz[0] = din.readUnsignedShort();
                m_triangles[i].index_xyz[1] = din.readUnsignedShort();
                m_triangles[i].index_xyz[2] = din.readUnsignedShort();
                m_triangles[i].index_st[0] = din.readUnsignedShort();
                m_triangles[i].index_st[1] = din.readUnsignedShort();
                m_triangles[i].index_st[2] = din.readUnsignedShort();
            }
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        }

        // vertex array initialization
        try {
            din.reset();
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        }

        m_frames = new frame_t[m_header.num_frames];
        try {
            din.skip(m_header.ofs_frames);
            
            for (int i = 0; i < m_header.num_frames; i++) {
                m_frames[i] = new frame_t();

                m_frames[i].scale[0] = din.readFloat();
                m_frames[i].scale[1] = din.readFloat();
                m_frames[i].scale[2] = din.readFloat();

                m_frames[i].translate[0] = din.readFloat();
                m_frames[i].translate[1] = din.readFloat();
                m_frames[i].translate[2] = din.readFloat();

                //name of frame
                byte[] name = new byte[16];
                din.readFully(name);
                
                m_frames[i].name = new String(name);

                m_frames[i].verts = new vertex_t[num_xyz];
                for (int j = 0; j < num_xyz; j++) {
                    m_frames[i].verts[j] = new vertex_t();
                    
                    m_frames[i].verts[j].v[0] = din.readUnsignedByte();
                    m_frames[i].verts[j].v[1] = din.readUnsignedByte();
                    m_frames[i].verts[j].v[2] = din.readUnsignedByte();
                    m_frames[i].verts[j].normalIndex = din.readUnsignedByte();
                    
                    //calculate m_vertieces
                    m_vertices[i * num_xyz + j] = new vec3_t();
                    
                    m_vertices[i * num_xyz + j].v[0] = m_frames[i].scale[0] * m_frames[i].verts[j].v[0] + m_frames[i].translate[0];
                    m_vertices[i * num_xyz + j].v[1] = m_frames[i].scale[1] * m_frames[i].verts[j].v[1] + m_frames[i].translate[1];
                    m_vertices[i * num_xyz + j].v[2] = m_frames[i].scale[2] * m_frames[i].verts[j].v[2] + m_frames[i].translate[2];
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        currentVList = new vec3_t[m_header.num_xyz]; // current frame vertices 
        nextVList = new vec3_t[m_header.num_xyz]; // next frame vertices
        vertlist = new vec3_t[m_header.num_xyz]; // list interpolated vertices
        
        for (int i = 0; i < m_header.num_xyz; i++) {
            currentVList[i] = new vec3_t();
            nextVList[i] = new vec3_t();
            vertlist[i] = new vec3_t();
        }

        if (num_frames > 1) {
            Interpolate();
        }

        return true;
    }

    public void LoadSkin(GLAutoDrawable drawable, String filename) {
        GL gl = drawable.getGL();

            InputStream stream;
            TextureData data;

            m_texture = ResourceManager.getInst().getTexture(filename, false, GL.GL_REPEAT);

            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
    }
    
    public void LoadSkin(Texture texture)
    {
        this.m_texture = texture;
    }

    //renders a single key frame
    public void DrawModel(GL gl, int keyframe) {
        gl.glEnable(GL.GL_TEXTURE);
        m_texture.enable();
        m_texture.bind();
        gl.glBegin(GL.GL_TRIANGLES);
        for (int i = 0; i < m_header.num_tris; i++) {
            float x0 = (m_vertices[m_triangles[i].index_xyz[0] + m_header.num_xyz * keyframe].v[0]) * scale;
            float y0 = (m_vertices[m_triangles[i].index_xyz[0] + m_header.num_xyz * keyframe].v[1]) * scale;
            float z0 = (m_vertices[m_triangles[i].index_xyz[0] + m_header.num_xyz * keyframe].v[2]) * scale;
                
            float s0 = (float)(m_texCoord[m_triangles[i].index_st[0]].s) / m_header.skinwidth;
            float t0 = (float)(m_texCoord[m_triangles[i].index_st[0]].t) / m_header.skinheight;
            
            float x1 = (m_vertices[m_triangles[i].index_xyz[1] + m_header.num_xyz * keyframe].v[0]) * scale;
            float y1 = (m_vertices[m_triangles[i].index_xyz[1] + m_header.num_xyz * keyframe].v[1]) * scale;
            float z1 = (m_vertices[m_triangles[i].index_xyz[1] + m_header.num_xyz * keyframe].v[2]) * scale;
                
            float s1 = (float)(m_texCoord[m_triangles[i].index_st[1]].s) / m_header.skinwidth;
            float t1 = (float)(m_texCoord[m_triangles[i].index_st[1]].t) / m_header.skinheight;
            
            float x2 = (m_vertices[m_triangles[i].index_xyz[2] + m_header.num_xyz * keyframe].v[0]) * scale;
            float y2 = (m_vertices[m_triangles[i].index_xyz[2] + m_header.num_xyz * keyframe].v[1]) * scale;
            float z2 = (m_vertices[m_triangles[i].index_xyz[2] + m_header.num_xyz * keyframe].v[2]) * scale;
                
            float s2 = (float)(m_texCoord[m_triangles[i].index_st[2]].s) / m_header.skinwidth;
            float t2 = (float)(m_texCoord[m_triangles[i].index_st[2]].t) / m_header.skinheight;
            
            vec3_t n0,n1,n2;
            n0 = new vec3_t(x0,y0,z0);
            n1 = new vec3_t(x1,y1,z1);
            n2 = new vec3_t(x2,y2,z2);
            
            this.CalculateNormal(n0, n1, n2, gl);
            gl.glTexCoord2f(s0, t0);
            gl.glVertex3f(x0, y0, z0);
            
            gl.glTexCoord2f(s1, t1);
            gl.glVertex3f(x1, y1, z1);
            
            gl.glTexCoord2f(s2, t2);
            gl.glVertex3f(x2, y2, z2);
        }
        gl.glEnd();
        m_texture.disable();
    }

    //Draw animation from start frame too end frame
    public int DrawAnimate(GL gl,int startFrame, int endFrame, float percent)
    {
        if ((startFrame > currentFrame)) {
            currentFrame = startFrame;
        }
        
        if ((startFrame < 0) || (endFrame < 0)) {
            return -1;
        }

	if ((startFrame >= num_frames) || (endFrame >= num_frames)) {
            return -1;
        }
        
        if (interpol >= 1.0)
        {
            interpol = 0.0f;
            currentFrame++;
            //this.change();
            if (currentFrame >= endFrame) {
                currentFrame = startFrame;
            }
          
            nextFrame = currentFrame + 1;
            if (nextFrame >= endFrame) {
                nextFrame = startFrame;
            }
        }
        Interpolate();
        gl.glEnable(GL.GL_TEXTURE);
        m_texture.enable();
        m_texture.bind();
        gl.glBegin(GL.GL_TRIANGLES);
        /*for (int i = 0; i < m_header.num_tris ; i++) {
                for (int j = 0; j < 3 ; j++) {
                    float x = vertlist[m_triangles[i].index_xyz[j]].v[0];
                    float y = vertlist[m_triangles[i].index_xyz[j]].v[1];
                    float z = vertlist[m_triangles[i].index_xyz[j]].v[2];
                    //gl.glColor3f(0.0f, 0.0f, 1.0f);
                    //gl.glColor3f(0.5f, 0.5f, 1.0f);
                    float s = (float)(m_texCoord[m_triangles[i].index_st[j]].s) / m_header.skinwidth;
                    float t = (float)(m_texCoord[m_triangles[i].index_st[j]].t) / m_header.skinheight;
                    
                    gl.glTexCoord2f(s, t);
                    gl.glVertex3f(x, y, z);
                }
            }*/
        for (int i = 0; i < m_header.num_tris ; i++) {
            float x0 = vertlist[m_triangles[i].index_xyz[0]].v[0];
            float y0 = vertlist[m_triangles[i].index_xyz[0]].v[1];
            float z0 = vertlist[m_triangles[i].index_xyz[0]].v[2];
            
            float s0 = (float)(m_texCoord[m_triangles[i].index_st[0]].s) / m_header.skinwidth;
            float t0 = (float)(m_texCoord[m_triangles[i].index_st[0]].t) / m_header.skinheight;
            
            float x1 = vertlist[m_triangles[i].index_xyz[1]].v[0];
            float y1 = vertlist[m_triangles[i].index_xyz[1]].v[1];
            float z1 = vertlist[m_triangles[i].index_xyz[1]].v[2];

            float s1 = (float)(m_texCoord[m_triangles[i].index_st[1]].s) / m_header.skinwidth;
            float t1 = (float)(m_texCoord[m_triangles[i].index_st[1]].t) / m_header.skinheight;
            
            float x2 = vertlist[m_triangles[i].index_xyz[2]].v[0];
            float y2 = vertlist[m_triangles[i].index_xyz[2]].v[1];
            float z2 = vertlist[m_triangles[i].index_xyz[2]].v[2];

            float s2 = (float)(m_texCoord[m_triangles[i].index_st[2]].s) / m_header.skinwidth;
            float t2 = (float)(m_texCoord[m_triangles[i].index_st[2]].t) / m_header.skinheight;
            
            vec3_t n0,n1,n2;
            n0 = new vec3_t(x0,y0,z0);
            n1 = new vec3_t(x1,y1,z1);
            n2 = new vec3_t(x2,y2,z2);
            
            this.CalculateNormal(n0, n1, n2, gl);
            gl.glTexCoord2f(s0, t0);
            gl.glVertex3f(x0, y0, z0);
            
            gl.glTexCoord2f(s1, t1);
            gl.glVertex3f(x1, y1, z1);
            
            gl.glTexCoord2f(s2, t2);
            gl.glVertex3f(x2, y2, z2);
        }
        gl.glEnd();
        interpol += percent;
        return 0;
    }

    private void CalculateNormal( vec3_t p1, vec3_t p2,vec3_t p3,GL gl ){
        float[] a = new float[3];
        float[] b = new float[3];
        float[] result = new float[3];
	float length;
        
        a[0] = p1.v[0] - p2.v[0];
	a[1] = p1.v[1] - p2.v[1];
	a[2] = p1.v[2] - p2.v[2];

	b[0] = p1.v[0] - p3.v[0];
	b[1] = p1.v[1] - p3.v[1];
	b[2] = p1.v[2] - p3.v[2];
        
        result[0] = a[1] * b[2] - b[1] * a[2];
	result[1] = b[0] * a[2] - a[0] * b[2];
	result[2] = a[0] * b[1] - b[0] * a[1];
        
        length = (float)Math.sqrt(result[0]*result[0] + result[1]*result[1] + result[2]*result[2]);
        
        gl.glNormal3f(result[0]/length, result[1]/length, result[2]/length);
    }
        
    public void SetScale(float scale) {
        this.scale = scale;
    }
    // calculate interpolated vertices from current frame vertices and next frame vertices,scale,interpol

    public void Interpolate() {
        for (int i = 0; i < m_header.num_xyz; i++) {
            currentVList[i].v[0] = this.m_vertices[i + m_header.num_xyz * currentFrame].v[0];
            currentVList[i].v[1] = this.m_vertices[i + m_header.num_xyz * currentFrame].v[1];
            currentVList[i].v[2] = this.m_vertices[i + m_header.num_xyz * currentFrame].v[2];

            nextVList[i].v[0] = this.m_vertices[i + m_header.num_xyz * nextFrame].v[0];
            nextVList[i].v[1] = this.m_vertices[i + m_header.num_xyz * nextFrame].v[1];
            nextVList[i].v[2] = this.m_vertices[i + m_header.num_xyz * nextFrame].v[2];

            vertlist[i].v[0] = (currentVList[i].v[0] + interpol * (nextVList[i].v[0] - currentVList[i].v[0])) * scale;
            vertlist[i].v[1] = (currentVList[i].v[1] + interpol * (nextVList[i].v[1] - currentVList[i].v[1])) * scale;
            vertlist[i].v[2] = (currentVList[i].v[2] + interpol * (nextVList[i].v[2] - currentVList[i].v[2])) * scale;
        }
    }
}
