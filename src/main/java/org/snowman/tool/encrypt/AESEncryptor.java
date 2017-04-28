/*
 * @(#)AESEncryptor.java 1.0 2014-10-8上午11:08:09
 *
 */
package org.snowman.tool.encrypt;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

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
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
 
	@SuppressWarnings("restriction")
	public static String decrypt(String content) {
		
		try {
			// 创建密码器
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec key = new SecretKeySpec(password, "AES");
			
			//初始化
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = new BASE64Decoder().decodeBuffer(content);
			
			//解密
			return new String(cipher.doFinal(result));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <p>加密对象属性</p>
	 * @param obj
	 * @param properties
	 */
/*	public static void encryptProperties(Object obj, Class<?> class_, String[] properties) {
		for(String prop : properties) {
			
			PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(class_, prop);
			
			String plainProp = null;
			try {
				plainProp = (String) pd.getReadMethod().invoke(obj, null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			if(null == plainProp || "".equals(plainProp)) {
				continue;
			}
			
			String cipherText = AESEncryptor.encrypt(plainProp);
			
			try {
				pd.getWriteMethod().invoke(obj, cipherText);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	/**
	 * <p>解密对象属性</p>
	 * @param obj
	 * @param class_
	 * @param properties
	 */
/*	public static void decryptProperties(Object obj, Class<?> class_, String[] properties) {
		for(String prop : properties) {
			
			PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(class_, prop);
			
			String cipherProp = null;
			try {
				cipherProp = (String) pd.getReadMethod().invoke(obj, null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			if(null == cipherProp || "".equals(cipherProp)) {
				continue;
			}
			
			String plainProp = AESEncryptor.decrypt(cipherProp);
			
			try {
				pd.getWriteMethod().invoke(obj, plainProp);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}*/
}
