package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

/**
 * Created by markm on 08/02/2017.
 */

public class AddEvAbility extends OwnerEffectedAbility {

    int levelOne = 1, levelTwo = 2, levelThree = 3;


    public AddEvAbility(){
        hasAbility = true;
    }

    public void effect(String effectLevel){
        hasAbility = true;
        int add = 0;

        switch(effectLevel.toLowerCase()) {
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
        effectPlayer.addToEvTotal(add);

    }


}
