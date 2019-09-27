package org.springdemo.test.v1;

import static org.junit.Assert.*;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springdemo.beans.BeanDefinition;
import org.springdemo.beans.factory.BeanCreationException;
import org.springdemo.beans.factory.BeanDefinitionStoreException;
import org.springdemo.beans.factory.support.DefaultBeanFactory;
import org.springdemo.beans.factory.xml.XmlBeanDefinitionReader;
import org.springdemo.core.io.ClassPathResource;
import org.springdemo.service.v1.PetStoreService;

public class BeanFactoryTest {
	DefaultBeanFactory factory = null;
	XmlBeanDefinitionReader reader = null;
	
	@Before
	public void setUp(){
		factory = new DefaultBeanFactory();
		reader = new XmlBeanDefinitionReader(factory);
		
	}

	/**
	 * 从XML获取Bean
	 */
	@Test
	public void testGetBean() {
		//加载xml并把beanz存到 BeanFactory中去
		reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
		
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		
		assertTrue(bd.isSingleton());
		
		assertFalse(bd.isPrototype());
		
		assertEquals(BeanDefinition.SCOPE_DEFAULT,bd.getScope());
		
		assertEquals("org.springdemo.service.v1.PetStoreService",bd.getBeanClassName());
		
		PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
		
		assertNotNull(petStore);
		
		PetStoreService petStore1 = (PetStoreService)factory.getBean("petStore");
		
		assertTrue(petStore.equals(petStore1));
	}
	
	@Test
	public void testInvalidBean(){

		reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
		try{
			factory.getBean("invalidBean");
		}catch(BeanCreationException e){
			return;
		}
		Assert.fail("expect BeanCreationException ");
	}
	@Test
	public void testInvalidXML(){
		
		try{
			reader.loadBeanDefinitions(new ClassPathResource("xxxx.xml"));
		}catch(BeanDefinitionStoreException e){
			return;
		}
		Assert.fail("expect BeanDefinitionStoreException ");
	}

}
