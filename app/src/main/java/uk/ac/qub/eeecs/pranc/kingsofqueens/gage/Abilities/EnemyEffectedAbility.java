package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Player;

/**
 * Created by mark on 20/04/2017.
 */

public class EnemyEffectedAbility implements Ability {

    protected boolean hasAbility = true;
    protected Player effectPlayer;

    public void effect(int effectLevel){}

    public boolean getHasAbility(){return hasAbility;};

    public void addEffectPlayer(Player pPlayer){
        this.effectPlayer = pPlayer;
    }

}
