package com.characters;

import com.behaviour.off.Attack;
import com.behaviour.def.Defence;
import com.behaviour.Heal;
import com.behaviour.off.NoAttack;
import com.behaviour.def.NoDefence;
import com.behaviour.NoHeal;

public abstract class Character {
    //  Character behaviour ( comportement)
    protected Attack attack = new NoAttack();
    protected Defence defence = new NoDefence();
    protected Heal heal = new NoHeal();
    // basic character stats
    protected int hp = 50;
    protected int strength = 2;
    protected int resistance = 2;
    protected int damage = 0;
    protected int weapon = 0;
    protected int shield = 0;
    // Total Character attack value
    protected static int[] playerHit = new int[2];
    // Total Character defence value
    protected static int[] playerDef = new int[2];
    // false = player1 | true = player2
    protected static boolean player = false;

    public Character() {}

    public Character(Attack attack, Defence defence, Heal heal) {
        this.attack = attack;
        this.defence = defence;
        this.heal = heal;
    }

    public String fight() {
        return attack.hit();
    }

    public String block() {
        return defence.block();
    }

    // calcul la force offensive d'un personnage pour un tour
    public void calcCharacterAttack() {
//        int characterHit;
        if(!player){
            playerHit[0] = this.strength + this.weapon;
            playerDef[0] = this.resistance;
            player = true; //Si bug sur les actions, bug 99% ici
        } else {
            playerHit[1] = this.strength + this.weapon;
            playerDef[1] = this.resistance;
            player = false;
        }
    }
    // calcul la force défensive d'un personnage pour un tour
    public void calcCharacterDef() {

        if (!player) {
            playerDef[0] = this.resistance + shield;
            playerHit[0] = 0;
            player = true;//Si bug sur les actions, bug 99% ici
        } else {
            playerDef[1] = this.resistance + shield;
            playerHit[1] = 0;
            player = false;
        }
    }

    /**
     *
     * @param bool boolean
     */
    private void calcDamages(boolean bool) {

        if(bool) {
            this.damage = playerHit[0] - playerDef[1];
        } else {
            this.damage = playerHit[1] - playerDef[0];
        }

    }

    /**
     * bool = false = P1 loose HP
     * bool = true = P2 loose HP
     * @param bool boolean
     */
    public void hpDecrease(boolean bool) {
        calcDamages(bool);
        if(this.damage > 0) { this.hp -= this.damage; }

        this.damage = 0;
    }
    public int getLife() { return this.hp; }
}
