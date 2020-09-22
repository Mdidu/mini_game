package com.behaviour.off;

public class NoAttack implements Attack {
    /**
     *
     * @return String
     */
    @Override
    public String hit() {
        return "Vous n'attaquez pas !";
    }
}
