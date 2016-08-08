package com.ivstuart.tmud.server;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.ivstuart.tmud.utils.TestHelper;

public class MudConnectionTest {
	
	TestHelper help = new TestHelper();

	@Test
	public void launchServerThenStop() throws InterruptedException {

		
		help.startServer();

		boolean stopping = help.stopServer();

		assertTrue(stopping);

	}

	@Test
	public void sendSomeClientDataToServer() throws InterruptedException {
		help.startServer();

		LaunchMudClient  client = new LaunchMudClient();
		Thread clientThread = new Thread(client);
		clientThread.start();
		
		Thread.sleep(200); // Allow time to start before sending via the client

		try {
			client.send("1");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	class LaunchMudClient implements Runnable {

		boolean isRunning = true;

		LaunchClient client = null;

		@Override
		public void run() {

			try {
				client = LaunchClient.init();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				Thread.sleep(15 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // 15 minutes no shutdown coded yet.

			isRunning = false;

		}

		public void send(String userInput) throws IOException {

			client.send(userInput);
		}
	}

}
