package br.feevale.bytechat.exception;

public class ServerAlreadyStartedException extends RuntimeException {


	private static final long serialVersionUID = -8423607254554024037L;

	public ServerAlreadyStartedException(String message) {
		super(message);
	}
}
