package com.ivstuart.tmud.poc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class JWorldPanel extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger();
    private final int ROOM_SIZE = 4; // 11 works well.

    private final int GRID_SIZE = ROOM_SIZE * 2;

    private final int EXIT_OFFSET = (ROOM_SIZE / 2);

    public JWorldPanel(LayoutManager layout) {
        super(layout);
    }

    public static void setResized() {
    }


    public void addClickListener() {
        this.addMouseMotionListener(new MouseInputAdapter() {

            private Room previousRoom;

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                LOGGER.debug("Mouse dragged at:" + e.getX() + ":" + e.getY() + ": ID:" + e);

                int scaleFactor = JZoomPanel.getValue() / 20;
                int gridSize = GRID_SIZE * scaleFactor;
                // TODO select a room
                int x = e.getX() / gridSize;
                int y = e.getY() / gridSize;

                previousRoom = World.getSelectedRoom();

                int z = JModePanel.getValue();
                World.setRoomSelected(x, y, z);

                if (JModePanel.isEditRooms()) {

                    GridLocation gridLocation = new GridLocation(x, y, z);
                    Room room = World.getRoom(gridLocation);

                    if (e.getModifiers() == 4) { // Event.FOUR not found.
                        LOGGER.debug("Right mouse button held");
                        if (room != null) {
                            World.removeRoom(room);
                        }
                        JPanel source = (JPanel) e.getSource();
                        source.repaint();
                        return;
                    }

                    if (room == null) {
                        room = new Room(x, y, z);
                        room.setNarrowPassageway(!JModePanel.isOpen());
                        World.addRoom(room);

                        if (JModePanel.isEditExits() && !room.isNarrowPassageway()) {
                            room.addExit(0, true);
                            room.addExit(1, true);
                            room.addExit(2, true);
                            room.addExit(3, true);
                        }

                        if (JModePanel.isPath()) {
                            room.addExit(previousRoom);
                        }

                        World.setRoomSelected(x, y, z);
                    }

                }

                JPanel source = (JPanel) e.getSource();
                source.repaint();
            }
        });
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LOGGER.debug("Mouse clicked at:" + e.getX() + ":" + e.getY());
                LOGGER.debug("Mouse clicked at:" + e.getXOnScreen() + ":" + e.getYOnScreen());

                int scaleFactor = JZoomPanel.getValue() / 20;
                int gridSize = GRID_SIZE * scaleFactor;
                // TODO select a room
                int x = e.getX() / gridSize;
                int y = e.getY() / gridSize;
                int z = JModePanel.getValue();
                World.setRoomSelected(x, y, z);

                if (JModePanel.isEditRooms() && e.getClickCount() == 1) {

                    GridLocation gridLocation = new GridLocation(x, y, z);
                    Room room = World.getRoom(gridLocation);

                    if (room == null) {
                        room = new Room(x, y, 0);
                        room.setNarrowPassageway(!JModePanel.isOpen());
                        World.addRoom(room);

                        if (JModePanel.isEditExits() && !room.isNarrowPassageway()) {
                            room.addExit(0, true);
                            room.addExit(1, true);
                            room.addExit(2, true);
                            room.addExit(3, true);
                        }

                        World.setRoomSelected(x, y, z);
                    }

                }

                JPanel source = (JPanel) e.getSource();
                source.repaint();
            }

        });

    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);

        g.setFont(new Font("Ariel", Font.PLAIN, 8));


        if (World.getRoomMap().isEmpty()) {
            return;
        }

        int scaleFactor = JZoomPanel.getValue() / 20;

        int gridSize = GRID_SIZE * scaleFactor;
        int roomSize = ROOM_SIZE * scaleFactor;
        int exitOffsetSize = EXIT_OFFSET * scaleFactor;

        // LOGGER.debug("Scale factor is :"+scaleFactor);

        int maxY = 0;
        int maxX = 0;
        int level = JModePanel.getValue();
        for (Room room : World.getRoomMap().values()) {
            if (level != room.getGridLocation().getZ()) {
                continue;
            }

            int cx = room.getGridLocation().getX() * gridSize + roomSize;
            int cy = room.getGridLocation().getY() * gridSize + roomSize;

            if (cx > maxX) {
                maxX = cx;
            }
            if (cy > maxY) {
                maxY = cy;
            }

            int colourValue = 0;

            for (Exit exit : room.getExits()) {

                switch (exit.getName()) {
                    case "up":
                        colourValue = colourValue + 1;
                        break;
                    case "down":
                        colourValue = colourValue + 2;
                        break;
                }
            }


            if (World.getSelectedRoom() == room) {
                g.setColor(Color.LIGHT_GRAY);
            } else {
                switch (colourValue) {
                    case 0:
                        g.setColor(Color.GREEN);
                        break;
                    case 1:
                        g.setColor(Color.CYAN);
                        break;
                    case 2:
                        g.setColor(Color.RED);
                        break;
                    case 3:
                        g.setColor(Color.BLUE);
                        break;
                }
            }

            // g.drawString( ""+room.getRoomNumber(), room.getGridLocation().getX() * gridSize, room.getGridLocation().getY() * gridSize);

            if (room.isNarrowPassageway()) {
                g.fillOval(room.getGridLocation().getX() * gridSize, room.getGridLocation().getY() * gridSize, roomSize, roomSize);
            } else {
                g.fillRect(room.getGridLocation().getX() * gridSize, room.getGridLocation().getY() * gridSize, roomSize, roomSize);
            }


            g.setColor(Color.BLACK);

            for (Exit exit : room.getExits()) {

                int x = exitOffsetSize;
                int y = exitOffsetSize;
                int dx = 0; // Hack
                int dy = 0;

                String exitName = exit.getName();

                if (Facing.isCustom(exitName)) {
                    g.setColor(Color.RED);

                    exitName = room.getGridLocation().getDestinationExit(exit.getDestination());
                }

                switch (exitName) {
                    case "north":
                        y = 0;
                        dy = -exitOffsetSize;
                        break;
                    case "south":
                        y = roomSize;
                        dy = exitOffsetSize;
                        break;
                    case "west":
                        x = 0;
                        dx = -exitOffsetSize;
                        break;
                    case "east":
                        x = roomSize;
                        dx = exitOffsetSize;
                        break;
                }

                int xExit = x + (room.getGridLocation().getX() * gridSize);
                int yExit = y + (room.getGridLocation().getY() * gridSize);

                g.drawLine(xExit, yExit, xExit + dx, yExit + dy);
                if (exit.isDoor()) {
                    g.setColor(Color.BLACK);
                    g.drawString("D", xExit + dx, yExit + dy);
                }


            }
        }

        Dimension dim = getPreferredSize();

        if (dim.getHeight() < maxY || dim.getWidth() < maxX) {
            this.setPreferredSize(new Dimension(maxX + 10, maxY + 10));
        }

    }

}
