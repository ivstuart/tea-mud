/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
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
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * @author stuarti
 */
public class Connection {

    private static final Logger LOGGER = LogManager.getLogger();

    private static Charset charset = Charset.forName("ISO-8859-1");

    private static CharsetEncoder encoder = charset.newEncoder();

    private SocketChannel sc;

    private Readable state;

    private CharBuffer cbuf;

    private long timeLastRead;
    private volatile boolean disconnected = false;

    public Connection() {

    }

    /**
     *
     */
    public Connection(SocketChannel aSocketChannel) {

        LOGGER.info("Connection created for [ " + aSocketChannel + " ]");

        sc = aSocketChannel;

        state = new Login(this);

    }

    public boolean isDisconnected() {
        return disconnected;
    }

    public void disconnect() {
        disconnected = true;
        LOGGER.info("Disconnecting socket channel [ " + sc + " ]");

        try {

            sc.socket().close();
            sc.close();

        } catch (IOException e) {
            LOGGER.error("Problem closing connection", e);
        }
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

        cbuf = CharBuffer.wrap(output + "\n");

        try {
            sc.write(encoder.encode(cbuf));
        } catch (IOException e) {

            LOGGER.error("Problem writing to connection", e);
            disconnect();
        }

    }

    public void process(String cmd) {
        timeLastRead = System.currentTimeMillis();
        state.read(cmd);
    }

    public long getIdle() {
        return System.currentTimeMillis() - timeLastRead;
    }

    public String getLocalAddress() {
        if (sc == null) {
            return null;
        }
        return sc.socket().getLocalAddress().toString();
    }

    public String getRemoteAddress() {
        if (sc == null) {
            return null;
        }
        return sc.socket().getInetAddress().toString();
    }
}
