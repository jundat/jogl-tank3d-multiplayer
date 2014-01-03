package tank3dclient;

import java.awt.event.KeyEvent;
import java.io.Serializable;

/**
 * Contain data to send over network
 * @author Jundat
 *
 */
public class Tank3DMessage implements Serializable {
	
	private static final long serialVersionUID = 646295211358243075L;
	
	public final static int CMD_NONE = 0;
	public final static int CMD_MOVE = 1;
	public final static int CMD_FIRE = 2;
	public final static int CMD_FIND_HOST = 3;
	public final static int CMD_IM_HOST = 4;
	
	
	public int ClientId = 0;
	public int Cmd = CMD_NONE;
	public int PressKey = KeyEvent.VK_HOME;
	
	public Tank3DMessage() {
	}
	
	public String toString() {
		return "(" + ClientId + "," + Cmd + "," + PressKey + ")";		
	}
}
