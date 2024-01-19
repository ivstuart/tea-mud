package com.ivstuart.tmud.poc;

public class Exit {

    private final String name;

    private final GridLocation destination;

    private boolean door;

    public Exit(String name, GridLocation destination) {
        this.name = name;
        this.destination = destination;
    }

    public Exit(int facing, GridLocation destination) {
        this.name = getDirectionFromFacing(facing);
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public GridLocation getDestination() {
        return destination;
    }

    public boolean isDoor() {
        return door;
    }

    public String getDirectionFromFacing(int facing) {
        switch(facing) {
            case 0: return "west";
            case 1: return "north";
            case 2: return "east";
            case 3: return "south";
            case 4: return "up";
            case 5: return "down";
        }
        return null;
    }

    public static String getOppositeDirection(String direction) {
        switch(direction) {
            case "east": return "west";
            case "south": return "north";
            case "west": return "east";
            case "north": return "south";
            case "up": return "down";
            case "down": return "up";
        }
        return null;
    }


    @Override
    public String toString() {
        return "Exit{" +
                "name='" + name + '\'' +
                ", destination=" + destination +
                ", door=" + door +
                '}';
    }
}
