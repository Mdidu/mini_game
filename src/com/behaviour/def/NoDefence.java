package com.behaviour.def;

public class NoDefence implements Defence {
    /**
     *
     * @return String
     */
    @Override
    public String block() {
        return "Vous ne vous défendez pas !";
    }
}
