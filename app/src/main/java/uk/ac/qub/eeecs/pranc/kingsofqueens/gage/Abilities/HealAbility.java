package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

/**
 * Created by markm on 23/02/2017.
 */

public class HealAbility extends OwnerEffectedAbility {

    int levelOne = 1, levelTwo = 2, levelThree = 3;


    public HealAbility(){
        hasAbility = true;
    }


    public void effect(String effectLevel){

        int add = 0;

        switch(effectLevel) {
            case "level3":
                add = levelThree;
                break;
            case "level2":
                add = levelTwo;
                break;
            case "level1":
                add = levelOne;
                break;


        }
        effectPlayer.healPlayer(add);

    }

}

