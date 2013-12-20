package myjogl.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.media.opengl.GLAutoDrawable;

/**
 * Image loading class that converts BufferedImages into a data structure that
 * can be easily passed to OpenGL.
 *
 * @author Pepijn Van Eeckhoudt Downloaded from:
 * http://www.felixgers.de/teaching/jogl/
 */
// Uses the class GLModel from JautOGL to load and display obj files.
public class ModelLoaderOBJ {

    public static GLModel LoadModel(String objPath, String mtlPath, String skinPath, GLAutoDrawable drawable) {
        GLModel model = null;

        try {
            InputStream r_path1 = ModelLoaderOBJ.class.getResourceAsStream(objPath);
            
            InputStreamReader isr = new InputStreamReader(r_path1);
            
            BufferedReader b_read1 = new BufferedReader(isr);

            model = new GLModel(b_read1, true, mtlPath, skinPath, drawable);
            
            r_path1.close();
            b_read1.close();

        } catch (Exception e) {
            System.out.println("BuOI:ModelLoaderOBJ.LoadModel(): (line 62) LOADING ERROR" + e.getMessage());
        }

        System.out.println("ModelLoaderOBJ init() done"); // ddd
        return model;
    }
}
