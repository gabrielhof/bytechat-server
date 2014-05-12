package br.feevale.bytechat.exception;


public class ConnectorAlreadyInitializedException extends ServerException {

	private static final long serialVersionUID = -6290908273159918701L;

	public ConnectorAlreadyInitializedException(String message) {
		super(message);
	}
	
}
