/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.common;

/**
 * Provided to allow state changes in response to the passage of time.
 * 
 * @author Ivan Stuart
 * 
 */
public interface Tickable {

	public String getId();

    public boolean tick();

}
