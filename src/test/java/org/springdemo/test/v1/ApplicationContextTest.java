package org.springdemo.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.springdemo.context.ApplicationContext;
import org.springdemo.context.support.ClassPathXmlApplicationContext;
import org.springdemo.service.v1.PetStoreService;

public class ApplicationContextTest {

	@Test
	public void testGetBean() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		Assert.assertNotNull(petStore);
	}
    @Test 
	public void testGetBeanFromFileSystemContext(){
	    //注意啊，这里仍然是hardcode了一个本地路径，这是不好的实践!! 如何处理
		/*ApplicationContext ctx = new FileSystemXmlApplicationContext("C:\\Users\\liuxin\\git-springdemo\\src\\test\\resources\\petstore-v1.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		Assert.assertNotNull(petStore);*/
		
	}

}
