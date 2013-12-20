/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GLAutoDrawable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javax.media.opengl.GL;

/**
 *
 * @author Jundat
 */
public class Global {

    public static String highscorefile = "highscore.data";
    public static int level = 1; //start at level 1
    public static int score = 0; //start at score 0
    public static boolean isFullScreen = false;
    public static int FPS = 60;
    public static int wndWidth = 1024; //1030 - 6
    public static int wndHeight = 640; //676 - 36
    public static GLAutoDrawable drawable = null;
    
    public static Random random = new Random(System.currentTimeMillis());

    public static GL getGL() {
        return drawable.getGL();
    }
    
    public static void Print(HashMap hm) {
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println("[" + pairs.getKey().toString() + ", " + pairs.getValue().toString() + "]");
        }
    }

    public static void drawCube(Texture tt, float x, float y, float z,
            float sx, float sy, float sz) {
        GL gl = Global.drawable.getGL();
        gl.glPushMatrix();
        
        gl.glTranslatef(x, y, z);
        tt.enable();
        tt.bind();
        
        gl.glBegin(GL.GL_QUADS);        // Draw The Cube Using quads
        {
            gl.glNormal3f(0, 1, 0);
            //glColor3f(0.0f,1.0f,0.0f);    // Color Blue
            gl.glTexCoord2f(1, 1);
            gl.glVertex3f(sx, sy, 0);    // Top Right Of The Quad (Top)
            gl.glTexCoord2f(0.0f, 1);
            gl.glVertex3f(0, sy, 0);    // Top Left Of The Quad (Top)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(0, sy, sz);    // Bottom Left Of The Quad (Top)
            gl.glTexCoord2f(1, 0.0f);
            gl.glVertex3f(sx, sy, sz);    // Bottom Right Of The Quad (Top)
            
//            gl.glNormal3f(0, -1, 0);
//            //glColor3f(1.0f,0.5f,0.0f);    // Color Orange
//            gl.glTexCoord2f(1, 1);
//            gl.glVertex3f(sx, 0, sz);    // Top Right Of The Quad (Bottom)
//            gl.glTexCoord2f(0.0f, 1);
//            gl.glVertex3f(0, 0, sz);    // Top Left Of The Quad (Bottom)
//            gl.glTexCoord2f(0.0f, 0.0f);
//            gl.glVertex3f(0, 0, 0);    // Bottom Left Of The Quad (Bottom)
//            gl.glTexCoord2f(1, 0.0f);
//            gl.glVertex3f(sx, 0, 0);    // Bottom Right Of The Quad (Bottom)

            gl.glNormal3f(0, 0, 1);
            //glColor3f(1.0f,0.0f,0.0f);    // Color Red    
            gl.glTexCoord2f(1, 1);
            gl.glVertex3f(sx, sy, sz);    // Top Right Of The Quad (Front)
            gl.glTexCoord2f(0.0f, 1);
            gl.glVertex3f(0, sy, sz);    // Top Left Of The Quad (Front)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(0, 0, sz);    // Bottom Left Of The Quad (Front)
            gl.glTexCoord2f(1, 0.0f);
            gl.glVertex3f(sx, 0, sz);    // Bottom Right Of The Quad (Front)

            gl.glNormal3f(0, 0, -1);
            //glColor3f(1.0f,1.0f,0.0f);    // Color Yellow
            gl.glTexCoord2f(1, 1);
            gl.glVertex3f(sx, 0, 0);    // Top Right Of The Quad (Back)
            gl.glTexCoord2f(0.0f, 1);
            gl.glVertex3f(0, 0, 0);    // Top Left Of The Quad (Back)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(0, sy, 0);    // Bottom Left Of The Quad (Back)
            gl.glTexCoord2f(1, 0.0f);
            gl.glVertex3f(sx, sy, 0);    // Bottom Right Of The Quad (Back)

            gl.glNormal3f(-1, 0, 0);
            //glColor3f(0.0f,0.0f,1.0f);    // Color Blue
            gl.glTexCoord2f(1, 1);
            gl.glVertex3f(0, sy, sz);    // Top Right Of The Quad (Left)
            gl.glTexCoord2f(0.0f, 1);
            gl.glVertex3f(0, sy, 0);    // Top Left Of The Quad (Left)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(0, 0, 0);    // Bottom Left Of The Quad (Left)
            gl.glTexCoord2f(1, 0.0f);
            gl.glVertex3f(0, 0, sz);    // Bottom Right Of The Quad (Left)

            gl.glNormal3f(1, 0, 0);
            //glColor3f(1.0f,0.0f,1.0f);    // Color Violet
            gl.glTexCoord2f(1, 1);
            gl.glVertex3f(sx, sy, 0);    // Top Right Of The Quad (Right)
            gl.glTexCoord2f(0.0f, 1);
            gl.glVertex3f(sx, sy, sz);    // Top Left Of The Quad (Right)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(sx, 0, sz);    // Bottom Left Of The Quad (Right)
            gl.glTexCoord2f(1, 0.0f);
            gl.glVertex3f(sx, 0, 0);    // Bottom Right Of The Quad (Right)
        }
        gl.glEnd();            // End Drawing The Cube
        tt.disable();
        
        gl.glPopMatrix();
        gl.glColor4f(1, 1, 1, 1);
    }

    public static int getUpper(float n) {
        int down = (int) n;

        if (n == down) { // 0.000  tron so
            return down;
        } else {
            return down + 1;
        }
    }
}
