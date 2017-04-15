package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.method.Touch;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

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
    Player player;
    Player ePlayer;
    Hand pHand;
    Deck mDeck;
    AssetStore as;
    @Test
    public void setUpHand() throws Exception {
        mDeck = new Deck();
        Context appContext = InstrumentationRegistry.getTargetContext();
        as = new AssetStore(new FileIO(appContext));
        as.loadAndAddJson("Psych", "Decks/Psych.json");
        as.loadAndAddJson("Neutral","Decks/Neutral.json");
        mDeck.setDeckUp(as, "Psych", "Psych");
        as.loadAndAddBitmap("Hand", "img/PlayerIcons/HandCanvas.png");
        pHand = new Hand(mDeck.drawFromDeck(3));
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
        pHand.drawHand(genAlgorithm.field.BOTTOM,null,as,false,1000,1000);
    }
    @Test
    public void drawBot()throws Exception{
        setUpHand();
        pHand.drawHand(genAlgorithm.field.TOP,null,as,false,1000,1000);
    }
    @Test
    public void drawBack()throws Exception{
        setUpHand();
        pHand.drawHand(genAlgorithm.field.TOP,null,as,true,1000,1000);
    }
    @Test
    public void update()throws Exception{
        setUpHand();
        pHand.drawHand(genAlgorithm.field.TOP,null,as,true,720,1184);
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
        pHand.drawHand(genAlgorithm.field.TOP, null, as, true, 720, 1184);
        List<TouchEvent> touchEvents = new ArrayList<>();
        TouchEvent touchEvent = new TouchEvent();
        touchEvent.type = 0;
        touchEvent.x = 560f;
        touchEvent.y = 90f;
        touchEvents.add(0, touchEvent);
        pHand.update(new ElapsedTime(), touchEvents);
        touchEvent.type = 0;
        touchEvent.x = 460f;
        touchEvent.y = 90f;
        touchEvents.add(0, touchEvent);
        pHand.update(new ElapsedTime(), touchEvents);
        assertEquals(true,pHand.cardPicked());
        Card c = pHand.getPickedCardFromHand();
        Card c2 = pHand.getLastCardPlayed();
        assertNotNull(c);
        assertNotNull(c2);
        assertEquals(c,c2);
    }
    @Test
    public void pickCardIsNull()throws Exception {
        setUpHand();
        Card c = pHand.getPickedCardFromHand();
        assertNull(c);
    }
}
