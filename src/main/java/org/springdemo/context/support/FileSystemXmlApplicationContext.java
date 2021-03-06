package org.springdemo.context.support;

import org.springdemo.core.io.FileSystemResource;
import org.springdemo.core.io.Resource;

/**
 * 从文件系统读取xml
 */
public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

	public FileSystemXmlApplicationContext(String path) {
		super(path);		
	}

	@Override
	protected Resource getResourceByPath(String path) {
		return new FileSystemResource(path);
	}	

}
