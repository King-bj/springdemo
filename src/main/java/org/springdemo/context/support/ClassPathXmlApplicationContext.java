package org.springdemo.context.support;

import org.springdemo.core.io.ClassPathResource;
import org.springdemo.core.io.Resource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

	public ClassPathXmlApplicationContext(String configFile) {
		super(configFile);
		
	}

	@Override
	protected Resource getResourceByPath(String path) {
		
		return new ClassPathResource(path,this.getBeanClassLoader());
	}

}
