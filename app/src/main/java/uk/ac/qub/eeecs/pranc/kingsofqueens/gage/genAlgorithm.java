package uk.ac.qub.eeecs.pranc.kingsofqueens.gage;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.Ability;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Card;

import static android.content.ContentValues.TAG;

/**
 * Created by markm on 06/02/2017.
 */

public class genAlgorithm {
    // Based on Knuth's shuffle algorithm
    public enum field {
        TOP,
        BOTTOM
    }
    public static void knuthShuffle(List<Card> array){
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
            Log.e(TAG, "InstantiationException: "+ e.getMessage());
            return null;
            } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Can not find Class: " + path);
            return null;
        }

    }

}
