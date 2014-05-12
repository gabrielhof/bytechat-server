package br.feevale.bytechat.server;

import br.feevale.bytechat.config.Configuration;
import br.feevale.bytechat.server.exception.ServerException;

public class ServerSocketConnectorFactory implements ServerConnectorFactory {

	public ServerConnector create(Configuration configuration) throws ServerException {
		ServerConnector connector = new ServerSocketConnector();
		connector.init(configuration);
		
		return connector;
	}

}
