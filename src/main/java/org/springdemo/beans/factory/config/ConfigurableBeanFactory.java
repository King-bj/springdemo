package org.springdemo.beans.factory.config;

import org.springdemo.beans.factory.BeanFactory;

/**
 * ClassLoader的引入和使用
 */
public interface ConfigurableBeanFactory extends BeanFactory {	
	void setBeanClassLoader(ClassLoader beanClassLoader);
	ClassLoader getBeanClassLoader();	
}
