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

package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.server.LaunchMud;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Ivan on 15/08/2016.
 */
public class BehaviourFactory {

    private static final Logger LOGGER = LogManager.getLogger();

    public static BaseBehaviour create(String behaviour) {

        String className = behaviour;
        if (behaviour.indexOf(":") > -1) {
            className = behaviour.split(":",2)[0];
        }

        BaseBehaviour bb = createClass(className);
        initBehaviour(behaviour, bb);

        return bb;

    }

    private static void initBehaviour(String behaviour, BaseBehaviour baseBehaviour) {
        if (behaviour.indexOf(":") > -1) {
            String input[] = behaviour.split(":");
            if (input.length > 1) {
                int parameter = Integer.parseInt(input[1]);
                baseBehaviour.setParameter(parameter);
            }
            if (input.length > 2) {
                int parameter2 = Integer.parseInt(input[2]);
                baseBehaviour.setParameter2(parameter2);
            }
            if (input.length > 3) {
                int parameter3 = Integer.parseInt(input[3]);
                baseBehaviour.setParameter2(parameter3);
            }
        }
    }

    private static BaseBehaviour createClass(String name) {
        try {
            String classprefix = LaunchMud.getMudServerClassPrefix() + "behaviour.";
            return (BaseBehaviour) Class.forName(classprefix + name).newInstance();
        } catch (Exception e) {
            LOGGER.error("Problem creating new behaviour instance", e);
        }

        LOGGER.warn("Behaviour [" + name + "] does not exists");

        return null;
    }
}
