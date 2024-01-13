package com.ivstuart.tmub.server;

import com.ivstuart.tmud.server.LaunchMud;

public class RunLaunchMud implements Runnable {

    @Override
    public void run() {
        LaunchMud.main(new String[0]);
    }
}
