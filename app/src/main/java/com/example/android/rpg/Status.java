package com.example.android.rpg;

/**
 * Created by rojya on 2017-02-05.
 */

public class Status {
    private int HP;
    private int ATK;
    private int DEF;
    private int SPD;
    private int TEC;
    private int LUK;
    private String name;
    public void setHP(int hp){
        HP = hp;
    }
    public void setATK(int atk){
        ATK = atk;
    }
    public void setDEF(int def){
        DEF = def;
    }
    public void setSPD(int spd){
        SPD = spd;
    }
    public void setTEC(int tec){
        TEC = tec;
    }
    public void setLUK(int luk){
        LUK = luk;
    }
    public void setName(String n){
        name = n;
    }
    public int getHP(){
        return HP;
    }
    public int getATK(){
        return ATK;
    }
    public int getDEF(){
        return DEF;
    }
    public int getSPD(){
        return SPD;
    }
    public int getTEC(){
        return TEC;
    }
    public int getLUK(){
        return LUK;
    }
    public String getName(){
        return name;
    }
}
