package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.CardGame;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.CanvasGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Deck;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.RenderGameScreen;

/**
 * Created by mark on 15/04/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RenderGameScreen_Test {

    Context appContext;
    AssetStore assetStore;
    AssetManager assetManager;
    CanvasGraphics2D canvasGraphics2D;
    RenderGameScreen deckScreen;
    Deck    mDeck;
    @Before
    public void setUp() throws Exception {
        appContext       = InstrumentationRegistry.getTargetContext();
        assetManager     = appContext.getAssets();
        assetStore       = new AssetStore(new FileIO(appContext));
        canvasGraphics2D = new CanvasGraphics2D(assetManager);
        Canvas n         = new Canvas();

        canvasGraphics2D.setCanvas(n);
        Game mGame = new CardGame();
        mDeck = new Deck();
        Context appContext = InstrumentationRegistry.getTargetContext();
        assetStore = new AssetStore(new FileIO(appContext));
        assetStore.loadAndAddMusic("BGM","music/Layer Cake.m4a");
        assetStore.loadAndAddJson("Psych", "Decks/Psych.json");
        assetStore.loadAndAddJson("Neutral","Decks/Neutral.json");
        mDeck.setDeckUp(assetStore, "Psych", "Psych");

        deckScreen = new RenderGameScreen(mGame,mDeck,assetStore);
    }

    @Test
    public void draw() throws Exception{
        deckScreen.draw(new ElapsedTime(), canvasGraphics2D);
    }
    @Test
    public void update() throws Exception{
        deckScreen.update(new ElapsedTime());
    }

}
