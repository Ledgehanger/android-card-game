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
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.PickDeckScreen;

/**
 * Created by mark on 15/04/2017.
 */
@RunWith(AndroidJUnit4.class)
public class PickDeckScreen_Test {
    Context             appContext;
    AssetStore          assetStore;
    AssetManager        assetManager;
    CanvasGraphics2D    canvasGraphics2D;
    PickDeckScreen      deckScreen;

    @Before
    public void setUp() throws Exception {
        appContext       = InstrumentationRegistry.getTargetContext();
        assetManager     = appContext.getAssets();
        assetStore       = new AssetStore(new FileIO(appContext));
        canvasGraphics2D = new CanvasGraphics2D(assetManager);
        Canvas n         = new Canvas();

        canvasGraphics2D.setCanvas(n);
        Game mGame = new CardGame();

        deckScreen = new PickDeckScreen(mGame,assetStore);
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
    public void updateTouchEvents() throws Exception{
        draw();
        List<TouchEvent> touchEvents = new ArrayList<>();
        TouchEvent touchEvent = new TouchEvent();
        touchEvent.type = 0;
        touchEvent.x =  0 ;
        touchEvent.y =  0;
        touchEvents.add(0,touchEvent);
        deckScreen.updateTouchEvents(touchEvents);
    }

}
