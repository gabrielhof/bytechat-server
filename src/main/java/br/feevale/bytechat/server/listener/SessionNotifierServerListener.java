package br.feevale.bytechat.server.listener;

import java.io.IOException;

import br.feevale.bytechat.server.ChatServer;
import br.feevale.bytechat.server.exception.ServerException;
import br.feevale.bytechat.util.Session;

public class SessionNotifierServerListener implements ServerListener {

	private ChatServer server;
	
	public SessionNotifierServerListener(ChatServer server) {
		this.server = server;
	}
	
	@Override
	public void newSession(Session newSession) throws ServerException {
		notifySessions(newSession, String.format("%s acabou de entrar", newSession.getUser().getName()));
	}

	@Override
	public void endedSession(Session endedSession) throws ServerException {
		notifySessions(endedSession, String.format("%s acabou de sair", endedSession.getUser().getName()));
	}
	
	protected void notifySessions(Session ignoredSession, String message) throws ServerException {
		for (Session session : server.getSessions()) {
			if (!ignoredSession.equals(session)) {
				try {
					session.getConnection().getWriter().write(message);
					session.getConnection().getWriter().flush();
				} catch (IOException e) {
					throw new ServerException(e);
				}
			}
		}	
	}

}
