package com.ivstuart.tmud.poc;

public class Exit {

    private final String name;

    private final GridLocation destination;

    private boolean door = false;

    public Exit(String name, GridLocation destination) {
        this.name = name;
        this.destination = destination;
    }

    public Exit(int facing, GridLocation destination) {
        this.name = Facing.getDirection(facing);
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


    @Override
    public String toString() {
        return "Exit{" +
                "name='" + name + '\'' +
                ", destination=" + destination +
                ", door=" + door +
                '}';
    }

    public void toggleDoor() {
        door = !door;
    }
}
