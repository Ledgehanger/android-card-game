package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.content.res.AssetManager;
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

public class Field {

    public final int ROWS_PER_FIELD = 1;
    private ArrayList<Row> myField;
    public Field(){
        myField = new ArrayList<Row>();
        for(int i = 0; i < ROWS_PER_FIELD; i++){
            myField.add(new Row());
        }

    }
    public void draw (genAlgorithm.field side, IGraphics2D iGraphics2D, AssetStore
            aStore) {
        for (int i = 0; i < ROWS_PER_FIELD; i++ ){

            myField.get(i).draw(side, iGraphics2D, aStore);
        }
    }
    public void update(ElapsedTime elapsedTime, List< TouchEvent > touchEvents, Hand pHand) {

        if(!touchEvents.isEmpty()) {
            TouchEvent touchEvent = touchEvents.get(0);
            for (int i = 0; i < ROWS_PER_FIELD; i++) {
                Row c = myField.get(i);
                if (c.getMyRowRect().contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                    c.update(elapsedTime, touchEvents, pHand);

                }
            }
        }
    }



}
