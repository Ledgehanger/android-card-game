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
public class Deck {
    private List<String> deckType;
    public Queue<Card> Deck = new LinkedList<Card>();
    public boolean deckIsEmpety = true;
    Card [] Deck1 , Deck2, NeutralDeck;
    public Deck(){
        //setDeck(Deck1,Deck2);
    }
    // TODO: 20/11/2016  Update this for a Card Array
    public boolean loadDecksIntoAssestManger(Game game, String pDeck1, String pDeck2){
        game.getAssetManager().loadAndAddJson("Neutral","Decks/Neutral.json");
        return  game.getAssetManager().loadAndAddJson(pDeck1,"Decks/"+pDeck1+".json") &&
                game.getAssetManager().loadAndAddJson(pDeck2,"Decks/"+pDeck2+".json");
    }
    public void setDeck(){
        for (Card s: Deck1) {
            if(s.inDeck == true) {
                for (int i = 0; i < 3; i++) {
                    Deck.add(s);
                }
            }
        }
        for (Card s: Deck2) {
            if(s.inDeck == true) {
                for (int i = 0; i < 3; i++) {
                    Deck.add(s);
                }
            }
        }
        for(Card s: NeutralDeck){
            if(s.inDeck == true) {
                for (int i = 0; i < 2; i++) {
                    Deck.add(s);
                }
            }
        }
        deckIsEmpety = Deck.peek() == null;
    }
    public Card [] drawCards(int draws){
        Card [] hand = new Card[draws];
        for(int i = 0; i < draws; i++){
            if(Deck.isEmpty()){
                deckIsEmpety = true;
                return hand;
            }
            hand[i] = Deck.poll();
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
        return Deck.size();
    }
}