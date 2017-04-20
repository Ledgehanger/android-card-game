package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.collect.BiMap;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.CanvasGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Card;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Deck;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.scaleScreenReso;

import static org.junit.Assert.assertEquals;

/**
 * Created by markm on 20/11/2016.
 */


@RunWith(AndroidJUnit4.class)
public class Deck_Test {
    AssetStore as;
    Deck mDeck;
    Card[] card1;
    Card[] card2;

    public final String BITMAP_FILE   = "img/PlayerIcons/deckimg.png";
    private final int   LEVEL_WIDTH   = 1200;
    private final int   LEVEL_HEIGHT  = 720;

    AssetStore              assetStore;
    AssetManager            assetManager;
    CanvasGraphics2D        canvasGraphics2D;
    scaleScreenReso         scalar;
    @Before
    public void setUp() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        assetManager = appContext.getAssets();
        assetStore = new AssetStore(new FileIO(appContext));
        canvasGraphics2D = new CanvasGraphics2D(assetManager);
        Canvas n = new Canvas();
        canvasGraphics2D.setCanvas(n);
        assetStore.loadAndAddBitmap("PlayerPictureHolder", "img/PlayerIcons/PlayerIcon.png");
        assetStore.loadAndAddBitmap("Spot", "img/PlayerIcons/Spot.PNG");
        assetStore.loadAndAddBitmap("deckimg", "img/PlayerIcons/deckimg.png");
        assetStore.loadAndAddBitmap("Hand", "img/PlayerIcons/HandCanvas.png");
        assetStore.loadAndAddBitmap("Row", "img/PlayerIcons/Row.PNG");
        scalar = new scaleScreenReso(LEVEL_WIDTH,LEVEL_HEIGHT);
    }

    @Test
    public void setUpDeck() throws Exception {
        mDeck = new Deck();
        Context appContext = InstrumentationRegistry.getTargetContext();
        as = new AssetStore(new FileIO(appContext));
        as.loadAndAddJson("Psych", "Decks/Psych.json");
        as.loadAndAddJson("Neutral","Decks/Neutral.json");
        mDeck.setDeckUp(as, "Psych", "Psych");
    }
    @Test
    public void drawTestForReturnValueSize() throws Exception{
        setUpDeck();
        Card[] hand = mDeck.drawFromDeck(3);
        assertEquals(3, hand.length);

    }
    @Test
    public void drawTestForDeckSize() throws Exception{
        setUpDeck();
        int size = mDeck.getSize() - 3;
        mDeck.drawFromDeck(3);
        assertEquals(size, mDeck.getSize());
    }
    @Test
    public void OverDrawTest() throws Exception{
        setUpDeck();
        mDeck.drawFromDeck(100);
        assertEquals(0, mDeck.getSize());
        assertEquals(true, mDeck.isDeckIsEmpty());
    }

    @Test
    public void SetAndCheckBitmap()throws Exception{
        setUpDeck();
        as.loadAndAddBitmap("Deck", BITMAP_FILE);
        Bitmap b = as.getBitmap("Deck");
        mDeck.setDeckImg(b);
        assertEquals(b,mDeck.getDeckImg());
    }

    @Test
    public void DrawTestTopWithDraw() throws Exception{
        setUpDeck();
        mDeck.drawFromDeck(100);
        mDeck.drawDeck(genAlgorithm.field.TOP, canvasGraphics2D,1000,scalar);
    }
    public void DrawTestTop() throws Exception{
        setUpDeck();
        mDeck.drawDeck(genAlgorithm.field.TOP, canvasGraphics2D,1000,scalar);
    }
    @Test
    public void DrawTestBot() throws Exception{
        setUpDeck();
        Bitmap deckImg = assetStore.getBitmap("deckimg");
        mDeck.setDeckImg(deckImg);
        mDeck.drawDeck(genAlgorithm.field.BOTTOM, canvasGraphics2D,1000,scalar);
    }
    @Test
    public void DrawTestTopWithDrawWithNull() throws Exception{
        setUpDeck();
        mDeck.drawFromDeck(100);
        mDeck.drawDeck(genAlgorithm.field.TOP, null,1000,scalar);
    }
    @Test
    public void DrawTestTopWithNull() throws Exception{
        setUpDeck();
        mDeck.drawDeck(genAlgorithm.field.TOP, null,1000,scalar);
        mDeck.drawDeck(genAlgorithm.field.TOP, null,1000,scalar);
    }
    @Test
    public void DrawTestBotWithNull() throws Exception{
        setUpDeck();
        mDeck.drawDeck(genAlgorithm.field.BOTTOM, null,1000,scalar);
    }

}


