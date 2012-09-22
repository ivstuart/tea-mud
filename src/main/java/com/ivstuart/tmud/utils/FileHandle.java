package com.ivstuart.tmud.utils;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.StringTokenizer;

/*
 * Point of this is that it is making use of new File NIO Java 1.4 code!!
 * 
 */
public class FileHandle {

	private static Charset charset = Charset.forName("ISO-8859-1");

	private static CharsetEncoder encoder = charset.newEncoder();

	private static CharsetDecoder decoder = charset.newDecoder();

	public static void main(String args[]) {

		FileHandle newFile = new FileHandle("filename3");

		try {
			newFile.writeln("Testing 1 2 3");

			String fileContent = newFile.read();

			System.out.println("Content:" + fileContent);

			newFile.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private File file = null;

	private FileChannel wChannel = null;

	private StringTokenizer st = null;

	private StringBuffer sb = null;

	public FileHandle(String filename) {

		file = new File(filename);
	}

	public void close() throws IOException {
		// Close the file
		if (wChannel != null) {
			wChannel.close();
			wChannel = null;
		}

		file = null;
		wChannel = null;
		st = null;

	}

	public String getLine() {
		if (st.hasMoreTokens()) {
			return st.nextToken();
		}
		return null;
	}

	public boolean hasMoreLines() {
		if (st == null) {
			return false;
		}
		return st.hasMoreTokens();
	}

	public String read() throws IOException {

		// Allocate buffers
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		CharBuffer charBuffer = CharBuffer.allocate(1024);

		// Create a readable file channel
		FileInputStream fio = new FileInputStream(file);

		FileChannel rChannel = fio.getChannel();

		sb = new StringBuffer();
		// Read response
		while ((rChannel.read(buffer)) != -1) {
			buffer.flip();
			// Decode buffer
			decoder.decode(buffer, charBuffer, false);
			// Display
			charBuffer.flip();
			sb.append(charBuffer.toString());
			buffer.clear();
			charBuffer.clear();
		}

		fio.close();
		rChannel.close();

		// Need to change this to use platform specific carriage return string
		st = new StringTokenizer(sb.toString(), "\r\n");

		return sb.toString();

	}

	public void restart() {
		if (sb != null) {
			st = new StringTokenizer(sb.toString(), "\r\n");
		}
	}

	public void write(String content) throws IOException {
		this.write(content, true);
	}

	public void write(String content, boolean append) throws IOException {

		CharBuffer cbuf = CharBuffer.wrap(content);

		// Create a writable file channel
		if (wChannel == null) {
			wChannel = new FileOutputStream(file, append).getChannel();
		}

		// Write the ByteBuffer contents; the bytes between the ByteBuffer's
		// position and the limit is written to the file
		wChannel.write(encoder.encode(cbuf));
	}

	public void writeln(String content) throws IOException {
		this.write(content + "\n", true);
	}
}
