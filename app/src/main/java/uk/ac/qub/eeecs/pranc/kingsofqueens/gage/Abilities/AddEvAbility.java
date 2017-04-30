package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

/**
 * Author Mark McAleese (40177285) all methods
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
