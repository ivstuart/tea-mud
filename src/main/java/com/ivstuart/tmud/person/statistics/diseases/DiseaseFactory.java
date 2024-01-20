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

package com.ivstuart.tmud.person.statistics.diseases;

import com.ivstuart.tmud.server.LaunchMud;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Ivan on 06/09/2016.
 */
public class DiseaseFactory {

    private static final Logger LOGGER = LogManager.getLogger();

    public static Disease createClass(String name) {
        try {
            String classPrefix = LaunchMud.getMudServerClassPrefix() + "person.statistics.diseases.";
            return (Disease) Class.forName(classPrefix + name).newInstance();
        } catch (Exception e) {
            LOGGER.error("Problem creating new disease instance", e);
        }

        LOGGER.warn("Disease [" + name + "] does not exists");

        return null;
    }
}
