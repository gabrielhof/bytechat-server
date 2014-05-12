package br.feevale.bytechat.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.feevale.bytechat.listener.SessionListener;
import br.feevale.bytechat.server.Connection;

public class Session implements Runnable {
	
	private User user;
	private Connection connection;
	
	private List<SessionListener> listeners = new ArrayList<SessionListener>();
	
	private Executor eventDispatcher = Executors.newSingleThreadExecutor();
	private Thread thread;
	
	public Session(User user, Connection connection) {
		this.user = user;
		this.connection = connection;
	}
	
	public User getUser() {
		return user;
	}
	
	public Connection getConnection() {
		return connection;
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
			while (thread != null) {
				String message = connection.getReader().readLine();
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
