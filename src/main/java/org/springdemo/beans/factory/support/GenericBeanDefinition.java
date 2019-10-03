package org.springdemo.beans.factory.support;

import org.springdemo.beans.BeanDefinition;
import org.springdemo.beans.ConstructorArgument;
import org.springdemo.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

public class GenericBeanDefinition implements BeanDefinition {
	private String id;
	private String beanClassName;
	private boolean singleton = true;
	private boolean prototype = false;
	private String scope = SCOPE_DEFAULT;
	//保存xml里所有的property定义
	List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
	private ConstructorArgument constructorArgument = new ConstructorArgument();

	public GenericBeanDefinition(String id, String beanClassName) {
		
		this.id = id;
		this.beanClassName = beanClassName;
	}
	public String getBeanClassName() {
		
		return this.beanClassName;
	}

	public List<PropertyValue> getPropertyValues() {
		return this.propertyValues;
	}

	public ConstructorArgument getConstructorArgument() {
		return this.constructorArgument;
	}

	public boolean isSingleton() {
		return this.singleton;
	}
	public boolean isPrototype() {
		return this.prototype;
	}
	public String getScope() {
		return this.scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
		this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
		this.prototype = SCOPE_PROTOTYPE.equals(scope);
		
	}

	public String getID() {
		return this.id;
	}

	public boolean hasConstructorArgumentValues() {
		return !this.constructorArgument.isEmpty();
	}
}
