package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;
import android.content.res.AssetManager;
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
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Gen_Algorithm;
public class Deck {

    private List<String> deckType;
    public boolean deckIsEmpety = true;

    Card [] Deck1 , Deck2, NeutralDeck;

    private final int SIZE_OF_CLASS_DECK = 3;
    private final int SIZE_OF_NEUTRAL_DECK = 2;
    private final int DECK_SIZE = 18;

    public ArrayList<Card> deck2 = new ArrayList<Card> ();

    public Deck(){}
    public boolean loadDecksIntoAssestManger(Game game, String pDeck1, String pDeck2){
        game.getAssetManager().loadAndAddJson("Neutral","Decks/Neutral.json");
        return  game.getAssetManager().loadAndAddJson(pDeck1,"Decks/"+pDeck1+".json") &&
                game.getAssetManager().loadAndAddJson(pDeck2,"Decks/"+pDeck2+".json");
    }

    public ArrayList<Card> shuffle(ArrayList<Card> pDeck){
        Gen_Algorithm.KnuthShuffle(pDeck);
        return pDeck;
    }

    public void setDeck(){
        int total = 0;
        while(total < DECK_SIZE) {
            for (Card s : Deck1) {
                if (s.inDeck == true) {
                    for (int i = 0; i < SIZE_OF_CLASS_DECK; i++) {
                        deck2.add(s);
                        total++;
                    }
                }
            }
            for (Card s : Deck2) {
                if (s.inDeck == true) {
                    for (int i = 0; i < SIZE_OF_CLASS_DECK; i++) {
                        deck2.add(s);
                        total++;
                    }
                }
            }
            for (Card s : NeutralDeck) {
                if (s.inDeck == true) {
                    for (int i = 0; i < SIZE_OF_NEUTRAL_DECK; i++) {
                        deck2.add(s);
                        total++;
                     }
                }
            }
        }
        deckIsEmpety = deck2.size() <= 0;
        if(!deckIsEmpety)
            deck2 = shuffle(deck2);
    }


    public Card [] drawCards(int draws){
        Card [] hand = new Card[draws];
        for(int i = 0; i < draws; i++){
            if(deck2.size() > 0){
                deckIsEmpety = true;
                return hand;
            }
            hand[i] = deck2.get(1);
            deck2.remove(1);
        }
        return hand;
    }
    public boolean setDeckUp(AssetStore assetStore, String DeckName1, String DeckName2){
        Deck1 = jsonToCardCollection(assetStore, DeckName1);
        Deck2 = jsonToCardCollection(assetStore, DeckName2);
        NeutralDeck = jsonToCardCollection(assetStore, "Neutral");

        setDeck();
        return deckIsEmpety;
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
                String ability  = object.getString("ability");
                String imgFile  = object.getString("picture");
                int EV          = object.getInt("ev");
                //int EvCost        = object.getInt("evCost");
                boolean inDeck  = object.getBoolean("inDeck");
                String type     = object.getString("type");
                deck[index] = new Card(id,type,defense,attack,EV,0,inDeck,imgFile);
            }
        }catch(JSONException e){
            // TODO: Exception
            String p = e.toString();
        }
        return deck;
    }
    public int getSize() {
        return deck2.size();
    }
}