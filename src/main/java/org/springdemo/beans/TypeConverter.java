package org.springdemo.beans;

public interface TypeConverter {

	/**
	 * 类型转换，根据requiredType转换value
	 * @param value
	 * @param requiredType 想要的类型
	 * @param <T>
	 * @return
	 * @throws TypeMismatchException
	 */
	<T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;


}
