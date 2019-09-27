package org.springdemo.beans.factory.config;

import org.springdemo.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory {	
	void setBeanClassLoader(ClassLoader beanClassLoader);
	ClassLoader getBeanClassLoader();	
}
