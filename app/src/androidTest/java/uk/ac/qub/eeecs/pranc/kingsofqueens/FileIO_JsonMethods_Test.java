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
            mDeck.addCardsInDeckToDeckFromJSONFile(as, "Psych", mDeck.SIZE_OF_CLASS_DECK);
            int draws = 3;
            Card [] check = mDeck.drawFromDeck(draws);

            for(int i = 0; i < draws; i++)
                assertNotNull(check[i]);
        }



}