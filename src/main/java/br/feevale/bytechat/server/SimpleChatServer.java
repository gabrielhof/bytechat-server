package br.feevale.bytechat.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.feevale.bytechat.config.Configuration;
import br.feevale.bytechat.exception.ServerAlreadyStartedException;
import br.feevale.bytechat.exception.ServerException;
import br.feevale.bytechat.server.bind.ConnectionBinder;
import br.feevale.bytechat.server.connector.ServerConnector;
import br.feevale.bytechat.server.factory.ServerConnectorFactory;
import br.feevale.bytechat.server.listener.ServerListener;
import br.feevale.bytechat.server.listener.SessionNotifierServerListener;
import br.feevale.bytechat.util.Session;

public class SimpleChatServer implements ChatServer {

	private ServerConnectorFactory connectorFactory; 
	
	private Configuration configuration;
	
	private ServerConnector connector;
	private Set<Session> sessions = new HashSet<Session>();
	
	private List<ServerListener> serverListeners = new ArrayList<ServerListener>();
	
	private ConnectionBinder connectionBinder;
	private Executor eventDispatcher = Executors.newSingleThreadExecutor();
	
	public SimpleChatServer() {
		this(null);
	}
	
	public SimpleChatServer(Configuration configuration) {
		this.configuration = configuration;
		this.connectorFactory = ServerConnectorFactory.getDefault();
	}

	public void start() throws ServerException {
		if (configuration == null) {
			throw new NullPointerException("A configuração não pode ser nula.");
		}
		
		if (this.isRunning()) {
			throw new ServerAlreadyStartedException(String.format("O servidor ja foi iniciado na porta %d", configuration.getPort()));
		}
		
		connector = connectorFactory.create(configuration);
		addServerListener(new SessionNotifierServerListener(this));
			
		connectionBinder = new ConnectionBinder(this);
		connectionBinder.start();
	}

	public void stop() throws ServerException {
		if (connector != null) {
			connector.close();
			connector = null;
		}
	}

	public boolean isRunning() {
		return connector != null;
	}
	
	public void setConfiguration(Configuration configuration) throws ServerException {
		this.configuration = configuration;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}

	public ServerConnector getConnector() {
		return connector;
	}
	
	public Collection<Session> getSessions() {
		return sessions;
	}

	public boolean addSession(Session session) throws ServerException {
		boolean result = sessions.add(session);
		fireNewSession(session);
		
		return result;
	}

	public boolean removeSession(Session session) throws ServerException {
		boolean result = sessions.remove(session);
		fireSessionEnded(session);
		
		return result;
	}

	public boolean addServerListener(ServerListener serverListener) {
		return serverListeners.add(serverListener);
	}
	
	public boolean removeServerListener(ServerListener serverListener) {
		return serverListeners.remove(serverListener);
	}
	
	public List<ServerListener> getServerListeners() {
		return serverListeners;
	}
	
	protected void fireNewSession(Session session) {
		eventDispatcher.execute(new NewSessionDispatcher(session));
	}
	
	protected void fireSessionEnded(Session session) {
		eventDispatcher.execute(new EndSessionDispatcher(session));
	}
	
	abstract class SessionDispatcher implements Runnable {
		
		private Session session;
		
		public SessionDispatcher(Session session) {
			this.session = session;
		}
		
		@Override
		public void run() {
			for (ServerListener listener : serverListeners) {
				try {
					fireEvent(listener, session);
				} catch (ServerException e) {
					//TODO
					e.printStackTrace();
				}
			}
		}
		
		protected abstract void fireEvent(ServerListener listener, Session session) throws ServerException;
		
	}
	
	class NewSessionDispatcher extends SessionDispatcher {
		
		public NewSessionDispatcher(Session session) {
			super(session);
		}

		@Override
		protected void fireEvent(ServerListener listener, Session session) throws ServerException {
			listener.newSession(session);
		}

	}
	
	class EndSessionDispatcher extends SessionDispatcher {
		
		public EndSessionDispatcher(Session session) {
			super(session);
		}

		@Override
		protected void fireEvent(ServerListener listener, Session session) throws ServerException {
			listener.endedSession(session);
		}
	}
	
}
