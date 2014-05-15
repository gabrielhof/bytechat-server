package br.feevale.bytechat.server.inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringInjector extends Injector {

	private ApplicationContext context;
	
	SpringInjector() {}
	
	@Override
	public void init() {
		if (context == null) {
			context = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
	}
	
	@Override
	public void inject(Object instance) {
		AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
		factory.autowireBean(instance);
	}

	@Override
	public Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	@Override
	public Object getBean(String beanName, Object... args) {
		return context.getBean(beanName, args);
	}
	
	@Override
	public <T> T getBean(String beanName, Class<T> type) {
		return context.getBean(beanName, type);
	}
	
	@Override
	public <T> T getBean(Class<T> type) {
		return context.getBean(type);
	}

}
