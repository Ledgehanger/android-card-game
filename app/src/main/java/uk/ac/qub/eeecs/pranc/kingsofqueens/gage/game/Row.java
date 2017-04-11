package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;

/**
 * Created by Paddy_Lenovo on 11/04/2017.
 */

public class Row {

    private ArrayList<Spot> myRow;
    private Bitmap rowBitmap;
    private Rect rowRect;
    public final String ROW_IMAGE_FILE = "Row";
    public final int SPOTS_PER_ROW = 5;

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
        return rowRect;
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
        for (int i = 0; i < SPOTS_PER_ROW; i++) {
            Spot c = myRow.get(i);
            if (c.getCardPlaced()) {
                if (c.getSpotRect().contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                    c.setSpotCard(pHand.getPickedCardFromHand());
                }
            }
        }
    }

    public void draw (genAlgorithm.field side, IGraphics2D iGraphics2D, AssetStore
            aStore){

        float top;
        float bot;
        int left;
        int right;
        int topI;
        int botI;

        rowBitmap = aStore.getBitmap(ROW_IMAGE_FILE);
        bot = iGraphics2D.getSurfaceHeight();
        left =  166 + 100;
        right = iGraphics2D.getSurfaceWidth() - 250;

        if (side == genAlgorithm.field.TOP) {
            topI = 0 + (int) ((bot) - (bot / 1.5)) - 75;
            botI = iGraphics2D.getSurfaceHeight()/2 - 5;
            rowRect = new Rect(left, topI, right, botI);

        } else {
            top = iGraphics2D.getSurfaceHeight() / 2;
            bot = iGraphics2D.getSurfaceHeight();

            botI = (int) bot  + ((int) ((top) + (top / 4) - 155)) - 1185;
            topI = (int) top;

            rowRect = new Rect(left, topI, right, botI);
        }


        iGraphics2D.drawBitmap(rowBitmap,null,rowRect,null);


    }


}
