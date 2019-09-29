package org.springdemo.beans.factory.support;

import org.springdemo.beans.factory.config.RuntimeBeanReference;
import org.springdemo.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {
	private final DefaultBeanFactory beanFactory;
	
	public BeanDefinitionValueResolver(
			DefaultBeanFactory beanFactory) {

		this.beanFactory = beanFactory;
	}
	
	public Object resolveValueIfNecessary(Object value) {
		//判断value是否是一个RuntimeBeanReference也就是Bean,如果是就从beanFactory获取Bean
		if (value instanceof RuntimeBeanReference) {
			RuntimeBeanReference ref = (RuntimeBeanReference) value;			
			String refName = ref.getBeanName();			
			Object bean = this.beanFactory.getBean(refName);				
			return bean;
			
		}else if (value instanceof TypedStringValue) {
			return ((TypedStringValue) value).getValue();
		} else{
			//TODO
			throw new RuntimeException("the value " + value +" has not implemented");
		}		
	}
}
