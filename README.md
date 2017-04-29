# snowman-tool

encrypt function:

1. encrypt data prevent decompilation support AES HASH(MD5, SHA).
2. use binary key file
3. mask model and dto with annotation
4. easy to use

---------------------

usage:
##### 1. add maven dependency
	
```xml
<dependency>
	<groupId>org.snowman.tools</groupId>
	<artifactId>snowman-tools</artifactId>
	<version>0.1.0</version>
</dependency>
```


##### 2. write mask or encryption model

```java  
class MaskBean {
	private String name;
	
	@Mask(type=MaskType.MOBILE, format="#")
	private String mobile;

	@Mask(type=MaskType.IDCARD)
	private String idcard;
	
	@Encryption(type=EncryptionType.AES)
	private String cardNumber;

	@Mask(type=MaskType.ADDRESS)
	private String address;
	
	private Date birthday;
	
	@Override
	public String toString() {
		return MaskUtils.toString(this);
	}
	//TODO getter and setters
}
```

##### 3. run test case

```java
public class MaskUtilsTest extends TestCase {
	@Test
	public void testMaskBean() {
		MaskBean maskBean = new MaskBean();
		maskBean.setName("Andrew");
		maskBean.setMobile("12318638123");
		maskBean.setIdcard("123212196309222123");
		maskBean.setCardNumber("1238390056241234");
		maskBean.setAddress("2915 Canyon Lake Dr, Rapid City, SD 57702");
		maskBean.setBirthday(new Date());
		maskBean.setAge(17);
		
		AESEncryptor.encryptObject(maskBean);
		
		System.out.printf("after mask and encrypt: %s\r\n", maskBean);
	}
}
```

##### 4.output: 
```
after mask and encrypt: MaskBean[name=Andrew, mobile=123####8123, idcard=1232121963****2123, cardNumber=jzvCqL2QEMQliI2Pvdx7Chi7uEURzsK8I7iejfobS7Q=, address=2915 Canyon Lake Dr, Rapid City, ****7702, birthday=Sat Apr 29 11:36:35 CST 2017, age=17
```
