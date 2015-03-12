package com.cp.nio;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * NIO.2 变更通知
 * 
 * @author Administrator
 *
 */
public class Watcher {

	public static void main(String[] args) {
		// System.out.println(Watcher.class.getResource(".").toString());
		Path this_dir = Paths.get(".");
		System.out.println(this_dir.toUri().toString());
		System.out.println("Now watching the current directory ...");

		for (;;) {
			try {
				WatchService watcher = this_dir.getFileSystem()
						.newWatchService();
				this_dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

				WatchKey watckKey = watcher.take();

				for (WatchEvent<?> event : watckKey.pollEvents()) {
					System.out.println("create file: " + event.count());
				}
				
				 boolean valid = watckKey.reset();
		         if (!valid) {
		             // object no longer registered
		        	 System.out.println("object no longer registered");
		         }

			} catch (Exception e) {
				System.out.println("Error: " + e.toString());
			}
		}
	}
}
