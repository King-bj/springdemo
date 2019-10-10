package org.springdemo.test.v4;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.springdemo.core.annotation.AnnotationAttributes;
import org.springdemo.core.io.ClassPathResource;
import org.springdemo.core.type.AnnotationMetadata;
import org.springdemo.core.type.classreading.MetadataReader;
import org.springdemo.core.type.classreading.SimpleMetadataReader;
import org.springdemo.stereotype.Component;

public class MetadataReaderTest {
	@Test
	public void testGetMetadata() throws IOException{
		ClassPathResource resource = new ClassPathResource("org/springdemo/service/v4/PetStoreService.class");
		
		MetadataReader reader = new SimpleMetadataReader(resource);
		//注意：不需要单独使用ClassMetadata
		//ClassMetadata clzMetadata = reader.getClassMetadata();
		AnnotationMetadata amd = reader.getAnnotationMetadata();
		
		String annotation = Component.class.getName();
		
		Assert.assertTrue(amd.hasAnnotation(annotation));		
		AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);		
		Assert.assertEquals("petStore", attributes.get("value"));
		
		//注：下面对class metadata的测试并不充分
		Assert.assertFalse(amd.isAbstract());		
		Assert.assertFalse(amd.isFinal());
		Assert.assertEquals("org.springdemo.service.v4.PetStoreService", amd.getClassName());
		
	}
}
