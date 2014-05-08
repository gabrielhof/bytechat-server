package br.feevale.bytechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.feevale.bytechat.listener.SessionListener;

public class Session implements Runnable {
	
	private User user;
	private Socket socket;
	
	private List<SessionListener> listeners = new ArrayList<SessionListener>();
	
	private Executor eventDispatcher = Executors.newSingleThreadExecutor();
	private Thread thread;
	
	public Session(User user, Socket socket) {
		this.user = user;
		this.socket = socket;
	}
	
	public User getUser() {
		return user;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void addListener(SessionListener listener) {
		listeners.add(listener);
	}
	
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while (thread != null) {
				String message = reader.readLine();
				fireMessageReceived(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void fireMessageReceived(final String message) {
		eventDispatcher.execute(new Runnable() {
			public void run() {
				for (SessionListener listener : listeners) {
					listener.messageReceived(Session.this, message);
				}
			}
		});
	}

}
