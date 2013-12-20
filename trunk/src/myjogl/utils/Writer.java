/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;
import javax.media.opengl.GL;
import myjogl.Global;

/**
 *
 * @author Jundat
 */
public class Writer {

    class CharacterInfo {

        public char ch; //character
        public int x, y, w, h, a, c;

        public CharacterInfo() {
        }

        public CharacterInfo(char ch, int x, int y, int w, int h, int a, int c) {
            this.ch = ch;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.a = a;
            this.c = c;
        }
    }
    private HashMap characters;
    private String fntFile;
    private String fileTexture;
    private Texture tt;

    public Writer(String fntFile) {
        characters = new HashMap();

        InputStream is = ResourceManager.class.getResourceAsStream(fntFile);
        Scanner scn = new Scanner(is);
        String s;
        s = scn.nextLine(); //[HGEFONT]
        s = scn.nextLine(); //
        s = scn.nextLine(); //Bitmap=Nyala_72.png

        //load
        fileTexture = s.substring("Bitmap=".length()); //Nyala_72.png
        int e = fntFile.lastIndexOf("/");
        fileTexture = fntFile.substring(0, e + 1) + fileTexture;
        tt = ResourceManager.getInst().getTexture(fileTexture, false, GL.GL_REPEAT);
        //end-load

        s = scn.nextLine(); //

        while (scn.hasNext()) {
            s = scn.nextLine();

            //Char="!",1,1,10,83,4,2
            if (s.startsWith("Char=")) {
                CharacterInfo ci = new CharacterInfo();
                ci.ch = s.charAt("Char=\"".length()); //get '!'                
                s = s.substring("Char=\"-\",".length()); //1,1,10,83,4,2

                String[] nums = s.split(",");

                ci.x = Integer.parseInt(nums[0]);
                ci.y = Integer.parseInt(nums[1]);
                ci.w = Integer.parseInt(nums[2]);
                ci.h = Integer.parseInt(nums[3]);
                ci.a = Integer.parseInt(nums[4]);
                ci.c = Integer.parseInt(nums[5]);

                characters.put(ci.ch, ci);
                System.out.println("Char: "
                        + ci.ch
                        + "," + ci.x
                        + "," + ci.y
                        + "," + ci.w
                        + "," + ci.h
                        + "," + ci.a
                        + "," + ci.c);
            }
        }
    }

    public void Render(String content, float x, float y, float scalex, float scaley) {
        int len = content.length();
        char c;
        float curpos = x;
        
        for (int i = 0; i < len; i++) {
            c = content.charAt(i);
            CharacterInfo ci = (CharacterInfo) characters.get(c);

            if (ci != null) {
                Renderer.Render(tt,
                        ci.x, ci.y, ci.w, ci.h,
                        curpos, y, ci.w * scalex, ci.h * scaley);

                curpos += ci.w * scalex;
            }
        }
    }

    public void Render(String content, float x, float y, float scalex, float scaley,
            float red, float green, float blue) {
        GL gl = Global.drawable.getGL();

        gl.glEnable(GL.GL_BLEND);
        gl.glColor3f(red, green, blue);
        Render(content, x, y, scalex, scaley);
        gl.glColor3f(1, 1, 1);
    }
}
