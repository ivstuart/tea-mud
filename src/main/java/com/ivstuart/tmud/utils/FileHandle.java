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

package com.ivstuart.tmud.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;


/**
 * Point of this is that it is making use of new File NIO Java 1.4 code!!
 * update this to use a JDK7 mechanism to read files.
 */
public class FileHandle {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Charset charset = StandardCharsets.ISO_8859_1;

    private static final CharsetEncoder encoder = charset.newEncoder();

    private static final CharsetDecoder decoder = charset.newDecoder();
    private final String NL;
    private File file;
    private FileChannel wChannel = null;
    private StringTokenizer st = null;

    public FileHandle(String filename) {

        file = new File(filename);
        NL = System.getProperty("line.separator");
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

    public String read() throws FileNotFoundException {

        // Allocate buffers
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        CharBuffer charBuffer = CharBuffer.allocate(1024);

        // Create a readable file channel
        FileInputStream fio = new FileInputStream(file);

        FileChannel rChannel = fio.getChannel();

        StringBuilder sb = new StringBuilder();

        // Read response
        try {
            try {
                while ((rChannel.read(buffer)) != -1) {
                    buffer.flip();

                    // Decode buffer
                    decoder.decode(buffer, charBuffer, false);

                    // Display
                    charBuffer.flip();
                    sb.append(charBuffer);
                    buffer.clear();
                    charBuffer.clear();
                }
            } finally {
                fio.close();
                rChannel.close();
            }
        } catch (IOException e) {
            LOGGER.error("Problem reading file", e);
        }

        st = new StringTokenizer(sb.toString(), NL);

        return sb.toString();

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

    public String getName() {
        return file.getName();
    }
}
