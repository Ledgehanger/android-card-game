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

public class  Hand {
    //The max number of cards a player can have in their hand is 3 cards(temp)
    public final static int maxHandSize = 5;
    public final static int startingHandSize = 3;
    //These are the cooirdinates for the Cards on the field
    final static float posX = 290;
    final static float pos1Y = 195;
    final static float pos2Y = 235;
    final static float pos3Y = 275;
    protected  ArrayList<Card> myHand = new ArrayList<>();
    Card[] Hand = new Card[maxHandSize];


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

    public Hand(Card [] fromDeck){
        for (Card c : fromDeck){
            myHand.add(c);
        };
    }

    public void AddToHand(Card [] fromDeck){
        for (Card c : fromDeck){
            if(myHand.size() < maxHandSize)
            myHand.add(c);
        };
    }

    public Card RemoveFromHand(int index){
        if(index >= 0 && index < maxHandSize)
            return myHand.remove(index);
        else
            return null;
    }


}
