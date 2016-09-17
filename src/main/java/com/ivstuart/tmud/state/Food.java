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

package com.ivstuart.tmud.state;

public class Food extends Item {

	private static final long serialVersionUID = -6124978537993047365L;

	private int _portions = 2;
    private boolean isCookable;
    private boolean isSaltable;

	public Food() {

	}

    public boolean isSaltable() {
        return isSaltable;
    }

    public void setSaltable(boolean saltable) {
        isSaltable = saltable;
    }

    public boolean isCookable() {
        return isCookable;
    }

    public void setCookable(boolean cookable) {
        isCookable = cookable;
    }

	public void eat() {
		_portions--;
	}

	public int getPortions() {
		return _portions;
	}

    public void setPortions(int drafts_) {
        _portions = drafts_;
    }

	public void setNumberPortions(int drafts_) {
		_portions = drafts_;
	}
}
