package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

/**
 * Created by markm on 08/02/2017.
 */

public class AddEvAbility extends OwnerEffectedAbility {


    public AddEvAbility(){
        HasAbility = true;
        this.levelOne   = 1;
        this.levelTwo   = 2;
        this.levelThree = 3;
    }

    public void effect(int effectLevel){
       super.effect(effectLevel);
        effectPlayer.addToEvTotal(effect);
    }


}
