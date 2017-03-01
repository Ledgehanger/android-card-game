package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;
import android.graphics.Bitmap;
import android.graphics.Rect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Gen_Algorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;

public class Deck{


    public boolean deckIsEmpty = true;

    private final int SIZE_OF_CLASS_DECK = 3;
    private final int SIZE_OF_NEUTRAL_DECK = 2;
    private final int DECK_SIZE = 18;
    private Rect deckRect;
    public ArrayList<Card> playerDeck = new ArrayList<Card> ();

    public Deck(){}
    public boolean loadDecksIntoAssestManger(Game game, String pDeck1, String pDeck2){
        game.getAssetManager().loadAndAddJson("Neutral","Decks/Neutral.json");
        return  game.getAssetManager().loadAndAddJson(pDeck1,"Decks/"+pDeck1+".json") &&
                game.getAssetManager().loadAndAddJson(pDeck2,"Decks/"+pDeck2+".json");
    }

    public void shuffle(){
        Gen_Algorithm.KnuthShuffle(playerDeck);
    }

    public void setDeck( Card [] Deck1,  Card [] Deck2,  Card [] NeutralDeck){
        int total = 0;
        while(total < DECK_SIZE) {
            for (Card s : Deck1) {
                if (s.inDeck == true) {
                    for (int i = 0; i < SIZE_OF_CLASS_DECK; i++) {
                        playerDeck.add(s);
                        total++;
                    }
                }
            }
            for (Card s : Deck2) {
                if (s.inDeck == true) {
                    for (int i = 0; i < SIZE_OF_CLASS_DECK; i++) {
                        playerDeck.add(s);
                        total++;
                    }
                }
            }
            for (Card s : NeutralDeck) {
                if (s.inDeck == true) {
                    for (int i = 0; i < SIZE_OF_NEUTRAL_DECK; i++) {
                        playerDeck.add(s);
                        total++;
                     }
                }
            }
        }
        deckIsEmpty = playerDeck.size() <= 0;
        if(!deckIsEmpty)
            shuffle();
    }

    public void generateAIDeck(Game game){
        game.getAssetManager().loadAndAddJson("Neutral","Decks/Neutral.json");
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
            game.getAssetManager().loadAndAddJson(deckNames[randomNumber],"Decks/"+deckNames[randomNumber]+".json");
            firstChoice = randomNumber;
            pickedDecks[i] = deckNames[randomNumber];
        }
        setDeckUp(game.getAssetManager(), pickedDecks[0], pickedDecks[1]);
    }

    public Card [] drawFromDeck(int draws){
        Card [] hand = new Card[draws];
        for(int i = 0; i < draws; i++){
            if(playerDeck.size() <= 0){
                deckIsEmpty = true;
                return hand;
            }
            hand[i] = playerDeck.get(0);
            playerDeck.remove(0);
        }
        return hand;
    }
    public boolean setDeckUp(AssetStore assetStore, String DeckName1, String DeckName2){
        Card [] Deck1 = jsonToCardCollection(assetStore, DeckName1);
        Card [] Deck2 = jsonToCardCollection(assetStore, DeckName2);
        Card [] NeutralDeck = jsonToCardCollection(assetStore, "Neutral");

        setDeck(Deck1,Deck2,NeutralDeck);
        return deckIsEmpty;
    }

    public Card[] jsonToCardCollection(AssetStore assetStore, String JsonFileName){
        JSONArray jsonArray = assetStore.getJson(JsonFileName);
        Card [] deck = new Card[jsonArray.length()];
        try {
            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject object = jsonArray.getJSONObject(index);
                int id          = object.getInt("_id");
                String name     = object.getString("name");
                int attack      = object.getInt("attack");
                int defense     = object.getInt("defense");
                String ability  = "uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities." + object.getString("ability");
                String imgFile  = object.getString("picture");
                int EV          = object.getInt("ev");
                //int EvCost        = object.getInt("evCost");
                boolean inDeck  = object.getBoolean("inDeck");
                String type     = object.getString("type");
                deck[index] = new Card(id,type,defense,attack,EV,0,inDeck,imgFile,ability);
            }
        }catch(JSONException e){
            // TODO: Exception
            String p = e.toString();
        }
        return deck;
    }
    public int getSize() {
        return playerDeck.size();
    }

    public Rect drawDeck(Gen_Algorithm.field side, Bitmap DeckImg, IGraphics2D iGraphics2D) {
        float top, bot, est;
        int left, right, topI, botI;
        if (deckRect == null) {
            if (side == Gen_Algorithm.field.top) {
                top = 0;
                bot = iGraphics2D.getSurfaceHeight();

                est = iGraphics2D.getSurfaceWidth() - iGraphics2D.getSurfaceWidth();
                left = (int) est;
                right = (int) est + 100;
                topI = (int) top;
                botI = (int) ((bot) - (bot / 1.5)) - 75;
                deckRect = new Rect(left, topI, right, botI);

            } else {
                top = iGraphics2D.getSurfaceHeight() / 2;
                bot = iGraphics2D.getSurfaceHeight();

                est = iGraphics2D.getSurfaceWidth() - iGraphics2D.getSurfaceWidth();
                left = (int) est;
                right = (int) est + 100;
                topI = (int) ((top) + (top / 4) + 105);//(iGraphics2D.getSurfaceHeight()/4)+(koqTitle.getHeight()/2);
                botI = (int) bot;//(iGraphics2D.getSurfaceHeight()/4)-(koqTitle.getHeight()/2);
                deckRect = new Rect(left, topI, right, botI);
            }
        }

        return deckRect;
    }


}


