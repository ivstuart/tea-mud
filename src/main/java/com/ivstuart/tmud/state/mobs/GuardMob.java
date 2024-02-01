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

package com.ivstuart.tmud.state.mobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuardMob extends Mob {

    /**
     *
     */
    private static final long serialVersionUID = -3758337691304906202L;

    private static final Logger LOGGER = LogManager.getLogger();

    private String exit;
    private boolean isAlignmentGuard;

    public GuardMob() {
        super();
        setMobEnum(MobEnum.GUARD);
    }

    public GuardMob(GuardMob baseMob) {
        super(baseMob);
        this.exit = baseMob.exit;
        isAlignmentGuard = baseMob.isAlignmentGuard;
        setMobEnum(MobEnum.GUARD);
    }

    public boolean isAlignmentGuard() {
        return isAlignmentGuard;
    }

    public void setAlignmentGuard(boolean alignmentGuard) {
        isAlignmentGuard = alignmentGuard;
    }

    @Override
    public boolean isGuarding(Mob mob_, String id) {

        LOGGER.debug("id [ " + id + " ] exit [ " + exit + " ]");

        if (isDead()) {
            return false;
        }

        if (isAlignmentGuard && mob_.isGood() == this.isGood()) {
            LOGGER.debug("Alignment guard and they match so may pass");
            return false;
        }

        return id.equals(exit);

    }

    public void setGuardExit(String direction) {
        this.exit = direction;
    }
}
