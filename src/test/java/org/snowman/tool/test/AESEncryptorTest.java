package org.snowman.tool.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.snowman.tool.encrypt.AESEncryptor;
import org.snowman.tool.model.PojoUtils;


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
	public void testEncryptProperties() {
		User user = new User();
		user.setAddress("曹杨路332号309室");
		user.setMobilePhone("186343436132");
		user.setAge(10);
		
		String[] props = {"mobilePhone", "address"};
		PojoUtils.encryptProperties(user, User.class, props);
		
		System.out.println(user.getMobilePhone());
		System.out.println(user.getAddress());
	}
	

	@Test
	public void testDecryptProperties() {
		User user = new User();
		user.setAddress("mh2r+BDfEBtHQPo2/2lJqg==");
		user.setMobilePhone("vRCG87o9Fy+o7YpqQaubvg==");
		user.setAge(10);
		
		String[] props = {"mobilePhone", "address"};
		PojoUtils.decryptProperties(user, User.class, props);
		
		System.out.println(user.getMobilePhone());
		System.out.println(user.getAddress());
	}
	
	@Test
	public void testDecryption() {
		System.out.println(AESEncryptor.decrypt("Xnru30mJZ21Yrui7k8NrdwEX5SnPTeVtMMufHAPavUI="));
		System.out.println(AESEncryptor.decrypt("lyBDWekZaczkB/KYq4bMX+QKziaq4An6WEIbroSamyhq0ens7vTyA8Onvi4lIKck"));
		System.out.println(AESEncryptor.decrypt("+gUtmqPyrW1bMp4l0DnvBxD9uaCxymC/1G6NbungoWkCMxNH7YAa6B8fh/rrH3QIA1KUadJ+cW40vu7ki38MCw=="));
	}
}
