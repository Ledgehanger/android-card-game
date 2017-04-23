package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

/**
 * Created by mark on 20/04/2017.
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

