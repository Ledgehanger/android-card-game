package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.DeckSelection;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Author Mark McAleese (40177285) all methods
 */

public class DeckSelection {

    private String name;
    private String path;
    private String imgPath;

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

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getImgPath() {
        return imgPath;
    }
}
