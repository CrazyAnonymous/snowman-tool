package io.github.snowythinker.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
/**
 * <dl>
 *    <dt><b>Title: Hash encryptor</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author Andrew
 * @version 1.0
 * @since 2015-2-9 下午4:15:57 
 *
 */
public class HashEncryptor{
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HashEncryptor.class);
 
    public static String MD2 = "MD2";
    public static String MD5 = "MD5";
    public static String SHA1 = "SHA-1";
    public static String SHA256 = "SHA-256";
    public static String SHA384 = "SHA-384";
    public static String SHA512 = "SHA-512";
 
    /**
     * @param plainText plain text
     * @param algorithm algorithm
     * @return String
     */
    public static String encrypt(String plainText, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(plainText.getBytes("UTF-8"));
            byte[] res = messageDigest.digest();
            return byte2hex(res);
        } catch (NoSuchAlgorithmException e) {
        	logger.error("encrypt error", e);
        } catch (UnsupportedEncodingException e) {
        	logger.error("encrypt error", e);
		}
        
        return plainText;
    }
 
    /**
     * convert byte to hex
     * @param byteArray
     * @return hex string
     */
    private static String byte2hex(byte[] byteArray) {
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
 
        }
        return md5StrBuff.toString();
    }
}