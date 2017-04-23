package uk.ac.qub.eeecs.pranc.kingsofqueens.gage;
import android.graphics.Rect;
import android.os.DeadObjectException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.Ability;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.AddEvAbility;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.DealDamageAbility;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.Default;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.OwnerEffectedAbility;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Card;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Player;

import static android.content.ContentValues.TAG;

/**
 * Created by markm on 06/02/2017.
 */

public class genAlgorithm {


    public enum field {
        TOP,
        BOTTOM
    }

    // Based on Knuth's shuffle algorithm
    public static void knuthShuffle(List<Card> array){
        int totalSize = array.size();
        for(int count = 0; count < totalSize; count++){
            int randomPos = count + (int) (Math.random() * (totalSize - count));

            Card swap = array.get(randomPos);
            array.set(randomPos,array.get(count));
            array.set(count, swap);
        }
    }

    public static void useCardAbility(Card pCard,Player pPlayer, Player pEnemyPlayer) {
        if(pCard.ability.getHasAbility()){
            if(pCard.ability instanceof OwnerEffectedAbility){
                ((OwnerEffectedAbility) pCard.ability).addEffectPlayer(pPlayer);
            }
            if(pCard.ability instanceof DealDamageAbility){
                ((DealDamageAbility) pCard.ability).addEffectPlayer(pEnemyPlayer);
            }
            pCard.ability.effect(pCard.abilityLvl);
        }
    }

    public static boolean hasTouchEvent(TouchEvent touchEvent, Rect pRect) {
        return pRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0;
    }


}
