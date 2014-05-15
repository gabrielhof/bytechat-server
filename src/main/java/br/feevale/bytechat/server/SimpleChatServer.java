package br.feevale.bytechat.server;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

	@Inject
	private ServerConnectorFactory connectorFactory; 
	
	private Configuration configuration;
	
	private ServerConnector connector;
	private List<Session> sessions = new ArrayList<Session>();
	
	private List<ServerListener> serverListeners = new ArrayList<ServerListener>();
	
	private ConnectionBinder connectionBinder;
	
	public SimpleChatServer() {}
	
	public SimpleChatServer(Configuration configuration) {
		this.configuration = configuration;
	}

	public void start() throws ServerException {
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
	
	public List<Session> getSessions() {
		return sessions;
	}

	public boolean addSession(Session session) throws ServerException {
		boolean result = sessions.add(session);
		//TODO fireNewSession
		
		return result;
	}

	public boolean removeSession(Session session) throws ServerException {
		boolean result = sessions.remove(session);
		//TODO fireSessionEnded
		
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
	
}
