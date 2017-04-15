package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

/**
 * Created by markm on 08/02/2017.
 */

public class AddEvAbility extends OwnerEffectedAbility {
    private int levelOne = 1;
    private int levelTwo = 2;
    private int levelThree = 3;

    public AddEvAbility(){
        hasAbility = true;
    }

    public void effect(int effectLevel){
        hasAbility = true;
        int add = 0;

        switch(effectLevel) {
            case 3:
                add = levelThree;
                break;
            case 2:
                add = levelTwo;
                break;
            case 1:
                add = levelOne;
                break;


        }
        effectPlayer.addToEvTotal(add);

    }


}
