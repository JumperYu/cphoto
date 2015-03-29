package com.cp.thread;

class TicketTest implements Runnable {
	// 票的总数
	private int ticket = 10;

	public void run() {
		for (int i = 1; i < 50; i++) {

			// 休眠1s秒中，为了使效果更明显，否则可能出不了效果
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sale();
		}

	}

	public void sale() {
		synchronized (this) {
			if (ticket > 0) {
				System.out.println(Thread.currentThread().getName() + "号窗口卖出"
						+ this.ticket-- + "号票");
			}
		}
	}

	public static void main(String[] args) {
		TicketTest mt = new TicketTest();
		// 基于火车票创建三个窗口
		new Thread(mt, "a").start();
		new Thread(mt, "b").start();
		new Thread(mt, "c").start();
	}
}
