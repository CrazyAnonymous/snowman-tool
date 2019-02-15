package org.snowman.tool.mask.test;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import junit.framework.TestCase;

public class MaskUtilsTest extends TestCase {
	
	@Test
	public void testMaskBean() {
		MaskBean maskBean = new MaskBean();
		maskBean.setName("Andrew");
		maskBean.setMobile("12318638123");
		maskBean.setIdcard("123212196309222123");
		maskBean.setCardNumber("1238390056241234");
		maskBean.setAddress("2915 Canyon Lake Dr, Rapid City, SD 57702");
		maskBean.setBirthday(new Date());
		maskBean.setAge(17);
		
		System.out.printf("after mask and encrypt: %s\r\n", maskBean);
	}

	//@Test
	public void testMaskconcurrent() throws InterruptedException {
		
		MaskBean maskBean1 = new MaskBean();
		maskBean1.setName("Jack1");
		maskBean1.setMobile("11018638240");
		maskBean1.setIdcard("110212196309222212");
		maskBean1.setCardNumber("1168390056242624");
		maskBean1.setAddress("2915 Canyon Lake Dr, Rapid City, SD 57702");
		maskBean1.setBirthday(new Date());
		maskBean1.setAge(11);
		
		MaskBean maskBean2 = new MaskBean();
		maskBean2.setName("Jack2");
		maskBean2.setMobile("22018638240");
		maskBean2.setIdcard("220212196309222212");
		maskBean2.setCardNumber("2268390056242624");
		maskBean2.setAddress("2915 Canyon Lake Dr, Rapid City, SD 57702");
		maskBean2.setBirthday(new Date());
		maskBean2.setAge(22);
		
		MaskBean maskBean3 = new MaskBean();
		maskBean3.setName("Jack3");
		maskBean3.setMobile("33018638240");
		maskBean3.setIdcard("330212196309222212");
		maskBean3.setCardNumber("3368390056242624");
		maskBean3.setAddress("33|5001||南川区河滨南路17号附21号");
		maskBean3.setBirthday(new Date());
		maskBean3.setAge(33);
		
		MaskBean maskBean4 = new MaskBean();
		maskBean4.setName("Jack4");
		maskBean4.setMobile("44018638240");
		maskBean4.setIdcard("440212196309222212");
		maskBean4.setCardNumber("4468390056242624");
		maskBean4.setAddress("2915 Canyon Lake Dr, Rapid City, SD 57702");
		maskBean4.setBirthday(new Date());
		maskBean4.setAge(44);
		
		MaskBean maskBean5 = new MaskBean();
		maskBean5.setName("Jack5");
		maskBean5.setMobile("55018638240");
		maskBean5.setIdcard("550212196309222212");
		maskBean5.setCardNumber("5568390056242624");
		maskBean5.setAddress("2915 Canyon Lake Dr, Rapid City, SD 57702");
		maskBean5.setBirthday(new Date());
		maskBean5.setAge(55);
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for(int i=0; i<100; i++) {
			executorService.submit(new MaskTask(maskBean1));
			executorService.submit(new MaskTask(maskBean2));
			executorService.submit(new MaskTask(maskBean3));
			executorService.submit(new MaskTask(maskBean4));
			executorService.submit(new MaskTask(maskBean5));	
		}
		
		Thread.sleep(50000);
		
		executorService.shutdown();
	}
	
	class MaskTask implements Runnable {
		
		private MaskBean maskBean;
		
		public MaskTask(MaskBean maskBean) {
			this.maskBean = maskBean;
		}

		public void run() {
			System.out.println(maskBean);
		}
		
	}
}
