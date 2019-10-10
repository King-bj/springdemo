package org.springdemo.core.type;

import java.util.Set;

import org.springdemo.core.annotation.AnnotationAttributes;

public interface AnnotationMetadata extends ClassMetadata{
	
	Set<String> getAnnotationTypes();


	boolean hasAnnotation(String annotationType);
	
	public AnnotationAttributes getAnnotationAttributes(String annotationType);
}
