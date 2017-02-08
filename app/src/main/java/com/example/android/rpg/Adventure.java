package com.example.android.rpg;
import java.util.Random;
/**
 * Created by rojya on 2017-02-05.
 */

class Adventure {
    private Random rand = new Random();
    private String result = "";
    String go(String name) {
        int humanStandard = 10;
        Status status = statusGenerate(humanStandard, name);
        addStatus(status);
        for (int i = 0; i < 50; i++) {
            if (rand.nextInt(5) == 0){
                addMessage("");
                other(status);
            } else if (rand.nextInt(5) == 1){
                addMessage("");
                trap(status);
                if (status.getHP() == 0){
                    addNameMessage(status, "体力耗尽，无力前行");
                    break;
                }
            } else if (rand.nextInt(5) == 2){
                addMessage("");
                box(status);
            } else {
                addMessage("");
                encounter(status);
                if (status.getHP() == 0) {
                    addNameMessage(status, "体力耗尽，无力前行");
                    break;
                }
            }
        }
        return result;
    }
    private void other(Status status) {
        status.setHP(status.getHP() + 2);
        addNameMessage(status, "发现四周安全，休息之后，HP恢复2");
        addStatus(status);
    }
    private void trap(Status status) {
        addNameMessage(status, "遇上陷阱");
        if (rand.nextInt(11) > status.getLUK()){
            addNameMessage(status, "落入陷阱，HP减少2");
            if (status.getHP() - 2 < 0) {
                status.setHP(0);
            } else {
                status.setHP(status.getHP() - 2);
            }
        } else {
            addNameMessage(status, "躲过一劫");
        }
        addStatus(status);
    }
    private void box(Status status){
        addNameMessage(status, "打开宝箱");
        if (rand.nextInt(5) == 0){
            status.setATK(status.getATK() + 1);
            addNameMessage(status, "发现并喝下攻药水，攻击加1");
        } else if (rand.nextInt(5) == 1){
            status.setDEF(status.getDEF() + 1);
            addNameMessage(status, "发现并喝下防药水，防御加1");
        } else if (rand.nextInt(5) == 2){
            status.setSPD(status.getSPD() + 1);
            addNameMessage(status, "发现并喝下速药水，速度加1");
        } else if (rand.nextInt(5) == 3){
            status.setTEC(status.getTEC() + 1);
            addNameMessage(status, "发现并喝下技药水，技艺加1");
        } else {
            status.setLUK(status.getLUK() + 1);
            addNameMessage(status, "发现并喝下运药水，运气加1");
        }
        addStatus(status);
    }
    private void encounter(Status status){
        addNameMessage(status, "遇上怪兽");
        int monsterStandard = 7;
        Status monsterStatus = statusGenerate(monsterStandard, "怪兽");
        addStatus(monsterStatus);
        if (status.getSPD() >= monsterStatus.getSPD()){
            for (int i = 1; i > 0; i++){
                addMessage("*********");
                addMessage(attack(status, monsterStatus));
                addStatus(monsterStatus);
                if (monsterStatus.getHP() == 0) {
                    addNameMessage(status, "打倒怪兽，获得胜利");
                    if (status.getLUK() >= rand.nextInt(11)) {
                        addNameMessage(status, "发现宝箱");
                        box(status);
                    }
                    break;
                }
                addMessage("*********");
                addMessage(attack(monsterStatus,status));
                addStatus(status);
                if (status.getHP() == 0){
                    addNameMessage(status, "被怪兽击败");
                    break;
                }
            }
        } else {
            for (int i = 1; i > 0; i++) {
                addMessage("*********");
                addMessage(attack(monsterStatus,status));
                addStatus(status);
                if (status.getHP() == 0){
                    addNameMessage(status, "被怪兽击败");
                    break;
                }
                addMessage("*********");
                addMessage(attack(status, monsterStatus));
                addStatus(monsterStatus);
                if (monsterStatus.getHP() == 0) {
                    addNameMessage(status, "打倒怪兽，获得胜利");
                    if (status.getLUK() >= rand.nextInt(11)) {
                        addNameMessage(status, "发现宝箱");
                        box(status);
                    }
                    break;
                }
            }
        }
    }
    private Status statusGenerate(int standard, String name){
        Status statusN = new Status();
        statusN.setHP(rand.nextInt(standard) + standard);
        statusN.setATK(rand.nextInt(standard));
        statusN.setDEF(rand.nextInt(standard));
        statusN.setSPD(rand.nextInt(standard));
        statusN.setTEC(rand.nextInt(standard));
        statusN.setLUK(rand.nextInt(standard));
        statusN.setName(name);
        return statusN;
    }
    private void addNameMessage(Status status, String message){
        result = result + "\n" + status.getName() + message;
    }
    private void addMessage(String message){
        result = result + "\n" + message;
    }
    private void addStatus(Status status){
        result = result + "\n" + status.getName() + "\nHP:" + status.getHP() + "  ATK:" + status.getATK() + "  DEF:" + status.getDEF() + "\nSPD:" + status.getSPD() + "  TEC:" + status.getTEC() + "  LUK:" + status.getLUK();
    }
    private String attack(Status attackStatus, Status defenceStatus){
        String result = attackStatus.getName() + "攻击" + defenceStatus.getName();
        Random battleRand = new Random();
        if (defenceStatus.getLUK() - attackStatus.getLUK() > battleRand.nextInt(11)){
            result = result + "\n" + defenceStatus.getName() + "躲过攻击";
        } else {
            int damage = attackStatus.getATK() - defenceStatus.getDEF();
            if (damage <= 0) {
                damage = 1;
            }
            if (attackStatus.getTEC() >= defenceStatus.getTEC()) {

                if (attackStatus.getTEC() - defenceStatus.getTEC() > battleRand.nextInt(11)) {
                    damage = (int) (damage * 2.5);
                    result = result + "\n暴击！";
                }
            }
            if (attackStatus.getSPD() >= defenceStatus.getSPD() * 2) {
                damage = damage * 2;
                result = result + "\n二次攻击！";
            }
            defenceStatus.setHP(defenceStatus.getHP() - damage);
            if (defenceStatus.getHP() < 0) {
                defenceStatus.setHP(0);
            }
            result = result + "\n" + "造成" + damage + "点伤害";
        }
        return result;
    }
}