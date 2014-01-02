/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

/**
 *
 * @author bu0i
 */
public class MatrixUtil {
    public static float M(float[] matrix, int row, int column) {
        return matrix[column * 4 + row];
    }
    
    public static void SetM(float[] matrix, int row, int column, float value) {
        matrix[column * 4 + row] = value;
    }
    
    public static void MatrixIdentity(float[] matrix) {
        if (matrix == null)
            matrix = new float[16];
        for (int i = 0; i < 16; i++)
            matrix[i] = 0;
        SetM(matrix, 0, 0, 1);
        SetM(matrix, 1, 1, 1);
        SetM(matrix, 2, 2, 1);
        SetM(matrix, 3, 3, 1);
    }
    
    // Nhan ma tran 4x1 voi ma tran 4x4
    public static void MatrixMulti4(float[] A, float[] B, float[] KQ) {
        KQ[0] = B[0] * M(A, 0, 0) + B[1] * M(A, 1, 0) + B[2] * M(A, 2, 0) + B[3] * M(A, 3, 0);
        KQ[1] = B[0] * M(A, 0, 1) + B[1] * M(A, 1, 1) + B[2] * M(A, 2, 1) + B[3] * M(A, 3, 1);
        KQ[2] = B[0] * M(A, 0, 2) + B[1] * M(A, 1, 2) + B[2] * M(A, 2, 2) + B[3] * M(A, 3, 2);
        KQ[3] = B[0] * M(A, 0, 3) + B[1] * M(A, 1, 3) + B[2] * M(A, 2, 3) + B[3] * M(A, 3, 3);
    }
    
    //
    // Nhan ma tran 4x1 voi 4x4. Tra ve Vector3
    //
    public static Vector3 MatrixMulti4(float[] A, float[] B) {
        float[] KQ = new float[4];
        KQ[0] = B[0] * M(A, 0, 0) + B[1] * M(A, 1, 0) + B[2] * M(A, 2, 0) + B[3] * M(A, 3, 0);
        KQ[1] = B[0] * M(A, 0, 1) + B[1] * M(A, 1, 1) + B[2] * M(A, 2, 1) + B[3] * M(A, 3, 1);
        KQ[2] = B[0] * M(A, 0, 2) + B[1] * M(A, 1, 2) + B[2] * M(A, 2, 2) + B[3] * M(A, 3, 2);
        KQ[3] = B[0] * M(A, 0, 3) + B[1] * M(A, 1, 3) + B[2] * M(A, 2, 3) + B[3] * M(A, 3, 3);
        
        return new Vector3(KQ);
    }
    
    // Nhan ma tran 4x4 voi ma tran 4x4
    public static void MatrixMulti16(float[] A, float[] B, float[] KQ) {
        for (int i = 0; i < 4; i++) {
            SetM(KQ, i, 0, M(A, i, 0) * M(B, 0, 0) + M(A, i, 1) * M(B, 1, 0) + M(A, i, 2) * M(B, 2, 0) + M(A, i, 3) * M(B, 3, 0));
            SetM(KQ, i, 1, M(A, i, 0) * M(B, 0, 1) + M(A, i, 1) * M(B, 1, 1) + M(A, i, 2) * M(B, 2, 1) + M(A, i, 3) * M(B, 3, 1));
            SetM(KQ, i, 2, M(A, i, 0) * M(B, 0, 2) + M(A, i, 1) * M(B, 1, 2) + M(A, i, 2) * M(B, 2, 2) + M(A, i, 3) * M(B, 3, 2));
            SetM(KQ, i, 3, M(A, i, 0) * M(B, 0, 3) + M(A, i, 1) * M(B, 1, 3) + M(A, i, 2) * M(B, 2, 3) + M(A, i, 3) * M(B, 3, 3));
        }
    }
    
    public static void MatrixTranslate(float[] A, float x, float y, float z) {
        float[] TranslateMatrix = new float[16];
        float[] result = new float[16];
        MatrixIdentity(TranslateMatrix);
        TranslateMatrix[3] = x;
        TranslateMatrix[7] = y;
        TranslateMatrix[11] = z;
        
        MatrixMulti16(TranslateMatrix, A, result);
        System.arraycopy(result, 0, A, 0, 16);
        
    }
    
    public static void MatrixScale(float[] A, float x) {
        float[] ScaleMatrix = new float[16];
        float[] result = new float[16];
        
        MatrixIdentity(ScaleMatrix);
        ScaleMatrix[0] = x;
        ScaleMatrix[5] = x;
        ScaleMatrix[10] = x;
        
        MatrixMulti16(ScaleMatrix, A, result);
        System.arraycopy(result, 0, A, 0, 16);
    } 
    
    //
    // Truyen Goc Deg vao
    public static void MatrixRoate(float[] A, float goc, float x, float y, float z) {
        float[] RotateMatrix = new float[16];
        float[] result = new float[16];
        
        MatrixIdentity(RotateMatrix);
        
        if (x == 0 && y == 0 && z == 0)
            return;
        float s = (float)Math.sin(Math.toRadians(goc));
        float c = (float)Math.cos(Math.toRadians(goc));
        
        if (x == 0 && y == 0)
        {
            SetM(RotateMatrix, 0, 0, c);
            SetM(RotateMatrix,0,1, s);
            SetM(RotateMatrix,1,0, -s);
            SetM(RotateMatrix,1,1, c);
        }else if (x == 0 && z == 0)
        {
            SetM(RotateMatrix,0,0, c);
            SetM(RotateMatrix,0,2, -s);
            SetM(RotateMatrix,2,0, s);
            SetM(RotateMatrix,2,2, c);
        }else if (y == 0 && z == 0)
        {
            SetM(RotateMatrix,1,1, c);
            SetM(RotateMatrix,1,2, s);
            SetM(RotateMatrix,2,1, -s);
            SetM(RotateMatrix,2,2, c);
        }else
        {       
            // Chuyen vector(x,y,z) thanh vector don vi
            float d = (float)Math.sqrt(x*x + y*y + z*z);

            // Qua nho -> khong xoay ma tran
            if (d <= 1.0e-4)
                return;

            x = x/d;
            y = y/d;
            z = z/d;

            float xx = x * x;
            float yy = y * y;
            float zz = z * z;
            float xy = x * y;
            float yz = y * z;
            float zx = z * x;
            float xs = x * s;
            float ys = y * s;
            float zs = z * s;
            float one_c = (float)1.0 - c;

            SetM(RotateMatrix,0,0, (one_c * xx) + c);
            SetM(RotateMatrix,0,1, (one_c * xy) - zs);
            SetM(RotateMatrix,0,2, (one_c * zx) + ys);

            SetM(RotateMatrix,1,0, (one_c * xy) + zs);
            SetM(RotateMatrix,1,1, (one_c * yy) + c);
            SetM(RotateMatrix,1,2, (one_c * yz) - xs);

            SetM(RotateMatrix,2,0, (one_c * zx) - ys);
            SetM(RotateMatrix,2,1, (one_c * yz) + xs);
            SetM(RotateMatrix,2,2, (one_c * zz) + c);
        }
        MatrixMulti16(RotateMatrix, A, result);
        System.arraycopy(result, 0, A, 0, 16);
    }
}
