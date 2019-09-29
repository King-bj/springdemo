package org.springdemo.test.v2;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springdemo.context.ApplicationContext;
import org.springdemo.context.support.ClassPathXmlApplicationContext;
import org.springdemo.dao.v2.AccountDao;
import org.springdemo.dao.v2.ItemDao;
import org.springdemo.service.v2.PetStoreService;

public class ApplicationContextTestV2 {

	@Test
	public void testGetBeanProperty() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		
		assertNotNull(petStore.getAccountDao());
		assertNotNull(petStore.getItemDao());
		assertNotNull(petStore.getAccountDao().getItemDao());
		assertTrue(petStore.getAccountDao() instanceof AccountDao);
		assertTrue(petStore.getItemDao() instanceof ItemDao);
		
		assertEquals("lajin",petStore.getOwner());
		assertEquals(2, petStore.getVersion()); 
		
	}

}
