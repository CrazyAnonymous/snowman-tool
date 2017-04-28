package org.snowman.tool.test;

import java.util.Date;

import org.snowman.tool.mask.Mask;
import org.snowman.tool.mask.MaskType;
import org.snowman.tool.mask.MaskUtils;

class MaskBean {
	
	private String name;
	

	@Mask(type=MaskType.MOBILE, format="#")
	private String mobile;

	@Mask(type=MaskType.IDCARD)
	private String idcard;
	

	@Mask(type=MaskType.BANKCARD)
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
		return MaskUtils.toString(this);
	}
}

