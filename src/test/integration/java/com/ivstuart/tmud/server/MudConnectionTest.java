/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ivstuart.tmud.server;

import com.ivstuart.tmud.client.LaunchClient;
import com.ivstuart.tmud.utils.TestHelper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

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
