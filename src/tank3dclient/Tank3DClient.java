///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package tank3dclient;
//
//import javax.jms.JMSException;
//import javax.naming.NamingException;
//
//
//public class Tank3DClient implements IMessageHandler {
//
//	private Tank3DMessageListener m_listener;
//	private static Tank3DClient s_instance;
//	
//	public static Tank3DClient getInstance() {
//		if(s_instance == null) {
//			s_instance = new Tank3DClient();
//		}
//		
//		return s_instance;
//	}
//	
//	private Tank3DClient() {
//	}
//	
//	public void init() {
//		
//	}
//	
//	public void startConnection() {
//		m_listener = Tank3DMessageListener.getInstance();
//		m_listener.setMessageHandler(this);
//		
//		m_listener.startConnection();
//	}
//	
//	public void stopConnection() {
//		try {
//			m_listener.stopConnection();
//		} catch (JMSException e) {
//
//		} catch (NamingException e) {
//
//		} finally {
//			// disconnected ///////////////////////////////////////////////
//		}
//	}
//
//	public void sendMessage() {
//		Tank3DMessage message = new Tank3DMessage();
//		m_listener.sendMessage(message);
//	}
//
//	@Override
//	public void onReceiveMessage(Tank3DMessage message) {
//		System.out.println(message.toString());
//	}
//
//	@Override
//	public void onConnected() {
//		// TODO Auto-generated method stub
//		
//	}
//}
