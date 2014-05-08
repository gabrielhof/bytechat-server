package br.feevale.bytechat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

import br.feevale.bytechat.config.ChatConfiguration;
import br.feevale.bytechat.exception.ServerAlreadyStartedException;
import br.feevale.bytechat.listener.ServerListener;
import br.feevale.bytechat.server.bind.ConnectionBindThread;
import br.feevale.bytechat.util.Session;

public class CliChatServer {

	private ChatConfiguration configuration;
	private ChatContext context;
	
	private ConnectionBindThread connectionBinder;
	
	public CliChatServer(ChatConfiguration configuration) {
		this.configuration = configuration;
	}

	public void start() throws IOException {
		if (this.isRunning()) {
			throw new ServerAlreadyStartedException(String.format("O servidor ja foi iniciado na porta %d", configuration.getPort()));
		}
		
		context = new ChatContext(new ServerSocket(configuration.getPort()));
		context.addServerListener(new DefaultSessionListener());
		
		connectionBinder = new ConnectionBindThread(context);
		connectionBinder.start();
	}

	public void stop() throws IOException {
		if (context != null) {
			context.getServerSocket().close();
		}
	}
	
	public ChatConfiguration getConfiguration() {
		return configuration;
	}

	public boolean isRunning() {
		return context != null;
	}

	public List<Session> getSessions() {
		return context.getSessions();
	}

	public boolean addSession(Session session) {
		return context.addSession(session);
	}

	public boolean removeSession(Session session) {
		return context.removeSession(session);
	}

	public boolean addServerListener(ServerListener listener) {
		return context.addServerListener(listener);
	}
	
	class DefaultSessionListener implements ServerListener {

		public void newSession(Session newSession) {
			for (Session session : context.getSessions()) {
				if (session != newSession) {
					try {
						session.getSocket().getOutputStream().write(String.format("%s acabou de entrar.", newSession.getUser().getName()).getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
}
