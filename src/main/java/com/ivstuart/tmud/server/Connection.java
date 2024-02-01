/*
 *  Copyright 2024. Ivan Stuart
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

import com.ivstuart.tmud.command.misc.ForcedQuit;
import com.ivstuart.tmud.state.mobs.Mob;
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
    private final int IDLE_TIMEOUT = 500; // Seconds

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
        } catch (ClosedChannelException cce) {
            LOGGER.warn("ClosedChannelException writing:" + output);
            disconnected = true;
        } catch (IOException e) {
            LOGGER.error("Problem writing to closed connection:" + e.getMessage());
            disconnected = true;
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

    public void checkTimeout(Mob mob) {
        int secondsIdle = (int) (getIdle() / 1000);

        if (secondsIdle > IDLE_TIMEOUT) {
            LOGGER.info("Player " + mob.getName() + " has been idle for " + secondsIdle + " and has been kicked off");
            out("You have been idle for " + secondsIdle + " seconds hence quiting for you");
            new ForcedQuit().execute(mob, null);
        } else if (isConnected()) {
            LOGGER.info("Player has lost there connection and has been kicked off");
            new ForcedQuit().execute(mob, null);
        }

    }
}
