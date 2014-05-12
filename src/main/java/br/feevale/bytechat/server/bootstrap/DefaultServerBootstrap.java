package br.feevale.bytechat.server.bootstrap;

import br.feevale.bytechat.config.Configuration;
import br.feevale.bytechat.exception.ServerException;
import br.feevale.bytechat.server.impl.CommandLineChatServer;

public class DefaultServerBootstrap {

	public static void main(String[] args) throws ServerException {
		Configuration config = new Configuration();
		config.setPort(8080);
		
		CommandLineChatServer server = new CommandLineChatServer(config);
		server.start();
	}
	
}
