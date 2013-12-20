/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * **************************************************************************
 * Author : tieunun - Nguyen Ngoc Thanh Huy Written for OpenGL Game Programming
****************************************************************************
 */
package myjogl.particles;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import myjogl.utils.Camera;
import myjogl.utils.ResourceManager;
import myjogl.utils.Vector3;

/**
 *
 * @author TIEUNUN
 */
public class RoundSparks extends ParticleEngine {

    private final float SEED = 20.0f;
    private final Vector3 VECLOCITY = new Vector3(0.2f, 0.2f, 0.2f);
    private final Vector3 VECLOCITY_VARIATION = new Vector3(0.02f, 0.02f, 0.02f);
    private final Vector3 GRAVITY = new Vector3(0.1f, 0.1f, 0.1f);
    private final float PARTICLE_SIZE = 0.3f;
    private final float PARTICLE_SIZE_DELTA = 0.1f;
    private final Vector3 COLOR = new Vector3(1.0f, 0.5f, 0.2f);
    private final int MAXPARTICLES = 250;
    Texture m_texture;
    int countTime;

    public RoundSparks(Vector3 _origin, float elapsedTime, float scale) {
        m_origin = _origin;
        m_scale = scale;
        m_elapsedTime = elapsedTime;
        this.m_maxParticles = MAXPARTICLES;
        this.Init();
        this.Emit(200);
    }

    public void LoadingTexture() {
        m_texture = ResourceManager.getInst().getTexture("data/particle/explosion.png");
    }
    static Random random = new Random();

    @Override
    public void InitParticle(int index) {
        float rand;

        m_ParticleList[index] = new Particle();
        rand = Math.abs((random.nextFloat() * 2) - 1);
        m_ParticleList[index].life = 30.5f + rand * SEED;
        m_ParticleList[index].seed = 0.05f;

        m_ParticleList[index].m_Position = new Vector3();

        rand = ((random.nextFloat() * 2) - 1);
        m_ParticleList[index].m_Position.x = m_origin.x + rand * 12;
        rand = ((random.nextFloat() * 2) - 1);
        m_ParticleList[index].m_Position.y = m_origin.y + rand * 12;
        rand = ((random.nextFloat() * 2) - 1);
        m_ParticleList[index].m_Position.z = m_origin.z + rand * 12;
        //m_ParticleList[index].m_Position.z = 1 - m_ParticleList[index].m_Position.x - m_ParticleList[index].m_Position.y ;
        //double temp = m_ParticleList[index].m_Position.x * m_ParticleList[index].m_Position.y;
        //m_ParticleList[index].m_Position.z = -(float)Math.sqrt(temp);
        m_ParticleList[index].m_velocity = new Vector3();

        rand = Math.abs((random.nextFloat() * 2) - 1);
        if (m_ParticleList[index].m_Position.x - m_origin.x >= 0) {
            m_ParticleList[index].m_velocity.x = VECLOCITY.x + VECLOCITY_VARIATION.x * rand;
        } else {
            m_ParticleList[index].m_velocity.x = -(VECLOCITY.x + VECLOCITY_VARIATION.x * rand);
        }
        rand = Math.abs((random.nextFloat() * 2) - 1);
        if (m_ParticleList[index].m_Position.y - m_origin.y >= 0) {
            m_ParticleList[index].m_velocity.y = VECLOCITY.y + VECLOCITY_VARIATION.y * rand;
        } else {
            m_ParticleList[index].m_velocity.y = -(VECLOCITY.y + VECLOCITY_VARIATION.y * rand);
        }
        rand = Math.abs((random.nextFloat() * 2) - 1);
        if (m_ParticleList[index].m_Position.z - m_origin.z >= 0) {
            m_ParticleList[index].m_velocity.z = VECLOCITY.z + VECLOCITY_VARIATION.z * rand;
        } else {
            m_ParticleList[index].m_velocity.z = -(VECLOCITY.z + VECLOCITY_VARIATION.z * rand);
        }

        m_ParticleList[index].m_Gravity = new Vector3();

        rand = Math.abs((random.nextFloat() * 2) - 1);

        if (m_ParticleList[index].m_Position.x - m_origin.x >= 0) {
            m_ParticleList[index].m_Gravity.x = GRAVITY.x * rand * Math.abs(m_ParticleList[index].m_Position.x - m_origin.x);
        } else {
            m_ParticleList[index].m_Gravity.x = -(GRAVITY.x * rand * Math.abs(m_ParticleList[index].m_Position.x - m_origin.x));
        }
        rand = Math.abs((random.nextFloat() * 2) - 1);
        if (m_ParticleList[index].m_Position.y - m_origin.y >= 0) {
            m_ParticleList[index].m_Gravity.y = GRAVITY.y * rand * Math.abs(m_ParticleList[index].m_Position.y - m_origin.y);
        } else {
            m_ParticleList[index].m_Gravity.y = -(GRAVITY.y * rand * Math.abs(m_ParticleList[index].m_Position.y - m_origin.y));
        }
        rand = Math.abs((random.nextFloat() * 2) - 1);
        if (m_ParticleList[index].m_Position.z - m_origin.z >= 0) {
            m_ParticleList[index].m_Gravity.z = GRAVITY.z * rand * Math.abs(m_ParticleList[index].m_Position.z - m_origin.z);
        } else {
            m_ParticleList[index].m_Gravity.z = -(GRAVITY.z * rand * Math.abs(m_ParticleList[index].m_Position.z - m_origin.z));
        }
        rand = Math.abs((random.nextFloat() * 2) - 1);
        m_ParticleList[index].m_size = PARTICLE_SIZE + 0.5f * rand;
        //m_ParticleList[index].m_sizeDelta = -(m_ParticleList[index].m_size / m_ParticleList[index].life);
        m_ParticleList[index].m_sizeDelta = PARTICLE_SIZE_DELTA;

        float tempRed, tempBlue, tempGreen, tempAlpha;
        tempRed = COLOR.x;
        rand = Math.abs((random.nextFloat() * 2) - 1);
        tempGreen = COLOR.y + rand * 0.5f;
        tempBlue = COLOR.z;
        tempAlpha = 1.0f;
        m_ParticleList[index].m_Color = new GLColor(tempRed, tempGreen, tempBlue, tempAlpha);

        tempRed = 0.0f;
        tempGreen = -(m_ParticleList[index].m_Color.green / 2.0f) / m_ParticleList[index].life;
        tempBlue = 0.0f;
        tempAlpha = -1.0f / m_ParticleList[index].life;
        m_ParticleList[index].m_ColorDelta = new GLColor(tempRed, tempGreen, tempBlue, tempAlpha);

    }

    //Update list particle
    @Override
    public void Update() {
        //float m_elapsedTime = 0.05f;
        countTime++;
        if (countTime % 150 == 0) //this.Emit(200);
        {
            m_isDie = true;
        }
        for (int i = 0; i < m_numParticles;) {
            //float rand = (float) (Math.random() * Math.abs(m_numParticles - i) ) / 1000;
            float rand = random.nextFloat();
            m_ParticleList[i].m_Position.x += m_elapsedTime * m_ParticleList[i].m_velocity.x; //trai phai
            m_ParticleList[i].m_Position.y += m_elapsedTime * m_ParticleList[i].m_velocity.y; // len xuong
            m_ParticleList[i].m_Position.z += m_elapsedTime * m_ParticleList[i].m_velocity.z; // do sau

            m_ParticleList[i].m_velocity.x += m_elapsedTime * m_ParticleList[i].m_Gravity.x;
            m_ParticleList[i].m_velocity.y += m_elapsedTime * m_ParticleList[i].m_Gravity.y;
            m_ParticleList[i].m_velocity.z += m_elapsedTime * m_ParticleList[i].m_Gravity.z;

            if (m_ParticleList[i].m_Gravity.x >= 0) {
                m_ParticleList[i].m_Gravity.x += 0.5f * m_elapsedTime;
            } else {
                m_ParticleList[i].m_Gravity.x -= 0.5f * m_elapsedTime;
            }
            if (m_ParticleList[i].m_Gravity.y >= 0) {
                m_ParticleList[i].m_Gravity.y += 0.5f * m_elapsedTime;
            } else {
                m_ParticleList[i].m_Gravity.y -= 0.5f * m_elapsedTime;
            }
            if (m_ParticleList[i].m_Gravity.z >= 0) {
                m_ParticleList[i].m_Gravity.z += 0.5f * m_elapsedTime;
            } else {
                m_ParticleList[i].m_Gravity.z -= 0.5f * m_elapsedTime;
            }

            m_ParticleList[i].life -= 13.5 * m_elapsedTime;
            //m_ParticleList[i].m_size += m_elapsedTime * PARTICLE_SIZE_DELTA;

            m_ParticleList[i].m_Color.green += m_ParticleList[i].m_ColorDelta.green * m_elapsedTime;
            m_ParticleList[i].m_Color.alpha += m_ParticleList[i].m_ColorDelta.alpha * m_elapsedTime;
            if (m_ParticleList[i].life <= 0) {
                m_ParticleList[i] = m_ParticleList[--m_numParticles];
            } else {
                i++;
            }
        }
    }

    //Draw list particle
    @Override
    public void Draw(GL gl, Camera camera) {
        gl.glEnable(GL.GL_TEXTURE);
        gl.glDepthMask(false);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
        gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);

        m_texture.enable();
        m_texture.bind();
        for (int i = 0; i < m_numParticles; ++i) {
            gl.glPushMatrix();
            
            Particle pa = m_ParticleList[i];
            
            float Y = camera.GetAngleY(pa.m_Position);
            float X = camera.GetAngleX(pa.m_Position);
            float Z = camera.GetAngleZ(pa.m_Position);

            gl.glTranslatef(m_ParticleList[i].m_Position.x, m_ParticleList[i].m_Position.y, m_ParticleList[i].m_Position.z);
            
            gl.glRotatef(Z, 0, 0, 1);
            gl.glRotatef(X, 1, 0, 0);
            gl.glRotatef(Y, 0, 1, 0);
            
            gl.glScalef(m_scale, m_scale, m_scale);
            gl.glBegin(GL.GL_QUADS);

            float size = pa.m_size / 2;
            pa.m_Color.set(gl);

            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-size, -size, 0);

            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(size, -size, 0);

            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(size, size, 0);

            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-size, size, 0);

            gl.glEnd();
            gl.glPopMatrix();
        }
        m_texture.disable();

        gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
        gl.glDisable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glDepthMask(true);
    }
}
