package br.feevale.bytechat.server.bind;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.feevale.bytechat.listener.impl.MessageDistributorListener;
import br.feevale.bytechat.server.ChatContext;
import br.feevale.bytechat.util.Session;
import br.feevale.bytechat.util.User;

public class ConnectionBindThread implements Runnable {

	private ChatContext context;
	private Executor executor = Executors.newCachedThreadPool();
	
	public ConnectionBindThread(ChatContext context) {
		this.context = context;
	}
	
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		while (!context.getServerSocket().isClosed()) {
			try {
				Socket socket = context.getServerSocket().accept();
				executor.execute(new BindSession(socket));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	class BindSession implements Runnable {

		private Socket socket;
		
		public BindSession(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				User user = new User();
				user.setName(reader.readLine());
				
				Session session = new Session(user, socket);
				session.addListener(new MessageDistributorListener(context));
				session.start();
				
				context.addSession(session);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}