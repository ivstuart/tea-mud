/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
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
            String classprefix = LaunchMud.getMudServerClassPrefix() + "person.statistics.diseases.";
            return (Disease) Class.forName(classprefix + name).newInstance();
        } catch (Exception e) {
            LOGGER.error("Problem creating new disease instance", e);
        }

        LOGGER.warn("Disease [" + name + "] does not exists");

        return null;
    }
}
