package br.feevale.bytechat.bootstrap;

import java.io.IOException;

import br.feevale.bytechat.config.ChatConfiguration;
import br.feevale.bytechat.server.CliChatServer;

public class DefaultServerBootstrap {

	public static void main(String[] args) throws IOException {
		ChatConfiguration config = new ChatConfiguration();
		config.setPort(8080);
		
		CliChatServer server = new CliChatServer(config);
		server.start();
	}
	
}
