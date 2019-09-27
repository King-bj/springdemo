package org.springdemo.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springdemo.beans.BeanDefinition;
import org.springdemo.beans.factory.BeanDefinitionStoreException;
import org.springdemo.beans.factory.support.BeanDefinitionRegistry;
import org.springdemo.beans.factory.support.GenericBeanDefinition;
import org.springdemo.core.io.Resource;

public class XmlBeanDefinitionReader {
	
	public static final String ID_ATTRIBUTE = "id";	

	public static final String CLASS_ATTRIBUTE = "class";
	
	public static final String SCOPE_ATTRIBUTE = "scope";
	
	BeanDefinitionRegistry registry;
	
	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
		this.registry = registry;
	}
	public void loadBeanDefinitions(Resource resource){
		InputStream is = null;
		try{			
			is = resource.getInputStream();

			//dom4j类库 解析xml变为一个document
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			
			Element root = doc.getRootElement(); //<beans>
			Iterator<Element> iter = root.elementIterator();
			while(iter.hasNext()){
				Element ele = (Element)iter.next();
				String id = ele.attributeValue(ID_ATTRIBUTE);
				String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
				//通过id和name构造一个bean的对象。
				BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
				if (ele.attribute(SCOPE_ATTRIBUTE)!=null) {					
					bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));					
				}
				//把bean对象存到map里
				this.registry.registerBeanDefinition(id, bd);
			}
		} catch (Exception e) {		
			throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(),e);
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
		}
		
	}
}

