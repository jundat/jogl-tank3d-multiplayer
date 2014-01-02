/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tank3dclient;

import javax.jms.JMSException;
import javax.naming.NamingException;


public class Main extends javax.swing.JFrame implements
		MessageHandler {

	private static final long serialVersionUID = -780301070320968585L;

	public Main() {
		initComponents();
	}
	
	private void initComponents() {

		new javax.swing.ButtonGroup();
		new javax.swing.ButtonGroup();
		new javax.swing.ButtonGroup();
		new javax.swing.ButtonGroup();
		
		jLabel1 = new javax.swing.JLabel();
		tfPublishTopic = new javax.swing.JTextField();
		tfSubscribeTopic = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		btnStartReset = new javax.swing.JButton();
		btnSend = new javax.swing.JButton();
		tfNewMessage = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		tfUserName = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		chatLog = new javax.swing.JTextArea();
		jLabel5 = new javax.swing.JLabel();
		tfTopicConnectionFactory = new javax.swing.JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setModalExclusionType(java.awt.Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
		setName("FrameJMSJavaSide"); // NOI18N

		jLabel1.setText("Publish to:");

		tfPublishTopic.setText("jms/Topic01");
		tfPublishTopic.setToolTipText("jms/YourTopicName");
		tfPublishTopic.setName("tfPublishTopic"); // NOI18N
		
		tfSubscribeTopic.setText("jms/Topic02");
		tfSubscribeTopic.setToolTipText("jms/YourTopicName");
		
		jLabel2.setText("Subscribe:");

		btnStartReset.setText("START");
		btnStartReset.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnStartResetActionPerformed(evt);
			}
		});

		btnSend.setText("Send");
		btnSend.setEnabled(false);
		btnSend.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSendActionPerformed(evt);
			}
		});

		tfNewMessage.setToolTipText("New message here");
		tfNewMessage.setEnabled(false);

		jLabel3.setText("New message:");

		tfUserName.setToolTipText("jms/YourTopicName");
		
		jLabel4.setText("User name:");

		chatLog.setColumns(20);
		chatLog.setRows(5);
		chatLog.setEnabled(false);
		jScrollPane2.setViewportView(chatLog);

		jLabel5.setText("Topic connection factory:");

		tfTopicConnectionFactory.setText("GFConnectionFactory");
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		
		getContentPane().setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane2).addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup().addGroup(layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING)
																				.addGroup(layout.createSequentialGroup()
																								.addComponent(
																										jLabel4)
																								.addGap(13,
																										13,
																										13)
																								.addComponent(
																										tfUserName))
																				.addComponent(
																						tfNewMessage)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addGap(0,
																																		0,
																																		Short.MAX_VALUE)
																																.addComponent(
																																		jLabel2)
																																.addGap(18,
																																		18,
																																		18))
																												.addGroup(
																														layout.createSequentialGroup()
																																.addComponent(
																																		jLabel1)
																																.addGap(18,
																																		18,
																																		18)))
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING,
																												false)
																												.addComponent(
																														tfPublishTopic,
																														javax.swing.GroupLayout.DEFAULT_SIZE,
																														254,
																														Short.MAX_VALUE)
																												.addComponent(
																														tfSubscribeTopic))))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						btnSend,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						btnStartReset,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel3)
																.addGap(0,
																		0,
																		Short.MAX_VALUE))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel5)
																.addGap(13, 13,
																		13)
																.addComponent(
																		tfTopicConnectionFactory,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		262,
																		Short.MAX_VALUE)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(0, 11, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														tfTopicConnectionFactory,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel5))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						jLabel1)
																				.addComponent(
																						tfPublishTopic,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						jLabel2)
																				.addComponent(
																						tfSubscribeTopic,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addComponent(
														btnStartReset,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														46,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel4)
												.addComponent(
														tfUserName,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(13, 13, 13)
								.addComponent(jLabel3)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														tfNewMessage,
														javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														btnSend,
														javax.swing.GroupLayout.Alignment.TRAILING))
								.addGap(18, 18, 18)
								.addComponent(jScrollPane2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										185,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		pack();
	}// </editor-fold>


	private void btnStartResetActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		if (btnStartReset.getText().equals("START")) {

			communication = new PubSubObjectCommunication();
			communication.setMessageHandler(this);
			communication.CONNECTION_FACTORY = tfTopicConnectionFactory.getText();
			communication.PUBLISH_TOPIC = tfPublishTopic.getText();
			communication.SUBSCRIBE_TOPIC = tfSubscribeTopic.getText();
			try {
				communication.start();
			} catch (JMSException e) {

			} catch (NamingException e) {

			} finally {

				btnStartReset.setText("RESET");
				tfNewMessage.setEnabled(true);
				btnSend.setEnabled(true);
				chatLog.setEnabled(true);

				tfTopicConnectionFactory.setEnabled(false);
				tfPublishTopic.setEnabled(false);
				tfSubscribeTopic.setEnabled(false);
				tfUserName.setEnabled(false);
			}
		} else {
			try {
				communication.stop();
			} catch (JMSException e) {

			} catch (NamingException e) {

			} finally {

				btnStartReset.setText("START");
				tfNewMessage.setEnabled(false);
				btnSend.setEnabled(false);
				chatLog.setEnabled(false);

				tfTopicConnectionFactory.setEnabled(true);
				tfPublishTopic.setEnabled(true);
				tfSubscribeTopic.setEnabled(true);
				tfUserName.setEnabled(true);

				// reset all field
				tfNewMessage.setText("");
				chatLog.setText("");
			}
		}
	}

	private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		try {
			communication.sendMessage(tfUserName.getText(), tfNewMessage.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(final String args[]) {
		new Main().setVisible(true);
	}


	
	// Variables declaration - do not modify
	private javax.swing.JButton btnSend;
	private javax.swing.JButton btnStartReset;
	private javax.swing.JTextArea chatLog;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTextField tfNewMessage;
	private javax.swing.JTextField tfPublishTopic;
	private javax.swing.JTextField tfSubscribeTopic;
	private javax.swing.JTextField tfTopicConnectionFactory;
	private javax.swing.JTextField tfUserName;
	
	// End of variables declaration
	PubSubObjectCommunication communication;

	@Override
	public void notifyOnMessage(String newMessage) {
		// Show in Log
		chatLog.setText(chatLog.getText() + newMessage);
	}
}
