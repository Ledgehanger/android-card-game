package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;
/**
 * Created by Paddy_Lenovo on 22/11/2016.
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import uk.ac.qub.eeecs.pranc.kingsofqueens.R;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;

public class Hand {
    //The max number of cards a player can have in their hand is 3 cards(temp)
    final static int maxHandSize = 3;
    //These are the coordinates for the Cards on the field
    final static float posX = 290;
    final static float pos1Y = 195;
    final static float pos2Y = 235;
    final static float pos3Y = 275;

    Card[] Hand = new Card[maxHandSize];

    public Hand(Card[] hand) {
        Hand = hand;
    }

    //Counts how many cards is currently in the hand and returns it as an int
    public int CheckHandSize(){
        int needCards = 0;
        for(int i = 0; i< maxHandSize; i++){
            if(Hand[i] == null)
                needCards++;
        }
        return needCards;
    }

    //Updates the position of the cards on the player screen as they are played
    public void CardPositionUpdate(){

    }
    //When cards are played they need to be removed from the Hand
    public void removeCard(int cardPostion){
        Hand[0] = null;


    }
}
