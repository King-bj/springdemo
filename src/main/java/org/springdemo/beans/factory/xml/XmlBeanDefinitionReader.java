package org.springdemo.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springdemo.beans.BeanDefinition;
import org.springdemo.beans.ConstructorArgument;
import org.springdemo.beans.PropertyValue;
import org.springdemo.beans.factory.BeanDefinitionStoreException;
import org.springdemo.beans.factory.config.RuntimeBeanReference;
import org.springdemo.beans.factory.config.TypedStringValue;
import org.springdemo.beans.factory.support.BeanDefinitionRegistry;
import org.springdemo.beans.factory.support.GenericBeanDefinition;
import org.springdemo.core.io.Resource;
import org.springdemo.util.StringUtils;

public class XmlBeanDefinitionReader {

	public static final String ID_ATTRIBUTE = "id";

	public static final String CLASS_ATTRIBUTE = "class";

	public static final String SCOPE_ATTRIBUTE = "scope";

	public static final String PROPERTY_ELEMENT = "property";

	public static final String REF_ATTRIBUTE = "ref";

	public static final String VALUE_ATTRIBUTE = "value";

	public static final String NAME_ATTRIBUTE = "name";

	public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";

	public static final String TYPE_ATTRIBUTE = "type";

	BeanDefinitionRegistry registry;

	protected final Log logger = LogFactory.getLog(getClass());

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
				//解析ConstructorArgElements   也就是：constructor-arg标签
				parseConstructorArgElements(ele,bd);
				//解析PropertyElement  也就是property标签
				parsePropertyElement(ele,bd);
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

	public void parsePropertyElement(Element beanElem, BeanDefinition bd) {
		Iterator iter= beanElem.elementIterator(PROPERTY_ELEMENT);
		while(iter.hasNext()){
			Element propElem = (Element)iter.next();
			String propertyName = propElem.attributeValue(NAME_ATTRIBUTE);
			if (!StringUtils.hasLength(propertyName)) {
				logger.fatal("Tag 'property' must have a 'name' attribute");
				return;
			}
			Object val = parsePropertyValue(propElem, bd, propertyName);
			PropertyValue pv = new PropertyValue(propertyName, val);

			bd.getPropertyValues().add(pv);
		}

	}

	/**
	 * 解析ConstructorArgElements
	 * @param beanEle
	 * @param bd
	 */
	public void parseConstructorArgElements(Element beanEle, BeanDefinition bd) {
		Iterator iter = beanEle.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
		while(iter.hasNext()){
			Element ele = (Element)iter.next();
			parseConstructorArgElement(ele, bd);
		}

	}

	/**
	 * 解析单条的ConstructorArgElements
	 * @param ele
	 * @param bd
	 */
	public void parseConstructorArgElement(Element ele, BeanDefinition bd) {
		//目前只实现了value的解析
		String typeAttr = ele.attributeValue(TYPE_ATTRIBUTE);
		String nameAttr = ele.attributeValue(NAME_ATTRIBUTE);
		Object value = parsePropertyValue(ele, bd, null);
		String name = (String)parsePropertyOther(ele,bd,NAME_ATTRIBUTE);
		String type = (String)parsePropertyOther(ele,bd,TYPE_ATTRIBUTE);
		ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value,name,type);
		if (StringUtils.hasLength(typeAttr)) {
			valueHolder.setType(typeAttr);
		}
		if (StringUtils.hasLength(nameAttr)) {
			valueHolder.setName(nameAttr);
		}

		bd.getConstructorArgument().addArgumentValue(valueHolder);
	}

	/**
	 * 解析value属性
	 * @param ele
	 * @param bd
	 * @param propertyName
	 * @return
	 */
	public Object parsePropertyValue(Element ele, BeanDefinition bd, String propertyName) {
		String elementName = (propertyName != null) ?
				"<property> element for property '" + propertyName + "'" :
				"<constructor-arg> element";


		boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE)!=null);
		boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) !=null);


		if (hasRefAttribute) {
			String refName = ele.attributeValue(REF_ATTRIBUTE);
			if (!StringUtils.hasText(refName)) {
				logger.error(elementName + " contains empty 'ref' attribute");
			}
			RuntimeBeanReference ref = new RuntimeBeanReference(refName);
			return ref;
		}else if (hasValueAttribute) {
			TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));

			return valueHolder;
		}
		else {

			throw new RuntimeException(elementName + " must specify a ref or value");
		}
	}

	/**
	 * 解析其他属性
	 * @param ele
	 * @param bd
	 * @param propertyName
	 * @return
	 */
	public Object parsePropertyOther(Element ele, BeanDefinition bd, String propertyName) {
		String elementName = (propertyName != null) ?
				"<property> element for property '" + propertyName + "'" :
				"<constructor-arg> element";
		if(NAME_ATTRIBUTE.equals(propertyName)){
			boolean hasNameAttribute = (ele.attribute(NAME_ATTRIBUTE) !=null);
			if(hasNameAttribute){
				String name = ele.attributeValue(NAME_ATTRIBUTE);
				if (!StringUtils.hasText(name)) {
					logger.error(elementName + " contains empty 'name' attribute");
				}
				TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(NAME_ATTRIBUTE));
				return valueHolder;

			}
		}else if(TYPE_ATTRIBUTE.equals(propertyName)){
			boolean hasTypeAttribute = (ele.attribute(TYPE_ATTRIBUTE) !=null);
			if(hasTypeAttribute){
				String typeName = ele.attributeValue(TYPE_ATTRIBUTE);
				if (!StringUtils.hasText(typeName)) {
					logger.error(elementName + " contains empty 'type' attribute");
				}
				TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(TYPE_ATTRIBUTE));
				return valueHolder;
			}
		}

		return null;

	}
}

