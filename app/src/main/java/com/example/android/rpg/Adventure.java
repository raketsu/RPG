package com.example.android.rpg;
import java.util.Random;
/**
 * Created by rojya on 2017-02-05.
 */

class Adventure {
    private int r;
    String go(String name) {
        Random statusRand = new Random();
        int humanStandard = 10;
        Status status = new Status(statusRand.nextInt(humanStandard) + humanStandard, statusRand.nextInt(humanStandard), statusRand.nextInt(humanStandard), statusRand.nextInt(humanStandard), statusRand.nextInt(humanStandard), statusRand.nextInt(humanStandard), name);
        status = addStatus(status);
        for (int i = 0; i < 50; i++) {
            Random eventRand = new Random();
            r = eventRand.nextInt(5);
            if (r == 0){
                status = addBattleMessage(status, "\n");
                other(status);
            } else if (r == 1){
                status = addBattleMessage(status, "\n");
                trap(status);
                if (status.getHP() == 0){
                    status = addMessage(status, "体力耗尽，无力前行");
                    break;
                }
            } else if (r == 2){
                status = addBattleMessage(status, "\n");
                box(status);
            } else {
                status = addBattleMessage(status, "\n");
                encounter(status);
                if (status.getHP() == 0) {
                    status = addMessage(status, "体力耗尽，无力前行");
                    break;
                }
            }
        }
        return status.getMessage();
    }
    private Status other(Status status) {
        Status otherStatus = status;
        otherStatus.setHP(otherStatus.getHP() + 2);
        otherStatus = addMessage(otherStatus, "发现四周安全，休息之后，HP恢复2");
        otherStatus = addStatus(otherStatus);
        return otherStatus;
    }
    private Status trap(Status status) {
        Status trapStatus = status;
        trapStatus = addMessage(trapStatus, "遇上陷阱");
        Random trapRand = new Random();
        if (trapRand.nextInt(11) > trapStatus.getLUK()){
            trapStatus = addMessage(trapStatus, "落入陷阱，HP减少2");
            if (trapStatus.getHP() - 2 < 0) {
                trapStatus.setHP(0);
            } else {
                trapStatus.setHP(trapStatus.getHP() - 2);
            }
        } else {
            trapStatus = addMessage(trapStatus, "躲过一劫");
        }
        trapStatus = addStatus(trapStatus);
        return trapStatus;
    }
    private Status box(Status status){
        Status boxStatus = status;
        boxStatus = addMessage(boxStatus, "打开宝箱");
        Random boxRand = new Random();
        int r = boxRand.nextInt(5);
        if (r == 0){
            boxStatus.setATK(boxStatus.getATK() + 1);
            boxStatus = addMessage(boxStatus, "发现并喝下攻药水，攻击加1");
        } else if (r == 1){
            boxStatus.setDEF(boxStatus.getDEF() + 1);
            boxStatus = addMessage(boxStatus, "发现并喝下防药水，防御加1");
        } else if (r == 2){
            boxStatus.setSPD(boxStatus.getSPD() + 1);
            boxStatus = addMessage(boxStatus, "发现并喝下速药水，速度加1");
        } else if (r == 3){
            boxStatus.setTEC(boxStatus.getTEC() + 1);
            boxStatus = addMessage(boxStatus, "发现并喝下技药水，技艺加1");
        } else {
            boxStatus.setLUK(boxStatus.getLUK() + 1);
            boxStatus = addMessage(boxStatus, "发现并喝下运药水，运气加1");
        }
        boxStatus = addStatus(boxStatus);
        return boxStatus;
    }
    private Status encounter(Status status){
        Status encounterStatus = status;
        encounterStatus = addMessage(encounterStatus, "遇上怪兽");
        Random monsterStatusRand = new Random();
        int monsterStandard = 7;
        Status monsterStatus = new Status(monsterStatusRand.nextInt(monsterStandard) + monsterStandard * 2, monsterStatusRand.nextInt(monsterStandard), monsterStatusRand.nextInt(monsterStandard), monsterStatusRand.nextInt(monsterStandard), monsterStatusRand.nextInt(monsterStandard), monsterStatusRand.nextInt(monsterStandard), "怪兽");
        encounterStatus = addMonsterStatus(encounterStatus, monsterStatus);
        if (encounterStatus.getSPD() >= monsterStatus.getSPD()){
            for (int i = 1; i > 0; i++){
                encounterStatus = addBattleMessage(encounterStatus, "\n*******");
                encounterStatus = addBattleMessage(encounterStatus, attack(encounterStatus, monsterStatus));
                encounterStatus = addMonsterStatus(encounterStatus, monsterStatus);
                if (monsterStatus.getHP() == 0) {
                    encounterStatus = addMessage(encounterStatus, "打倒怪兽，获得胜利");
                    if (encounterStatus.getLUK() >= monsterStatusRand.nextInt(11)) {
                        encounterStatus = addMessage(encounterStatus, "发现宝箱");
                        box(status);
                    }
                    break;
                }
                encounterStatus = addBattleMessage(encounterStatus, "\n*******");
                encounterStatus = addBattleMessage(encounterStatus, attack(monsterStatus,encounterStatus));
                encounterStatus = addStatus(encounterStatus);
                if (encounterStatus.getHP() == 0){
                    encounterStatus = addMessage(encounterStatus, "被怪兽击败");
                    break;
                }
            }
        } else {
            for (int i = 1; i > 0; i++) {
                encounterStatus = addBattleMessage(encounterStatus, "\n*******");
                encounterStatus = addBattleMessage(encounterStatus, attack(monsterStatus, encounterStatus));
                encounterStatus = addStatus(encounterStatus);
                if (encounterStatus.getHP() == 0) {
                    encounterStatus = addMessage(encounterStatus, "被怪兽击败");
                    break;
                }
                encounterStatus = addBattleMessage(encounterStatus, "\n*******");
                encounterStatus = addBattleMessage(encounterStatus, attack(encounterStatus, monsterStatus));
                encounterStatus = addMonsterStatus(encounterStatus, monsterStatus);
                if (monsterStatus.getHP() == 0) {
                    encounterStatus = addMessage(encounterStatus, "打倒怪兽，获得胜利");
                    if (encounterStatus.getLUK() >= monsterStatusRand.nextInt(11)) {
                        encounterStatus = addMessage(encounterStatus, "发现宝箱");
                        box(status);
                    }
                    break;
                }
            }
        }
        return encounterStatus;
    }
    private Status addMessage(Status status, String message){
        status.setMessage(status.getMessage() +  "\n" + status.getName() + message);
        return status;
    }
    private Status addBattleMessage(Status status, String message){
        status.setMessage(status.getMessage() +  message);
        return status;
    }
    private Status addStatus(Status status){
        status.setMessage(status.getMessage() + "\n冒险者" + status.getName() + "\nHP:" + status.getHP() + "  ATK:" + status.getATK() + "  DEF:" + status.getDEF() + "\nSPD:" + status.getSPD() + "  TEC:" + status.getTEC() + "  LUK:" + status.getLUK());
        return status;
    }
    private Status addMonsterStatus(Status statusH, Status statusM){
        statusH.setMessage(statusH.getMessage() + "\n" + statusM.getName() + "\nHP:" + statusM.getHP() + "  ATK:" + statusM.getATK() + "  DEF:" + statusM.getDEF() + "\nSPD:" + statusM.getSPD() + "  TEC:" + statusM.getTEC() + "  LUK:" + statusM.getLUK());
        return statusH;
    }
    private String attack(Status attackStatus, Status defenceStatus){
        String result = "\n" + attackStatus.getName() + "攻击" + defenceStatus.getName();
        int damage = attackStatus.getATK() - defenceStatus.getDEF();
        if (damage <= 0){
            damage = 1;
        }
        if (attackStatus.getTEC() >= defenceStatus.getTEC()){
            Random battleRand = new Random();
            if (attackStatus.getTEC() - defenceStatus.getTEC()> battleRand.nextInt(11)){
                damage = (int)(damage * 2.5);
                result = result + "\n暴击！";
            }
        }
        if (attackStatus.getSPD() >= defenceStatus.getSPD() * 2){
            damage = damage * 2;
            result = result + "\n二次攻击！";
        }
        defenceStatus.setHP(defenceStatus.getHP() - damage);
        if (defenceStatus.getHP() < 0){
            defenceStatus.setHP(0);
        }
        result = result + "\n" + "造成" + damage + "点伤害";
        return result;
    }
}