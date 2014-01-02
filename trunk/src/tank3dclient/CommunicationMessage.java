package tank3dclient;

import java.io.Serializable;

/**
 * Contain data to send over network
 * @author Jundat
 *
 */
public class CommunicationMessage implements Serializable {
	
	private static final long serialVersionUID = 646295211358243075L;
	String name;
	String message;
	
	public CommunicationMessage(String name, String message) {
		this.name = name;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
