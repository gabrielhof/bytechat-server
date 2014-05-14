package br.feevale.bytechat.server.connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.feevale.bytechat.config.Configuration;
import br.feevale.bytechat.server.connector.Connection;
import br.feevale.bytechat.server.connector.ServerConnector;
import br.feevale.bytechat.server.exception.ConnectorAlreadyInitializedException;
import br.feevale.bytechat.server.exception.ServerException;

public class ServerSocketConnector implements ServerConnector {

	private ServerSocket serverSocket;
	
	@Override
	public void init(Configuration configuration) throws ServerException {
		try {
			if (serverSocket != null && !serverSocket.isClosed()) {
				throw new ConnectorAlreadyInitializedException(String.format("Connector ja inicializado na porta %d", configuration.getPort()));
			}
			
			serverSocket = new ServerSocket(configuration.getPort());
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}

	@Override
	public Connection accept() throws ServerException {
		try {
			Socket socket = serverSocket.accept();
			return new SocketConnection(socket);
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}
	
	@Override
	public void close() throws ServerException {
		try {
			serverSocket.close();
			serverSocket = null;
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}
	
	@Override
	public boolean isClosed() {
		return serverSocket == null || serverSocket.isClosed();
	}

}
