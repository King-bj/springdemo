package org.springdemo.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * FileSystemResource
 * ClassPathResource 只有读取xml的部分不同
 * 提取出来相同的getInputStream
 */
public interface Resource {
	public InputStream getInputStream() throws IOException;
	public String getDescription();
}
