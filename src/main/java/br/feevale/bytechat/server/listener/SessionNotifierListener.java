package br.feevale.bytechat.server.listener;

import br.feevale.bytechat.exception.PacketException;
import br.feevale.bytechat.packet.Packet;
import br.feevale.bytechat.packet.impl.Message;
import br.feevale.bytechat.server.ChatServer;
import br.feevale.bytechat.util.Session;

public class SessionNotifierListener implements SessionListener {

	private ChatServer server;
	
	public SessionNotifierListener(ChatServer server) {
		this.server = server;
	}
	
	@Override
	public void packetReceived(Session source, Packet packet) {
		if (packet instanceof Message) {
			Message message = (Message) packet;
			
			for (Session session : server.getSessions()) {
				if (!source.equals(session)) {
					try {
						session.send(message);
					} catch (PacketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}