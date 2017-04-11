package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;

/**
 * Created by Paddy_Lenovo on 11/04/2017.
 */

public class Row {

    private ArrayList<Spot> myRow;
    private Bitmap rowBitmap;
    private Rect myRowRect;
    public final int ROWSIZE = 1;

    public Row(){
        myRow = new ArrayList<Spot>();

    }

    public Row(int size){
        myRow = new ArrayList<Spot>();
        setupRow(size);
    }

    public void setupRow(int size){
        for(int i = 0; i < size; i++){
            myRow.add(new Spot());
        }

    }

    public Bitmap getRowBitmap() {
        return rowBitmap;
    }

    public Rect getMyRowRect() {
        return myRowRect;
    }

    public Boolean checkSpotPlayable(int index){
    if(index > myRow.size() || index < 0)
        {return false;
    }else{
        return myRow.get(index).getCardPlaced();

        }
    }

    public void update(ElapsedTime elapsedTime, List<TouchEvent> touchEvents, Hand pHand) {
        TouchEvent touchEvent = touchEvents.get(0);
        for (int i = 0; i < ROWSIZE; i++) {
            Spot c = myRow.get(i);
            if (c.getCardPlaced()) {
                if (c.getSpotRect().contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                    c.setSpotCard(pHand.getPickedCardFromHand());
                }
            }
        }
    }

    public void draw(){


    }
}
