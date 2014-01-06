package tank3dclient;

/**
 *
 * @author Jundat
 */
public interface IMessageHandler {
	public void onReceiveMessage(Tank3DMessage message);
	public void onConnected();
}
