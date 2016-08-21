package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.ExitEnum;

/**
 * Created by Ivan on 20/08/2016.
 */
public class RoomIdentifer {

    public RoomIdentifer() {
    }

    public RoomIdentifer(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getRoomPrefix() {
        return roomPrefix;
    }

    public void setRoomPrefix(String roomPrefix) {
        this.roomPrefix = roomPrefix;
    }

    @Override
    public String toString() {
        return roomPrefix + ":" + x + ":" + y + ":" + z;
    }

    private int x, y, z = 0;
    private String roomPrefix;

    public RoomIdentifer createNewRoomId(ExitEnum exit) {

        RoomIdentifer newRoomId = new RoomIdentifer();
        newRoomId.setRoomPrefix(roomPrefix);
        newRoomId.x = this.x;
        newRoomId.y = this.y;
        newRoomId.z = this.z;
        newRoomId.x += exit.getDx();
        newRoomId.y += exit.getDy();
        newRoomId.z += exit.getDz();
        return newRoomId;
    }

    public static RoomIdentifer getRoomId(Room room) {
        String[] tokens = room.getId().split(":");
        RoomIdentifer newRoomId = new RoomIdentifer();
        newRoomId.setRoomPrefix(tokens[0]);
        newRoomId.x = Integer.parseInt(tokens[1]);
        newRoomId.y = Integer.parseInt(tokens[2]);
        newRoomId.z = Integer.parseInt(tokens[3]);
        return newRoomId;
    }
}
