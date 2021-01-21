package io.github.snowythinker.model.test;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;


import io.github.snowythinker.mask.test.MaskBean;
import io.github.snowythinker.model.PojoHelper;
import org.junit.jupiter.api.Test;

public class PojoHelperTest  {

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
		
		System.out.println(targetObject);
	}
	
	@Test
	public void testMap2Pojo() {
		MaskBean maskBean4 = new MaskBean();
		maskBean4.setName("Jack4");
		maskBean4.setMobile("44018638240");
		maskBean4.setIdcard("440212196309222212");
		maskBean4.setCardNumber("4468390056242624");
		maskBean4.setAddress("2915 Canyon Lake Dr, Rapid City, SD 57702");
		maskBean4.setBirthday(new Date());
		maskBean4.setAge(44);
		maskBean4.setCreateTime(LocalDateTime.now());
		
		Map<String, Object> map = PojoHelper.convertPojo2Map(maskBean4);
		System.out.println(map);
		
		System.out.println(PojoHelper.convertMap2Pojo(map, MaskBean.class));
	}
}
