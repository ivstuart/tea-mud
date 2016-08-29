/*
 * Created on 08-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.person;

import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.Mob;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author stuarti
 */
public class PlayerData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private transient String password;
    private byte[] passwordDigest;

    private String email;

    private String title;

    private int totalXp; // TODO refactor into a statistic object

    private int toLevelXp;

    private int age; // years

    private int playingFor; // Minutes?
    private long loginTime;

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
    private Attribute thirstAttribute;
    private Attribute hungerAttribute;
    private Attribute alignment;
    /**
     *
     */
    public PlayerData() {
        thirstAttribute = new Attribute("Thirst", 500);
        hungerAttribute = new Attribute("Thirst", 500);
        level = 1;

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

    public void addXp(int xp_) {
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
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(password.getBytes());
        this.passwordDigest = md.digest();
    }

    public int getPlayingFor() {
        return playingFor;
    }

    public void setPlayingFor(int playingFor) {
        this.playingFor = playingFor;
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

    public int getToLevelXp() {
        return toLevelXp;
    }

    public void setToLevelXp(int toLevelXp) {
        this.toLevelXp = toLevelXp;
    }

    public int getTotalXp() {
        return totalXp;
    }

    public void setTotalXp(int totalXp) {
        this.totalXp = totalXp;
    }

    public void incrementLevel() {
        level++;
        toLevelXp += 10000 * Math.pow(level, 3);
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
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
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

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public void incrementWarpoints(int points) {
        warpoints += points;
    }
}
