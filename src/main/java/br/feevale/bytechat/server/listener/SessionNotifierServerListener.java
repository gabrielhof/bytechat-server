package br.feevale.bytechat.server.listener;

import br.feevale.bytechat.builder.BindBuilder;
import br.feevale.bytechat.builder.UnbindBuilder;
import br.feevale.bytechat.exception.PacketException;
import br.feevale.bytechat.exception.ServerException;
import br.feevale.bytechat.packet.Packet;
import br.feevale.bytechat.server.ChatServer;
import br.feevale.bytechat.util.Session;

public class SessionNotifierServerListener implements ServerListener {

	private ChatServer server;
	
	public SessionNotifierServerListener(ChatServer server) {
		this.server = server;
	}
	
	@Override
	public void newSession(Session newSession) throws ServerException {
		notifySessions(newSession, BindBuilder.create().user(newSession.getUser()).getBind());
	}

	@Override
	public void endedSession(Session endedSession) throws ServerException {
		notifySessions(endedSession, UnbindBuilder.create().user(endedSession.getUser()).getUnbind());
	}
	
	protected void notifySessions(Session ignoredSession, Packet packet) throws ServerException {
		for (Session session : server.getSessions()) {
			if (!ignoredSession.equals(session)) {
				try {
					session.send(packet);
				} catch (PacketException e) {
					throw new ServerException(e);
				}
			}
		}	
	}

}
