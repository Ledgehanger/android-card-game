package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.CanvasGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.GameObjects.Deck;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.DeckSelection.DeckPickerRect;

import static org.junit.Assert.assertEquals;

/**
 * Created by markm on 01/05/2017.
 */


@RunWith(AndroidJUnit4.class)
public class DeckPickerRect_Test {

    Context             appContext;
    AssetStore          assetStore;
    AssetManager        assetManager;
    CanvasGraphics2D    canvasGraphics2D;
    Deck mDeck;
    @Before
    public void setUp() throws Exception {
        appContext       = InstrumentationRegistry.getTargetContext();
        assetManager     = appContext.getAssets();
        assetStore       = new AssetStore(new FileIO(appContext));
        canvasGraphics2D = new CanvasGraphics2D(assetManager);
        Canvas canvas    = new Canvas();

        canvasGraphics2D.setCanvas(canvas);
        mDeck = new Deck();
        Context appContext = InstrumentationRegistry.getTargetContext();
        assetStore = new AssetStore(new FileIO(appContext));
        assetStore.loadAndAddMusic("BGM","music/Layer Cake.m4a");
        assetStore.loadAndAddJson("Psych", "Decks/Psych.json");
        assetStore.loadAndAddJson("Neutral","Decks/Neutral.json");
        mDeck.setDeckUp(assetStore, "Psych", "Psych");
    }
    @Test
    public void defaultConstructor()throws Exception{
        DeckPickerRect deafult = new DeckPickerRect();
        assertEquals("",deafult.getDeckName());
    }
    @Test
    public void RectConstructor()throws Exception{
        Rect test = new Rect(10,10,10,10);
        DeckPickerRect deafult = new DeckPickerRect(test);
        assertEquals(test,deafult.getButton());
    }
    @Test
    public void RectImageConstructor()throws Exception{
        assetStore.loadAndAddBitmap("bitmap","img/PickDeckImages/cs");
        Bitmap cs = assetStore.getBitmap("bitmap");
        Rect test = new Rect(10,10,10,10);
        DeckPickerRect deafult = new DeckPickerRect(test,cs);
        assertEquals(test,deafult.getButton());
        assertEquals(cs,deafult.getImage());
    }
    @Test
    public void RectImageDeckNameConstructor()throws Exception{
        assetStore.loadAndAddBitmap("bitmap","img/PickDeckImages/cs");
        Bitmap cs = assetStore.getBitmap("bitmap");
        String deckName = "cs";
        Rect test = new Rect(10,10,10,10);
        DeckPickerRect deafult = new DeckPickerRect(test,cs,deckName);
        assertEquals(test,deafult.getButton());
        assertEquals(cs,deafult.getImage());
        assertEquals(deckName,deafult.getDeckName());
    }
    @Test
    public void setName()throws Exception{
        DeckPickerRect deafult = new DeckPickerRect();
        assertEquals("",deafult.getDeckName());
        deafult.setDeckName("CS");
        assertEquals("CS",deafult.getDeckName());
    }




}

