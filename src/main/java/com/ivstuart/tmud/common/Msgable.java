/*
 *  Copyright 2016. Ivan Stuart
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

    boolean isPlayer();
}
