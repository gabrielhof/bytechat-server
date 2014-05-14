package br.feevale.bytechat.server.factory;

import br.feevale.bytechat.config.Configuration;
import br.feevale.bytechat.server.connector.ServerConnector;
import br.feevale.bytechat.server.connector.ServerSocketConnector;
import br.feevale.bytechat.server.exception.ServerException;
import br.feevale.bytechat.server.factory.ServerConnectorFactory;

public class ServerSocketConnectorFactory implements ServerConnectorFactory {

	@Override
	public ServerConnector create(Configuration configuration) throws ServerException {
		ServerConnector connector = new ServerSocketConnector();
		connector.init(configuration);
		
		return connector;
	}

}
