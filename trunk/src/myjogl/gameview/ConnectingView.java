/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.media.opengl.GL;
import tank3dclient.IMessageHandler;
import tank3dclient.Tank3DMessage;
import tank3dclient.Tank3DMessageListener;
import myjogl.GameEngine;
import myjogl.Global;
import myjogl.utils.Renderer;
import myjogl.utils.ResourceManager;

/**
 *
 * @author Jundat
 */
public class ConnectingView implements GameView, IMessageHandler {

	private Tank3DMessageListener m_listener;
	
    private float rotate = 0.0f;
    private Texture ttLoadingCircle;
    
    private boolean m_isFoundHost = false;
    private boolean m_isFoundClient = false;

    public ConnectingView() {
        System.out.println("Go to connecting view ---------------------------------");
        
        m_listener = Tank3DMessageListener.getInstance();
        m_listener.setMessageHandler(this);
        
		m_listener.startConnection();
    }

	@Override
	public void onConnected() {
		// TODO Auto-generated method stub
		System.out.println("Connected in ConnectingView... ");
		
		Tank3DMessage message = new Tank3DMessage();
		message.ClientId = Global.clientId;
		message.Cmd = Tank3DMessage.CMD_FIND_HOST;
		this.m_listener.sendMessage(message);
	}
	
	@Override
	public void onReceiveMessage(Tank3DMessage message) {
		Tank3DMessage newmessage;
		
		if(message.ClientId != Global.clientId) {
			switch(message.Cmd) {
				case Tank3DMessage.CMD_FIND_HOST: //reply client, choose client
					//m_listener.setMessageHandler(null);
					//Global.opponentClientId = message.ClientId;
					//Global.isHost = true;
					
					newmessage = new Tank3DMessage();
					newmessage.ClientId = Global.clientId;
					newmessage.OpponentClientId = message.ClientId; //choose client will play with you
					newmessage.Cmd = Tank3DMessage.CMD_IM_HOST;
					this.m_listener.sendMessage(newmessage);
					
					//preloadMainGame();
			        //GameEngine.getInstance().attach(new LoadingView((GameView) new MainGameView2Online()));
			        //GameEngine.getInstance().detach(this);
					break;
					
				case Tank3DMessage.CMD_IM_HOST: //reply from host, choose client
					if(message.OpponentClientId == Global.clientId && m_isFoundHost == false) { //you are chose to play with host						
						m_isFoundHost = true;
						
						m_listener.setMessageHandler(null);
						Global.opponentClientId = message.ClientId;
						Global.isHost = false;
						
						newmessage = new Tank3DMessage();
						newmessage.ClientId = Global.clientId;
						newmessage.OpponentClientId = message.ClientId; //choose client will play with you
						newmessage.Cmd = Tank3DMessage.CMD_FOUND_HOST;
						this.m_listener.sendMessage(newmessage);
						
						preloadMainGame();
				        GameEngine.getInstance().attach(new LoadingView((GameView) new MainGameView2Online()));
				        GameEngine.getInstance().detach(this);
					}
					break;
					
				case Tank3DMessage.CMD_FOUND_HOST: //reply from client, choose host
					if(message.OpponentClientId == Global.clientId && m_isFoundClient == false) {
						m_isFoundClient = true;
						
						m_listener.setMessageHandler(null);
						Global.opponentClientId = message.ClientId;
						Global.isHost = true;
						
						preloadMainGame();
				        GameEngine.getInstance().attach(new LoadingView((GameView) new MainGameView2Online()));
				        GameEngine.getInstance().detach(this);
					}
					break;
			}
		}
	}
	
	private void preloadMainGame() {
        //pre-load main game
        ResourceManager.getInst().PreLoadTexture("data/game/gach_tuong0.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_tuong1.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_tuong2.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_tuong3.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_tuong4.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        //
        ResourceManager.getInst().PreLoadTexture("data/game/gach_men1.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_men2.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_men3.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ResourceManager.getInst().PreLoadTexture("data/game/gach_men4.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        
        //skybox
        ResourceManager.getInst().PreLoadTexture("data/skybox/top.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
        ResourceManager.getInst().PreLoadTexture("data/skybox/bottom.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
        ResourceManager.getInst().PreLoadTexture("data/skybox/left.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
        ResourceManager.getInst().PreLoadTexture("data/skybox/right.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
        ResourceManager.getInst().PreLoadTexture("data/skybox/front.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
        ResourceManager.getInst().PreLoadTexture("data/skybox/back.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
    		GameEngine.sClick.play();
            //
            GameEngine.getInstance().attach(new ChooseModeView());
            GameEngine.getInstance().detach(this);
    	}
    }

    public void pointerPressed(MouseEvent e) {
    }

    public void pointerMoved(MouseEvent e) {
    }

    public void pointerReleased(MouseEvent e) {
    }

    public void load() {
        ttLoadingCircle = ResourceManager.getInst().getTexture("data/loading/loading_circle2.png");
    }

    public void unload() {
        //ResourceManager.getInst().deleteTexture("data/loading/loading_circle2.png");
    }

    public void update(long elapsedTime) {
        rotate += elapsedTime;
    }

    public void display() {
        Renderer.Render(ttLoadingCircle,
                Global.designWidth / 2 - ttLoadingCircle.getWidth() / 2, 
                Global.designHeight / 2 - ttLoadingCircle.getHeight() / 2, 
                ttLoadingCircle.getWidth(),
                ttLoadingCircle.getHeight(), 
                rotate);
        
        GameEngine.writer.Render("...Connecting... ", 10, 10, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
    }
}
