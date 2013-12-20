package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import myjogl.Global;

/**
 *
 * @author Jundat
 */
public class GLModel {

    /**
     * ArrayList of all vertexes
     */
    private ArrayList vertexsets;
    /**
     * ArrayList of all vertexes normal(normal vector for each vertexes)
     */
    private ArrayList vertexsetsnorms;
    /**
     * ArrayList of all vertexes texture coordinate
     */
    private ArrayList vertexsetstexs;
    private ArrayList faces;
    private ArrayList facestexs;
    private ArrayList facesnorms;
    private ArrayList mattimings;
    private MtlLoader materials;
    /**
     * The display list, used in funtion glCallList(...)
     */
    private int objectlist;
    private int numpolys;
    public float toppoint;
    public float bottompoint;
    public float leftpoint;
    public float rightpoint;
    public float farpoint;
    public float nearpoint;
    private String mtl_path;
    private String skin_path;
    private Texture skin;
    
    public Vector3 MidPoint;
    public BoundSphere BigBound;

    //THIS CLASS LOADS THE MODELS
    public GLModel(BufferedReader ref, boolean isCenterit, String path, String skinPath, GLAutoDrawable drawable) {

        mtl_path = path;
        skin_path = skinPath;

        vertexsets = new ArrayList();

        vertexsetsnorms = new ArrayList();

        vertexsetstexs = new ArrayList();

        faces = new ArrayList();
        facestexs = new ArrayList();
        facesnorms = new ArrayList();
        mattimings = new ArrayList();
        numpolys = 0;

        //bounding box
        toppoint = 0.0F;
        bottompoint = 0.0F;

        leftpoint = 0.0F;
        rightpoint = 0.0F;

        farpoint = 0.0F;
        nearpoint = 0.0F;

        this.skin = ResourceManager.getInst().getTexture(skin_path, false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);

        this.loadobject(ref);

        if (isCenterit) {
            this.centerit();
        }

        this.opengldrawtolist(drawable);

        numpolys = faces.size();
        cleanup();
    }

    private void cleanup() {
        vertexsets.clear();
        vertexsetsnorms.clear();
        vertexsetstexs.clear();
        faces.clear();
        facestexs.clear();
        facesnorms.clear();
    }

    private void loadobject(BufferedReader br) {
        int linecounter = 0;
        int facecounter = 0;
        try {
            boolean firstpass = true;
            String newline;
            while ((newline = br.readLine()) != null) {
                linecounter++;
                if (newline.length() > 0) {
                    newline = newline.trim();

                    //LOADS VERTEX COORDINATES
                    if (newline.startsWith("v ")) {
                        float coords[] = new float[3]; //jundat new float[4]; -> new float[3];
                        newline = newline.substring(2, newline.length());
                        StringTokenizer st = new StringTokenizer(newline, " ");
                        for (int i = 0; st.hasMoreTokens(); i++) {
                            coords[i] = Float.parseFloat(st.nextToken());
                        }

                        if (firstpass) {
                            rightpoint = coords[0]; //x
                            leftpoint = coords[0];

                            toppoint = coords[1]; //y
                            bottompoint = coords[1];

                            nearpoint = coords[2]; //z
                            farpoint = coords[2];

                            firstpass = false;
                        }

                        if (coords[0] > rightpoint) {
                            rightpoint = coords[0];
                        }
                        if (coords[0] < leftpoint) {
                            leftpoint = coords[0];
                        }
                        if (coords[1] > toppoint) {
                            toppoint = coords[1];
                        }
                        if (coords[1] < bottompoint) {
                            bottompoint = coords[1];
                        }
                        if (coords[2] > nearpoint) {
                            nearpoint = coords[2];
                        }
                        if (coords[2] < farpoint) {
                            farpoint = coords[2];
                        }
                        vertexsets.add(coords);

                    } else //LOADS VERTEX TEXTURE COORDINATES
                    if (newline.startsWith("vt")) {
                        float coords[] = new float[3]; //jundat new float[4]; -> new float[2];
                        newline = newline.substring(3, newline.length());
                        StringTokenizer st = new StringTokenizer(newline, " ");
                        for (int i = 0; st.hasMoreTokens(); i++) {
                            coords[i] = Float.parseFloat(st.nextToken());
                        }

                        vertexsetstexs.add(coords);
                    } else //LOADS VERTEX NORMALS COORDINATES
                    if (newline.startsWith("vn")) {
                        float coords[] = new float[3]; //jundat  new float[4]; ->  new float[3];
                        newline = newline.substring(3, newline.length());
                        StringTokenizer st = new StringTokenizer(newline, " ");
                        for (int i = 0; st.hasMoreTokens(); i++) {
                            coords[i] = Float.parseFloat(st.nextToken());
                        }

                        vertexsetsnorms.add(coords);
                    } else //LOADS FACES COORDINATES
                    if (newline.startsWith("f ")) {
                        facecounter++;
                        newline = newline.substring(2, newline.length());
                        StringTokenizer st = new StringTokenizer(newline, " ");
                        int count = st.countTokens();
                        int v[] = new int[count];
                        int vt[] = new int[count];
                        int vn[] = new int[count];
                        for (int i = 0; i < count; i++) {
                            String sb = st.nextToken();

                            StringTokenizer st2 = new StringTokenizer(sb, "/");
                            int num = st2.countTokens();

                            v[i] = Integer.parseInt(st2.nextToken()); //: 10

                            if (num > 1) {
                                vt[i] = Integer.parseInt(st2.nextToken()); //: 9
                            } else {
                                vt[i] = 0;
                            }

                            //TH ?úng là ?ây
                            if (num > 2) { //right
                                vn[i] = Integer.parseInt(st2.nextToken());
                            } else {
                                vn[i] = 0;
                            }
                        }

                        faces.add(v);
                        facestexs.add(vt);
                        facesnorms.add(vn);
                    } else //LOADS MATERIALS
                    if (newline.charAt(0) == 'm' && newline.charAt(1) == 't' && newline.charAt(2) == 'l' && newline.charAt(3) == 'l' && newline.charAt(4) == 'i' && newline.charAt(5) == 'b') {
                        String[] coordstext = new String[3];
                        coordstext = newline.split("\\s+");
                        if (mtl_path != null) {
                            loadmaterials();
                        }
                    } else //USES MATELIALS
                    if (newline.charAt(0) == 'u' && newline.charAt(1) == 's' && newline.charAt(2) == 'e' && newline.charAt(3) == 'm' && newline.charAt(4) == 't' && newline.charAt(5) == 'l') {
                        String[] coords = new String[2];
                        String[] coordstext = new String[3];
                        coordstext = newline.split("\\s+");
                        coords[0] = coordstext[1];
                        coords[1] = facecounter + "";
                        mattimings.add(coords);
                        //System.out.println(coords[0] + ", " + coords[1]);
                    }
                }
            }
            // THong tin them
            MidPoint = new Vector3((this.rightpoint + this.leftpoint)/2,
                (this.toppoint + this.bottompoint)/2,
                (this.nearpoint + this.farpoint)/2);
            float a = getXWidth() / 2;
            float b = getZDepth() / 2;
            float c = getYHeight() / 2;
            float r = (float)Math.sqrt(a*a+b*b+c*c);
            BigBound = new BoundSphere(MidPoint, r);
        } catch (IOException e) {

            System.out.println("GLModel.loadobject(): (line 198) Failed to read file: " + br.toString());
        } catch (NumberFormatException e) {

            System.out.println("GLModel.loadobject(): (line 202)Malformed OBJ file: " + br.toString() + "\r \r" + e.getMessage());
        }
    }

    private void loadmaterials() {
        String refm = mtl_path;

        try {
            InputStream is = this.getClass().getResourceAsStream(mtl_path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader brm = new BufferedReader(isr);
            materials = new MtlLoader(brm, mtl_path);
            is.close();
        } catch (IOException e) {

            System.out.println("GLModel.loadmarterial() (line 217)Could not open file: " + refm);
            materials = null;
        }
    }

    /**
     * Tính toán l?i các ??nh và ??t nó t?i ngay tâm O Sao cho tâm c?a hình
     * chính là tâm O
     */
    private void centerit() {
        float mid_x = (rightpoint - leftpoint) / 2.0F;
        float mid_y = (toppoint - bottompoint) / 2.0F;
        float mid_z = (nearpoint - farpoint) / 2.0F;

        for (int i = 0; i < vertexsets.size(); i++) {
            float coords[] = new float[4];

            coords[0] = ((float[]) vertexsets.get(i))[0] - leftpoint - mid_x;
            coords[1] = ((float[]) vertexsets.get(i))[1] - bottompoint - mid_y;
            coords[2] = ((float[]) vertexsets.get(i))[2] - farpoint - mid_z;

            vertexsets.set(i, coords);
        }

    }

    public float getXWidth() {
        float returnval = 0.0F;
        returnval = rightpoint - leftpoint;
        return returnval;
    }

    public float getYHeight() {
        float returnval = 0.0F;
        returnval = toppoint - bottompoint;
        return returnval;
    }

    public float getZDepth() {
        float returnval = 0.0F;
        returnval = nearpoint - farpoint;
        return returnval;
    }

    public int numpolygons() {
        return numpolys;
    }

    /**
     *
     * generate this.objectlist
     *
     * @param gl
     */
    public void opengldrawtolist(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        this.objectlist = gl.glGenLists(1);

        int nextmat = -1;
        int matcount = 0;
        int totalmats = mattimings.size();
        String[] nextmatnamearray = null;
        String nextmatname = null;

        if (totalmats > 0 && materials != null) {
            nextmatnamearray = (String[]) (mattimings.get(matcount));
            nextmatname = nextmatnamearray[0];
            nextmat = Integer.parseInt(nextmatnamearray[1]);
        }

        /////////////////////////////////////////////////////////////////////////////////// jundat begin list
        gl.glNewList(objectlist, GL.GL_COMPILE);

        for (int i = 0; i < faces.size(); i++) {
            if (i == nextmat) {
                gl.glEnable(GL.GL_COLOR_MATERIAL);
                gl.glColor4f((materials.getKd(nextmatname))[0], (materials.getKd(nextmatname))[1], (materials.getKd(nextmatname))[2], (materials.getd(nextmatname)));
                matcount++;
                if (matcount < totalmats) {
                    nextmatnamearray = (String[]) (mattimings.get(matcount));
                    nextmatname = nextmatnamearray[0];
                    nextmat = Integer.parseInt(nextmatnamearray[1]);
                }
            }

            int[] tempfaces = (int[]) (faces.get(i));
            int[] tempfacesnorms = (int[]) (facesnorms.get(i));
            int[] tempfacestexs = (int[]) (facestexs.get(i));

            //// Quad Begin Header ////
            int polytype;
            if (tempfaces.length == 3) {
                polytype = gl.GL_TRIANGLES;
            } else if (tempfaces.length == 4) {
                polytype = gl.GL_QUADS;
            } else {
                polytype = gl.GL_POLYGON;
            }

            gl.glBegin(polytype);
            ////////////////////////////

            for (int w = 0; w < tempfaces.length; w++) {
                if (tempfacesnorms[w] != 0) {
                    float normtempx = ((float[]) vertexsetsnorms.get(tempfacesnorms[w] - 1))[0];
                    float normtempy = ((float[]) vertexsetsnorms.get(tempfacesnorms[w] - 1))[1];
                    float normtempz = ((float[]) vertexsetsnorms.get(tempfacesnorms[w] - 1))[2];
                    gl.glNormal3f(normtempx, normtempy, normtempz);
                }

                if (tempfacestexs[w] != 0) {
                    float textempx = ((float[]) vertexsetstexs.get(tempfacestexs[w] - 1))[0];
                    float textempy = ((float[]) vertexsetstexs.get(tempfacestexs[w] - 1))[1];
                    float textempz = ((float[]) vertexsetstexs.get(tempfacestexs[w] - 1))[2];
                    gl.glTexCoord3f(textempx, 1f - textempy, textempz);
                }

                float tempx = ((float[]) vertexsets.get(tempfaces[w] - 1))[0];
                float tempy = ((float[]) vertexsets.get(tempfaces[w] - 1))[1];
                float tempz = ((float[]) vertexsets.get(tempfaces[w] - 1))[2];
                gl.glVertex3f(tempx, tempy, tempz);
            }


            //// Quad End Footer /////
            gl.glEnd();
            ///////////////////////////

        }
        gl.glEndList();
    }

    public void opengldraw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        this.skin.enable();
        this.skin.bind();
        
        gl.glColor3f(1, 1, 1);
        gl.glCallList(objectlist);
        gl.glDisable(GL.GL_COLOR_MATERIAL);
        this.skin.disable();
    }
}
