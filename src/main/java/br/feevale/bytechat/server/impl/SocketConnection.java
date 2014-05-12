package br.feevale.bytechat.server.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import br.feevale.bytechat.exception.ServerException;
import br.feevale.bytechat.server.Connection;

public class SocketConnection implements Connection {

	private Socket socket;
	
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public SocketConnection(Socket socket) throws ServerException {
		this.socket = socket;
		
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}

	public BufferedReader getReader() {
		return reader;
	}

	public BufferedWriter getWriter() {
		return writer;
	}

	public void close() throws ServerException {
		try {
			socket.close();
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}

	public boolean isClosed() {
		return socket.isClosed();
	}
}
