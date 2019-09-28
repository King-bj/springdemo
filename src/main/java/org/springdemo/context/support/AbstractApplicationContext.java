package org.springdemo.context.support;

import org.springdemo.beans.factory.support.DefaultBeanFactory;
import org.springdemo.beans.factory.xml.XmlBeanDefinitionReader;
import org.springdemo.context.ApplicationContext;
import org.springdemo.core.io.Resource;
import org.springdemo.util.ClassUtils;

/**
 * 抽象出来getResourceByPath
 * ClassPathXml和FileSystemXML的区别在于获取Resource方法不同
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

	//持有一个DefaultBeanFactory
	private DefaultBeanFactory factory = null;
	private ClassLoader beanClassLoader;

//	/**
//	 * 加载xml
//	 * @param configFile
//	 */
//	public AbstractApplicationContext(String configFile){
//		factory = new DefaultBeanFactory();
//		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
//		Resource resource = this.getResourceByPath(configFile);
//		reader.loadBeanDefinitions(resource);
//		//setClassLoader有问题 目前全都是创建空的
//		factory.setBeanClassLoader(this.getBeanClassLoader());
//	}


	/**
	 * 加载xml
	 * @param configFile
	 */
	public AbstractApplicationContext(String configFile){
		 this(configFile,ClassUtils.getDefaultClassLoader());
	}
	/**
	 * 加载xml
	 * @param configFile
	 */
	public AbstractApplicationContext(String configFile, ClassLoader cl){
		factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = this.getResourceByPath(configFile);
		reader.loadBeanDefinitions(resource);
		//setClassLoader有问题 目前全都是创建空的
		factory.setBeanClassLoader(cl);
	}


	/**
	 * 获取bean
	 * @param beanID
	 * @return
	 */
	public Object getBean(String beanID) {
		
		return factory.getBean(beanID);
	}
	
	protected abstract Resource getResourceByPath(String path);
	
	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}

    public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
	}

}
