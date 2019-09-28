package org.springdemo.context;

import org.springdemo.beans.factory.config.ConfigurableBeanFactory;

/**
 * 对外暴露的接口，使用户可以直接通过ApplicationContext实现xml的读取注册和getBean
 */
public interface ApplicationContext extends ConfigurableBeanFactory{

}
