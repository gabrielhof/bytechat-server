package br.feevale.bytechat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.feevale.bytechat.config.Configuration;
import br.feevale.bytechat.server.bind.ConnectionBinder;
import br.feevale.bytechat.server.exception.ServerAlreadyStartedException;
import br.feevale.bytechat.server.exception.ServerException;
import br.feevale.bytechat.server.listener.ServerListener;
import br.feevale.bytechat.util.Session;

public class CommandLineChatServer implements ChatServer {

	@Inject
	private ServerConnectorFactory connectorFactory; 
	
	private Configuration configuration;
	private ChatContext context;
	
	private ServerConnector connector;
	private List<Session> sessions = new ArrayList<Session>();
	
	private List<ServerListener> serverListeners = new ArrayList<ServerListener>();
	
	private ConnectionBinder connectionBinder;
	
	public CommandLineChatServer(Configuration configuration) {
		this.configuration = configuration;
	}

	public void start() throws ServerException {
		if (this.isRunning()) {
			throw new ServerAlreadyStartedException(String.format("O servidor ja foi iniciado na porta %d", configuration.getPort()));
		}
		
		try {
			connector = connectorFactory.create(configuration);
			
			context = new ChatContext(new ServerSocket(configuration.getPort()));
			context.addServerListener(new DefaultSessionListener());
			
			connectionBinder = new ConnectionBinder(this);
			connectionBinder.start();
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}

	public void stop() throws ServerException {
		if (context != null) {
			try {
				context.getServerSocket().close();
			} catch (IOException e) {
				throw new ServerException(e);
			}
		}
	}

	public boolean isRunning() {
		return context != null;
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
		boolean result = context.removeSession(session);
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
	
	class DefaultSessionListener implements ServerListener {

		public void newSession(Session newSession) {
			for (Session session : context.getSessions()) {
				if (session != newSession) {
					try {
						session.getConnection().getWriter().write(String.format("%s acabou de entrar.", newSession.getUser().getName()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
}
