/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.particles;

import java.awt.Color;
import java.util.Vector;
import javax.media.opengl.GL;
import myjogl.utils.Camera;
import myjogl.utils.Writer;

/**
 *
 * @author bu0i
 */
public class ParticalManager {

    private static ParticalManager m_instance = null;

    public static ParticalManager getInstance() {
        if (m_instance == null) {
            m_instance = new ParticalManager();
        }
        return m_instance;
    }
    private Vector m_listPartical;

    private ParticalManager() {
        m_listPartical = new Vector();
    }

    public void Clear() {
        m_listPartical.clear();
    }
    
    public void Update() {
        for (int i = 0; i < m_listPartical.size(); i++) {
            ParticleEngine par = (ParticleEngine) m_listPartical.elementAt(i);
            if (par.m_isDie) {
                m_listPartical.removeElement(par);
                i--;
                continue;
            }
            par.Update();
        }
    }

    public void Draw(GL gl, Camera camera) {
        for (int i = 0; i < m_listPartical.size(); i++) {
            gl.glPushMatrix();
                ParticleEngine temp = (ParticleEngine) m_listPartical.elementAt(i);
                temp.Draw(gl, camera);
            gl.glPopMatrix();
        }
    }

    public void Add(ParticleEngine par) {
        if (par == null) {
            return;
        }
        m_listPartical.add(par);
    }
}
