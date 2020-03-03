/*
 * @(#)PojoUtils.java 1.0 2013-2-25上午10:06:16
 *
 */
package io.github.snowthinker.model;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.snowthinker.reflect.ReflectionHelper;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description: Bean 工具类，命名PojoUtils, 不易与  Apache BeanUtils 和 Spring BeanUtils 混淆</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-2-25 上午10:06:16 
 * 
 */
public class PojoHelper {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PojoHelper.class);
	
	private static final Pattern humpPattern = Pattern.compile("[A-Z]");

	/**
	 * Convert POJO to DTO
	 * @param <T> parameter and result type
	 * @param pojo The given POJO
	 * @param class_ The given class
	 * @return target class
	 */
	public static <T extends Object> T convertPojo2Dto(Object pojo, Class<T> class_) {
		T dto = null;
		try {
			dto = class_.newInstance();
		} catch (InstantiationException e) {
			logger.info("convertPojo2Dto error", e.getMessage());
		} catch (IllegalAccessException e) {
			logger.info("convertPojo2Dto error", e.getMessage());
		}
		
		copyProperties(pojo, dto, "class");
		
		return dto;
	}
	
	/**
	 * Convert DTO to POJO
	 * @param <T> parameter and result type
	 * @param obj target object
	 * @param class_ target class
	 * @return object result
	 */
	public static <T extends Object> T convertDto2Pojo(Object obj, Class<T> class_) {
		return convertPojo2Dto(obj, class_);
	}
	
	/**
	 * Convert POJO list to DTO list
	 * @param <T> The parameter and result
	 * @param pojoList The result
	 * @param class_ The class
	 * @return Object The result
	 */
	public static <T extends Object> List<T> convertPojoList2DtoList(List<?> pojoList, Class<T> class_) {
		List<T> dtoList = new ArrayList<>();
		for (Object obj : pojoList) {
			T dto = convertPojo2Dto(obj, class_);
			dtoList.add(dto);
		}
		return dtoList;
	}

	/**
	 * <p> Convert POJO to HashMap
	 * @param obj The given object
	 * @return Map The result HashMap
	 */
	public static Map<String, Object> convertPojo2Map(Object obj){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<PropertyDescriptor> propDescList = ReflectionHelper.getNotNullPropertyDescriptor(obj);
		for(PropertyDescriptor propDesc : propDescList) {
			String fieldName = propDesc.getName();
			
			if(fieldName.equalsIgnoreCase("serialVersionUID")) {
				continue;
			}
			
			Object o = null;
			try {
				Method readMethod = propDesc.getReadMethod();
				if(null != readMethod) {
					o = readMethod.invoke(obj, new Object[] {});	
				}
			} catch (Exception e) {
				logger.info("convertPojo2Map error", e.getMessage());
			}
			
			if (o != null) {
				resultMap.put(fieldName, o);
			}
		}
		
		return resultMap;
	}


	/**
	 * <p>比较两个对象是否相同， 并返回当前对象与比较对象差异的字段的PropertyDescriptor
	 * 要调用该方法，您必需用重写PO或VO的hashcode() 方法
	 * </p>
	 * @param original comparer1
	 * @param present comparer2
	 * @return List
	 */
	public static List<PropertyDescriptor> compare(Object original, Object present) {
		if(null==original || null==present){
			throw new IllegalArgumentException("比较对象不能为null");
		}
		
		List<PropertyDescriptor> propDescList = new ArrayList<PropertyDescriptor>();
		
		PropertyDescriptor[] oriPropsDescriptors = ReflectionHelper.getPropertyDescriptors(original.getClass());
		PropertyDescriptor[] prePropsDescriptors = ReflectionHelper.getPropertyDescriptors(present.getClass());
		
		for(PropertyDescriptor oriPropDescriptor : oriPropsDescriptors){
			for(PropertyDescriptor prePropsDescriptor : prePropsDescriptors){
				//oriPropDescriptor.equals(obj)
				//如果两个字段的名称、getter、setter方法相同
				Method oriReadMethod = oriPropDescriptor.getReadMethod();
				Method oriWriteMethod = oriPropDescriptor.getWriteMethod();
				Method preReadMethod = prePropsDescriptor.getReadMethod();
				Method preWriteMethod = prePropsDescriptor.getWriteMethod();
				
				if(oriPropDescriptor.getDisplayName().equals(prePropsDescriptor.getDisplayName()) &&
						compareMethods(oriReadMethod, preReadMethod) &&
						compareMethods(oriWriteMethod, preWriteMethod)){
					
					Object oriValue = null;
					Object preValue = null;
					try {
						oriValue = oriPropDescriptor.getReadMethod().invoke(original, new Object());
						preValue = prePropsDescriptor.getReadMethod().invoke(present, new Object());
					} catch (IllegalAccessException e) {
						logger.info("compare error", e.getMessage());
					} catch (IllegalArgumentException e) {
						logger.info("compare error", e.getMessage());
					} catch (InvocationTargetException e) {
						logger.info("compare error", e.getMessage());
					}
					
					if(null==oriValue && null==preValue){
						continue;
					}else if(null==oriValue && null!=preValue){
						propDescList.add(prePropsDescriptor);
					}else if(null!=oriValue && null==preValue){
						throw new RuntimeException("传入数据有误，不能将值擦除");
					}else if(null!=oriValue && null!=preValue){
						if(!equals(oriValue, preValue)){
							propDescList.add(prePropsDescriptor);
						}
					}
				}
			}
		}
		
		return propDescList;
	}


	/**
	 * <p>判断两个对象是否相等</p>
	 * @param oriValue
	 * @param preValue
	 * @return Boolean
	 */
	private static boolean equals(Object oriValue, Object preValue) {
		if(null==oriValue && null!=preValue){
			return false;
		}
		if(null!=oriValue && null==preValue){
			return false;
		}
		if(null==oriValue && null==preValue){
			return true;
		}
		
		if(oriValue.hashCode()== preValue.hashCode()){
			return true;
		}
		
		return false;
	}


	/**
	 * <p>比较两个方法是否是同一个方法</p>
	 * @param a
	 * @param b
	 * @return Boolean
	 */
    static boolean compareMethods(Method a, Method b) {
        // Note: perhaps this should be a protected method in FeatureDescriptor
        if ((a == null) != (b == null)) {
            return false;
        }

        if (a != null && b != null) {
            if (!a.equals(b)) {
                return false;
            }
        }
        return true;
    }

	/**
	 * <p>Copy property values from given object to target object
	 * @param source The given object
	 * @param target The target object
	 * @param ignoreProperties The properties to ignore
	 */
	public static void copyProperties(Object source, Object target, 
			String... ignoreProperties) {
		PropertyDescriptor[] sourcePdArr = ReflectionHelper.getPropertyDescriptors(source.getClass());
		
		PropertyDescriptor[] targetPdArr = ReflectionHelper.getPropertyDescriptors(target.getClass());
		
		List<String> ignoreList = (null != ignoreProperties ? Arrays.asList(ignoreProperties) : new ArrayList<String>());
		
		for(PropertyDescriptor sourcePd : sourcePdArr) {
		
			if(ignoreList.contains(sourcePd.getName())) {
				continue;
			}
			
			for(PropertyDescriptor targetPd : targetPdArr) {
				if(targetPd.getName().equals(sourcePd.getName())) {
					Object invokedValue;
					try {
						Method readMethod = sourcePd.getReadMethod();
						readMethod.setAccessible(true);
						invokedValue = readMethod.invoke(source, new Object[] {});
						
						Method writeMethod = targetPd.getWriteMethod();
						writeMethod.setAccessible(true);
						writeMethod.invoke(target, invokedValue);
					} catch (Exception e) {
						logger.error("Invoking error", e);
					} 
					
				}
			}
		}
	}
	
	/**
	 * <p>Convert Map to Pojo
	 * @param map The given map parameter
	 * @param clazz The pojo class
	 * @return Object The result
	 */
	
	/**
	 * Convert Map to Pojo
	 * @param <T> The parameter and result type
	 * @param map The parameter
	 * @param clazz The given class
	 * @return Object The result
	 */
	public static <T extends Object> T convertMap2Pojo(Map<String, Object> map, Class<T> clazz) {
		if(null == map || null == clazz) {
			return null;
		}
		
		T instance = null;
		
		try {
			instance = clazz.newInstance();
		} catch (InstantiationException e) {
			logger.error("convertMap2Pojo error",  e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("convertMap2Pojo error",  e.getMessage());
		}
		
		PropertyDescriptor[]propDescArr = ReflectionHelper.getPropertyDescriptors(clazz);
		
		if(null == propDescArr) {
			return null;
		}
		
		for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			String property = iter.next();
			Object value = map.get(property);
			
			if(null != value) {
					
				for(PropertyDescriptor propDesc : propDescArr ) {
					if(propDesc.getName().equalsIgnoreCase(property) && propDesc.getPropertyType() instanceof Object) {
						try {
							propDesc.getWriteMethod().invoke(instance, value);
						} catch (IllegalAccessException e) {
							logger.error("convertMap2Pojo error", e.getMessage());
						} catch (IllegalArgumentException e) {
							logger.error("convertMap2Pojo error", e.getMessage());
						} catch (InvocationTargetException e) {
							logger.error("convertMap2Pojo error", e.getMessage());
						}
					}
				}
			}
		}
		
		return instance;
	}
	
	public static String hump2Line(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
