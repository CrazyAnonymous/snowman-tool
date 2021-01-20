package io.github.snowthinker.mask.test;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime createTime;
	
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
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "MaskBean{" +
				"name='" + name + '\'' +
				", mobile='" + mobile + '\'' +
				", idcard='" + idcard + '\'' +
				", cardNumber='" + cardNumber + '\'' +
				", address='" + address + '\'' +
				", birthday=" + birthday +
				", age=" + age +
				", createTime=" + createTime +
				'}';
	}
}

