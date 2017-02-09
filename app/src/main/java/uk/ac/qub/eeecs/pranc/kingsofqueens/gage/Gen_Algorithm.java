package uk.ac.qub.eeecs.pranc.kingsofqueens.gage;
import java.util.ArrayList;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Card;
/**
 * Created by markm on 06/02/2017.
 */

public class Gen_Algorithm {
    // Based on Knuth's shuffle algorithm

    public static void KnuthShuffle(ArrayList<Card> array){
        int totalSize = array.size();
        for(int count = 0; count < totalSize; count++){
            int randomPostion = count + (int) (Math.random() * (totalSize - count));
            Card swap = array.get(randomPostion);
            array.set(randomPostion,array.get(count));
            array.set(count, swap);
        }
    }
}
