package org.springdemo.beans.factory;


/**
 * BeanFactory实际上是一个内部的概念
 *  所以把原来在BeanFactory 里的
 *  getBeanDefinition 和  registerBeanDefinition
 *  暴露到一个新的接口BeanDefinitionRegistry中去
 *   好处是，XmlBeanDefinitionReader只知道如何 获取和注册BeanDefinition
 *
 *
 */
public interface BeanFactory {

	Object getBean(String beanID);

}
