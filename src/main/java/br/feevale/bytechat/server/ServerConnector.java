package br.feevale.bytechat.server;

import br.feevale.bytechat.config.Configuration;
import br.feevale.bytechat.exception.ServerException;

public interface ServerConnector {

	public void init(Configuration configuration) throws ServerException;
	
	public Connection accept() throws ServerException;
	
	public void destroy() throws ServerException;
	
	public boolean isClosed();
	
}
