package br.feevale.bytechat.server.bind;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.feevale.bytechat.server.ChatServer;
import br.feevale.bytechat.server.Connection;
import br.feevale.bytechat.util.Session;
import br.feevale.bytechat.util.User;

public class ConnectionBinder {

	private ChatServer server;
	
	private Thread connectionBindThread;
	
	public ConnectionBinder(ChatServer server) {
		this.server = server;
	}
	
	public void start() {
		connectionBindThread = new Thread(new ConnectionBindThread());
		connectionBindThread.start();
	}
	
	class ConnectionBindThread implements Runnable {

		private Executor connectionBindDispatcher = Executors.newCachedThreadPool();
		
		public void run() {
			while (!server.getConnector().isClosed()) {
				try {
					Connection connection = server.getConnector().accept();
					connectionBindDispatcher.execute(new SessionBinder(connection));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			connectionBindThread = null;
		}
		
	}
	
	class SessionBinder implements Runnable {

		private Connection connection;
		
		public SessionBinder(Connection connection) {
			this.connection = connection;
		}
		
		public void run() {
			try {
				User user = new User();
				user.setName(connection.getReader().readLine());
				
				Session session = new Session(user, connection);
//				TODO session.addListener(new MessageDistributorListener(context));
				session.start();
				
				server.addSession(session);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}