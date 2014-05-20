package br.feevale.bytechat.server.listener;

import java.util.ArrayList;
import java.util.List;

import br.feevale.bytechat.exception.PacketException;
import br.feevale.bytechat.exception.ServerException;
import br.feevale.bytechat.listener.AbstractSessionListener;
import br.feevale.bytechat.packet.File;
import br.feevale.bytechat.packet.Message;
import br.feevale.bytechat.packet.Unbind;
import br.feevale.bytechat.packet.UserList;
import br.feevale.bytechat.server.ChatServer;
import br.feevale.bytechat.util.Session;
import br.feevale.bytechat.util.User;

public class SessionNotifierListener extends AbstractSessionListener {

	private ChatServer server;
	
	public SessionNotifierListener(ChatServer server) {
		this.server = server;
	}
	
	@Override
	public void messageReceived(Session source, Message message) {
		for (Session session : server.getSessions()) {
			if (!source.equals(session) && (!message.isPrivate() || message.getRecipients().contains(session.getUser()))) {
				try {
					session.send(message);
				} catch (PacketException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void userListReceived(Session source, UserList userList) {
		List<User> users = new ArrayList<User>();
		for (Session session : server.getSessions()) {
			if (!source.getUser().equals(session.getUser())) {
				users.add(session.getUser());
			}
		}
		
		userList.setUsers(users);
		
		try {
			source.send(userList);
		} catch (PacketException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void fileReceived(Session source, File file) {
		for (Session session : server.getSessions()) {
			try {
				if (!source.equals(session)) {
					session.send(file);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void unbindReceived(Session source, Unbind unbind) {
		source.stop();
	}
	
	@Override
	public void sessionEnded(Session session) {
		try {
			server.removeSession(session);
		} catch (ServerException e) {
			throw new RuntimeException(e);
		}
	}
	
}