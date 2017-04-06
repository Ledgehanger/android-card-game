package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.DeckSelection;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by markm on 24/11/2016.
 */

public class DeckPickerRect {

    private Rect button;
    private Bitmap Image;
    private String deckName;

    public DeckPickerRect(){
        deckName = "";
    }

    public DeckPickerRect(Rect button, Bitmap image) {
        this.button = button;
        Image = image;
    }

    public DeckPickerRect(Rect button) {
        this.button = button;
    }

    public DeckPickerRect(Rect button, Bitmap image, String deckName) {
        this.button = button;
        Image = image;
        this.deckName = deckName;
    }

    public Rect getButton() {
        return button;
    }

    public Bitmap getImage() {
        return Image;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
}
