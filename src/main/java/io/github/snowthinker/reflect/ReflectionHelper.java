package io.github.snowthinker.reflect;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Andrew
 * <p> Reflection Helper</p>
 * 
 */
public class ReflectionHelper {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ReflectionHelper.class);

	/**
	 * <p>Load Bean propertyDescriptors</p>
	 * @param class_ target class
	 * @return PropertyDescriptor[]
	 * @throws IntrospectionException
	 */
	public static PropertyDescriptor[] getPropertyDescriptors(Class<? extends Object> class_) {
		Field[] fields = class_.getDeclaredFields();
		PropertyDescriptor[] pds = new PropertyDescriptor[fields.length];
		
		for(int i =0 ; i<fields.length; i++) {
			PropertyDescriptor pd = null;
			try {
				pd = new PropertyDescriptor(fields[i].getName(), class_);
			} catch (IntrospectionException e) {
				logger.error("Construct PropertyDescriptor error: {}", fields[i], e);
			}
			pds[i] = pd;
		}
		return pds;
	}

	/**
	 * <p>Get Bean PropertyDescriptor by fieldName
	 * @param class_ Target class
	 * @param fieldName Property name
	 * @return PropertyDescriptor
	 */
	public static PropertyDescriptor getPropertyDescriptor(Class<? extends Object> class_, String fieldName) {
		Field[] fields = class_.getDeclaredFields();
		PropertyDescriptor pd = null;
		
		for(Field filed : fields) {
			
			if(filed.getName().equalsIgnoreCase(fieldName)) {
				
				try {
					pd = new PropertyDescriptor(fieldName, class_);
				} catch (IntrospectionException e) {
					logger.error("Get PropertyDescriptor error: {}", fieldName, e);
				}
				
				break;
			}
		}
		
		return pd;
	}
	
	/**
	 * <p>动态调用</p>
	 * @param source source object
	 * @param target target object
	 * @param propDescList PropertyDescriptor list
	 */
	public static void invoke(Object source, Object target, List<PropertyDescriptor> propDescList) {
		if(null==source || null==target || null==propDescList){
			throw new IllegalArgumentException("null parameter");
		}
		
		for(PropertyDescriptor propDesc : propDescList){
			String fieldName = propDesc.getName();
			Object fieldValue = null;
			try {
				propDesc.getReadMethod().setAccessible(true);
				fieldValue = propDesc.getReadMethod().invoke(source, new Object());
				PropertyDescriptor targetPropDesc = getPropertyDescriptor(target.getClass(), fieldName);
				
				if(null!=targetPropDesc){
					targetPropDesc.getWriteMethod().invoke(target, fieldValue);
				}
			} catch (Exception e) {
				logger.error("Invoking error", e);
			} 
		}
		
	}

	/**
	 * Get not null PropertyDescriptor list from given object
	 * @param obj target object
	 * @return List
	 */
	public static List<PropertyDescriptor> getNotNullPropertyDescriptor(Object obj) {
		if(null==obj){
			throw new IllegalArgumentException("obj could not be null");
		}
		
		List<PropertyDescriptor> propDescList = new ArrayList<PropertyDescriptor>();
		
		PropertyDescriptor[] propDescs = ReflectionHelper.getPropertyDescriptors(obj.getClass());
		for(PropertyDescriptor propDesc : propDescs){
			String name = propDesc.getName();
			if(null!=name && name.equals("class")){
				continue;
			}
			
			Method readMethod = propDesc.getReadMethod();
			try {
				readMethod.setAccessible(true);
				Object filedValue = readMethod.invoke(obj, new Object());
				if(null!=filedValue){
					propDescList.add(propDesc);
				}
			} catch (Exception e) {
				logger.error("Invoking error", e);
			} 
		}
		
		return propDescList;
	}
}
