package io.github.snowythinker.id.test;

import java.net.SocketException;


import io.github.snowythinker.id.SnowFlaker;
import org.junit.jupiter.api.Test;

public class SnowFlakeTest {
	
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
