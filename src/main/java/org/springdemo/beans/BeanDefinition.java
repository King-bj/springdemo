package org.springdemo.beans;

import java.util.List;

public interface BeanDefinition {
	public static final String SCOPE_SINGLETON = "singleton";
	public static final String SCOPE_PROTOTYPE = "prototype";
	public static final String SCOPE_DEFAULT = "";
	
	public boolean isSingleton();
	public boolean isPrototype();
	String getScope();
	void setScope(String scope);
	
	public String getBeanClassName();

	/**
	 * 获取xml里的property定义
	 * @return
	 */
	public List<PropertyValue> getPropertyValues();

	/**
	 * 获取List<ValueHolder>的列表 ValueHolder里的value 就是xml中的constructor-arg对应的内容，有可能是字符串，也有可能是bean
	 * ValueHolder 在我们的实现里只支持了name,type,value，事实上在spring中会有更多的扩展
	 */
	public ConstructorArgument getConstructorArgument();

	public String getID();

	public boolean hasConstructorArgumentValues();
}
