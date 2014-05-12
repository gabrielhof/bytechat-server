package br.feevale.bytechat.server.listener;

import java.io.IOException;

import br.feevale.bytechat.server.ChatContext;
import br.feevale.bytechat.util.Session;

public class MessageDistributorListener implements SessionListener {

	private ChatContext context;
	
	public MessageDistributorListener(ChatContext context) {
		this.context = context;
	}

	public void messageReceived(Session sourceSession, String message) {
		for (Session session : context.getSessions()) {
			if (sourceSession != session) {
				try {
					session.getConnection().getWriter().write(String.format("%s\n", message));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
