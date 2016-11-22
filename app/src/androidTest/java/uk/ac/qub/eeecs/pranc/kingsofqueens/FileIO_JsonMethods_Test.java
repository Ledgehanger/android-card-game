package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Card;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Deck;

import static org.junit.Assert.*;

/**
 * Created by markm on 20/11/2016.
 */
@RunWith(AndroidJUnit4.class)
public class FileIO_JsonMethods_Test {
        @Test
        public void useFileIOJsonMethods()throws Exception {
            Context appContext = InstrumentationRegistry.getTargetContext();

            AssetStore as = new AssetStore(new FileIO(appContext));

            Deck mDeck = new Deck();
            as.loadAndAddJson("Psych", "Decks/Psych.json");
            Card[] card1 = mDeck.jsonToCardCollection(as, "Psych");
            Card[] card2 = mDeck.jsonToCardCollection(as, "Psych");

            assertEquals(11, card1[0].id);
            assertEquals(12, card1[1].id);
            assertEquals(13, card1[2].id);
            assertEquals(14, card1[3].id);
        }



}