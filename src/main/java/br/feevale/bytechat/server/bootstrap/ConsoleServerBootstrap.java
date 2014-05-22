package br.feevale.bytechat.server.bootstrap;

import br.feevale.bytechat.config.Configuration;
import br.feevale.bytechat.exception.ServerException;
import br.feevale.bytechat.server.ChatServer;
import br.feevale.bytechat.server.SimpleChatServer;

public class ConsoleServerBootstrap {

	public static void main(String[] args) throws ServerException {
		Configuration config = Configuration.fromProperties();
		
		ChatServer server = new SimpleChatServer(config);
		server.start();
	}
	
}
