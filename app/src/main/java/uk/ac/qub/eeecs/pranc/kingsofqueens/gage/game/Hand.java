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
    public static final int    MAX_HAND_SIZE        = 5;
    public static final int    PICKED_DEFAULT_INDEX = -1;
    public static final int    HAND_OFFSET          = 166;
    public static final String HAND_BITMAP_NAME     = "Hand";

    //Member Attributes
    private   Bitmap          handBitmap;
    private   ArrayList<Card> myHand;
    private   Rect            handRect;
    private   int             indexOfPickedCard = PICKED_DEFAULT_INDEX;

    public Hand(Card [] fromDeck){
        myHand =  new ArrayList<>();
        for (Card c : fromDeck){
            myHand.add(c);
        }

    }

    public void addToHand(Card [] fromDeck){
        for (Card c : fromDeck){
            if(myHand.size() < MAX_HAND_SIZE)
                myHand.add(c);
            else
                break;
        }
    }

    /// Returns the picked card from the myHand, or a null if no card is picked
    public Card getPickedCardFromHand(){
        if(indexOfPickedCard != PICKED_DEFAULT_INDEX) {
            myHand.get(indexOfPickedCard).setCardToNull();
            Card toReturn = myHand.remove(indexOfPickedCard);
            indexOfPickedCard = PICKED_DEFAULT_INDEX;
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
                    manageSelection(i);
                }
            }
        }
    }

    private void manageSelection(int index) {
        if (indexOfPickedCard == index) {
            myHand.get(indexOfPickedCard).isPicked = false;
            indexOfPickedCard = PICKED_DEFAULT_INDEX;
        } else {
            unselectPickedCard();
            myHand.get(index).isPicked = true;
            indexOfPickedCard = index;
        }
    }

    private void unselectPickedCard() {
        if (indexOfPickedCard >= 0) {
            myHand.get(indexOfPickedCard).isPicked = false;
        }
    }

    public boolean cardPicked(){
        if(indexOfPickedCard >= 0)
            return true;
        return false;
    }

}
