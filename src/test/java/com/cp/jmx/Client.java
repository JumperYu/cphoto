package com.cp.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.TimeUnit;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * 
 * JMX远程连接
 * 
 * -Djava.rmi.server.hostname=127.0.0.1 -Dcom.sun.management.jmxremote
 * -Dcom.sun.management.jmxremote.port=9999
 * -Dcom.sun.management.jmxremote.ssl="false"
 * -Dcom.sun.management.jmxremote.authenticate="false"
 * 
 * @author zengxm 2015年3月31日
 * 
 */
public class Client {

	public static void main(String[] args) throws IOException,
			MalformedObjectNameException, InterruptedException,
			InstanceNotFoundException, IntrospectionException,
			ReflectionException {
		JMXServiceURL url = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://127.0.0.1:9999/jmxrmi");
		JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
		MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
		// 获取自定义mbean
		/*
		 * ObjectName objectName = new ObjectName("hehe:type=SystemConfig");
		 * SystemConfigMBean mbeaProxy = JMX.newMBeanProxy(mbsc, objectName,
		 * SystemConfigMBean.class, true); int count = mbeaProxy.getCount();
		 * System.out.println("proxy count : " + count);
		 * mbeaProxy.setCount(100); int countNow = mbeaProxy.getCount();
		 * System.out.println("proxy coun now : " + countNow);
		 * TimeUnit.SECONDS.sleep(3); jmxc.close();
		 */
		// 获取所有mbean
		/*
		 * Set<ObjectInstance> MBeanset = mbsc.queryMBeans(null, null);
		 * Iterator<ObjectInstance> MBeansetIterator = MBeanset.iterator();
		 * while (MBeansetIterator.hasNext()) { ObjectInstance objectInstance =
		 * MBeansetIterator .next(); ObjectName objectName =
		 * objectInstance.getObjectName(); MBeanInfo objectInfo =
		 * mbsc.getMBeanInfo(objectName); System.out.print("ObjectName:" +
		 * objectName.getCanonicalName() + ".");
		 * System.out.print("mehtodName:"); for (int i = 0; i <
		 * objectInfo.getAttributes().length; i++) {
		 * System.out.print(objectInfo.getAttributes()[i].getName() + ","); }
		 * System.out.println(); }
		 */
		// 获取堆内存
		MemoryMXBean memBean = ManagementFactory.newPlatformMXBeanProxy(mbsc,
				ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
		MemoryUsage.from((CompositeData) memBean.getHeapMemoryUsage());
		OperatingSystemMXBean opMXbean = ManagementFactory
				.newPlatformMXBeanProxy(mbsc,
						ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
						OperatingSystemMXBean.class);
		/** Collect data every 5 seconds */
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
		}

		MemoryUsage heap = memBean.getHeapMemoryUsage();
		MemoryUsage nonHeap = memBean.getNonHeapMemoryUsage();
		long heapSizeUsed = heap.getUsed();// 堆使用的大小
		long nonHeapSizeUsed = nonHeap.getUsed();
		long heapCommitedSize = heap.getCommitted();
		long nonHeapCommitedSize = nonHeap.getCommitted();
		double load = opMXbean.getSystemLoadAverage();
		String version = opMXbean.getVersion();
		// end - start 即为当前采集的时间单元，单位ms
		// endT - startT 为当前时间单元内cpu使用的时间，单位为ns
		// 所以：double ratio =
		// (entT-startT)/1000000.0/(end-start)/opMXbean.getAvailableProcessors()
		System.out
				.println(String
						.format("heap:%d,nonheap:%d,heapused:%d,nonheapused:%d,heapCommitedSize:%d,nonHeapCommitedSize:%d,load:%d,version%s",
								heap, nonHeap, heapSizeUsed, nonHeapSizeUsed,
								heapCommitedSize, nonHeapCommitedSize, load,
								version));
	}
}
