package tank3dclient;

public class Tank3DConnectionThread extends Thread {
	
	private Tank3DMessageListener m_listener;
	
	public Tank3DConnectionThread(Tank3DMessageListener listener) {
		m_listener = listener;	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		m_listener.startThread();
	}
}
