/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.person;

import java.io.Serializable;

/**
 * Created by Ivan on 11/09/2016.
 */
public class ClanMembership implements Serializable {
    private static final long serialVersionUID = 1L;
    private int clanId;
    private int level;

    public int getClanId() {
        return clanId;
    }

    public void setClanId(int clanId) {
        this.clanId = clanId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void promote() {
        if (level < 9) {
            this.level++;
        }
    }

    public void demote() {
        if (level > 1) {
            this.level--;
        }
    }
}
