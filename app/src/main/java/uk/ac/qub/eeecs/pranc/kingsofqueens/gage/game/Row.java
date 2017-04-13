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
        setupRow(SPOTS_PER_ROW);
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
            if (!c.getCardPlaced()) {
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
        left =  166 + 20;
        right = iGraphics2D.getSurfaceWidth() - 250;

        if (side == genAlgorithm.field.TOP) {
           // topI = 0 + (int) ((bot) - (bot / 1.5)) - 75;
            topI = (int) bot/2 - (int) (bot/2)/2;
            botI = iGraphics2D.getSurfaceHeight()/2;

            rowRect = new Rect(left, topI, right, botI);

        } else {
            top = iGraphics2D.getSurfaceHeight() / 2;
            botI = (int) top + (int) (top / 2);
            topI = (int) top;

            rowRect = new Rect(left, topI, right, botI);
        }


        //iGraphics2D.drawBitmap(rowBitmap,null,rowRect,null);
        int offset = 150;
        for (Spot s : myRow) {
            int rightDraw = left + offset;
            s.draw(topI,botI,left,rightDraw,side,iGraphics2D,aStore);
            left += offset;

        }
    }


}

