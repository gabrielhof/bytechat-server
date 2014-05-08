package br.feevale.bytechat.server;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.feevale.bytechat.listener.ServerListener;
import br.feevale.bytechat.util.Session;

public class ChatContext {

	private ServerSocket serverSocket;
	private List<Session> sessions = new ArrayList<Session>();
	
	private List<ServerListener> serverListeners = new ArrayList<ServerListener>();
	
	private Executor eventDispatcher = Executors.newSingleThreadExecutor();
	
	public ChatContext(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	public boolean addSession(Session session) {
		boolean result = sessions.add(session);
		fireNewSession(session);
		
		return result;
	}
	
	public boolean removeSession(Session session) {
		return sessions.remove(session);
	}
	
	public List<Session> getSessions() {
		return sessions;
	}
	
	public boolean addServerListener(ServerListener listener) {
		return serverListeners.add(listener);
	}
	
	public List<ServerListener> getServerListeners() {
		return serverListeners;
	}

	protected void fireNewSession(final Session newSession) {
		eventDispatcher.execute(new Runnable() {
			public void run() {
				for (ServerListener listener : serverListeners) {
					listener.newSession(newSession);
				}
			}
		});
	}
	
}
