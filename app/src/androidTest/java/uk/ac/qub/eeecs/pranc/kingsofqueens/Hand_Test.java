package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.CanvasGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Card;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Deck;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Hand;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Player;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by mark on 15/04/2017.
 */
@RunWith(AndroidJUnit4.class)
public class Hand_Test {
    Player              player;
    Player              ePlayer;
    Hand                pHand;
    Deck                mDeck;
    AssetStore          assetStore;
    AssetManager        assetManager;
    CanvasGraphics2D    canvasGraphics2D;


    @Before
    public void setUp() throws Exception {
        mDeck = new Deck();
        Context appContext = InstrumentationRegistry.getTargetContext();
        assetManager = appContext.getAssets();
        assetStore = new AssetStore(new FileIO(appContext));
        canvasGraphics2D = new CanvasGraphics2D(assetManager);
        Canvas n = new Canvas();
        canvasGraphics2D.setCanvas(n);


        assetStore.loadAndAddJson("Psych", "Decks/Psych.json");
        assetStore.loadAndAddJson("Neutral","Decks/Neutral.json");
        mDeck.setDeckUp(assetStore, "Psych", "Psych");
        assetStore.loadAndAddBitmap("Hand", "img/PlayerIcons/HandCanvas.png");
    }

    public void setUpHand() throws Exception {
        pHand = new Hand(mDeck.drawFromDeck(3));
    }
    @Test
    public void passInNullCards() throws Exception {
        Deck nullDeck = new Deck();
        pHand = new Hand(nullDeck.drawFromDeck(3));
        assertEquals(0,pHand.getHandSize());
    }

    @Test
    public void cardPicked()throws Exception{
        setUpHand();
        assertEquals(false,pHand.cardPicked());
        assertEquals(false,pHand.getCardPlayedThisTurn());
        pHand.setCardPlayedThisTurn(true);
        assertEquals(true,pHand.getCardPlayedThisTurn());
        pHand.endTurn();
    }
    @Test
    public void drawTop()throws Exception{
        setUpHand();
        pHand.drawHand(genAlgorithm.field.BOTTOM, canvasGraphics2D, assetStore,false,1000,1000);
    }
    @Test
    public void drawBot()throws Exception{
        setUpHand();
        pHand.drawHand(genAlgorithm.field.TOP,null, assetStore,false,1000,1000);
    }
    @Test
    public void drawBack()throws Exception {
        setUpHand();
        pHand.drawHand(genAlgorithm.field.TOP, canvasGraphics2D, assetStore, true, 1000, 1000);
    }

    @Test
    public void update()throws Exception{
        setUpHand();
        pHand.drawHand(genAlgorithm.field.TOP,null, assetStore,true,720,1184);
        List<TouchEvent> touchEvents = new ArrayList<>();
        TouchEvent touchEvent = new TouchEvent();
        touchEvent.type = 0;
        touchEvent.x =  560f;
        touchEvent.y =  90f;
        touchEvents.add(0,touchEvent);
        pHand.update(new ElapsedTime(), touchEvents);
        pHand.update(new ElapsedTime(), touchEvents);

    }
    @Test
    public void unSelect()throws Exception {
        setUpHand();
        pHand.drawHand(genAlgorithm.field.TOP, null, assetStore, true, 720, 1184);
        List<TouchEvent> touchEvents = new ArrayList<>();

        TouchEvent touchEvent = new TouchEvent();
        setupTouchEvent(touchEvent,0, 560f,90f);
        touchEvents.add(0, touchEvent);
        pHand.update(new ElapsedTime(), touchEvents);

        setupTouchEvent(touchEvent,0, 460f,90f);
        touchEvents.add(0, touchEvent);
        pHand.update(new ElapsedTime(), touchEvents);
        assertEquals(true,pHand.cardPicked());

        Card c  = pHand.getPickedCardFromHand();
        Card c2 = pHand.getLastCardPlayed();

        assertNotNull(c);
        assertNotNull(c2);
        assertEquals(c,c2);

        setupTouchEvent(touchEvent,1, 460f,90f);
        touchEvents.add(0, touchEvent);
        pHand.update(new ElapsedTime(), touchEvents);

        touchEvents.remove(0);
        pHand.update(new ElapsedTime(), touchEvents);
    }

    private void setupTouchEvent(TouchEvent touchEvent, int type, float x, float y) {
        touchEvent.type = type;
        touchEvent.x = x;
        touchEvent.y = y;
    }

    @Test
    public void pickCardIsNull()throws Exception {
        setUpHand();
        Card c = pHand.getPickedCardFromHand();
        assertNull(c);
    }

    @Test
    public void overdraw()throws Exception {
        setUpHand();
        pHand.addToHand(mDeck.drawFromDeck(10));
        assertEquals(pHand.MAX_HAND_SIZE, pHand.getHandSize());
    }
}
