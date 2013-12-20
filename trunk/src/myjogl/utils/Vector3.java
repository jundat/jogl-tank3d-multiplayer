/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

/**
 *
 * @author Jundat
 */
public class Vector3			// expanded 3D vector struct
{
    public float x, y, z;		// 3D vector coordinates
    
    public Vector3() {
        x = y = z = 0;
    }	// constructor
    
    public Vector3 (float new_x, float new_y, float new_z){
        x = new_x; y = new_y; z = new_z;
    }
    
    //tieunun
    public Vector3(float[] p) {
        x = p[0];
        y = p[1];
        z = p[2];
    }
    
    public Vector3(Vector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }
    
    public Vector3 Clone() {
        return new Vector3(this);
    }
    
    public void Copy(Vector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }
    
    // overload + operator so that we easier can add vectors
    public Vector3 Add(Vector3 vVector) {
        return new Vector3(vVector.x+x, vVector.y+y, vVector.z+z);
    }

    // overload - operator that we easier can subtract vectors
    public Vector3 Sub(Vector3 vVector) {
        return new Vector3(x-vVector.x, y-vVector.y, z-vVector.z);
    }

    // overload * operator that we easier can multiply by scalars
    public Vector3 Multi(float number){
        return new Vector3(x*number, y*number, z*number);
    }

    // overload / operator that we easier can divide by a scalar
    public Vector3 Div(float number){
        return new Vector3(x/number, y/number, z/number);
    }

    public float[] ToArray3()
    {
            float[] res;
            res = new float[3];
            res[0] = x;
            res[1] = y;
            res[2] = z;

            return res;
    }

    public float[] ToArray4(float newValue)
    {
            float[] res;
            res = new float[4];
            res[0] = x;
            res[1] = y;
            res[2] = z;
            res[3] = newValue;

            return res;
    }
    
    /*----------------------------------------------------------------------------------------------*/
    //@author bu0i, giang chau
    public float[] ToArrayFloat() {
        float[] temp = new float[4];
        temp[0] = x;
        temp[1] = y;
        temp[2] = z;
        temp[3] = 1;
        return temp;
    }
    /*---------------------------------------------------------------------------------------------*/
}
