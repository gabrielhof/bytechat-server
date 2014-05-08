package br.feevale.bytechat.listener;

import br.feevale.bytechat.util.Session;

public interface SessionListener {
	
	public void messageReceived(Session session, String message);

}
