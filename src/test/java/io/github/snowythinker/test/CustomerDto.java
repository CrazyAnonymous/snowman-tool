package io.github.snowythinker.test;

import java.util.Date;

import io.github.snowythinker.encryption.Encryption;
import io.github.snowythinker.encryption.EncryptionType;

/**
 * 
 * @author Andrew-PC
 * @since 2017年4月29日 上午10:10:26
 */
public class CustomerDto {

	private String name;
	
	@Encryption(type=EncryptionType.AES)
	private String mobile;

	@Encryption
	private String idcard;

	@Encryption(type=EncryptionType.AES)
	private String cardNumber;

	@Encryption
	private String address;
	
	private Date birthday;
	
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "CustomerDto [name=" + name + ", mobile=" + mobile + ", idcard=" + idcard + ", cardNumber=" + cardNumber
				+ ", address=" + address + ", birthday=" + birthday + ", age=" + age + "]";
	}
}
