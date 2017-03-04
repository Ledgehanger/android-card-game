package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;
import android.graphics.Rect;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.GenAlgorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameObject;

import static android.content.ContentValues.TAG;

public class Deck{


    private boolean deckIsEmpty = true;

    public static final int SIZEOFCLASSDECK = 3;
    public static final int SIZEOFNEUTRALDECK = 2;

    public static final String NEUTRAL = "Neutral";
    public static final String DECKPATH = "Decks/";
    public static final String JSON = ".json";

    private Rect deckRect;
    private ArrayList<Card> playerDeck = new ArrayList<> ();

    public Deck(){
        deckRect = null;
    }

    public Deck(Game pGame,String pDeck1, String pDeck2){

        loadDecksIntoAssestManger(pGame,pDeck1, pDeck2);
        setDeckUp(pGame.getAssetManager(), pDeck1, pDeck2);
    }
    // Refactor json string joining into a method so it gets called instead of d// doing it over and over again
    public boolean loadDecksIntoAssestManger(Game game, String pDeck1, String pDeck2){
        addJsonToAssetManager(game,NEUTRAL);
        return addJsonToAssetManager(game,pDeck1) && addJsonToAssetManager(game,pDeck2);

    }

    public boolean addJsonToAssetManager(Game game,String deckName) {
        return game.getAssetManager().loadAndAddJson(deckName, DECKPATH + deckName + JSON);
    }

    public void shuffle(){
        GenAlgorithm.knuthShuffle(playerDeck);
    }

    public void setDeck( Card [] pDeck1,  Card [] pDeck2,  Card [] pNeutralDeck){


        addCardsToDeck(pDeck1      , SIZEOFCLASSDECK);
        addCardsToDeck(pDeck2      , SIZEOFCLASSDECK);
        addCardsToDeck(pNeutralDeck, SIZEOFNEUTRALDECK);

        deckIsEmpty = playerDeck.isEmpty();

        if(!deckIsEmpty)
            shuffle();
    }

    private void addCardsToDeck(Card[] pDeck1, int pTimesAdded) {
        for (Card s : pDeck1) {
            if (s.inDeck) {
                for (int i = 0; i < pTimesAdded; i++) {
                    playerDeck.add(s);
                }
            }
        }
    }


    public Card [] drawFromDeck(int draws){
        Card [] hand = new Card[draws];
        for(int i = 0; i < draws; i++){
            if(playerDeck.isEmpty()){
                deckIsEmpty = true;
                return hand;
            }
            hand[i] = playerDeck.get(0);
            playerDeck.remove(0);
        }
        return hand;
    }
    public boolean setDeckUp(AssetStore assetStore, String pDeckName1, String pDeckName2){
        Card [] deck1 = jsonToCardCollection(assetStore, pDeckName1);
        Card [] deck2 = jsonToCardCollection(assetStore, pDeckName2);
        Card [] neutralDeck = jsonToCardCollection(assetStore, NEUTRAL);

        setDeck(deck1,deck2,neutralDeck);
        return deckIsEmpty;
    }

    public Card[] jsonToCardCollection(AssetStore pAssetStore, String pJsonFileName){
        JSONArray jsonArray = pAssetStore.getJson(pJsonFileName);
        Card [] deck = new Card[jsonArray.length()];
        try {
            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject object = jsonArray.getJSONObject(index);
                int id          = object.getInt("_id");
                int attack      = object.getInt("attack");
                int defense     = object.getInt("defense");
                String ability  = "uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities." + object.getString("ability");
                String imgFile  = object.getString("picture");
                int ev          = object.getInt("ev");
                boolean inDeck  = object.getBoolean("inDeck");
                String type     = object.getString("type");
                deck[index] = new Card(id,type,defense,attack,ev,0,inDeck,imgFile,ability);
            }
        }catch(JSONException e){
            Log.e(TAG, "findAbility: ");
        }
        return deck;
    }
    public int getSize() {
        return playerDeck.size();
    }

    public Rect drawDeck(GenAlgorithm.field side, IGraphics2D iGraphics2D) {
        float top;
        float bot;
        float leftSide;

        int left;
        int right;
        int topI;
        int botI;

        if (deckRect == null) {
            if (side == GenAlgorithm.field.TOP) {
                top = 0;
                bot = iGraphics2D.getSurfaceHeight();

                leftSide = 0;
                left = (int) leftSide;
                right = (int) leftSide + 100;
                topI = (int) top;
                botI = (int) ((bot) - (bot / 1.5)) - 75;
                deckRect = new Rect(left, topI, right, botI);

            } else {
                top = iGraphics2D.getSurfaceHeight() / 2;
                bot = iGraphics2D.getSurfaceHeight();

                leftSide = 0;
                left = (int) leftSide;
                right = (int) leftSide + 100;
                topI = (int) ((top) + (top / 4) + 105);
                botI = (int) bot;
                deckRect = new Rect(left, topI, right, botI);
            }
        }

        return deckRect;
    }

    public boolean isDeckIsEmpty() {
        return deckIsEmpty;
    }

}


