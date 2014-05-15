package br.feevale.bytechat.server.bootstrap;

import br.feevale.bytechat.config.Configuration;
import br.feevale.bytechat.exception.ServerException;
import br.feevale.bytechat.server.ChatServer;
import br.feevale.bytechat.server.inject.Injector;

public class DefaultServerBootstrap {

	public static void main(String[] args) throws ServerException {
		Configuration config = new Configuration();
		config.setPort(8080);
		
		ChatServer server = Injector.getInstance().getBean("simpleChatServer", ChatServer.class);
		server.setConfiguration(config);
		server.start();
	}
	
}
