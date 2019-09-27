package org.springdemo.beans.factory.support;

import org.springdemo.beans.BeanDefinition;

/**
 * 获取和注册BeanDefinition
 */
public interface BeanDefinitionRegistry {
	BeanDefinition getBeanDefinition(String beanID);
	void registerBeanDefinition(String beanID, BeanDefinition bd);
}
