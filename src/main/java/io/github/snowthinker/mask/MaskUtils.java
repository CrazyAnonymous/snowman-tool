package io.github.snowthinker.mask;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import io.github.snowthinker.reflect.ReflectionHelper;

/**
 * mask utils
 * @author andrew
 *
 */
public class MaskUtils {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MaskUtils.class);

	/**
	 * print mask object
	 * @param obj target object
	 * @return String
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
			
			PropertyDescriptor propertyDescriptor = ReflectionHelper.getPropertyDescriptor(obj.getClass(), fieldName);
			Object readValue = null;
			try {
				propertyDescriptor.getReadMethod().setAccessible(true);
				readValue = propertyDescriptor.getReadMethod().invoke(obj, new Object[] {});
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
	 * <p>Mask and return string
	 * @param object mask object
	 * @param maskType mask type
	 * @param format which format
	 * @return String
	 */
	private static String maskType2String(Object object, MaskType maskType, String format) {
		
		String value = object.toString();
		
		// idcard 2222281982****1111
		if(maskType == MaskType.IDCARD) {
			if(value.length() >= 16) {
				value = maskFixLen(value, 4, 4, format);
			}
		// bankcard number  1111664830****2222
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
	 * <p>Mask with fix length</p>
	 * @param value the mask value
	 * @param noMaskSuffix the index of mask suffix
	 * @param maskLength the mask length
	 * @param format the mask format
	 * @return String
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
