package org.springdemo.test.v2;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springdemo.beans.BeanDefinition;
import org.springdemo.beans.PropertyValue;
import org.springdemo.beans.factory.config.RuntimeBeanReference;
import org.springdemo.beans.factory.support.DefaultBeanFactory;
import org.springdemo.beans.factory.xml.XmlBeanDefinitionReader;
import org.springdemo.core.io.ClassPathResource;

public class BeanDefinitionTestV2 {

	
	@Test
	public void testGetBeanDefinition() {
		
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		
		reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));
		
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		
		List<PropertyValue> pvs = bd.getPropertyValues();
		
		Assert.assertTrue(pvs.size() == 4);
		{
			PropertyValue pv = this.getPropertyValue("accountDao", pvs);
			
			Assert.assertNotNull(pv);
			
			Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
		}
		
		{
			PropertyValue pv = this.getPropertyValue("itemDao", pvs);
			
			Assert.assertNotNull(pv);
			
			Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
		}
		
	}
	
	private PropertyValue getPropertyValue(String name,List<PropertyValue> pvs){
		for(PropertyValue pv : pvs){
			if(pv.getName().equals(name)){
				return pv;
			}
		}
		return null;
	}

}
