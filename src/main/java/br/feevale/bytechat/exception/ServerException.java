package br.feevale.bytechat.exception;

public class ServerException extends Exception {

	private static final long serialVersionUID = 3314640144276378173L;
	
	public ServerException() {
		super();
	}
	
	public ServerException(String message) {
		super(message);
	}
	
	public ServerException(String message, Throwable cause) {
		 super(message, cause);
	}
	
	public ServerException(Throwable cause) {
		super(cause);
	}
}
