package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

/**
 * Author Mark McAleese (40177285) all methods
 */

public class HealAbility extends OwnerEffectedAbility {

    public HealAbility(){
        HasAbility = true;
        this.levelOne = 1;
        this.levelTwo = 3;
        this.levelThree = 5;
    }


    public void effect(int effectLevel){
        super.effect(effectLevel);
        effectPlayer.healPlayer(effect);

    }

}

