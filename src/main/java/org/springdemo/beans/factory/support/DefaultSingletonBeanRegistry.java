package org.springdemo.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springdemo.beans.factory.config.SingletonBeanRegistry;
import org.springdemo.util.Assert;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
	
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);

	/**
	 * 单例bean的注册 如果已有抛出异常
	 * @param beanName
	 * @param singletonObject
	 */
	public void registerSingleton(String beanName, Object singletonObject) {
		
		Assert.notNull(beanName, "'beanName' must not be null");
		
		Object oldObject = this.singletonObjects.get(beanName);
		if (oldObject != null) {
			throw new IllegalStateException("Could not register object [" + singletonObject +
					"] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
		}
		this.singletonObjects.put(beanName, singletonObject);
		
	}

	public Object getSingleton(String beanName) {
		
		return this.singletonObjects.get(beanName);
	}

}
