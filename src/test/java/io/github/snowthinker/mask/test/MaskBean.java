package io.github.snowthinker.mask.test;

import java.util.Date;

import io.github.snowthinker.encryption.Encryption;
import io.github.snowthinker.encryption.EncryptionType;
import io.github.snowthinker.mask.Mask;
import io.github.snowthinker.mask.MaskType;

public class MaskBean {
	
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
	
	private Integer age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Override
	public String toString() {
		return "MaskBean [name=" + name + ", mobile=" + mobile + ", idcard=" + idcard + ", cardNumber=" + cardNumber
				+ ", address=" + address + ", birthday=" + birthday + ", age=" + age + "]";
	}
}

