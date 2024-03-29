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

package com.ivstuart.tmud.person;

import com.ivstuart.tmud.state.player.Attribute;
import com.ivstuart.tmud.state.mobs.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author stuarti
 */
public class PlayerData implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger();
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final Attribute thirstAttribute;
    private final Attribute hungerAttribute;
    private transient String password;
    private byte[] passwordDigest;
    private String email;
    private String title;
    private long totalXp;
    private long toLevelXp;
    private int xpForFighting;
    private int age; // years
    private long loginTime;
    private long playingTime;
    private int kills;
    private int remorts;
    private String lastToldBy; // Should be in the communicate code.
    private int learns;
    private int pracs;
    private int level;
    // New attributes since 2016
    private int evasion;
    private int stealth;
    private int perception;
    private int deaths;
    private int warpoints;
    private int tier;
    private int killpoints;
    private Attribute drunkAttribute;
    private Attribute poisonAttribute;
    private Attribute alignment;

    /**
     *
     */
    public PlayerData() {
        thirstAttribute = new Attribute("Thirst", 500);
        hungerAttribute = new Attribute("Hunger", 500);
        drunkAttribute = new Attribute("Drunk", 0, 500, 0);
        poisonAttribute = new Attribute("Poison", 0, 500, 0);
        level = 1;

    }

    public int getXpForFighting() {
        return xpForFighting;
    }

    public void setXpForFighting(int xpForFighting) {
        this.xpForFighting = xpForFighting;
    }

    public Attribute getDrunkAttribute() {
        return drunkAttribute;
    }

    public void setDrunkAttribute(Attribute drunkAttribute) {
        this.drunkAttribute = drunkAttribute;
    }

    public Attribute getPoisonAttribute() {
        return poisonAttribute;
    }

    public void setPoisonAttribute(Attribute poisonAttribute) {
        this.poisonAttribute = poisonAttribute;
    }

    public int getKillpoints() {
        return killpoints;
    }

    public void setKillpoints(int killpoints) {
        this.killpoints = killpoints;
    }

    public void addKill(Mob defender) {
        this.kills++;
        int xp = defender.getXp();
        totalXp += xp;
        toLevelXp -= xp;
    }

    public void addLearn() {
        learns++;
    }

    public void addXp(long xp_) {
        totalXp += xp_;
        toLevelXp -= xp_;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Attribute getAlignment() {
        return alignment;
    }

    public void setAlignment(Attribute alignment) {
        this.alignment = alignment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Attribute getHunger() {
        return hungerAttribute;
    }

    public void setHunger(int hunger_) {
        hungerAttribute.setValue(hunger_);
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public String getLastToldBy() {
        return lastToldBy;
    }

    public void setLastToldBy(String lastToldBy) {
        this.lastToldBy = lastToldBy;
    }

    public int getLearns() {
        return learns;
    }

    public void setLearns(int learns_) {
        learns = learns_;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("No algorithm found for password digest", e);
            return;
        }
        md.update(password.getBytes());
        this.passwordDigest = md.digest();
    }

    public int getPracs() {
        return pracs;
    }

    public void setPracs(int sp_) {
        pracs = sp_;
    }

    public int getRemorts() {
        return remorts;
    }

    public Attribute getThirst() {
        return thirstAttribute;
    }

    public void setThirst(int thirst_) {
        thirstAttribute.setValue(thirst_);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getToLevelXp() {
        return toLevelXp;
    }

    public void setToLevelXp(long toLevelXp) {
        this.toLevelXp = toLevelXp;
    }

    public long getTotalXp() {
        return totalXp;
    }

    public void setTotalXp(long totalXp) {
        this.totalXp = totalXp;
    }

    public void incrementLevel() {
        level++;
        toLevelXp += 1000 * ((long) level * level); // Better than
        learns++;
        pracs += 3;

    }

    public boolean learn() {
        if (learns > 0) {
            learns--;
            return true;
        }
        return false;
    }

    public boolean prac() {
        if (pracs > 0) {
            pracs--;
            return true;
        }
        return false;
    }

    public boolean isPasswordSame(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("No algorithm for digest found", e);
            return false;
        }
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return Arrays.equals(digest, passwordDigest);
    }

    public void setRemort(int remorts_) {
        remorts = remorts_;
    }

    public int getEvasion() {
        return evasion;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    public int getStealth() {
        return stealth;
    }

    public void setStealth(int stealth) {
        this.stealth = stealth;
    }

    public int getPerception() {
        return perception;
    }

    public void setPerception(int perception) {
        this.perception = perception;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void incrementDeaths() {
        deaths++;
    }

    public int getWarpoints() {
        return warpoints;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public long getPlayingTime() {
        return System.currentTimeMillis() - loginTime + playingTime;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public void incrementWarpoints(int points) {
        warpoints += points;
    }

    public void setPlayingTime() {
        playingTime = this.getPlayingTime();
    }

    public void addXpForFighting(int damage) {
        this.xpForFighting += damage;
    }

    public void restoreToPerfectHealth() {
        drunkAttribute.setValue(0);
        poisonAttribute.setValue(0);

        thirstAttribute.restore();
        hungerAttribute.restore();
    }
}
