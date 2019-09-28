package org.springdemo.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.springdemo.context.ApplicationContext;
import org.springdemo.context.support.ClassPathXmlApplicationContext;
import org.springdemo.context.support.FileSystemXmlApplicationContext;
import org.springdemo.service.v1.PetStoreService;


/**
 * ApplicationContext 、
 * 用户直接通过 ApplicationContext 获取 xml和getBean
 * 不需要关心其他的底层实现
 */
public class ApplicationContextTest {

	@Test
	public void testGetBean() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		Assert.assertNotNull(petStore);
	}



    @Test 
	public void testGetBeanFromFileSystemContext(){
		//相对路径
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src\\test\\resources\\petstore-v1.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		Assert.assertNotNull(petStore);
		
	}

}
