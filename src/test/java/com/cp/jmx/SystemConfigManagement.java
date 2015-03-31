package com.cp.jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class SystemConfigManagement {

	public static void main(String[] args) throws MalformedObjectNameException,
			InstanceAlreadyExistsException, MBeanException,
			NotCompliantMBeanException, InterruptedException {

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		SystemConfig mBean = new SystemConfig();
		ObjectName name = new ObjectName("hehe:type=SystemConfig");
		mbs.registerMBean(mBean, name);

		do {
			Thread.sleep(3000);
			System.out.println("count: " + mBean.getCount());
		} while (true);
	}
}
