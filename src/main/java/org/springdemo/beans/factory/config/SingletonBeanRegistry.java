package org.springdemo.beans.factory.config;

/**
 * 单例Bean的注册与获取
 */
public interface SingletonBeanRegistry {

	void registerSingleton(String beanName, Object singletonObject);
	
	Object getSingleton(String beanName);
}
