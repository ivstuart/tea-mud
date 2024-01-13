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

package com.ivstuart.tmud.client;

import com.ivstuart.tmud.server.LaunchMud;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Recommend putty or tintin++ however please please double check site and
 * source of any download to ensure that there any binary files are trustworthy.
 * In the meantime this can be used as a safe local client, which does not
 * handle the ansi colour coding.
 * <p>
 * To switch this off from command line use "configdata ansi" to toggle on and
 * off.
 *
 * @author Ivan
 */
public class LaunchClient implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();

    private volatile boolean isReadingFromCommandLine = true;
    private volatile boolean isRunningServerListener = true;
    private BufferedReader bufferReader = null;
    private PrintWriter pw = null;
    private Socket socket = null;
    private String lastResponse;
    private AtomicBoolean hasResponse;

    private Thread responseThread;

    public LaunchClient() throws IOException {
        super();
        String ip = InetAddress.getLocalHost().getHostAddress(); // Same as you
        // have
        // configured
        // your mud
        // server
        int port = LaunchMud.getMudServerPort();

        LOGGER.info("Opening socket connection on " + ip + ":" + port);

        hasResponse = new AtomicBoolean(false);

        try {
            socket = new Socket(ip, port);
        } catch (Exception e) {
            LOGGER.error("Problem opening socket connection on " + ip + ":"
                    + port, e);
        }

        bufferReader = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

        pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),
                true);
    }

    public static void main(String argv[]) throws IOException {

        LaunchClient client = new LaunchClient();
        client.start();
        client.readWriteStreams();

    }



    public boolean start() throws IOException {
        responseThread = new Thread(this);
        responseThread.start();
        return responseThread.isAlive();
    }

    public String getLastResponseWithWait(){
        while(!this.hasResponse()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return lastResponse;
    }

    public String getLastResponse() {
        return lastResponse;
    }

    public void readWriteStreams() {

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        isReadingFromCommandLine = true;
        while (isReadingFromCommandLine) {
            String userInput = null;
            try {
                userInput = stdIn.readLine();
            } catch (IOException e) {
                LOGGER.error("Problem reading line from stdin:"+e.getMessage());
                isReadingFromCommandLine = false;
            }

            if (userInput == null){
                LOGGER.debug("User input was null");
                isReadingFromCommandLine = false;
            }

            pw.println(userInput); // pushing input over to socket.

            if (pw.checkError()) {
                LOGGER.error("Problem with print writer");
                isReadingFromCommandLine = false;
            }

            System.out.println();
        }

        close();

    }

    /**
     * Used by integration tests only
     *
     * @param userInput
     */
    public void send(String userInput) {

        if (!isRunning()) {
            LOGGER.warn("Client no longer running not sending: " + userInput);
            return;
        }

        // Check if socket is still connected to server

        LOGGER.info("Sending: " + userInput);

        hasResponse.set(false);
        pw.println(userInput); // pushing input over to socket.

        if (pw.checkError()) {
            close();
        }

        System.out.println();

    }

    @Override
    public void run() {
        isRunningServerListener = true;
        while (isRunningServerListener) {
            try {
                if (bufferReader.ready()) {
                    lastResponse = bufferReader.readLine();
                    System.out.println(lastResponse);
                    hasResponse.set(true);
                }
            } catch (IOException e) {
                LOGGER.error(e);
                isRunningServerListener = false;
            }
        }

        LOGGER.info("Running false hence closing client");
        close();
    }

    public boolean isRunning() {
        boolean result = (isReadingFromCommandLine && isRunningServerListener);

        LOGGER.info("Running is:"+isReadingFromCommandLine+":"+isRunningServerListener+">"+result);

        return result;
    }

    public void close() {
        LOGGER.info("Client is closing...");
        isReadingFromCommandLine = false;
        isRunningServerListener = false;
        if (socket != null) {
            try {
                socket.shutdownInput();
                socket.shutdownOutput();
                // socket.close();
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        if (bufferReader != null) {
            try {
                bufferReader.close();
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        if (pw != null) {
            pw.close();
        }

        if (responseThread != null) {
            responseThread.interrupt();
        }

    }

    public boolean hasResponse() {
        return hasResponse.get();
    }

}
