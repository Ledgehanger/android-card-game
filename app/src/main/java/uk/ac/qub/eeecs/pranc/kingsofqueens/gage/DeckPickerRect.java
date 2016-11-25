package uk.ac.qub.eeecs.pranc.kingsofqueens.gage;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by markm on 24/11/2016.
 */

public class DeckPickerRect {

    public Rect button;
    public Bitmap Image;
    public String deckName;

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
}
