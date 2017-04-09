package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

/**
 * Created by Paddy_Lenovo on 22/11/2016.
 */
import android.graphics.Bitmap;
import android.graphics.Rect;

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
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;

public class  Hand {
    //The max number of cards a player can have in their hand is 3 cards(temp)
    public final static int MAX_HAND_SIZE = 5;
    public final static int STARTING_HAND_SIZE = 3;
    public final static String HAND_BITMAP_NAME = "Hand";
    //These are the cooirdinates for the Cards on the field
    final static float posX = 290;
    final static float pos1Y = 195;
    final static float pos2Y = 235;
    final static float pos3Y = 275;

    protected Bitmap handBitmap;
    protected  ArrayList<Card> myHand;

    private Rect handRect;
    final int offset = 166;
    //Mark Testing
    public int indexOfPickedCard = -1;
    public Hand(Card [] fromDeck,AssetStore pAssetManger){
        myHand =  new ArrayList<>();
        for (Card c : fromDeck){
            myHand.add(c);
        };

    }

    public void AddToHand(Card [] fromDeck){
        for (Card c : fromDeck){
            if(myHand.size() < MAX_HAND_SIZE)
                myHand.add(c);
            else
                break;
        };
    }

    public Card RemoveFromHand(int index){
        if(index >= 0 && index < MAX_HAND_SIZE)
            return myHand.remove(index);
        else
            return null;
    }


    public void drawHand(genAlgorithm.field side, IGraphics2D iGraphics2D,AssetStore pAssetManger, boolean drawBack) {

        float top;
        float bot;
        int left;
        int right;
        int topI;
        int botI;

         handBitmap = pAssetManger.getBitmap(HAND_BITMAP_NAME);
            bot = iGraphics2D.getSurfaceHeight();
            left =  166;
            right = iGraphics2D.getSurfaceWidth() - 150;

            if (side == genAlgorithm.field.TOP) {
                topI = 0;
                botI = (int) ((bot) - (bot / 1.5)) - 75;
                handRect = new Rect(left, topI, right, botI);

            } else {
                top = iGraphics2D.getSurfaceHeight() / 2;
                bot = iGraphics2D.getSurfaceHeight();

                topI = (int) ((top) + (top / 4) + 105);
                botI = (int) bot;
                handRect = new Rect(left, topI, right, botI);
            }


        iGraphics2D.drawBitmap(handBitmap,null,handRect,null);

        for (Card c: myHand) {
            c.drawCard(botI,left,topI,iGraphics2D,drawBack);
            left += offset;
        }
    }

    public void update(ElapsedTime elapsedTime,Input input, List<TouchEvent> touchEvents ) {
        if(!touchEvents.isEmpty()){
            TouchEvent touchEvent = touchEvents.get(0);
            for(int i = 0; i < myHand.size(); i++){
                Rect c = myHand.get(i).cardRect;
                if(c.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0) {
                    if (indexOfPickedCard == i) {
                        myHand.get(indexOfPickedCard).isPicked = false;
                        indexOfPickedCard = -1;
                    } else {
                            if (indexOfPickedCard >= 0) {
                                myHand.get(indexOfPickedCard).isPicked = false;
                            }
                            myHand.get(i).isPicked = true;
                            indexOfPickedCard = i;
                    }
                }
            }
        }
    }


    public Card getPickedCardFromHand(){
        myHand.get(indexOfPickedCard).setCardToNull();
        Card toReturn =  myHand.remove(indexOfPickedCard);
        indexOfPickedCard = -1;
        return toReturn;
    }

}
