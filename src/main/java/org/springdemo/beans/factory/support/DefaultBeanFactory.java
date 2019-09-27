package org.springdemo.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springdemo.beans.BeanDefinition;
import org.springdemo.beans.factory.BeanCreationException;
import org.springdemo.beans.factory.config.ConfigurableBeanFactory;
import org.springdemo.util.ClassUtils;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry 
	implements ConfigurableBeanFactory,BeanDefinitionRegistry{

	
	
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
	private ClassLoader beanClassLoader;
	
	public DefaultBeanFactory() {
		
	}

	public void registerBeanDefinition(String beanID,BeanDefinition bd){
		this.beanDefinitionMap.put(beanID, bd);
	}
	public BeanDefinition getBeanDefinition(String beanID) {
			
		return this.beanDefinitionMap.get(beanID);
	}

	/**
	 * 获取bean
	 * @param beanID
	 * @return
	 */
	public Object getBean(String beanID) {
		//从map中通过id直接拿到BeanDefinition
		BeanDefinition bd = this.getBeanDefinition(beanID);
		if(bd == null){
			throw new BeanCreationException("BeanDefinition does not exist");
		}

		//如果是单例模式的Bean 就采用单例的初始化bean方法
		if(bd.isSingleton()){
			Object bean = this.getSingleton(beanID);
			if(bean == null){
				bean = createBean(bd);
				this.registerSingleton(beanID, bean);
			}
			return bean;
		} 
		return createBean(bd);
	}

	/**
	 * 通过ClassLoader 反射的方式 实例化实际的bean
	 * 假定这个bean有一个无参的构造函数，才能被newInstance()出来
	 * @param bd
	 * @return
	 */
	private Object createBean(BeanDefinition bd) {
		ClassLoader cl = this.getBeanClassLoader();
		String beanClassName = bd.getBeanClassName();
		try {
			Class<?> clz = cl.loadClass(beanClassName);
			return clz.newInstance();
		} catch (Exception e) {			
			throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
		}	
	}
	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}

    public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
	}
}
