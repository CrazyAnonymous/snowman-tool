package io.github.snowthinker.id.test;

import java.net.SocketException;

import org.junit.Test;

import io.github.snowthinker.Id.SnowFlaker;
import junit.framework.TestCase;

public class SnowFlakeTest extends TestCase {
	
	@Test
	public void testBit() throws SocketException {
		Long id = 1L;
		byte b = id.byteValue();
		System.out.println(b);
		System.out.println(-1L ^ (-1L << 10));
		
		SnowFlaker sf = new SnowFlaker();
		byte[] byteArr = sf.getValidMacAddress();
		
		for(byte b1 : byteArr) {
			System.out.println((int)b1);
		}
	}

	@Test
	public void testGenerageId() {
		SnowFlaker sf = new SnowFlaker();
		
		long beginTime = System.currentTimeMillis();
		for(int i=0; i<10000; i++) {
			long id = sf.nextId();
			System.out.println(id);
		}
		
		long endTime = System.currentTimeMillis();
		
		System.out.printf("10000 Identifis time cost: %s \r\n", (endTime - beginTime));
		
	}
}
