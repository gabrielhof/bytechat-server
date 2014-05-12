package br.feevale.bytechat.server.impl;

import br.feevale.bytechat.config.Configuration;
import br.feevale.bytechat.exception.ServerException;
import br.feevale.bytechat.server.ServerConnector;
import br.feevale.bytechat.server.ServerConnectorFactory;

public class ServerSocketConnectorFactory implements ServerConnectorFactory {

	public ServerConnector create(Configuration configuration) throws ServerException {
		ServerConnector connector = new ServerSocketConnector();
		connector.init(configuration);
		
		return connector;
	}

}
