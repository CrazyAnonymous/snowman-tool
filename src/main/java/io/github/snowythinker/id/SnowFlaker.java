package io.github.snowythinker.Id;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 
 * @author Andrew
 *
 */
public class SnowFlaker {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SnowFlaker.class);
	
	/**
     * 起始的时间戳
     */
    private final static long START_STMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数

    /**
     * 每一部分的最大值
     */
//    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << (DATACENTER_BIT + MACHINE_BIT));
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    /**
     * 数据中心ID(0~31)
     */
    private long machineId;     //机器标识
    
    /**
     * 毫秒内序列(0-4095)
     */
    private long sequence = 0L; //序列号
    
    /**
     * 上次生成的时间戳
     */
    private long lastStmp = -1L;//上一次时间戳
    
    
    public SnowFlaker() {
    	this.machineId = this.getMachineId();
    }

    /**
     * Obtain unique address Id
     * @return long machine identifier
     */
    private long getMachineId() {
    	long id = 1;
    	try {
			byte[] mac = this.getValidMacAddress();
			id = (0x000000FF & (long)mac[mac.length-1] | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
			
			if(id > MAX_MACHINE_NUM || id < 0) {
				throw new RuntimeException("Machine identifier must between 0 and 1024");
			}
		} catch (Exception e) {
			logger.error("Obtain Mac address error", e);
		}
		return id;
	}

    /**
     * Get Physical Mac address
     * @return Byte[] Mac address bytes
     * @throws java.net.SocketException exception
     */
	public byte[] getValidMacAddress() throws SocketException {
		byte[] macAddress = null;
		Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
		while(networks.hasMoreElements()) {
			NetworkInterface network = networks.nextElement();
			if(null != network) {
				byte[] temp = network.getHardwareAddress();
				if(null != temp) {
					macAddress = temp;
				}
			}
		}
		return macAddress;
	}

    /**
     * Generate next Id
     * @return long Id
     */
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }
}
