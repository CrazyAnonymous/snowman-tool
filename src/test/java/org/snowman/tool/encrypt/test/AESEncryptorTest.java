package org.snowman.tool.encrypt.test;

import java.util.Date;

import org.junit.Test;
import org.snowman.tool.encryption.AESEncryptor;

import junit.framework.Assert;
import junit.framework.TestCase;


public class AESEncryptorTest extends TestCase{
	
	private String plainText = "time@09+0-.,l";

	@Test
	public void testEncrypt() {
		
		String cipherText = AESEncryptor.encrypt(plainText);
		
		System.out.println(cipherText);
		
	}
	
	@Test
	public void testDecrypt() {
		String temp = AESEncryptor.decrypt("Ll39BnS4T0B24zdBW6d0Nw==");
		Assert.assertEquals(temp, plainText);
	}
	
	
	@Test
	public void testDecryption() {
		System.out.println(AESEncryptor.decrypt("Xnru30mJZ21Yrui7k8NrdwEX5SnPTeVtMMufHAPavUI="));
		System.out.println(AESEncryptor.decrypt("lyBDWekZaczkB/KYq4bMX+QKziaq4An6WEIbroSamyhq0ens7vTyA8Onvi4lIKck"));
		System.out.println(AESEncryptor.decrypt("+gUtmqPyrW1bMp4l0DnvBxD9uaCxymC/1G6NbungoWkCMxNH7YAa6B8fh/rrH3QIA1KUadJ+cW40vu7ki38MCw=="));
	}
	
	@Test
	public void testEncryptObject() {
		EncryptBean encryptBean = new EncryptBean();
		encryptBean.setName("Jack");
		encryptBean.setMobile("12318638123");
		encryptBean.setIdcard("123212196309222123");
		encryptBean.setCardNumber("1238390056241234");
		encryptBean.setAddress("2915 Canyon Lake Dr, Rapid City, SD 57702");
		encryptBean.setBirthday(new Date());
		encryptBean.setAge(17);
		
		System.out.printf("before encryption: %s \r\n", encryptBean);
		
		AESEncryptor.encryptObject(encryptBean);
		
		System.out.printf("after encryption: %s \r\n", encryptBean);
	}
	
	@Test
	public void testDecryptObject() {
		EncryptBean encryptBean = new EncryptBean();
		encryptBean.setName("Jack");
		encryptBean.setMobile("keiMcYQt1SNgYkNAia4KVg==");
		encryptBean.setIdcard("123212196309222123");
		encryptBean.setCardNumber("jzvCqL2QEMQliI2Pvdx7Chi7uEURzsK8I7iejfobS7Q=");
		encryptBean.setAddress("2915 Canyon Lake Dr, Rapid City, SD 57702");
		encryptBean.setBirthday(new Date());
		encryptBean.setAge(17);
		
		System.out.printf("before decrypt: %s \r\n", encryptBean);
		
		AESEncryptor.decryptObject(encryptBean);
		
		System.out.printf("after decrypt: %s \r\n", encryptBean);
	}
}
