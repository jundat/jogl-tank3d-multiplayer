/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testtool;

import myjogl.utils.MathUtil;
import myjogl.utils.Vector3;

/**
 *
 * @author bu0i
 */
public class CameraFo {
    public double x, y, z, r; // V? trí ??t eye
    public double lookAtX, lookAtY, lookAtZ; // ?i?m nhìn
    public double upX, upY, upZ;
    public double alpha, beta;
    
    public CameraFo(double _lookAtX, double _lookAtY, double _lookAtZ, double _alpha, double _beta, double _R, double _upX,
            double _upY, double _upZ) {
        lookAtX = _lookAtX;
        lookAtY = _lookAtY;
        lookAtZ = _lookAtZ;
        r = _R;
        alpha = _alpha;
        beta = _beta;
        upX = _upX;
        upY = _upY;
        upZ = _upZ;
    }
    
    public void Update() {
        if (alpha <= 0)
            alpha = 0.000001f;
        else if (alpha >= Math.PI)
            alpha = Math.PI - 0.000001f;
        z = r * Math.sin(alpha) * Math.sin(beta) + lookAtZ;
        y = r * Math.cos(alpha) + lookAtY;
        x = r * Math.sin(alpha) * Math.cos(beta) + lookAtX;
    }
    
    public void moveAhead() {
        float TILE = 0.1f;
        Vector3 direct = MathUtil.SubVector(new Vector3((float)lookAtX, (float)lookAtY, (float)lookAtZ),
                new Vector3((float)x, (float)y, (float)z));
        double delta = MathUtil.AbsVector3(direct);
        Vector3 temp = new Vector3((float)(direct.x / delta), (float)(direct.y / delta), (float)(direct.z / delta));
        x += temp.x * TILE;
        y += temp.y * TILE;
        z += temp.z * TILE;
        lookAtX += temp.x * TILE;
        lookAtY += temp.y * TILE;
        lookAtZ += temp.z * TILE;
    }
    
    public void moveBack() {
        float TILE = 0.1f;
        Vector3 direct = MathUtil.SubVector(new Vector3((float)lookAtX, (float)lookAtY, (float)lookAtZ),
                new Vector3((float)x, (float)y, (float)z));
        double delta = MathUtil.AbsVector3(direct);
        Vector3 temp = new Vector3((float)(direct.x / delta), (float)(direct.y / delta), (float)(direct.z / delta));
        x -= temp.x * TILE;
        y -= temp.y * TILE;
        z -= temp.z * TILE;
        lookAtX -= temp.x * TILE;
        lookAtY -= temp.y * TILE;
        lookAtZ -= temp.z * TILE;
    }
    
    public void moveLeft() {
        float TILE = 0.1f;
        Vector3 direct = MathUtil.SubVector(new Vector3((float)lookAtX, (float)lookAtY, (float)lookAtZ),
                new Vector3((float)x, (float)y, (float)z));
        double delta = MathUtil.AbsVector3(direct);
        Vector3 temp = new Vector3((float)(direct.x / delta), (float)(direct.y / delta), (float)(direct.z / delta));
        Vector3 temp2 = new Vector3(-temp.y, temp.x, 0);
        x += temp2.x * TILE;
        y += temp2.y * TILE;
        //z -= temp2.z * TILE;
        lookAtX += temp2.x * TILE;
        lookAtY += temp2.y * TILE;
        //lookAtZ -= temp2.z * TILE;
    }
    
    public void moveRight() {
        float TILE = 0.1f;
        Vector3 direct = MathUtil.SubVector(new Vector3((float)lookAtX, (float)lookAtY, (float)lookAtZ),
                new Vector3((float)x, (float)y, (float)z));
        double delta = MathUtil.AbsVector3(direct);
        Vector3 temp = new Vector3((float)(direct.x / delta), (float)(direct.y / delta), (float)(direct.z / delta));
        Vector3 temp2 = new Vector3(-temp.y, temp.x, 0);
        x -= temp2.x * TILE;
        y -= temp2.y * TILE;
        //z -= temp2.z * TILE;
        lookAtX -= temp2.x * TILE;
        lookAtY -= temp2.y * TILE;
        //lookAtZ -= temp2.z * TILE;
    }
}

