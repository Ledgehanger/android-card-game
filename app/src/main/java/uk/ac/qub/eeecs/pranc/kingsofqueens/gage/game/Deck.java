package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;

import static android.content.ContentValues.TAG;

public class Deck{


    private boolean deckIsEmpty = true;

    public static final int SIZEOFCLASSDECK = 3;
    public static final int SIZEOFNEUTRALDECK = 2;

    public static final String NEUTRAL = "Neutral";
    public static final String DECKPATH = "Decks/";
    public static final String JSON = ".json";

    private Bitmap deckImg;
    private Rect deckRect;
    private ArrayList<Card> playerDeck = new ArrayList<> ();

    public Deck(){
        deckRect = null;
    }

    public Deck(AssetStore assetStore,String pDeck1, String pDeck2){

        loadDecksIntoAssestManger(assetStore,pDeck1, pDeck2);
        setDeckUp(assetStore, pDeck1, pDeck2);
    }
    // Refactor json string joining into a method so it gets called instead of d// doing it over and over again
    public boolean loadDecksIntoAssestManger(AssetStore assetStore, String pDeck1, String pDeck2){
        addJsonToAssetManager(assetStore,NEUTRAL);
        return addJsonToAssetManager(assetStore,pDeck1) && addJsonToAssetManager(assetStore,pDeck2);

    }

    public boolean addJsonToAssetManager(AssetStore assetStore,String deckName) {
        return assetStore.loadAndAddJson(deckName, DECKPATH + deckName + JSON);
    }

    public void shuffle(){
        genAlgorithm.knuthShuffle(playerDeck);
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
        addCardsInDeckToDeckFromJSONFile(assetStore, pDeckName1, SIZEOFCLASSDECK);
        addCardsInDeckToDeckFromJSONFile(assetStore, pDeckName2, SIZEOFCLASSDECK);
        addCardsInDeckToDeckFromJSONFile(assetStore, NEUTRAL, SIZEOFNEUTRALDECK);

        deckIsEmpty = playerDeck.isEmpty();

        if(!deckIsEmpty)
            shuffle();

        return deckIsEmpty;
    }

    public void addCardsInDeckToDeckFromJSONFile(AssetStore pAssetStore, String pJsonFileName, int add){
        JSONArray jsonArray = pAssetStore.getJson(pJsonFileName);
        Card [] deck = new Card[jsonArray.length()];
        try {
            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject object = jsonArray.getJSONObject(index);
                boolean inDeck  = object.getBoolean("inDeck");
                if(inDeck){
                int id          = object.getInt("_id");
                int attack      = object.getInt("attack");
                int defense     = object.getInt("defense");
                String ability  = "uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities."
                                    + object.getString("ability");
                String imgFile  = object.getString("picture");
                int ev          = object.getInt("ev");
                int evCost      = object.getInt("evCost");
                String type     = object.getString("type");
                String name     = object.getString("name");
                    for(int i = 0; i < add; i++){
                        Card local = new Card(name, id,type,defense,attack,
                                               ev,evCost,inDeck,imgFile,ability,pAssetStore);
                        playerDeck.add(local);
                    }
                }

            }
        }catch(JSONException e){
            Log.e(TAG, "findAbility: ");
        }
    }
    public int getSize() {
        return playerDeck.size();
    }

    public void drawDeck(genAlgorithm.field side, IGraphics2D iGraphics2D) {
        float top;
        float bot;
        float leftSide;

        int left;
        int right;
        int topI;
        int botI;

        if (deckRect == null) {
            if (side == genAlgorithm.field.TOP) {
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

        iGraphics2D.drawBitmap(deckImg,null,deckRect,null);
    }

    public boolean isDeckIsEmpty() {
        return deckIsEmpty;
    }

    public Bitmap getDeckImg() {
        return deckImg;
    }

    public void setDeckImg(Bitmap deckImg) {
        this.deckImg = deckImg;
    }
}


