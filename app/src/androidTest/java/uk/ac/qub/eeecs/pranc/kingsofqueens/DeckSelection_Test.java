package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.content.Context;
import android.graphics.Rect;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.DeckSelection.DeckSelection;

import static org.junit.Assert.assertEquals;

/**
 * Created by markm on 22/11/2016.
 */
@RunWith(AndroidJUnit4.class)
public class DeckSelection_Test {
    AssetStore as;
    DeckSelection mDeck;
    static String [] deckNames =
            {"Psych","Engineering", "Theology","Medical","CS", "Law"
            };


    @Test
    public void setUpDeck() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        mDeck = new DeckSelection();
        as = new AssetStore(new FileIO(appContext));

        assertEquals(true,as.loadAndAddJson("Decks", "Decks/deckTypes.json"));
    }

    @Test
    public void testThatAllKnownDecksLoaded() throws Exception{
        setUpDeck();
        ArrayList<String> names = new ArrayList<String>();
        DeckSelection[] newDecks = as.jsonToDeckCollection("Decks");

        for (DeckSelection d : newDecks){
            names.add(d.getName());
        }
        int total = 0;
        Set<String> MY_SET = new HashSet<String>(Arrays.asList(deckNames));
        for (String name : names) {
            total++;
            assertEquals(true, MY_SET.contains(name));
        }
        assertEquals(deckNames.length, total);



    }
    @Test
    public void getPath()throws Exception{
        setUpDeck();
        DeckSelection[] newDecks = as.jsonToDeckCollection("Decks");
        //Based on json layout
        assertEquals("Decks/Psych.json"      ,newDecks[0].getPath());
        assertEquals("Decks/Engineering.json",newDecks[1].getPath());
        assertEquals("Decks/Theology.json"   ,newDecks[2].getPath());
        assertEquals("Decks/Medical.json"    ,newDecks[3].getPath());
        assertEquals("Decks/CS.json"         ,newDecks[4].getPath());
        assertEquals("Decks/Law.json"        ,newDecks[5].getPath());

    }
    @Test
    public void setAndGetButton()throws Exception{
        setUpDeck();
        DeckSelection[] newDecks = as.jsonToDeckCollection("Decks");

        Rect test = new Rect(10,20,30,40);
        newDecks[0].setButton(test);

        assertEquals(test,newDecks[0].getButton());

}


}
