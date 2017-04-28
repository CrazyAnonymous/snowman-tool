package org.snowman.tool.mask;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.springframework.beans.BeanUtils;

/**
 * mask utils
 * @author andrew
 *
 */
public class MaskUtils {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MaskUtils.class);

	/**
	 * print mask object
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if(null == obj) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(obj.getClass().getSimpleName() + "[");
		
		Field[] fields = obj.getClass().getDeclaredFields();
		Integer index = 0;
		for(Field field : fields) {
			String fieldName = field.getName();
			if(fieldName.equalsIgnoreCase("serialVersionUID")) {
				continue;
			}
			
			PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(obj.getClass(), fieldName);
			Object readValue = null;
			try {
				propertyDescriptor.getReadMethod().setAccessible(true);
				readValue = propertyDescriptor.getReadMethod().invoke(obj, null);
			} catch (Exception e) {
				logger.error("invoke get method error", e);
			} 
			
			boolean contains = false;
			for(Annotation annotation : field.getAnnotations()) {
				if(annotation instanceof Mask) {
					contains = true;
					
					MaskType maskType = ((Mask) annotation).type();
					sb.append(fieldName + "=" + maskType2String(readValue, maskType, ((Mask) annotation).format()));
				}
			}
				
			if(!contains) {
				sb.append(fieldName + "=" + readValue);
			}
			
			if(index < (fields.length - 1)) {
				sb.append(", ");
			}
			
			index++;
		}
		
		return sb.toString();
	}

	/**
	 * 
	 * @param object 
	 * @param maskType 
	 * @param format 
	 * @return 
	 */
	private static String maskType2String(Object object, MaskType maskType, String format) {
		
		String value = object.toString();
		
		//idcard 2222281982****1111
		if(maskType == MaskType.IDCARD) {
			if(value.length() >= 16) {
				value = maskFixLen(value, 4, 4, format);
			}
		//bankcard number  1111664830****2222
		} else if(maskType == MaskType.BANKCARD) {
			if(value.length() > 10) {
				value = maskFixLen(value, 4, 4, format);
			}
		} else if(maskType == MaskType.MOBILE) {
			if(value.length() >= 11) {
				value = maskFixLen(value, 4, 4, format);
			}
		// address
		} else if(maskType == MaskType.ADDRESS) {
			if(value.length() >= 6) {
				value = maskFixLen(value, 4, 4, format);
			}
		}
		
		return value;
	}
	
	/**
	 * 
	 * @param value mask string
	 * @param noMaskSuffix 
	 * @param maskLength
	 * @return
	 */
	public static String maskFixLen(String value, int noMaskSuffix, int maskLength, String format) {
		StringBuffer sb = new StringBuffer();
		char[] charArr = value.toCharArray();
		// remain preffix
		int noMaskPreffix = charArr.length - noMaskSuffix - maskLength;

		for (int i = 0; i < charArr.length; i++) {
			if (i < noMaskPreffix) {
				sb.append(charArr[i]);
			} else if (i >= (charArr.length - (charArr.length - noMaskPreffix - noMaskSuffix))) {
				sb.append(charArr[i]);
			} else {
				sb.append(format);
			}
		}
		return sb.toString();
	}
}
