package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.Tickable;

/**
 * Created by Ivan on 15/08/2016.
 */
public class BehaviourFactory {

    public static BaseBehaviour create(String behaviour) {

        // TODO use refection instead of if checks.
        if (behaviour.indexOf("AGGRO") > -1) {
            return new Aggressive();
        }

        if (behaviour.indexOf("SLEEP") > -1) {
            return new Sleeping();
        }

        if (behaviour.indexOf("WANDER") > -1) {
            return new Wander();
        }

        return null;

    }
}
