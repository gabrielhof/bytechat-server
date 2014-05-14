package br.feevale.bytechat.server.bootstrap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.feevale.bytechat.config.Configuration;
import br.feevale.bytechat.server.ChatServer;
import br.feevale.bytechat.server.exception.ServerException;

public class DefaultServerBootstrap {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws ServerException {
		Configuration config = new Configuration();
		config.setPort(8080);
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
		
		ChatServer server = (ChatServer) context.getBean("commandLineChatServer");
		server.setConfiguration(config);
		server.start();
	}
	
}
