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
 * Author Mark McAleese (40177285) all methods
 */
public class genAlgorithm {


    public enum field {
        TOP,
        BOTTOM
    }

    // Based on Knuth's shuffle algorithm
    // https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
    public static void knuthShuffle(List<Card> array){
        int totalSize = array.size();
        int randomPos;
        for(int count = 0; count < totalSize; count++){
            if(count != totalSize/2)
                randomPos = (int) (Math.random() * (totalSize - count));
            else
                randomPos = count + (int) (Math.random() * (totalSize - count));

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

    public static boolean hasTouchEventType0(TouchEvent touchEvent, Rect pRect) {
        return pRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0;
    }


}
