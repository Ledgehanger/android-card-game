package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;
import java.util.ArrayList;

import android.graphics.Rect;
import android.graphics.Bitmap;
import android.util.Log;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;
/**
 * Created by rharg on 16/04/2017.
 */

public class Graveyard {

    private boolean graveyardEmpty = true;
    //TODO Graveyard size query as below
    public static final int SIZE_OF_GRAVEYARD_DECK = 6;

    public static final String NEUTRAL = "Neutral";
    public static final String DECK_PATH = "Decks/";
    public static final String JSON = ".json";

    private Bitmap graveyardImg;
    private Rect graveyardRect;
    private ArrayList<Card> graveyardDeck = new ArrayList<>();

    public Graveyard() {
        graveyardDeck = null;
    }

    public Graveyard(AssetStore assetStore, String graveyard1, String graveyard2)
    {
        addJsonToAssetManager(assetStore, graveyard1);
        addJsonToAssetManager(assetStore, graveyard2);
        setDeckUp(assetStore, graveyard1, graveyard2);
    }

    //TODO Rename these parameters
    public boolean addJsonToAssetManager(AssetStore assetStore, String deckName)
    {
        return assetStore.loadAndAddJson(deckName,DECK_PATH + deckName + JSON);
    }

    //TODO Check what we want this set as
    public boolean setDeckUp(AssetStore assetStore, String graveyard1, String graveyard2)
    {
        addCardsInDeckToDeckFromJSONFile(assetStore, graveyard1, SIZE_OF_GRAVEYARD_DECK);
        addCardsInDeckToDeckFromJSONFile(assetStore, graveyard2, SIZE_OF_GRAVEYARD_DECK);

        return graveyardEmpty;

    }
    //TODO Unsure how this will work with JSON, query with Mark
    public void addCardsInDeckToDeckFromJSONFile(AssetStore pAssetStore, String pJsonFileName, int add)
    {
        JSONArray jsonArray= pAssetStore.getJson(pJsonFileName);
        try{
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                boolean inDeck = object.getBoolean("inDeck");
                if(inDeck)
                {
                    int id = object.getInt("_id");
                    int attack = object.getInt("attack");
                    int defense = object.getInt("defense");
                    String ability = object.getString("ability");
                    String imgFile = object.getString("picture");
                    String desc = object.getString("desc");
                    int abilityLvl = object.getInt("abilityLvl");
                    int ev = object.getInt("ev");
                    int evCost = object.getInt("evCost");
                    String type = object.getString("type");
                    String name = object.getString("name");

                    for (int j=0;j<add;j++)
                    {
                        Card local = new Card (name, id, type, defense, attack, ev, evCost, inDeck, imgFile, ability, desc, abilityLvl, pAssetStore);
                        graveyardDeck.add(local);
                    }
                }
            }

        }
        catch (JSONException e)
        {
            Log.e (TAG, "findAbility: ");
        }
    }

    public int getSize()
    {
        return graveyardDeck.size();
    }

    public void drawGraveyard (genAlgorithm.field side, IGraphics2D iGraphics2D, int surfaceHeight)
    {
        if (graveyardDeck.size() <=0)
        {
            graveyardEmpty = true;
            graveyardRect = null;
        }
        else
        {
            if (graveyardRect==null)
                generategraveyardRect(side, surfaceHeight);
            if (iGraphics2D != null)
                iGraphics2D.drawBitmap(graveyardImg, null, graveyardRect, null);
        }
    }

    private void generategraveyardRect(genAlgorithm.field side, int surfaceHeight)
    {
        float top;
        float bottom;
        float leftSide;
        int left;
        int right;
        int topI;
        int bottomI;
        if (side == genAlgorithm.field.TOP)
        {
            top = 0;
            bottom = surfaceHeight;
            leftSide = 0;
            left = (int) leftSide;
            right = (int) leftSide + 100;
            topI = (int) top;
            bottomI = (int) ((bottom) - (bottom/1.5) - 75);
            graveyardRect = new Rect(left, topI, right, bottomI);
        }
        else
        {
            top = surfaceHeight /2;
            bottom = surfaceHeight;

            leftSide=0;
            left = (int) leftSide;
            right = (int) leftSide + 100;
            topI = (int) ((top) + (top /4) +105);
            bottomI = (int) bottom;
            graveyardRect = new Rect(left, topI, right, bottomI);
        }
    }

    public boolean isGraveyardEmpty()
    {
        return graveyardEmpty;
    }

    public Bitmap getGraveyardImg()
    {
        return graveyardImg;
    }

    public void setGraveyardImg(Bitmap graveyardImg)
    {
        this.graveyardImg = graveyardImg;
    }

}

