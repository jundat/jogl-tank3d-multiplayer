/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameobjects;

import java.io.Serializable;

/**
 * H??ng trong m?t ph?ng Oxz
 * Up:    -Oz
 * Down:   Oz
 * Left:  -Ox
 * Right:  Ox 
 * @author Jundat
 */
public class CDirections  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2086530904016517505L;
	public final static int NONE = -1;
    //
    public final static int UP = 0;     //-Oz
    public final static int DOWN = 1;   //Oz
    public final static int LEFT = 2;   //-Ox
    public final static int RIGHT = 3;  //Ox
    //
    public final static int NUMBER_DIRECTION = 4;
}
