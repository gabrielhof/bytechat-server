package br.feevale.bytechat.server.listener;

import java.io.IOException;

import br.feevale.bytechat.server.ChatServer;
import br.feevale.bytechat.server.exception.ServerException;
import br.feevale.bytechat.util.Session;

public class SessionNotifierListener implements SessionListener {

	private ChatServer server;
	
	public SessionNotifierListener(ChatServer server) {
		this.server = server;
	}
	
	@Override
	public void messageReceived(Session source, String message) throws ServerException {
		for (Session session : server.getSessions()) {
			if (!source.equals(session)) {
				try {
					session.getConnection().getWriter().write(String.format("%s\n", message));
					session.getConnection().getWriter().flush();
				} catch (IOException e) {
					throw new ServerException(e);
				}
			}
		}
	}

}
