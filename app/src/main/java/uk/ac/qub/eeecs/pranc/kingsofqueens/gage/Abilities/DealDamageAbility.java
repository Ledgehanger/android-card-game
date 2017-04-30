package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

/**
 * Author Mark McAleese (40177285) all methods
 */

public class DealDamageAbility extends EnemyEffectedAbility {

    public DealDamageAbility() {
        HasAbility = true;
        this.levelOne = 1;
        this.levelTwo = 2;
        this.levelThree = 3;
    }

    public void effect(int effectLevel){
        super.effect(effectLevel);
            effectPlayer.DamageTaken(effect);
    }


}

