/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;


/**
 *
 * @author bu0i
 */
public class MathUtil {
    //
    // Do dai cua vector
    //
    public static double AbsVector3(Vector3 vector) {
        return Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
    }
    
    //
    // Cái này là tích vô h??ng'
    //
    public static double DotVector3(Vector3 a, Vector3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }
    
    //
    // Tính góc gi?a 2 vector
    //
    public static double Angle2Vector(Vector3 a, Vector3 b) {
        double temp = DotVector3(a, b) / (AbsVector3(a) * AbsVector3(b));
        temp = temp > 1 ? 1 : temp;
        return Math.acos(temp);
    }
    
    //
    // 2 vector tr?` nhau
    //
    public static Vector3 SubVector(Vector3 a, Vector3 b) {
        return new Vector3(a.x - b.x, a.y - b.y, a.z - b.z);
    }
    
    //
    // Tich co huong
    //
    public static double ScrossProduct(Vector3 shoot, Vector3 direct) {
        double angle = Angle2Vector(shoot, direct);
        double result = AbsVector3(shoot) * AbsVector3(direct) * Math.sin(angle);
        return result;
    }
}
