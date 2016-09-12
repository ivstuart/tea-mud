/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.world;

import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.utils.MudIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Ivan on 12/09/2016.
 */
public class MudStats implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger();
    private static String fileName = "stats.sav";
    private transient int newPlayers;
    private transient int deletions;
    private transient int levelsGained;
    private transient int playerDeaths;
    private transient int mobDeaths;
    private transient int numberOfLogins;
    private transient int mostOnline;
    private transient int notesPosted;
    private transient int numberOfAuctions;
    private transient int numberOfRemorts;
    private int newPlayersTotal;
    private int deletionsTotal;
    private int levelsGainedTotal;
    private int playerDeathsTotal;
    private long mobDeathsTotal;
    private long numberOfLoginsTotal;
    private int mostOnlineTotal;
    private int notesPostedTotal;
    private int numberOfAuctionsTotal;
    private int numberOfRemortsTotal;

    public static MudStats init() {
        // Load the data from MudIO

        MudIO mudIO = new MudIO();
        MudStats mudStats = null;
        try {
            mudStats = (MudStats) mudIO.load(getSaveDirectory(), fileName, true);
        } catch (Exception e) {
            LOGGER.warn("Problem loading the mud stats", e);
        }
        return mudStats;
    }

    public static void shutdown() {
        // Save the data to MudIO.

        MudIO mudIO = new MudIO();

        try {
            mudIO.save(World.getMudStats(), getSaveDirectory(), fileName, true);
        } catch (IOException e) {
            LOGGER.error("Problem saving the mus stats", e);
        }

    }

    public static String getSaveDirectory() {
        return LaunchMud.mudServerProperties.getProperty("stats.save.dir");
    }

    public int getNewPlayers() {
        return newPlayers;
    }

    public void addNewPlayers() {
        this.newPlayers++;
        this.newPlayersTotal++;
    }

    public int getDeletions() {
        return deletions;
    }

    public void addDeletions() {
        this.deletions++;
        this.deletionsTotal++;
    }

    public int getLevelsGained() {
        return levelsGained;
    }

    public void addLevelsGained() {
        this.levelsGained++;
        this.levelsGainedTotal++;
    }

    public int getPlayerDeaths() {
        return playerDeaths;
    }

    public void addPlayerDeaths() {
        this.playerDeaths++;
        this.playerDeathsTotal++;
    }

    public int getMobDeaths() {
        return mobDeaths;
    }

    public void addMobDeaths() {
        this.mobDeaths++;
        this.mobDeathsTotal++;
    }

    public int getNumberOfLogins() {
        return numberOfLogins;
    }

    public void addNumberOfLogins() {
        this.numberOfLogins++;
        this.numberOfLoginsTotal++;
    }

    public int getMostOnline() {
        return mostOnline;
    }

    public int getNotesPosted() {
        return notesPosted;
    }

    public void addNotesPosted() {
        this.notesPosted++;
        this.notesPostedTotal++;
    }

    public int getNumberOfAuctions() {
        return numberOfAuctions;
    }

    public void addNumberOfAuctions() {
        this.numberOfAuctions++;
        this.numberOfAuctionsTotal++;
    }

    public int getNumberOfRemorts() {
        return numberOfRemorts;
    }

    public void addNumberOfRemorts() {
        this.numberOfRemorts++;
        this.numberOfRemortsTotal++;
    }

    public void checkMostOnline() {
        int playersOnline = World.getPlayers().size();
        if (playersOnline > mostOnline) {
            mostOnline = playersOnline;
        }
        if (playersOnline > mostOnlineTotal) {
            mostOnlineTotal = playersOnline;
        }
    }

    @Override
    public String toString() {
        return "MudStats{" +
                "newPlayers=" + newPlayers +
                ", deletions=" + deletions +
                ", levelsGained=" + levelsGained +
                ", playerDeaths=" + playerDeaths +
                ", mobDeaths=" + mobDeaths +
                ", numberOfLogins=" + numberOfLogins +
                ", mostOnline=" + mostOnline +
                ", notesPosted=" + notesPosted +
                ", numberOfAuctions=" + numberOfAuctions +
                ", numberOfRemorts=" + numberOfRemorts +
                ", newPlayersTotal=" + newPlayersTotal +
                ", deletionsTotal=" + deletionsTotal +
                ", levelsGainedTotal=" + levelsGainedTotal +
                ", playerDeathsTotal=" + playerDeathsTotal +
                ", mobDeathsTotal=" + mobDeathsTotal +
                ", numberOfLoginsTotal=" + numberOfLoginsTotal +
                ", mostOnlineTotal=" + mostOnlineTotal +
                ", notesPostedTotal=" + notesPostedTotal +
                ", numberOfAuctionsTotal=" + numberOfAuctionsTotal +
                ", numberOfRemortsTotal=" + numberOfRemortsTotal +
                '}';
    }
}
