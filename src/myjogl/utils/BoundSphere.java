/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author bu0i
 */
public class BoundSphere {
    public Vector3 Position;
    public float R;
    
    public BoundSphere() {
        
    }
    
    public BoundSphere(Vector3 pos, float r) {
        Position = pos;
        R = r;
    }
    
    public boolean IsCollideWith(BoundSphere other) {
        float deltaX = other.Position.x - Position.x;
        float deltaY = other.Position.y - Position.y;
        float deltaZ = other.Position.z - Position.z;
        if (Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) < (other.R + R)) {
            return true;
        }
        return false;
    }
    
    public void DrawSphere(GL gl) {
        GLU glu = new GLU();
        gl.glPushMatrix();
        gl.glTranslatef(Position.x, Position.y, Position.z);
        glu.gluSphere(glu.gluNewQuadric(), R, 32, 80);
        gl.glPopMatrix();
    }
    
}
