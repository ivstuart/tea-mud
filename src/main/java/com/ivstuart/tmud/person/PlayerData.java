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

    public int getKillpoints() {
        return killpoints;
    }

    public void setKillpoints(int killpoints) {
        this.killpoints = killpoints;
    }

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

    public Attribute getAlignment() {
        return alignment;
    }

    public String getEmail() {
        return email;
    }

    public Attribute getHunger() {
        return hungerAttribute;
    }

    public int getKills() {
        return kills;
    }

    public String getLastToldBy() {
        return lastToldBy;
    }

    public int getLearns() {
        return learns;
    }

    public int getLevel() {
        return level;
    }

    public String getPassword() {
        return password;
    }

    public int getPlayingFor() {
        return playingFor;
    }

    public int getPracs() {
        return pracs;
    }

    public int getRemorts() {
        return remorts;
    }

    public Attribute getThirst() {
        return thirstAttribute;
    }

    public String getTitle() {
        return title;
    }

    public int getToLevelXp() {
        return toLevelXp;
    }

    public int getTotalXp() {
        return totalXp;
    }

    public void incrementLevel() {
        level++;
        toLevelXp += (10000 * level ^ 4);
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

    public void setAge(int age) {
        this.age = age;
    }

    public void setAlignment(Attribute alignment) {
        this.alignment = alignment;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHunger(int hunger_) {
        hungerAttribute.setValue(hunger_);
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setLastToldBy(String lastToldBy) {
        this.lastToldBy = lastToldBy;
    }

    public void setLearns(int learns_) {
        learns = learns_;
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

    public void setPlayingFor(int playingFor) {
        this.playingFor = playingFor;
    }

    public void setPracs(int sp_) {
        pracs = sp_;
    }

    public void setRemort(int remorts_) {
        remorts = remorts_;
    }

    public void setThirst(int thirst_) {
        thirstAttribute.setValue(thirst_);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setToLevelXp(int toLevelXp) {
        this.toLevelXp = toLevelXp;
    }

    public void setTotalXp(int totalXp) {
        this.totalXp = totalXp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getEvasion() {
        return evasion;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    public void setStealth(int stealth) {
        this.stealth = stealth;
    }

    public void setPerception(int perception) {
        this.perception = perception;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getStealth() {
        return stealth;
    }

    public int getPerception() {
        return perception;
    }

    public int getDeaths() {
        return deaths;
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
}
