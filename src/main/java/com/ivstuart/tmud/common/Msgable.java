/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.common;

import java.util.List;

public interface Msgable {

	public Gender getGender();

	public String getId();

	public String getName();

	public List<String> getSenseFlags();

	public boolean hasDetectHidden();

	public boolean hasDetectInvisible();

	public boolean hasSeeInDark();

	public boolean isBlinded();

	public boolean isHidden();

	public boolean isInDark();

	public boolean isInvisible();

	public boolean isSleeping();

	public void out(Msg message);

	public boolean isGood();

	public String getRaceName();
}
