package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.Ability;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Player;

/**
 * Created by markm on 08/02/2017.
 */

public class AddEvAbility extends Ability {

    int levelOneEv = 1, levelTwoEv = 2, levelThreeEv = 3;


    public AddEvAbility(){
        hasAbility = true;
    }

    public void effect(Player effectedPlayer, String effectLevel){
        hasAbility = true;
        int add = 0;

        switch(effectLevel) {
            case "level3":
                add = levelThreeEv;
                break;
            case "level2":
                add = levelTwoEv;
                break;
            case "level1":
                add = levelOneEv;
                break;


        }
        effectedPlayer.addToEvTotal(add);

    }


}
