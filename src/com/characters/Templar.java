package com.characters;

import com.behaviour.*;
import com.behaviour.def.Defence;
import com.behaviour.def.GreatShield;
import com.behaviour.off.Attack;
import com.behaviour.off.SwordAttack;

// Class qui tape faiblement et encaisse énormément
public class Templar extends Character {

    public Templar() {
        templarStat();
        this.attack = new SwordAttack();
        this.defence = new GreatShield();
    }

    public Templar(Attack attack, Defence defence, Heal heal) {
        super(attack, defence, heal);
        templarStat();
    }
    public void templarStat() {
        this.hp += 25;
        this.strength += 1;
        this.resistance += 3;
        this.weapon = 5;
        this.shield = 9;
    }
}
