package tank3dclient;

import java.io.Serializable;

import myjogl.gameobjects.CDirections;
import myjogl.utils.Vector3;

/**
 * Contain data to send over network
 * @author Jundat
 */
public class Tank3DMessage implements Serializable {
	
	private static final long serialVersionUID = 646295211358243075L;
	
	public final static int CMD_NONE = 0;
	public final static int CMD_MOVE = 1;
	public final static int CMD_FIRE = 2;
	public final static int CMD_FIND_HOST = 3; //client find host
	public final static int CMD_IM_HOST = 4; //host reply
	public final static int CMD_FOUND_HOST = 5; //reply from client
	public final static int CMD_QUIT = 6;
	public final static int CMD_RESTART = 7;
	
	
	public int ClientId = -1;
	public int OpponentClientId = -1; //client will play with host
	public int Cmd = CMD_NONE;
	public Vector3 Position = new Vector3();
	public int Direction = CDirections.LEFT;
	
	public Tank3DMessage() {
	}
	
	public String toString() {
		return "(" + ClientId + "," + Cmd + "," +  Position + "," + Direction + ")";		
	}
}
