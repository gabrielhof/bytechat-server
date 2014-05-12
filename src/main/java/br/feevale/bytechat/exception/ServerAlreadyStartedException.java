package br.feevale.bytechat.exception;

public class ServerAlreadyStartedException extends ServerException {

	private static final long serialVersionUID = -6216637094176498708L;

	public ServerAlreadyStartedException(String message) {
		super(message);
	}
}
