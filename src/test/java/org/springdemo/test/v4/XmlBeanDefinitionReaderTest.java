package org.springdemo.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.springdemo.beans.BeanDefinition;
import org.springdemo.beans.factory.support.DefaultBeanFactory;
import org.springdemo.beans.factory.xml.XmlBeanDefinitionReader;
import org.springdemo.context.annotation.ScannedGenericBeanDefinition;
import org.springdemo.core.annotation.AnnotationAttributes;
import org.springdemo.core.io.ClassPathResource;
import org.springdemo.core.io.Resource;
import org.springdemo.core.type.AnnotationMetadata;
import org.springdemo.stereotype.Component;

public class XmlBeanDefinitionReaderTest {

	@Test
	public void testParseScanedBean(){
		
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource("petstore-v4.xml");
		reader.loadBeanDefinitions(resource);
		String annotation = Component.class.getName();
		
		//下面的代码和ClassPathBeanDefinitionScannerTest重复，该怎么处理？
		{
			BeanDefinition bd = factory.getBeanDefinition("petStore");
			Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
			AnnotationMetadata amd = sbd.getMetadata();
			
			
			Assert.assertTrue(amd.hasAnnotation(annotation));		
			AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);		
			Assert.assertEquals("petStore", attributes.get("value"));
		}
		{
			BeanDefinition bd = factory.getBeanDefinition("accountDao");
			Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;			
			AnnotationMetadata amd = sbd.getMetadata();
			Assert.assertTrue(amd.hasAnnotation(annotation));
		}
		{
			BeanDefinition bd = factory.getBeanDefinition("itemDao");
			Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;			
			AnnotationMetadata amd = sbd.getMetadata();
			Assert.assertTrue(amd.hasAnnotation(annotation));
		}
	}

}
