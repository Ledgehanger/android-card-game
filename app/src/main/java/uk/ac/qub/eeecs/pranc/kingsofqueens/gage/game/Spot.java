package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Rect;

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
        this.spotCard = spotCard;
    }

    public void Draw(){


    }

    public void update(){


    }

    public Rect getSpotRect() {
        return spotRect;
    }
}
