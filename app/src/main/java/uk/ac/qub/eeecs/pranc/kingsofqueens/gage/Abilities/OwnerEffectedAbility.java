package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Player;

/**
 * Created by mark on 10/04/2017.
 */

public abstract class OwnerEffectedAbility implements Ability {
    protected boolean hasAbility = true;
    protected Player effectPlayer;

    public void effect(int effectLevel){}

    public boolean getHasAbility(){return hasAbility;};

    public void addEffectPlayer(Player pPlayer){
        this.effectPlayer = pPlayer;
    }

}
