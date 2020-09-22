package com.characters;

import com.behaviour.*;
import com.behaviour.def.Defence;
import com.behaviour.def.LittleShield;
import com.behaviour.off.Attack;
import com.behaviour.off.AxeAttack;

// class qui attaque fortement et encaisse peu
public class Viking extends Character {
    public Viking() {
        vikingStat();
        this.attack = new AxeAttack();
        this.defence = new LittleShield();
    }
    public Viking(Attack attack, Defence defence, Heal heal) {
        super(attack, defence, heal);
        vikingStat();
    }

    public void vikingStat() {
        this.hp += 5;
        this.strength += 3;
        this.resistance += 1;
        this.weapon = 11;
        this.shield = 3;
    }
}
