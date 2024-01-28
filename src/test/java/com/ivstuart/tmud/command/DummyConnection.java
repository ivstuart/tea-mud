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


package com.ivstuart.tmud.command;


import com.ivstuart.tmud.server.Connection;
import com.ivstuart.tmud.server.Readable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DummyConnection extends Connection {

    private static final Logger LOGGER = LogManager.getLogger();
    private String output;


    /**
     *
     */
    public DummyConnection() {

    }

    public String getOutput() {
        return output;
    }

    @Override
    public void disconnect() {
        LOGGER.debug("disconnect");
    }

    @Override
    public Readable getState() {
        LOGGER.debug("get state");
        return null;
    }

    @Override
    public void setState(Readable readable) {
        LOGGER.debug("set state");
    }

    /**
     * @return
     */
    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public void out(String output) {
        this.output = output;
        LOGGER.debug("output [ " + output + " ]");

    }

    @Override
    public void process(String cmd) {
        LOGGER.debug("command [ " + cmd + " ]");
    }

}
