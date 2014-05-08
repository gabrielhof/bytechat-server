package br.feevale.bytechat.listener;

import br.feevale.bytechat.util.Session;

public interface ServerListener {

	
	public void newSession(Session newSession);
	
}
