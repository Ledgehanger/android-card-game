package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.CardGame;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.MainActivity;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.PickDeckScreen;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;

import static org.junit.Assert.*;

/**
 * Created by mark on 14/04/2017.
 */
@RunWith(AndroidJUnit4.class)
public class PickDeckScreenTest extends Application {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class,true,true);
    Game game;
    PickDeckScreen pickDeckScreen;
    @Test
    public void update() throws Exception{
        FragmentManager fm = mActivityRule.getActivity().getFragmentManager();
        game = (Game)fm.findFragmentById(R.id.activity_fragment_id);

        if (game == null) {
            game = new CardGame();
            fm.beginTransaction().add(R.id.activity_fragment_id, game)
                    .commit();
        }
         pickDeckScreen = new PickDeckScreen(game);

        ElapsedTime elapsedTime = new ElapsedTime();

        pickDeckScreen.update(elapsedTime);
        pickDeckScreen = null;
        assertNull(pickDeckScreen);
        Thread.sleep(2000);
    }

}