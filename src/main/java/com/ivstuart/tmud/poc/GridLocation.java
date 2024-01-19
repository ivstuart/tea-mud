package com.ivstuart.tmud.poc;

import java.util.Objects;

public class GridLocation {
    private final int x;
    private final int y;
    private final int z;

    public GridLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridLocation that = (GridLocation) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "GridLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public GridLocation goFacing(int facing) {
        switch(facing) {
            case 0: return new GridLocation(x - 1,y,z); // West
            case 1: return new GridLocation(x,y - 1,z); // North
            case 2: return new GridLocation(x+1,y,z); // East
            case 3: return new GridLocation(x,y+1,z); // South
            case 4: return new GridLocation(x,y,z-1); // up
            case 5: return new GridLocation(x,y,z+1); // down
        }
        return null;
    }

    public boolean isOutsideOfZone(Zone zone) {
        if (x < 0 || x > zone.getWidth()) { return true; }
        return y < 0 || y > zone.getHeight();
    }

    public boolean isNextTo(GridLocation gridLocation) {
        return (Math.abs(x - gridLocation.getX())+ Math.abs(y - gridLocation.getY()) == 1);
    }

    public String getDestinationExit(GridLocation gridLocation) {
        int dx = x - gridLocation.getX();
        int dy = y - gridLocation.getY();
        int dz = z - gridLocation.getZ();

        if (dx == 1 && dy == 0) { return "west"; }
        if (dx == -1 && dy == 0) { return "east"; }

        if (dx == 0 && dy == 1) { return "north"; }
        if (dx == 0 && dy == -1) { return "south"; }

        if (dz == -1) { return "up"; }
        if (dz == 1) { return "down"; }

        return "hidden";
    }

}
