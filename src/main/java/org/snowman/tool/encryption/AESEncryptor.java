/*
 * @(#)AESEncryptor.java 1.0 2014-10-8上午11:08:09
 *
 */
package org.snowman.tool.encryption;

import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.BeanUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2014-10-8 上午11:08:09 
 * 
 */
public class AESEncryptor {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AESEncryptor.class);
	
	private static final byte[] password = new byte[16];
	
	private AESEncryptor(){
		
	}
	
	static{
		InputStream is = AESEncryptor.class.getResourceAsStream("/key");
		
		BufferedInputStream bis = null;
		
		bis = new BufferedInputStream(is);

		byte[] tempBytes = new byte[100];
		
		try {
			bis.read(tempBytes, 0, tempBytes.length);
		} catch (IOException e) {
			logger.error("read file error", e);
		} finally{
			try {
				if (null != bis) {
					bis.close();
				}
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				logger.error("close InputStream error", e);
			}
		}
		
		int times = 0;
		for(int i=tempBytes.length -1; i > 0 && times < password.length; i--) {
			if(i % 2 == 0) {
				password[times] = tempBytes[i];
			}
		}
	}
	
	
	@SuppressWarnings("restriction")
	public static String encrypt(String content) {
		
		try {
			Cipher aesECB = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec key = new SecretKeySpec(password, "AES");
			aesECB.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = aesECB.doFinal(content.getBytes());
			
			return new BASE64Encoder().encode(result);
		} catch (NoSuchAlgorithmException e) {
			logger.error("encrypt error", e);
		} catch (NoSuchPaddingException e) {
			logger.error("encrypt error", e);
		} catch (InvalidKeyException e) {
			logger.error("encrypt error", e);
		} catch (IllegalBlockSizeException e) {
			logger.error("encrypt error", e);
		} catch (BadPaddingException e) {
			logger.error("encrypt error", e);
		}
		return null;
	}
 
	@SuppressWarnings("restriction")
	public static String decrypt(String content) {
		
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec key = new SecretKeySpec(password, "AES");
			
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = new BASE64Decoder().decodeBuffer(content);
			
			//解密
			return new String(cipher.doFinal(result));
		} catch (NoSuchAlgorithmException e) {
			logger.error("decrypt error", e);
		} catch (NoSuchPaddingException e) {
			logger.error("decrypt error", e);
		} catch (InvalidKeyException e) {
			logger.error("decrypt error", e);
		} catch (IllegalBlockSizeException e) {
			logger.error("decrypt error", e);
		} catch (BadPaddingException e) {
			logger.error("decrypt error", e);
		} catch (IOException e) {
			logger.error("decrypt error", e);
		}
		return null;
	}
	
	/**
	 * encryption object properties
	 * @param obj
	 */
	public static void encryptObject(Object obj) {
		
		Field[] fields = obj.getClass().getDeclaredFields();
		
		for(Field field : fields) {
			String fieldName = field.getName();
			if(fieldName.equalsIgnoreCase("serialVersionUID")) {
				continue;
			}
			
			for(Annotation annotation : field.getAnnotations()) {
				if(annotation instanceof Encryption) {
					
					PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(obj.getClass(), fieldName);
					Object readValue = null;
					try {
						propertyDescriptor.getReadMethod().setAccessible(true);
						readValue = propertyDescriptor.getReadMethod().invoke(obj, null);
					} catch (Exception e) {
						logger.error("invoke get method error", e);
					} 
					
					if(null == readValue) {
						continue;
					}
					
					EncryptionType encryptionType = ((Encryption) annotation).type();
					String cipherText = encryptionPolicy(readValue, encryptionType);
					
					propertyDescriptor.getWriteMethod().setAccessible(true);
					try {
						propertyDescriptor.getWriteMethod().invoke(obj, cipherText);
					} catch (Exception e) {
						logger.error("invoke write method error", e);
					} 
				}
			}
		}
	}

	/**
	 * @param readValue
	 * @param encryptionType
	 * @return
	 */
	private static String encryptionPolicy(Object plainText, EncryptionType encryptionType) {
		if(null == plainText || null == encryptionType) {
			logger.info("encryptionPolicy with empty parameters");
			return null;
		}
		if(encryptionType.equals(EncryptionType.AES)) {
			return encrypt(plainText.toString());
		} else if(encryptionType.equals(EncryptionType.SHA512)) {
			return HashEncryptor.encrypt(plainText.toString(), HashEncryptor.SHA512);
		}
		return null;
	}
	
	/**
	 * encryption object properties
	 * @param obj
	 */
	public static void decryptObject(Object obj) {
		
		Field[] fields = obj.getClass().getDeclaredFields();
		
		for(Field field : fields) {
			String fieldName = field.getName();
			if(fieldName.equalsIgnoreCase("serialVersionUID")) {
				continue;
			}
			
			for(Annotation annotation : field.getAnnotations()) {
				if(annotation instanceof Encryption) {
					
					PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(obj.getClass(), fieldName);
					Object readValue = null;
					try {
						propertyDescriptor.getReadMethod().setAccessible(true);
						readValue = propertyDescriptor.getReadMethod().invoke(obj, null);
					} catch (Exception e) {
						logger.error("invoke get method error", e);
					} 
					
					if(null == readValue) {
						continue;
					}
					
					EncryptionType encryptionType = ((Encryption) annotation).type();
					
					if(encryptionType.equals(EncryptionType.AES)) {

						String plainText = decryptPolicy(readValue, encryptionType);
						
						propertyDescriptor.getWriteMethod().setAccessible(true);
						try {
							propertyDescriptor.getWriteMethod().invoke(obj, plainText);
						} catch (Exception e) {
							logger.error("invoke write method error", e);
						}	
					} 
				}
			}
		}
	}

	/**
	 * @param readValue
	 * @param encryptionType
	 * @return
	 */
	private static String decryptPolicy(Object cipherObject, EncryptionType encryptionType) {
		if(encryptionType.equals(EncryptionType.AES)){
			return AESEncryptor.decrypt(cipherObject.toString());
		}
		return null;
	}
}
