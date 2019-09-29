package org.springdemo.beans.factory.support;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springdemo.beans.BeanDefinition;
import org.springdemo.beans.PropertyValue;
import org.springdemo.beans.SimpleTypeConverter;
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
			//直接从单例的map中查找，查不到就创建一个 然后返回
			Object bean = this.getSingleton(beanID);
			if(bean == null){
				bean = createBean(bd);
				this.registerSingleton(beanID, bean);
			}
			return bean;
		}
		//如果不是单例，直接创建和返回
		return createBean(bd);
	}


	private Object createBean(BeanDefinition bd) {
		//创建实例
		Object bean = instantiateBean(bd);
		//设置属性
		populateBean(bd, bean);

		return bean;

	}

	/**
	 * 通过ClassLoader 反射的方式 实例化实际的bean
	 * 假定这个bean有一个无参的构造函数，才能被newInstance()出来
	 * @param bd
	 * @return
	 */
	private Object instantiateBean(BeanDefinition bd) {
		ClassLoader cl = this.getBeanClassLoader();
		String beanClassName = bd.getBeanClassName();
		try {
			Class<?> clz = cl.loadClass(beanClassName);
			return clz.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
		}
	}

	/**
	 * 加载bean里引用另一个bean
	 * @param bd
	 * @param bean
	 */
	protected void populateBean(BeanDefinition bd, Object bean){
		//获取所有的propertyValues
		List<PropertyValue> pvs = bd.getPropertyValues();

		if (pvs == null || pvs.isEmpty()) {
			return;
		}

		BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);

		SimpleTypeConverter converter = new SimpleTypeConverter();
		try{
			//拿到bean这个类的相关信息
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			//拿到属性的描述器
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

			for (PropertyValue pv : pvs){
				String propertyName = pv.getName();
				Object originalValue = pv.getValue();
				////获取value的实例，Bean(RuntimeBeanReference)或者只是一个value(TypedStringValue)
				Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);

				for (PropertyDescriptor pd : pds) {
					//找到bean对应的propertyName
					if(pd.getName().equals(propertyName)){
						Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
						//反射set值
						pd.getWriteMethod().invoke(bean, convertedValue);
						break;
					}
				}


			}
		}catch(Exception ex){
			throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", ex);
		}
	}
	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}

    public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
	}
}
