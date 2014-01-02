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

public class PubSubObjectCommunication implements MessageListener {

	public String SUBSCRIBE_TOPIC = "jms/Topic01";
	public String PUBLISH_TOPIC = "jms/Topic01";
	public String CONNECTION_FACTORY = "GFConnectionFactory";
	

	private TopicConnection m_topicConnection;
	private Topic m_publishTopic;
	private Topic m_subscribeTopic;
	private TopicSession m_publishSession;
	private TopicPublisher m_topicPublisher;
	private MessageHandler m_messageHandler;
	
	
	@Override
	public void onMessage(Message arg0) {
		try {
			ObjectMessage objectMessage = (ObjectMessage) arg0;
			CommunicationMessage communicationMessage = (CommunicationMessage)objectMessage.getObject();
			System.out.println("Sender : " + communicationMessage.getName()
							+ "   | Message : " + communicationMessage.getMessage() + "\n");
			
			// Notify to UI
			m_messageHandler.notifyOnMessage("Sender : " + communicationMessage.getName()
					+ "    | Message : " + communicationMessage.getMessage() + "\n");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void stop() throws JMSException, NamingException {
		m_topicConnection.stop();
	}
	
	public void start() throws JMSException, NamingException {
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
	}
	
	public void sendMessage(String userName, String message) throws JMSException {
		ObjectMessage objectMessage = m_publishSession.createObjectMessage();
		objectMessage.setObject(new CommunicationMessage(userName, message));
		m_topicPublisher.publish(objectMessage);
	}
	
	public void setMessageHandler(MessageHandler handler) {
		this.m_messageHandler = handler;
	}
}
