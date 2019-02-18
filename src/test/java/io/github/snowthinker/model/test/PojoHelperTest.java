package io.github.snowthinker.model.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import io.github.snowthinker.mask.test.MaskBean;
import io.github.snowthinker.model.PojoHelper;
import junit.framework.TestCase;

public class PojoHelperTest extends TestCase {

	@Test
	public void testCopyProperties() {
		MaskBean maskBean4 = new MaskBean();
		maskBean4.setName("Jack4");
		maskBean4.setMobile("44018638240");
		maskBean4.setIdcard("440212196309222212");
		maskBean4.setCardNumber("4468390056242624");
		maskBean4.setAddress("2915 Canyon Lake Dr, Rapid City, SD 57702");
		maskBean4.setBirthday(new Date());
		maskBean4.setAge(44);
		
		MaskBean targetObject = new MaskBean();
		
		PojoHelper.copyProperties(maskBean4, targetObject, null);
		
		Assert.assertEquals(maskBean4.getName(), targetObject.getName());
		System.out.println(targetObject);
	}
}
