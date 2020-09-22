package com.characters;

import com.behaviour.off.Attack;
import com.behaviour.def.Defence;
import com.behaviour.Heal;
import com.behaviour.off.SaberAttack;

// Class qui attaque et encaisse moyennement
public class Bushido extends Character {

    public Bushido() {
        bushidoStat();
        this.attack = new SaberAttack();
    }
    public Bushido(Attack attack, Defence defence, Heal heal) {
        super(attack, defence, heal);
        bushidoStat();
    }
    public void bushidoStat() {
        this.hp += 15;
        this.strength += 2;
        this.resistance += 2;
        this.weapon = 9;
    }
}
