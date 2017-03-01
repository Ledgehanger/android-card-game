package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Card;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Deck;

import static org.junit.Assert.assertEquals;

/**
 * Created by markm on 20/11/2016.
 */

//TODO Create unit test for ai deck
@RunWith(AndroidJUnit4.class)
public class Deck_Test {
    AssetStore as;
    Deck mDeck;
    Card[] card1;
    Card[] card2;
    @Test
    public void setUpDeck() throws Exception {
        mDeck = new Deck();
        Context appContext = InstrumentationRegistry.getTargetContext();
        as = new AssetStore(new FileIO(appContext));
        as.loadAndAddJson("Psych", "Decks/Psych.json");
        as.loadAndAddJson("Neutral","Decks/Neutral.json");
        // TODO: 20/11/2016 This needs to be in its own test case for deck
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
        assertEquals(true, mDeck.deckIsEmpty);
    }

}


