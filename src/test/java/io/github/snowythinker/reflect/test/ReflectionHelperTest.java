package io.github.snowthinker.reflect.test;

import java.beans.PropertyDescriptor;

import org.junit.Test;

import io.github.snowthinker.reflect.ReflectionHelper;
import io.github.snowthinker.test.CustomerDto;
import junit.framework.TestCase;

public class ReflectionHelperTest extends TestCase {

	@Test
	public void testGetpropertyDescriptor() {
		PropertyDescriptor[] pdArr = ReflectionHelper.getPropertyDescriptors(CustomerDto.class);
		System.out.println(pdArr);
	}
}
