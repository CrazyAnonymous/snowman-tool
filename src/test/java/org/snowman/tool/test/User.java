/*
 * @(#)User.java 1.0 2015-3-26下午4:27:26
 */
package org.snowman.tool.test;

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
 * @author Andrew
 * @version 1.0
 * @since 2015-3-26 下午4:27:26 
 * 
 */
public class User {
	
	private String mobilePhone;
	private String address;
	private Integer age;
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
}
