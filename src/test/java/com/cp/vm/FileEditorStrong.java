package com.cp.vm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileEditorStrong {

	private Path path;
	private byte[] bytes;

	public FileEditorStrong(Path path) {
		this.path = path;
	}

	public Path getPath() {
		return path;
	}

	public byte[] getBytes() throws IOException {
		if (bytes == null || bytes.length == 0) {
			bytes = readFile();
		}
		return bytes;
	}

	public byte[] readFile() throws IOException {
		return Files.readAllBytes(path);
	}

}
