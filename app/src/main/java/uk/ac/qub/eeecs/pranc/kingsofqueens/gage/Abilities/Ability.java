package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Player;

/**
 * Created by markm on 08/02/2017.
 */

public abstract class Ability {

    public abstract void effect(Player effectedPlayer, String effectLevel);

}

