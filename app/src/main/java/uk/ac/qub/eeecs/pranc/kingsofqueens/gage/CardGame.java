package uk.ac.qub.eeecs.pranc.kingsofqueens.gage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.MainGameScreens.MainMenu;


/**
 * Created by markm on 22/11/2016.
 */

public class CardGame extends Game {

    public static final String MENUSCREEN = "MainMenuScreen";

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

        // Create and add a stub game screen to the screen manager.
        // Start up with MainMenu
        MainMenu stubMenuScreen = new MainMenu(MENUSCREEN,this);
        mScreenManager.addScreen(stubMenuScreen);

        return view;
    }

    @Override
    public boolean onBackPressed() {
        // If we are already at the menu screen then exit
        if(mScreenManager.getCurrentScreen().getName().equals(MENUSCREEN))
            return false;

        // Go back to the menu screen
        getScreenManager().removeScreen(mScreenManager.getCurrentScreen().getName());
        MainMenu menuScreen = new MainMenu(MENUSCREEN,this);
        getScreenManager().addScreen(menuScreen);
        return true;
    }

}
