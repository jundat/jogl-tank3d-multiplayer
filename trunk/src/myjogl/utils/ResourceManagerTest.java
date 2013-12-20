/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import myjogl.particles.RoundSparks;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.io.InputStream;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author bu0i
 */
public class ResourceManagerTest {
    
    private static ResourceManagerTest m_instance = null;
    
    public static ResourceManagerTest getInstance() {
        if (m_instance == null)
            m_instance = new ResourceManagerTest();
        return m_instance;
    }
    
    private ResourceManagerTest() {
    }
    
    public void LoadResource(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        // Load explo1
        int m_textureCount = 4;
        explo1 = new Texture[m_textureCount];
        String filename = "data/Explo1";
        for(int i = 0; i < 4; i ++) {
            try {
                InputStream stream;
                TextureData data;
                String temp = filename + "_" + i +".png";
                stream = getClass().getResourceAsStream(temp);
                data = TextureIO.newTextureData(stream, false, "png");
                explo1[i] = TextureIO.newTexture(data);
                //m_texture[i] = new Texture();

                gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
                gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
                gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
                gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);

            } catch (IOException exc) {
                System.out.println("LoadSkin: Can not load resource: " + exc.getMessage());
                System.exit(1);
            }
        }
        // End Load explo1
        // Load Smoke
        try {
            InputStream stream;
            TextureData data;
            filename = "data/smoke2.png";
            stream = getClass().getResourceAsStream(filename);
            data = TextureIO.newTextureData(stream, false, "png");
            smoke = TextureIO.newTexture(data);

            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);

        } catch (IOException exc) {
            System.out.println("LoadSkin: Can not load resource: " + exc.getMessage());
            System.exit(1);
        }
        // End load Smoke
        // Load explo
        explo = new Texture[4];
        filename = "data/Explo";
        for(int i = 0; i < 4; i ++) {
            try {
                    InputStream stream;
                    TextureData data;
                    String temp = filename + "_" + i +".png";
                    stream = getClass().getResourceAsStream(temp);
                    data = TextureIO.newTextureData(stream, false, "png");
                    explo[i] = TextureIO.newTexture(data);

                    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
                    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
                    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
                    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);

                } catch (IOException exc) {
                    System.out.println("LoadSkin: Can not load resource: " + exc.getMessage());
                    System.exit(1);
                }
        }
        // End Load Explo
        // Load RoundSpark
        filename = "data/explosion.png";
        try {
            InputStream stream;
            TextureData data;

            stream = getClass().getResourceAsStream(filename);
            data = TextureIO.newTextureData(stream, false, "png");
            roundsparks = TextureIO.newTexture(data);

            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);

        } catch (IOException exc) {
            System.out.println("LoadSkin: Can not load resource: " + exc.getMessage());
            System.exit(1);
        }
        // End load round
        // Load Debris
        m_textureCount = 9;
        debris = new Texture[m_textureCount];
        filename = "data/Debris";
        //Load resource
        for(int i = 0; i < m_textureCount; i ++) {
            try {
                InputStream stream;
                TextureData data;
                String temp = filename + "_" + i +".png";
                stream = getClass().getResourceAsStream(temp);
                data = TextureIO.newTextureData(stream, false, "png");
                debris[i] = TextureIO.newTexture(data);

                gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
                gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
                gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
                gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);

            } catch (IOException exc) {
                System.out.println("LoadSkin: Can not load resource: " + exc.getMessage());
                System.exit(1);
            }
        }
        // End Load Debris
        // Load Sockwave
        filename = "data/Shockwave.png";
        try {
            InputStream stream;
            TextureData data;

            stream = getClass().getResourceAsStream(filename);
            data = TextureIO.newTextureData(stream, false, "png");
            sockwave = TextureIO.newTexture(data);

            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);

        } catch (IOException exc) {
            System.out.println("LoadSkin: Can not load resource: " + exc.getMessage());
            System.exit(1);
        }
        // End Load SockWave
    }
    
    
    public Texture[] explo1 = null;
    public Texture[] explo = null;
    public Texture[] debris = null;
    public Texture smoke = null;
    public Texture roundsparks = null;
    public Texture sockwave = null;
}
