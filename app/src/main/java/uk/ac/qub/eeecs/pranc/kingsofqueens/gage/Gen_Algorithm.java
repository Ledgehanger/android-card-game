package uk.ac.qub.eeecs.pranc.kingsofqueens.gage;
import java.util.ArrayList;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.Ability;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Card;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Deck;

/**
 * Created by markm on 06/02/2017.
 */

public class Gen_Algorithm {
    // Based on Knuth's shuffle algorithm
    public enum field {
        top,
        bottom
    }
    public static void KnuthShuffle(ArrayList<Card> array){
        int totalSize = array.size();
        for(int count = 0; count < totalSize; count++){
            int randomPostion = count + (int) (Math.random() * (totalSize - count));
            Card swap = array.get(randomPostion);
            array.set(randomPostion,array.get(count));
            array.set(count, swap);
        }
    }

    public static Ability findAbility(String path){
        try{
            Ability toReturn;
            toReturn = (Ability) Class.forName(path).newInstance();
            return toReturn;
        }catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

}
