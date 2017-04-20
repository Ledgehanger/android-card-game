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
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.scaleScreenReso;

public class  Hand {
    //Constants
    public static final int    MAX_HAND_SIZE        = 5;
    public static final int    PICKED_DEFAULT_INDEX = -1;
    public static final int    CARD_GAP_OFFSET      = 166;
    public static final int    CARD_OFFSET          = 10;
    public static final String HAND_BITMAP_NAME     = "Hand";

    //Member Attributes
    private   Bitmap          handBitmap;
    private   ArrayList<Card> myHand;
    private   Rect            handRect;
    private   int             indexOfPickedCard = PICKED_DEFAULT_INDEX;
    private   Card            lastCardPlayed;
    private   boolean         cardPlayedThisTurn;

    public Hand(Card [] fromDeck){
        myHand =  new ArrayList<>();
        for (Card c : fromDeck){
            if(c != null)
             myHand.add(c);
        }
        cardPlayedThisTurn = false;
    }

    public void addToHand(Card [] fromDeck){
        for (Card c : fromDeck){
            if(myHand.size() < MAX_HAND_SIZE)
                if(c != null)
                    myHand.add(c);
            else
                break;
        }
    }

    /// Returns the picked card from the myHand, or a null if no card is picked
    public Card getPickedCardFromHand(){
        if(indexOfPickedCard != PICKED_DEFAULT_INDEX) {
            myHand.get(indexOfPickedCard).setCardToNull();
            lastCardPlayed = myHand.remove(indexOfPickedCard);
            indexOfPickedCard = PICKED_DEFAULT_INDEX;
            return lastCardPlayed;
        }
        return null;
    }

    public void drawHand(genAlgorithm.field pSide, IGraphics2D iGraphics2D, AssetStore pAssetManger,
                         boolean pDrawBack, int pSurfaceHeight, int pSurfaceWidth, scaleScreenReso scalar) {

        float top;
        float bot;
        int   left;
        int   right;
        int   topI;
        int   botI;

         handBitmap = pAssetManger.getBitmap(HAND_BITMAP_NAME);
            bot = pSurfaceHeight;
            left =  CARD_GAP_OFFSET;
            right = pSurfaceWidth - 150;

            if (pSide == genAlgorithm.field.TOP) {
                topI = 0;
                botI = (int) ((bot) - (bot / 1.5) - 75);
                handRect =scalar.scalarect(left, topI, right, botI);
                botI -= CARD_OFFSET;
                left += CARD_OFFSET;

            } else {
                top = pSurfaceHeight/ 2;
                bot = pSurfaceHeight;

                topI = (int) ((top) + (top / 4) + 105);
                botI = (int) bot;
                handRect = scalar.scalarect(left, topI, right, botI);
            }

        if(iGraphics2D != null)
            iGraphics2D.drawBitmap(handBitmap,null,handRect,null);

        for (Card c: myHand) {
            c.drawCard(botI,left,topI,iGraphics2D,pDrawBack,scalar);
            left += CARD_GAP_OFFSET;
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

    protected void manageSelection(int index) {
        if (indexOfPickedCard == index) {
            myHand.get(indexOfPickedCard).isPicked = false;
            indexOfPickedCard = PICKED_DEFAULT_INDEX;
        } else {
            unselectedPickedCard();
            myHand.get(index).isPicked = true;
            indexOfPickedCard = index;
        }
    }

    public void endTurn(){
        cardPlayedThisTurn = false;
        lastCardPlayed = null;
    }

    private void unselectedPickedCard() {
        if (indexOfPickedCard >= 0) {
            myHand.get(indexOfPickedCard).isPicked = false;
        }
    }

    public boolean cardPicked(){
        if(indexOfPickedCard >= 0)
            return true;
        return false;
    }
    public ArrayList<Card> getMyHand(){return myHand;}
    public int getHandSize(){
        return myHand.size();
    }
    public Card getLastCardPlayed(){
        return lastCardPlayed;
    }
    public boolean getCardPlayedThisTurn(){
        return cardPlayedThisTurn;
    }
    public void setCardPlayedThisTurn(boolean bool){
        this.cardPlayedThisTurn = bool;
    }

    public void setIndexOfPickedCard(int indexOfPickedCard){this.indexOfPickedCard=indexOfPickedCard;}

    public Card getCardFromHand(int cIndex){return myHand.get(cIndex);}

}
