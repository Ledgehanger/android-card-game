package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;

/**
 * Created by Paddy_Lenovo on 11/04/2017.
 */

public class Spot {
    public Boolean getCardPlaced() {
        return cardPlaced;
    }

    private Boolean cardPlaced;
    private Card spotCard;
    private Rect spotRect;

    public Spot(){
        cardPlaced = false;
    }

    public void setSpotCard(Card spotCard) {
        cardPlaced = true;
        this.spotCard = spotCard;
    }

    public void draw(int top, int bot, int right , int left, genAlgorithm.field side, IGraphics2D iGraphics2D, AssetStore
            aStore){
        spotRect = new Rect(right, top, left, bot);

        if(cardPlaced) {
            spotCard.isPicked = false;
            spotCard.drawCardInSpot(spotRect, iGraphics2D);

        }
        else{
            Bitmap bit = aStore.getBitmap("Spot");
            iGraphics2D.drawBitmap(bit,null,spotRect,null);
        }


    }

    public void update(){


    }

    public Rect getSpotRect() {
        return spotRect;
    }
}
