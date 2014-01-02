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
	

	private TopicConnection topicConnection;
	private Topic publishTopic;
	private Topic subscribeTopic;
	private TopicSession publishSession;
	private TopicPublisher topicPublisher;
	private MessageHandler mMessageHandler;
	
	
	@Override
	public void onMessage(Message arg0) {
		try {
			ObjectMessage objectMessage = (ObjectMessage) arg0;
			CommunicationMessage communicationMessage = (CommunicationMessage)objectMessage.getObject();
			System.out.println("Sender : " + communicationMessage.getName()
							+ "   | Message : " + communicationMessage.getMessage() + "\n");
			
			// Notify to UI
			mMessageHandler.notifyOnMessage("Sender : " + communicationMessage.getName()
					+ "    | Message : " + communicationMessage.getMessage() + "\n");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void stop() throws JMSException, NamingException {
		topicConnection.stop();
	}
	
	public void start() throws JMSException, NamingException {
		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
		properties.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
		properties.setProperty("java.naming.provider.url", "iiop://localhost:3700");
		
		Context initialContext = new InitialContext(properties);
		
		// Lookup topic SUBSCRIBE_TOPIC and PUBLISH_TOPIC
		subscribeTopic = (Topic)initialContext.lookup(SUBSCRIBE_TOPIC);
		publishTopic = (Topic)initialContext.lookup(PUBLISH_TOPIC);
		
		// Lookup topic factory
		TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory)initialContext.lookup(CONNECTION_FACTORY); 
		
		// Create TopicConnection from topicConnectionFactory
		TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();
		topicConnection.start();
		
		// Subscribe
		TopicSession subscribeSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		TopicSubscriber topicSubscriber = subscribeSession.createSubscriber(subscribeTopic);
		topicSubscriber.setMessageListener(this);
		
		// Publish
		publishSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		topicPublisher = publishSession.createPublisher(publishTopic);
	}
	
	public void sendMessage(String userName, String message) throws JMSException {
		ObjectMessage objectMessage = publishSession.createObjectMessage();
		objectMessage.setObject(new CommunicationMessage(userName, message));
		topicPublisher.publish(objectMessage);
	}
	
	public void setMessageHandler(MessageHandler handler) {
		this.mMessageHandler = handler;
	}
}
