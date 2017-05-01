package uk.ac.qub.eeecs.pranc.kingsofqueens.gage;
import android.graphics.Rect;

import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.DealDamageAbility;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.OwnerEffectedAbility;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.GameObjects.Card;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.GameObjects.Player;

/**
 * Author Mark McAleese (40177285) all methods
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

    public static boolean hasTouchEventType0(TouchEvent touchEvent, Rect pRect) {
        return pRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0;
    }


}
