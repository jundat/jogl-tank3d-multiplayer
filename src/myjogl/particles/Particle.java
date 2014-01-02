/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.particles;

import myjogl.utils.Vector3;



/**
 *
 * @author TIEUNUN
 */
public class Particle {
    public float life;             // Time to live of the particle
    public float seed;             // seed with life like velocity with m_Position
    public Vector3 m_Position;     //The current location of the particle
    public Vector3 m_velocity;     //The current velocity of the particle
    public Vector3 m_Gravity;      //The current gravity of the particle
    
    public float m_size;           //The size of the particle 
    public float m_sizeDelta;      // m_sizeDelta with m_size like velocity with m_Position
    
    public GLColor m_Color;        // The current of partile
    public GLColor m_ColorDelta;
    
}