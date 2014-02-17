package com.ivstuart.tmud.server;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class MudConnectionTest {

	@Test
	public void launchServerThenStop() throws InterruptedException {

		Thread serverThread = new Thread(new LaunchMudRunnable());
		serverThread.start();

		Thread.sleep(2000);

		boolean stopping = LaunchMud.stop();

		assertTrue(stopping);

	}

	@Test
	public void sendSomeClientDataToServer() throws InterruptedException {
		Thread serverThread = new Thread(new LaunchMudRunnable());
		serverThread.start();

		Thread.sleep(200); // Allow time for mud server to start (increasing
							// this is ok).

		LaunchMudClient client = new LaunchMudClient();
		Thread clientThread = new Thread(client);
		clientThread.start();

		Thread.sleep(200); // Allow time to start before sending via the client

		try {
			client.send("1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class LaunchMudRunnable implements Runnable {

		boolean isRunning = true;

		@Override
		public void run() {

			LaunchMud.main(new String[0]);

			try {
				Thread.sleep(15 * 60 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 15 minutes no shutdown coded yet.

			isRunning = false;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Thread.sleep(15 * 60 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 15 minutes no shutdown coded yet.

			isRunning = false;

		}

		public void send(String userInput) throws IOException {

			client.send(userInput);
		}
	}

}
