/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jundat
 */
public class Camera {

    private final float MAX_UP = 100f;
    private final float MAX_DOWN = -100.0f;
    public Vector3 mPos;
    public Vector3 mView;
    public Vector3 mUp;

    /**
     * Move camera farther your eyes Mean mPos farther mView
     *
     * @param speed
     */
    public void Move_Character_Farther(float speed) {
        Vector3 vVector = mView.Sub(mPos);	// Get the view vector

        // forward positive camera speed and backward negative camera speed.
        mPos.x = mPos.x + vVector.x * speed;
        mPos.z = mPos.z + vVector.z * speed;
    }

    /**
     * Move camera left and right, all eyes and view
     *
     * @param speed
     */
    public void Move_Left_Right(float speed) {
        Vector3 vVector = mView.Sub(mPos);	// Get the view vector
        Vector3 vOrthoVector = new Vector3();              // Orthogonal vector for the view vector

        vOrthoVector.x = -vVector.z;
        vOrthoVector.z = vVector.x;

        // left positive cameraspeed and right negative -cameraspeed.
        mPos.x = mPos.x + vOrthoVector.x * speed;
        mPos.z = mPos.z + vOrthoVector.z * speed;
        mView.x = mView.x + vOrthoVector.x * speed;
        mView.z = mView.z + vOrthoVector.z * speed;
    }

    /**
     * Move camera Up and Down Mean mPos and mView up and down
     *
     * @param speed
     */
    public void Move_Up_Down(float speed) {
        mPos.y += 5 * speed;
        mView.y += 5 * speed;
    }

    // This function makes it possible to rotate around a given point
    /**
     * Your view is center, rotate your eyes Mean mView is center, move mPos
     *
     * @param speed
     */
    public void Rotate_Position(float speed) {
        Vector3 vVector = mPos.Sub(mView);

        mPos.z = (float) (mView.z + Math.sin((double) speed) * vVector.x + Math.cos((double) speed) * vVector.z);
        mPos.x = (float) (mView.x + Math.cos((double) speed) * vVector.x - Math.sin((double) speed) * vVector.z);
    }

    /**
     * Move you straight ahead or back ahead
     *
     * @param speed
     */
    public void Move_Camera(float speed) {
        Vector3 vVector = mView.Sub(mPos);	// Get the view vector

        // forward positive camera speed and backward negative camera speed.
        mPos.x = mPos.x + vVector.x * speed;
        mPos.z = mPos.z + vVector.z * speed;
        mView.x = mView.x + vVector.x * speed;
        mView.z = mView.z + vVector.z * speed;
    }

    /**
     * Send mouseX and mouseY to function After run this function, please set
     * cursor to (mid_x, mid_y)
     *
     * @param mouseX
     * @param mouseY
     * @param wndWidth
     * @param wndHeight
     */
    public void Mouse_Move(int mouseX, int mouseY, int wndWidth, int wndHeight) {
        int mid_x = wndWidth / 2;
        int mid_y = wndHeight / 2;
        float angle_y;
        float angle_z;

        if ((mouseX == mid_x) && (mouseY == mid_y)) {
            return;
        }

        Robot r;
        try {
            r = new Robot();
            r.mouseMove(mid_x, mid_y);
        } catch (AWTException ex) {
            Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Get the direction from the mouse cursor, set a resonable maneuvering speed
        angle_y = (float) ((mid_x - mouseX)) / 1000;
        angle_z = (float) ((mid_y - mouseY)) / 1000;

        // The higher the value is the faster the camera looks around.
        mView.y += angle_z * 2;

        // limit the rotation around the x-axis
        if (mView.y > MAX_UP) {
            mView.y = MAX_UP;
        }
        if (mView.y < MAX_DOWN) {
            mView.y = MAX_DOWN;
        }

        Rotate_Position(-angle_y);
    }

    /**
     * Your eyes are center, your view rotate Mean mView is center, you move
     * around
     *
     * @param speed
     */
    public void Rotate_View(float speed) {
        Vector3 vVector = mView.Sub(mPos);	// Get the view vector

        mView.z = (float) (mPos.z + Math.sin((double) speed) * vVector.x + Math.cos((double) speed) * vVector.z);
        mView.x = (float) (mPos.x + Math.cos((double) speed) * vVector.x - Math.sin((double) speed) * vVector.z);
    }

    /**
     * Set up the your view and your eyes
     *
     * @param pos_x Your position (your eyes)
     * @param pos_y
     * @param pos_z
     * @param view_x Your view,
     * @param view_y
     * @param view_z
     * @param up_x Your head
     * @param up_y
     * @param up_z
     */
    public void Position_Camera(float pos_x, float pos_y, float pos_z,
            float view_x, float view_y, float view_z,
            float up_x, float up_y, float up_z) {
        mPos = new Vector3(pos_x, pos_y, pos_z); // set position
        mView = new Vector3(view_x, view_y, view_z); // set view
        mUp = new Vector3(up_x, up_y, up_z); // set the up vector
    }
    
    public void SetEyePoint(float x, float y, float z) {
        mPos = new Vector3(x, y, z);
    }
    
    
    public void SetViewPoint(float x, float y, float z) {
        mView = new Vector3(x, y, z);
    }
    
    
    public void SetVectorUp(float x, float y, float z) {
        mUp = new Vector3(x, y, z);
    }
    

    /**
     * Angle rotate in y axis
     * Luôn luôn song song vs màn hình
     * @return 
     */
    public float GetAngleY(Vector3 posi) {
        float dx = posi.x - mPos.x;
        float dz = posi.z - mPos.z;
        float angle = (float) Math.atan(dz / dx);
        angle = 180 * angle / 3.141592654f;
        int angle2 = (int) angle;
        angle2 %= 360;
//        if (dx < 0) {
//            angle2 = (int) (angle - 180);
//        }

        return 90-angle2;
    }
    
    /**
     * Angle rotate in X axis
     * @return 
     */
    public float GetAngleX(Vector3 posi) {
        float dy = posi.y - mPos.y;
        float dz = posi.z - mPos.z;
        float angle = (float) Math.atan(dy / dz);
        angle = 180 * angle / 3.141592654f;
        int angle2 = (int) angle;
        angle2 %= 360;
//        if (dz < 0) {
//            angle2 = (int) (angle - 180);
//        }
        
        //if (posi.x - mPos.x > 0)
        //    angle2 = -angle2;
        return -angle2;
    }
    
    /**
     * Angle rotate in Z axis
     * @return 
     */
    public float GetAngleZ(Vector3 posi) {
        float dy = posi.y - mPos.y;
        float dx = posi.x - mPos.x;
        float angle = (float) Math.atan(dx / dy);
        angle = 180 * angle / 3.141592654f;
        int angle2 = (int) angle;
        angle2 %= 360;
//        if (dy < 0) {
//            angle2 = (int) (angle - 180);
//        }

        return angle2;
    }
};
