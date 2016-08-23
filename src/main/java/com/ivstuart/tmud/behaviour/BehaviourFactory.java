package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.Tickable;

/**
 * Created by Ivan on 15/08/2016.
 */
public class BehaviourFactory {

    public static BaseBehaviour create(String behaviour) {

        // TODO use refection instead of if checks.
        if (behaviour.indexOf("AGGRO") > -1) {
            Aggressive aggressive = new Aggressive();
            initBehaviour(behaviour, aggressive);
            return aggressive;
        }

        if (behaviour.indexOf("SLEEP") > -1) {
            Sleeping sleeping = new Sleeping();
            initBehaviour(behaviour, sleeping);
            return sleeping;
        }

        if (behaviour.indexOf("WANDER") > -1)
        {
            Wander wander = new Wander();
            initBehaviour(behaviour, wander);
            return wander;
        }

        if (behaviour.indexOf("JANITOR") > -1)
        {
            Janitor janitor = new Janitor();
            initBehaviour(behaviour, janitor);
            return janitor;
        }

        if (behaviour.indexOf("STEAL") > -1)
        {
            Stealer stealer = new Stealer();
            initBehaviour(behaviour, stealer);
            return stealer;
        }

        return null;

    }

    private static void initBehaviour(String behaviour, BaseBehaviour aggressive) {
        if (behaviour.indexOf(":") > -1) {
            String input[] = behaviour.split(":");
            if (input.length > 1) {
                int parameter = Integer.parseInt(input[1]);
                aggressive.setParameter(parameter);
            }
            if (input.length > 2) {
                int parameter2 = Integer.parseInt(input[2]);
                aggressive.setParameter2(parameter2);
            }
        }
    }
}
