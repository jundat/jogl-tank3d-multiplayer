/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.media.opengl.GL;
import myjogl.Global;

/**
 * All game resource must be loaded into before run game.
 *
 * @author Jundat
 */
public class ResourceManager {

    /**
     * Used for saving data for pre-loading texture
     */
    class TextureInfo {

        public String fileName;
        public boolean wantFlip;
        public int wrap_s;
        public int wrap_t;
        public int minFilter;
        public int magFilter;

        public TextureInfo(String fileName, boolean wantFlip, int wrap_s, int wrap_t, int minFilter, int magFilter) {
            this.fileName = fileName;
            this.wantFlip = wantFlip;
            this.wrap_s = wrap_s;
            this.wrap_t = wrap_t;
            this.minFilter = minFilter;
            this.magFilter = magFilter;
        }
    }

    /**
     * Used for saving data for pre-loading sound
     */
    class SoundInfo {

        public String fileName;
        public boolean isLoop;

        public SoundInfo(String fileName, boolean isLoop) {
            this.fileName = fileName;
            this.isLoop = isLoop;
        }
    }
    //pre-load
    private HashMap preTextures;
    private HashMap preSounds;
    //loading
    private HashMap textures;
    private HashMap sounds;
    //pre-unload
    private HashMap preUnloadTextures;
    private static ResourceManager instance = null;

    private ResourceManager() {
        textures = new HashMap();
        sounds = new HashMap();
        preTextures = new HashMap();
        preSounds = new HashMap();
        preUnloadTextures = new HashMap();
    }

    public static ResourceManager getInst() {
        if (instance == null) {
            instance = new ResourceManager();
        }

        return instance;
    }

    public int GetNumberPreload() {
        return preSounds.values().size() + preTextures.values().size();
    }
    
    public int GetAllResource() {
        int preut = preUnloadTextures.values().size();
        int prett = preTextures.values().size();
        int pres = preSounds.values().size();
        int tt = textures.values().size();
        int s = sounds.values().size();

        System.out.println("ResourceManager at End game ------------------------");
        System.out.println("preUnloadTextures: " + preut);
        System.out.println("preTextures: " + prett);
        System.out.println("preSounds: " + pres);
        System.out.println("textures: " + tt);
        System.out.println("sounds: " + s);

        System.out.println("-------detail-------");

        if (preut > 0) {
            System.out.println("Pre-UnloadTextures: ");
            Global.Print(preUnloadTextures);
        }

        if (prett > 0) {
            System.out.println("Pre-Textures: ");
            Global.Print(preTextures);
        }

        if (pres > 0) {
            System.out.println("Pre-Sounds: ");
            Global.Print(preSounds);
        }

        if (tt > 0) {
            System.out.println("Textures: ");
            Global.Print(textures);
        }

        if (s > 0) {
            System.out.println("Sounds: ");
            Global.Print(sounds);
        }

        System.out.println("------end-detail-------");

        return preut + prett + pres + tt + s;
    }

    //load
    public Texture getTexture(String fileName, boolean wantFlip, int wrap_s, int wrap_t, int minFilter, int magFilter) {
        if (textures.containsKey(fileName)) {

            return (Texture) textures.get(fileName);
        } else {
            Texture tt = TextureLoader.Load(fileName, wantFlip, wrap_s, wrap_t, minFilter, magFilter);
            textures.put(fileName, tt);
            return tt;
        }
    }

    public Texture getTexture(String fileName, boolean wantFlip, int wrap) {
        if (textures.containsKey(fileName)) {
            return (Texture) textures.get(fileName);
        } else {
            Texture tt = TextureLoader.Load(fileName, wantFlip, wrap, GL.GL_LINEAR);
            textures.put(fileName, tt);
            return tt;
        }
    }

    public Texture getTexture(String fileName) {
        if (textures.containsKey(fileName)) {
            return (Texture) textures.get(fileName);
        } else {
            Texture tt = TextureLoader.Load(fileName);
            textures.put(fileName, tt);
            return tt;
        }
    }

    public Sound getSound(String fileName, boolean isLoop) {
        if (sounds.containsKey(fileName)) {
            return (Sound) sounds.get(fileName);
        } else {
            Sound s = new Sound(fileName, isLoop);
            sounds.put(fileName, s);
            return s;
        }
    }
    //end-load

    //preload
    /**
     * Call every frame to pre-load
     */
    public void PreLoad() {
        if (preTextures.isEmpty() == false) {
            Iterator it = preTextures.entrySet().iterator();
            if (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                //load

                TextureInfo ti = (TextureInfo) pairs.getValue();
                ResourceManager.getInst().getTexture(ti.fileName, ti.wantFlip, ti.wrap_s, ti.wrap_t, ti.minFilter, ti.magFilter);
                System.out.println(">+ Texture: " + ti.fileName);

                //remove
                preTextures.remove(ti.fileName);
            }
        }

        if (preSounds.isEmpty() == false) {
            Iterator it = preSounds.entrySet().iterator();
            if (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                //load

                SoundInfo si = (SoundInfo) pairs.getValue();
                ResourceManager.getInst().getSound(si.fileName, si.isLoop);
                System.out.println(">+ Sound: " + si.fileName);

                //remove
                preSounds.remove(si.fileName);
            }
        }
    }

    public void PreLoadTexture(String fileName, boolean wantFlip, int wrap_s, int wrap_t, int minFilter, int magFilter) {
        TextureInfo ti = new TextureInfo(fileName, wantFlip, wrap_s, wrap_t, minFilter, magFilter);
        preTextures.put(fileName, ti);
    }

    public void PreLoadSound(String fileName, boolean isLoop) {
        SoundInfo si = new SoundInfo(fileName, isLoop);
        preSounds.put(fileName, si);
    }
    //end-preload

    //pre-unload
    public void PreUnload() {
        if (preUnloadTextures.isEmpty() == false) {
            Iterator it = preUnloadTextures.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                //un-load
                String fileName = (String) pairs.getKey();
                Texture tt = (Texture) pairs.getValue();
                tt.disable();
                tt.dispose();
                tt = null;

                System.out.println("<- Texture: " + fileName);
            }

            preUnloadTextures.clear();
        }
    }
    //end-pre-unload

    //delete
    public void deleteTexture(String fileName) {
        Texture t = ResourceManager.getInst().getTexture(fileName);
        preUnloadTextures.put(fileName, t);
        textures.remove(fileName);
    }

    public void deleteSound(String fileName) {
        Sound s = ResourceManager.getInst().getSound(fileName, false);
        sounds.remove(fileName);
        s.dispose();
        s = null;
    }

    public void deleteSound(Sound s) {
        sounds.remove(s.fileName);
        s.dispose();
        s = null;
    }

    public void deleteAllTextures() {
        textures.clear();
    }

    public void deleteAllSounds() {
        sounds.clear();
    }
    //end-delete
}
