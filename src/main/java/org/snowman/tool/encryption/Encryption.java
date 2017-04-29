package org.snowman.tool.encryption;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Andrew-PC
 * @since 2017年4月29日 上午9:53:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Encryption {

	/**
	 * encryption type default SHA-512
	 * @return
	 */
	EncryptionType type() default EncryptionType.SHA512;
}
