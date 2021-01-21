package io.github.snowythinker.mask;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mask Annotation
 * @author andrew
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Mask {

	/**
	 * mask type
	 * @return MaskType
	 */
	public MaskType type() default MaskType.MOBILE; 
	
	/**
	 * mask format
	 * @return String
	 */
	public String format() default "*";
}
