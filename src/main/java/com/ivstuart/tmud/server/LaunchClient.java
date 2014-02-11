package com.ivstuart.tmud.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * Recommend putty or tintin++ however please please double check site and source of any download
 * to ensure that there any binary files are trustworthy. In the meantime this can be used 
 * as a safe local client, which does not handle the ansi colour coding.
 * 
 * To switch this off from command line use "configdata ansi" to toggle on and off.
 * 
 * @author Ivan
 *
 */
public class LaunchClient implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(LaunchClient.class);

	private boolean isRunning = true;
	private BufferedReader bufferReader = null;
	private PrintWriter pw = null;
	private Socket socket = null;

	public static void main(String argv[]) throws IOException {

		LaunchClient client = new LaunchClient();
		Thread responseThread = new Thread(client);

		responseThread.start();
		client.readWriteStreams();

	}

	public LaunchClient() throws IOException {
		super();
		String ip = InetAddress.getLocalHost().getHostAddress(); // Same as you have configured your mud server
		int port = LaunchMud.getMudServerPort();

		try {
			socket = new Socket(ip, port);
		} catch (Exception e) {
			LOGGER.error("Problem opening socket connection on "+ip+":"+port, e);
		}

		bufferReader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),
				true);
	}

	private void readWriteStreams() throws IOException {

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in));

		String userInput;

		while ((userInput = stdIn.readLine()) != null && isRunning) {
			pw.println(userInput); // pushing input over to socket.
			System.out.println();
		}

	}

	@Override
	public void run() {
		while (isRunning) {
			try {
				if (bufferReader.ready()) {
					System.out.println(bufferReader.readLine());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isRunning = false;
			}
		}
	}

	public void close() {
		isRunning = false;
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (bufferReader != null) {
			try {
				bufferReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (pw != null) {
			pw.close();
		}
	}
}
