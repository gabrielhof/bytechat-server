package br.feevale.bytechat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import br.feevale.bytechat.exception.ServerException;

public interface Connection {

	public BufferedReader getReader();
	
	public BufferedWriter getWriter();
	
	public void close() throws ServerException;
	
	public boolean isClosed();
}
