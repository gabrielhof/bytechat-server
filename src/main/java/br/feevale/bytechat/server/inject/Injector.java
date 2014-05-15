package br.feevale.bytechat.server.inject;

public abstract class Injector {
	
	private static Injector INSTANCE;
	
	public abstract void init();
	
	public abstract void inject(Object instance);
	
	public abstract Object getBean(String beanName);
	
	public abstract Object getBean(String beanName, Object... args);
	
	public abstract <T> T getBean(String beanName, Class<T> type);
	
	public abstract <T> T getBean(Class<T> type);
	
	public static Injector getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SpringInjector();
			INSTANCE.init();
		}
		
		return INSTANCE;
	}

}
