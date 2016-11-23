package uk.ac.qub.eeecs.pranc.kingsofqueens.gage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.MainMenu;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.PickDeckScreen;

/**
 * Created by markm on 22/11/2016.
 */

public class CardGame extends Game {

    public CardGame(){
        super();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Go with a default 20 UPS/FPS
        setTargetFramesPerSecond(20);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Call the Game's onCreateView to get the view to be returned.
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // Create and add a stub game screen to the screen manager. We don't
        // want to do this within the onCreate method as the menu screen
        // will layout the buttons based on the size of the view.
        MainMenu stubMenuScreen = new MainMenu("test",this);
        mScreenManager.addScreen(stubMenuScreen);

        return view;
    }

    @Override
    public boolean onBackPressed() {
        // If we are already at the menu screen then exit
        if(mScreenManager.getCurrentScreen().getName().equals("MenuScreen"))
            return false;

        // Go back to the menu screen
        getScreenManager().removeScreen(mScreenManager.getCurrentScreen().getName());
        PickDeckScreen menuScreen = new PickDeckScreen(this);
        getScreenManager().addScreen(menuScreen);
        return true;
    }

}
