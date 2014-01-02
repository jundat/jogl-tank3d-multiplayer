/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.tank3d;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Handle all window events here
 *
 * @author Jundat
 */
public class Tank3DWindowAdapter extends WindowAdapter {

    public Tank3D tank3d;

    public Tank3DWindowAdapter(Tank3D tank3d) {
        this.tank3d = tank3d;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Thread t = new Thread(new Tank3DRunnable(this.tank3d));
        t.start();
    }
}

class Tank3DRunnable implements Runnable {

    public Tank3D tank3d;

    public Tank3DRunnable(Tank3D tank3d) {
        this.tank3d = tank3d;
    }

    public void run() {
        //unload resource here

        this.tank3d.exit();
    }
}
