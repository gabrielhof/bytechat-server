package br.feevale.bytechat.server.bootstrap;

import org.apache.commons.lang.StringUtils;

import br.feevale.bytechat.config.Configuration;
import br.feevale.bytechat.exception.ServerException;
import br.feevale.bytechat.server.ChatServer;
import br.feevale.bytechat.server.SimpleChatServer;

public class ConsoleServerBootstrap {

	public static void main(String[] args) throws ServerException {
		String port = System.getenv("PORT");
		
		Configuration config = new Configuration();
		config.setPort(StringUtils.isBlank(port) ? 8080 : Integer.parseInt(port));
		
		ChatServer server = new SimpleChatServer(config);
		server.start();
	}
	
}
