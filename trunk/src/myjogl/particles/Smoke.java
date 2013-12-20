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
import myjogl.utils.ResourceManagerTest;
import myjogl.utils.Vector3;

/**
 *
 * @author TIEUNUN
 */
public class Smoke extends ParticleEngine {

    private final float SEED = 0.0f;
    private final Vector3 VECLOCITY = new Vector3(0.0f, 1.0f, 0.0f);
    private final Vector3 VECLOCITY_VARIATION = new Vector3(0.2f, 0.1f, 0.05f);
    private final Vector3 GRAVITY = new Vector3(0.1f, 0.001f, 0.05f);
    private final float PARTICLE_SIZE = 6.0f;
    private final float PARTICLE_SIZE_DELTA = 1.1f;
    private final Vector3 COLOR = new Vector3(0.05f, 0.05f, 0.05f);
    private final int MAXPARTICLES = 300;
    Texture m_texture;
    int count = 0;

    public Smoke(Vector3 _origin) {
        m_origin = _origin;
        this.m_maxParticles = MAXPARTICLES;
        this.Init();
        this.Emit(30);
    }

    public void LoadingTexture() {
        m_texture = ResourceManagerTest.getInstance().smoke;
        //Load resource
//        try {
//            InputStream stream;
//            TextureData data;
//
//            stream = getClass().getResourceAsStream(filename);
//            data = TextureIO.newTextureData(stream, false, "png");
//            m_texture = TextureIO.newTexture(data);
//
//            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
//            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
//            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
//            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
//
//        } catch (IOException exc) {
//            System.out.println("LoadSkin: Can not load resource: " + exc.getMessage());
//            System.exit(1);
//        }
    }
    static Random random = new Random();

    @Override
    public void InitParticle(int index) {
        float rand;

        m_ParticleList[index] = new Particle();
        rand = Math.abs((random.nextFloat() * 2) - 1);
        m_ParticleList[index].life = 40.5f + rand / 2.0f;
        m_ParticleList[index].seed = 0.05f;

        m_ParticleList[index].m_Position = new Vector3();

        rand = ((random.nextFloat() * 2) - 1);
        m_ParticleList[index].m_Position.x = m_origin.x + rand / 100;
        rand = Math.abs((random.nextFloat() * 2) - 1);
        m_ParticleList[index].m_Position.y = m_origin.y + rand / 100;
        rand = ((random.nextFloat() * 2) - 1);
        m_ParticleList[index].m_Position.z = m_origin.z + rand;

        m_ParticleList[index].m_velocity = new Vector3();

        rand = Math.abs((random.nextFloat() * 2) - 1);
        if (m_ParticleList[index].m_Position.x >= 0) {
            m_ParticleList[index].m_velocity.x = VECLOCITY.x + VECLOCITY_VARIATION.x * rand;
        } else {
            m_ParticleList[index].m_velocity.x = -(VECLOCITY.x + VECLOCITY_VARIATION.x * rand);
        }
        rand = Math.abs((random.nextFloat() * 2) - 1);
        m_ParticleList[index].m_velocity.y = VECLOCITY.y + VECLOCITY_VARIATION.y * rand;
        rand = Math.abs((random.nextFloat() * 2) - 1);
        if (m_ParticleList[index].m_Position.z >= 0) {
            m_ParticleList[index].m_velocity.z = VECLOCITY.z + VECLOCITY_VARIATION.z * rand;
        } else {
            m_ParticleList[index].m_velocity.z = -(VECLOCITY.z + VECLOCITY_VARIATION.z * rand);
        }

        m_ParticleList[index].m_Gravity = new Vector3();

        rand = Math.abs((random.nextFloat() * 2) - 1);
        if (m_ParticleList[index].m_Position.x >= 0) {
            m_ParticleList[index].m_Gravity.x = GRAVITY.x * rand;
        } else {
            m_ParticleList[index].m_Gravity.x = -GRAVITY.x * rand;
        }
        rand = Math.abs((random.nextFloat() * 2) - 1);
        m_ParticleList[index].m_Gravity.y = GRAVITY.y * rand;
        rand = Math.abs((random.nextFloat() * 2) - 1);
        if (m_ParticleList[index].m_Position.z >= 0) {
            m_ParticleList[index].m_Gravity.z = GRAVITY.z * rand;
        } else {
            m_ParticleList[index].m_Gravity.z = -GRAVITY.z * rand;
        }
        rand = Math.abs((random.nextFloat() * 2) - 1);
        m_ParticleList[index].m_size = PARTICLE_SIZE + 0.5f * rand;
        m_ParticleList[index].m_sizeDelta = -(m_ParticleList[index].m_size / m_ParticleList[index].life);

        /*
         float tempRed,tempBlue,tempGreen,tempAlpha;
        
         tempRed = COLOR.x;
         rand = Math.abs((random.nextFloat() * 2) - 1);
         tempGreen = COLOR.y + rand * 0.5f;
         tempBlue = COLOR.z;
         tempAlpha = 1.0f;
         m_ParticleList[index].m_Color = new GLColor(tempRed,tempBlue,tempGreen,tempAlpha);
        
         tempRed = 0.0f;
         tempGreen = -(m_ParticleList[index].m_Color.green/2.0f)/m_ParticleList[index].life;
         tempBlue = 0.0f;
         tempAlpha = -1.0f/m_ParticleList[index].life;
         m_ParticleList[index].m_ColorDelta  = new GLColor(tempRed,tempBlue,tempGreen,tempAlpha); */
        m_ParticleList[index].m_Color = new GLColor(COLOR.x, COLOR.y, COLOR.z, 1.0f);
    }

    //Update list particle
    @Override
    public void Update() {
        float elapsedTime = 0.05f;
        count++;
        if (count % 50 == 0) {
            this.Emit(30);
        }
        for (int i = 0; i < m_numParticles;) {
            float rand = random.nextFloat();
            m_ParticleList[i].m_Position.x += elapsedTime * m_ParticleList[i].m_velocity.x; //trai phai
            m_ParticleList[i].m_Position.y += elapsedTime * m_ParticleList[i].m_velocity.y; // len xuong
            m_ParticleList[i].m_Position.z += elapsedTime * m_ParticleList[i].m_velocity.z; // do sau

            m_ParticleList[i].m_velocity.x += elapsedTime * m_ParticleList[i].m_Gravity.x;
            m_ParticleList[i].m_velocity.y += elapsedTime * m_ParticleList[i].m_Gravity.y;
            m_ParticleList[i].m_velocity.z += elapsedTime * m_ParticleList[i].m_Gravity.z;

            m_ParticleList[i].life -= 2 * elapsedTime;
            m_ParticleList[i].m_size += elapsedTime * PARTICLE_SIZE_DELTA;

            //m_ParticleList[i].m_Color.green += m_ParticleList[i].m_ColorDelta.green * elapsedTime;
            //m_ParticleList[i].m_Color.alpha += m_ParticleList[i].m_ColorDelta.alpha * elapsedTime;
            if (m_ParticleList[i].life <= 0) {
                m_ParticleList[i] = m_ParticleList[--m_numParticles];
            } else {
                i++;
            }
        }

    }

    //Draw list particle
//    @Override
    public void Draw(GL gl, float Y) {
        gl.glEnable(GL.GL_TEXTURE);
        gl.glDepthMask(false);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);

        m_texture.enable();
        m_texture.bind();

        gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);

        for (int i = 0; i < m_numParticles; ++i) {

            gl.glPushMatrix();

            gl.glTranslatef(m_ParticleList[i].m_Position.x, m_ParticleList[i].m_Position.y, m_ParticleList[i].m_Position.z);
            gl.glRotatef(Y, 0, 1, 0);

            gl.glBegin(GL.GL_QUADS);

            float size = m_ParticleList[i].m_size / 2;
            m_ParticleList[i].m_Color.set(gl);

            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(0, 0 - size, 0);

            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(0 + size, 0 - size, 0);

            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(0 + size, 0, 0);

            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(0, 0, 0);

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
