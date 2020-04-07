package com.esiea.tp4A;

public class PlayerImplTest {
    private String name = "Spadabi";
    private double health = 50;
    private double range = 3.5;
    private double attack = 1.5;

    PlayerImplTest(){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHealth() {
        return health;

    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }
}
