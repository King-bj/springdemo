package org.springdemo.beans.factory.support;

import java.lang.reflect.Constructor;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springdemo.beans.BeanDefinition;
import org.springdemo.beans.ConstructorArgument;
import org.springdemo.beans.SimpleTypeConverter;
import org.springdemo.beans.factory.BeanCreationException;
import org.springdemo.beans.factory.config.ConfigurableBeanFactory;


/**
 * 选择构造器，校验各个属性是否与构造器一致
 */
public class ConstructorResolver {

	protected final Log logger = LogFactory.getLog(getClass());


	private final ConfigurableBeanFactory beanFactory;



	public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}


	public Object autowireConstructor(final BeanDefinition bd) {

		Constructor<?> constructorToUse = null;
		Object[] argsToUse = null;

		Class<?> beanClass = null;
		try {
			//装载一个beanCLass,可以在beanFactory里缓存每次load的Class提升效率
			beanClass = this.beanFactory.getBeanClassLoader().loadClass(bd.getBeanClassName());

		} catch (ClassNotFoundException e) {
			throw new BeanCreationException( bd.getID(), "Instantiation of bean failed, can't resolve class", e);
		}

		//通过反射
		Constructor<?>[] candidates = beanClass.getConstructors();

		//把ref 变成实际的对象
		BeanDefinitionValueResolver valueResolver =
				new BeanDefinitionValueResolver(this.beanFactory);
		//拿到所有的ConstructorArgument
		ConstructorArgument cargs = bd.getConstructorArgument();
		//转化类型
		SimpleTypeConverter typeConverter = new SimpleTypeConverter();
 		//循环所有构造器
		for(int i=0; i<candidates.length;i++){
			//判断构造器的参数和xml中的数量是否相等
			Class<?> [] parameterTypes = candidates[i].getParameterTypes();
			if(parameterTypes.length != cargs.getArgumentCount()){
				continue;
			}
			argsToUse = new Object[parameterTypes.length];

			//判断值是否和参数能进行匹配
			boolean result = this.valuesMatchTypes(parameterTypes,
					cargs.getArgumentValues(),
					argsToUse,
					valueResolver,
					typeConverter);

			if(result){
				constructorToUse = candidates[i];
				break;
			}

		}


		//找不到一个合适的构造函数
		if(constructorToUse == null){
			throw new BeanCreationException( bd.getID(), "can't find a apporiate constructor");
		}


		try {
			return constructorToUse.newInstance(argsToUse);
		} catch (Exception e) {
			throw new BeanCreationException( bd.getID(), "can't find a create instance using "+constructorToUse);
		}


	}

	private boolean valuesMatchTypes(Class<?> [] parameterTypes,
									 List<ConstructorArgument.ValueHolder> valueHolders,
									 Object[] argsToUse,
									 BeanDefinitionValueResolver valueResolver,
									 SimpleTypeConverter typeConverter ){


		for(int i=0;i<parameterTypes.length;i++){
			ConstructorArgument.ValueHolder valueHolder
					= valueHolders.get(i);
			//获取参数的值，可能是TypedStringValue, 也可能是RuntimeBeanReference
			Object originalValue = valueHolder.getValue();

			try{
				//获得真正的值
				Object resolvedValue = valueResolver.resolveValueIfNecessary( originalValue);
				//如果参数类型是 int, 但是值是字符串,例如"3",还需要转型
				//如果转型失败，则抛出异常。说明这个构造函数不可用
				Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);
				//转型成功，记录下来
				argsToUse[i] = convertedValue;
			}catch(Exception e){
				logger.error(e);
				return false;
			}
		}
		return true;
	}


}
