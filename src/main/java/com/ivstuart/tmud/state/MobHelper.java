package com.ivstuart.tmud.state;

public class MobHelper {

    /**
     * @return age string
     */
    public static String getAge(Mob mob) {
        if (mob.isPlayer()) {
            int remorts = mob.getPlayer().getData().getRemorts();
            switch (remorts) {
                case 0:
                    return "young";
                case 1:
                    return "youthful";
                case 2:
                    return "middle aged";
                case 3:
                    return "mature";
                case 4:
                    return "old";
                case 5:
                    return "very old";
                case 6:
                    return "ancient";
                default:
                    return "unknown";
            }
        }
        return "";
    }


    /**
     * Based on level
     *
     * @return size of mob
     */
    public static String getSize(int level) {
        if (level < 10) {
            return "small";
        } else if (level < 20) {
            return "medium";
        } else if (level < 40) {
            return "big";
        } else if (level < 60) {
            return "large";
        } else if (level < 80) {
            return "huge";
        } else {
            return "massive";
        }
    }
}
