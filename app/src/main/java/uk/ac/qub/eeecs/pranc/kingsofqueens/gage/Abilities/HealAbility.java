package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

/**
 * Created by markm on 23/02/2017.
 */

public class HealAbility extends OwnerEffectedAbility {

    public HealAbility(){
        HasAbility = true;
        this.levelOne = 1;
        this.levelTwo = 2;
        this.levelThree = 3;
    }


    public void effect(int effectLevel){
        super.effect(effectLevel);
        effectPlayer.healPlayer(effect);

    }

}

