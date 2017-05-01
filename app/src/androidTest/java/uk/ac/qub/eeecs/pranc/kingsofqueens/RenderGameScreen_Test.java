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

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.CardGame;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.CanvasGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.GameObjects.Deck;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.MainGameScreens.RenderGameScreen;

/**
 * Created by mark on 15/04/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RenderGameScreen_Test {

    Context             appContext;
    AssetStore          assetStore;
    AssetManager        assetManager;
    CanvasGraphics2D    canvasGraphics2D;
    RenderGameScreen    deckScreen;
    Deck                mDeck;
    @Before
    public void setUp() throws Exception {
        appContext       = InstrumentationRegistry.getTargetContext();
        assetManager     = appContext.getAssets();
        assetStore       = new AssetStore(new FileIO(appContext));
        canvasGraphics2D = new CanvasGraphics2D(assetManager);
        Canvas canvas = new Canvas();

        canvasGraphics2D.setCanvas(canvas);
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
    @Test
    public void updateCards() throws Exception{
        List<TouchEvent> touchEvents = new ArrayList<>();
        TouchEvent touchEvent = new TouchEvent();
        touchEvent.type = 0;
        touchEvent.x =  -150;
        touchEvent.y =  50;
        touchEvents.add(0,touchEvent);
        deckScreen.draw(new ElapsedTime(), canvasGraphics2D);
        deckScreen.playerPlaceCardPhase(new ElapsedTime(), touchEvents);
    }
    @Test
    public void playOutPlayeerTurn() throws Exception{
        List<TouchEvent> touchEvents = new ArrayList<>();
        TouchEvent touchEvent = new TouchEvent();

        touchEvent.type = 0;
        touchEvent.x =  218 ;
        touchEvent.y =  606 ;
        touchEvents.add(0,touchEvent);
        deckScreen.draw(new ElapsedTime(),canvasGraphics2D);
        deckScreen.drawPlayer(1184,720,canvasGraphics2D);
        deckScreen.playerPlaceCardPhase(new ElapsedTime(), touchEvents);

        touchEvent.type = 0;
        touchEvent.x =  395 ;
        touchEvent.y =  408;
        touchEvents.add(0,touchEvent);
        deckScreen.playerPlaceCardPhase(new ElapsedTime(), touchEvents);


        deckScreen.nextCurrentTurn();
        deckScreen.update(new ElapsedTime());
        deckScreen.nextCurrentTurn();
        deckScreen.update(new ElapsedTime());
        deckScreen.update(new ElapsedTime());
    }


}
