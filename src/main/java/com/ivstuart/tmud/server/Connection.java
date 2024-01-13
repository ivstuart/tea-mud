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

/*
 * Created on 09-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;

/**
 * @author stuarti
 */
public class Connection {

    private static final Logger LOGGER = LogManager.getLogger();

    private SocketChannel socketChannel;

    private Readable state;

    private long timeLastRead;
    private volatile boolean disconnected = false;

    public Connection() {

    }

    /**
     *
     */
    public Connection(SocketChannel aSocketChannel) {

        LOGGER.info("Connection created for [ " + aSocketChannel + " ]");

        this.socketChannel = aSocketChannel;

        this.state = new Login(this);

    }

    public boolean isDisconnected() {
        return disconnected;
    }

    public void disconnect() {
        disconnected = true;

        LOGGER.info("Disconnecting socket channel [ " + socketChannel + " ]");

        MudServer.getInstance().shutdownIO(socketChannel);

    }

    public Readable getState() {
        return state;
    }

    public void setState(Readable readable) {

        LOGGER.info("Setting state  [ " + readable + " ]");

        state = readable;
    }

    public boolean isConnected() {
        return !disconnected;
    }

    public void out(String output) {

        if (disconnected) {
            LOGGER.warn("Problem writing to closed connection");
            return;
            // throw new RuntimeException("Problem writing to closed connection");
        }

        try {
            MudServer.getInstance().write(socketChannel, output);
        }
        catch (ClosedChannelException cce) {
            LOGGER.warn("ClosedChannelException writing:"+output);
            disconnected = true;
        }
        catch (IOException e) {
            LOGGER.error("Problem writing to closed connection:"+e.getMessage());
            disconnected = true;
//            e.printStackTrace();
//            throw new RuntimeException(e);
        }

    }

    public void setTimeLastRead(long timeLastRead) {
        this.timeLastRead = timeLastRead;
    }

    public void process(String cmd) {
        timeLastRead = System.currentTimeMillis();
        state.read(cmd);
    }

    public long getIdle() {
        return System.currentTimeMillis() - timeLastRead;
    }

    public String getLocalAddress() {
        if (socketChannel == null) {
            return null;
        }
        return socketChannel.socket().getLocalAddress().toString();
    }

    public String getRemoteAddress() {
        if (socketChannel == null) {
            return null;
        }
        return socketChannel.socket().getInetAddress().toString();
    }
}
