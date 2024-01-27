package com.ivstuart.tmud.person.config;

public enum ChannelEnum {

    AUCTION("You will receive communication from auctions"),
    CLAN("You will receive communication from your clan"),
    CHAT("You will receive chat messages"),
    GROUP("You will receive group tell messages"),
    NEWBIE("You will receive chat from newbies"),
    RAID("You will receive get raid communication");


    ChannelEnum(String on) {
        this.on = on;
        this.off = on.replaceFirst("receive","NOT receive");
    }

    public String getOn() {
        return on;
    }

    public String getOff() {
        return off;
    }

    private final String on;

    private final String off;
}
