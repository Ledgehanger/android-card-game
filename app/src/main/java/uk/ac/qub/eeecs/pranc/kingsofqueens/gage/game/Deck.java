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
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;

import static android.content.ContentValues.TAG;

public class Deck{


    public boolean deckIsEmpty = true;

    private static final int SizeOfClassDeck = 3;
    private static final int SizeOfNeutralDeck = 2;
    private static final int SizeOfDeck = 18;
    private static final String Neutral = "Neutral";
    private static final String NeutralPath = "Decks/Neutral.json";
    private static final String DeckPath = "Decks/";
    private static final String Json = ".json";

    private Rect deckRect;
    public ArrayList<Card> playerDeck = new ArrayList<Card> ();

    public Deck(){
        deckRect = null;
    }
    public boolean loadDecksIntoAssestManger(Game game, String pDeck1, String pDeck2){
        game.getAssetManager().loadAndAddJson(Neutral,NeutralPath);
        return  game.getAssetManager().loadAndAddJson(pDeck1, DeckPath + pDeck1 + Json) &&
                game.getAssetManager().loadAndAddJson(pDeck2, DeckPath + pDeck2 + Json);
    }

    public void shuffle(){
        genAlgorithm.KnuthShuffle(playerDeck);
    }

    public void setDeck( Card [] Deck1,  Card [] Deck2,  Card [] NeutralDeck){


        AddToArrayList(Deck1);
        AddToArrayList(Deck2);
        AddToArrayList(NeutralDeck);

        deckIsEmpty = playerDeck.isEmpty();

        if(!deckIsEmpty)
            shuffle();
    }

    private void AddToArrayList(Card[] Deck1) {
        for (Card s : Deck1) {
            if (s.inDeck == true) {
                for (int i = 0; i < SizeOfClassDeck; i++) {
                    playerDeck.add(s);
                }
            }
        }
    }

    public void generateAIDeck(Game game){
        game.getAssetManager().loadAndAddJson(Neutral,NeutralPath);
        int firstChoice = -1;
        String [] pickedDecks = new String[2];
        String [] deckNames =
                {"Psych","Engineering", "Theology","Medical","CS", "Law"
                };
        Random rand = new Random();

        for(int i = 0; i < 2; i++){
        int randomNumber = firstChoice;
        while(randomNumber == firstChoice){
            randomNumber = rand.nextInt(deckNames.length);
        }
            game.getAssetManager().loadAndAddJson(deckNames[randomNumber], DeckPath+deckNames[randomNumber]+ Json);
            firstChoice = randomNumber;
            pickedDecks[i] = deckNames[randomNumber];
        }
        setDeckUp(game.getAssetManager(), pickedDecks[0], pickedDecks[1]);
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
        Card [] neutralDeck = jsonToCardCollection(assetStore, Neutral);

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
                int EV          = object.getInt("ev");
                boolean inDeck  = object.getBoolean("inDeck");
                String type     = object.getString("type");
                deck[index] = new Card(id,type,defense,attack,EV,0,inDeck,imgFile,ability);
            }
        }catch(JSONException e){
            Log.e(TAG, "findAbility: ");
        }
        return deck;
    }
    public int getSize() {
        return playerDeck.size();
    }

    public Rect drawDeck(genAlgorithm.field side, IGraphics2D iGraphics2D) {
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

        return deckRect;
    }


}


