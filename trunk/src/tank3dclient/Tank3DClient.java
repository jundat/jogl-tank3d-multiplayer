/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tank3dclient;

import javax.jms.JMSException;
import javax.naming.NamingException;


public class Tank3DClient implements IMessageHandler {

	private static final long serialVersionUID = -780301070320968585L;
	private Tank3DMessageListener m_listener;

	
	private void start() {
		m_listener = new Tank3DMessageListener();
		m_listener.setMessageHandler(this);
		try {
			m_listener.start();
		} catch (JMSException e) {

		} catch (NamingException e) {

		} finally {
			// connected ///////////////////////////////////////////////
		}
	}
	
	private void stop() {
		try {
			m_listener.stop();
		} catch (JMSException e) {

		} catch (NamingException e) {

		} finally {
			// disconnected ///////////////////////////////////////////////
		}
	}

	private void sendMessage() {
		try {
			Tank3DMessage message = new Tank3DMessage();
			m_listener.sendMessage(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onReceiveMessage(Tank3DMessage message) {
		System.out.println(message.toString());
	}
}
