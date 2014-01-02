/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.particles;

import javax.media.opengl.GL;
import myjogl.utils.Camera;
import myjogl.utils.Vector3;

/**
 *
 * @author TIEUNUN
 */
public class ParticleEngine {
    
    public Particle[] m_ParticleList; // Particles list for this emitter //Danh sach cac particles cua lan phong hien tai - kich thuoc List bang m_numParticles
    public int m_maxParticles;        // Maximum of particle number; //so particle lon nhat trong 1 lan phong
    public int m_numParticles;        // current particles number; //so particle phong hien tai
    
    public Vector3 m_origin;          // center of the particle engine
    public float m_elapsedTime;
    public float m_scale;
    public boolean m_isDie = false;
    
    
    public ParticleEngine(){
        
    }
    
    //Allocate memory for the maximum number of particles in the system
    public void Init(){
        m_ParticleList = new Particle[m_maxParticles];
        m_numParticles = 0;
    }
    
    //Khoi tao them _numParticle particles
    
    //Luu y : khi truyen bien _numParticles
    //ham nay se khoi tao them _numParticles parcticles vao List Particles va tang m_numParticle len
    //neu so m_numParticle = m_maxParticles thi k tao them nua
    public void Emit(int _numParticles){
        while((_numParticles >0) && (m_numParticles < m_maxParticles))
        {
            InitParticle(m_numParticles++);
            --_numParticles;
        }
    }
    
    //khoi tao particle thu index
    public void InitParticle(int index){
    }
    
    //Update list particle
    public void Update(){
        
    }
    
    //Draw list particle
    public void Draw(GL gl, Camera Y) {

    }
}
    

