package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

/**
 * Created by Paddy_Lenovo on 22/11/2016.
 */
import android.graphics.Bitmap;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.List;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;

public class  Hand {
    //Constants
    public final int    MAX_HAND_SIZE       = 5;
    public final int    HAND_OFFSET         = 166;
    public final String HAND_BITMAP_NAME    = "Hand";

    //Member Attributes
    private   Bitmap          handBitmap;
    private   ArrayList<Card> myHand;
    private   Rect            handRect;
    private   int             indexOfPickedCard = -1;

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

    /// Returns the picked card from the myHand, or a null if no card is picked
    public Card getPickedCardFromHand(){
        if(indexOfPickedCard != -1) {
            myHand.get(indexOfPickedCard).setCardToNull();
            Card toReturn = myHand.remove(indexOfPickedCard);
            indexOfPickedCard = -1;
            return toReturn;
        }
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
            left += HAND_OFFSET;
        }
    }
    public void update(ElapsedTime elapsedTime, List<TouchEvent> touchEvents ) {
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

public void drawCard(){


}



}
