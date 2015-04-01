package com.cp.vm;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;

public class FileEditor {

	private SoftReference<Object> ref;
	private Map<String, Object> cache = new HashMap<String, Object>();

	public void get(String key) {
		if (cache.containsKey(key)) {
			object = cache.get(key);
		} else {
			Object object = new Object();
			cache.put(key, object);
		}
	}

	public Object newObj() {
		return new Object();
	}

	public static void main(String[] args) {
		FileEditor fileEditor = new FileEditor();
		for (;;) {
			fileEditor.get("A" + Math.floor((Math.random() * 100000000)));
		}
	}

}
