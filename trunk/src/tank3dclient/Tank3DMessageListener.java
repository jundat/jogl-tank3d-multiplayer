package tank3dclient;

import java.util.Properties;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Tank3DMessageListener implements MessageListener {

	public String SUBSCRIBE_TOPIC = "jms/Topic01";
	public String PUBLISH_TOPIC = "jms/Topic01";
	public String CONNECTION_FACTORY = "GFConnectionFactory";

	private TopicConnection m_topicConnection;
	private Topic m_publishTopic;
	private Topic m_subscribeTopic;
	private TopicSession m_publishSession;
	private TopicPublisher m_topicPublisher;
	private IMessageHandler m_messageHandler;
	
	private static Tank3DMessageListener s_instance = null;
	
	public static Tank3DMessageListener getInstance() {
		if(s_instance == null) {
			s_instance = new Tank3DMessageListener();
		}
		
		return s_instance;
	}
	
	private Tank3DMessageListener() {
		
	}
	
	@Override
	public void onMessage(Message arg0) {
		try {
			ObjectMessage objectMessage = (ObjectMessage) arg0;
			Tank3DMessage message = (Tank3DMessage)objectMessage.getObject();
			System.out.println(message.toString());
			
			// Notify to UI
			if(m_messageHandler != null) {
				m_messageHandler.onReceiveMessage(message);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void stopConnection() throws JMSException, NamingException {
		m_topicConnection.stop();
	}
	
	public void startThread() {
		try {
			Properties properties = new Properties();
			properties.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
			properties.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
			properties.setProperty("java.naming.provider.url", "iiop://localhost:3700");
			
			Context initialContext = new InitialContext(properties);
			
			// Lookup topic SUBSCRIBE_TOPIC and PUBLISH_TOPIC
			m_subscribeTopic = (Topic)initialContext.lookup(SUBSCRIBE_TOPIC);
			m_publishTopic = (Topic)initialContext.lookup(PUBLISH_TOPIC);
			
			// Lookup topic factory
			TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory)initialContext.lookup(CONNECTION_FACTORY); 
			
			// Create TopicConnection from topicConnectionFactory
			m_topicConnection = topicConnectionFactory.createTopicConnection();
			m_topicConnection.start();
			
			// Subscribe
			TopicSession subscribeSession = m_topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			TopicSubscriber topicSubscriber = subscribeSession.createSubscriber(m_subscribeTopic);
			topicSubscriber.setMessageListener(this);
			
			// Publish
			m_publishSession = m_topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			m_topicPublisher = m_publishSession.createPublisher(m_publishTopic);
			
			if(m_messageHandler != null) {
				m_messageHandler.onConnected();
			}
		} catch (Exception eee){
			System.err.println(eee.getMessage());
		}
	}
	
	public void startConnection() {
		Tank3DConnectionThread t = new Tank3DConnectionThread(this);
		t.start();
	}
	
	public void sendMessage(Tank3DMessage message) {
		try {
			ObjectMessage objectMessage = m_publishSession.createObjectMessage();
			objectMessage.setObject(message);
			m_topicPublisher.publish(objectMessage);
		} catch (Exception eee) {
			System.err.println("Can not send message");
		}
	}
	
	public void setMessageHandler(IMessageHandler handler) {
		this.m_messageHandler = handler;
	}
}
