package org.springdemo.test.v1;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.springdemo.core.io.ClassPathResource;
import org.springdemo.core.io.FileSystemResource;
import org.springdemo.core.io.Resource;

public class ResourceTest {

	@Test
	public void testClassPathResource() throws Exception {

		Resource r = new ClassPathResource("petstore-v1.xml");

		InputStream is = null;

		try {
			is = r.getInputStream();
			// 注意：这个测试其实并不充分！！
			Assert.assertNotNull(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}

	}

	@Test
	public void testFileSystemResource() throws Exception {
		//System.out.println(System.getProperty("user.dir"));//获取当前目录
		//相对路径
		Resource r = new FileSystemResource("src\\test\\resources\\petstore-v1.xml");

		InputStream is = null;

		try {
			is = r.getInputStream();
			// 注意：这个测试其实并不充分！！
			Assert.assertNotNull(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}

	}

}
