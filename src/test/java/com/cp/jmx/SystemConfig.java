package com.cp.jmx;

public class SystemConfig implements SystemConfigMBean {

	private int count;
	
	@Override
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public String doCount() {
		return "count is : " + count;
	}

}
