package io.github.snowythinker.reflect.test;

import java.beans.PropertyDescriptor;


import io.github.snowythinker.reflect.ReflectionHelper;
import io.github.snowythinker.test.CustomerDto;
import org.junit.jupiter.api.Test;

public class ReflectionHelperTest {

	@Test
	public void testGetpropertyDescriptor() {
		PropertyDescriptor[] pdArr = ReflectionHelper.getPropertyDescriptors(CustomerDto.class);
		System.out.println(pdArr);
	}
}
