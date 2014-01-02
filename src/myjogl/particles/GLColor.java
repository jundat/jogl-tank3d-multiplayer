/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.particles;

import javax.media.opengl.GL;

/**
 *
 * @author TIEUNUN
 */

/** A custom color class. */
public class GLColor {
    /** The color component */
    public float red;
    public float green;
    public float blue;
    public float alpha;

    /** A default static color */
    public static final GLColor WHITE = new GLColor(1.0f, 1.0f, 1.0f, 1.0f);
    public static final GLColor BLACK = new GLColor(0.0f, 0.0f, 0.0f, 1.0f);
    public static final GLColor RED = new GLColor(1.0f, 0.0f, 0.0f, 1.0f);
    public static final GLColor GREEN = new GLColor(0.0f, 1.0f, 0.0f, 1.0f);
    public static final GLColor BLUE = new GLColor(0.0f, 0.0f, 1.0f, 1.0f);
    public static final GLColor YELLOW = new GLColor(1.0f, 1.0f, 0.0f, 1.0f);
    public static final GLColor GRAY = new GLColor(0.5f, 0.5f, 0.5f, 1.0f);
    public static final GLColor DARK_GRAY = new GLColor(0.2f, 0.2f, 0.2f, 1.0f);


    public GLColor(float mRed, float mGreen, float mBlue, float mAlpha) {
        red = mRed;
        green = mGreen;
        blue = mBlue;
        alpha = mAlpha;
    }
    public GLColor(){
        red = green = blue = alpha = 1.0f;
    }
    /**
     * Generates a random color in full alpha
     * 
     * @return The generated color
     */
    public static GLColor randomColorFullAlpha() {
        float red = (float) Math.random();
        float green = (float) Math.random();
        float blue = (float) Math.random();
        return new GLColor(red, green, blue, 1.0f);
    }

    /**
     * Generates a random color with a minimum alpha
     * 
     * @param mMinimumAlpha The minimum value for the alpha
     * @return The generated color
     */
    public static GLColor randomColor(float mMinimumAlpha) {
        float red = (float) Math.random();
        float green = (float) Math.random();
        float blue = (float) Math.random();
        float alpha = (float) Math.random() + mMinimumAlpha;
        return new GLColor(red, green, blue, alpha);
    }


    //Sets the current color to be this one.
    public void set(GL gl) {
        gl.glColor4f(red, green, blue, alpha);
    }

    //Resets the current color to white. 
    public void reset(GL gl) {
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    /**
     * Gets a shade of gray
     * 
     * @param shade The 0.0-1.0 value that you want
     * @return The gray GLColor
     */
    public static GLColor getGray(float shade) {
        return new GLColor(shade, shade, shade, 1.0f);
    }
}
