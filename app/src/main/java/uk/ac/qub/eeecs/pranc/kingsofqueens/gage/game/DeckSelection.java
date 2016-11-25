package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by markm on 20/11/2016.
 */

public class DeckSelection {

    public String name;
    public String path;
    public String imgPath;

    private Rect button;
    private Bitmap bitImage;

    public DeckSelection(){
        name = "";
        path = "";
        imgPath = "";
    }

    public DeckSelection(String name, String path, String imgPath) {
        this.name = name;
        this.path = path;
        this.imgPath = imgPath;
    }

    public Rect getButton() {
        return button;
    }

    public void setButton(Rect button) {
        this.button = button;
    }

    public Bitmap getBitImage() {
        return bitImage;
    }

    public void setBitImage(Bitmap bitImage) {
        this.bitImage = bitImage;
    }
}
