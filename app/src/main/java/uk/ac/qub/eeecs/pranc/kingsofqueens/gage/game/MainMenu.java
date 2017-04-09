/**
 * Created by Carl on 14/11/2016.
 */
package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.audio.Music;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ScreenManager;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Card;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.scaleScreenReso;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

public class MainMenu extends GameScreen
{

    //Creates Rect which bound buttons
    private Rect boundPlayBtn,boundOptionsBtn,boundTitle;


    //Set up AssetStore
    AssetStore aStore = mGame.getAssetManager();

    public MainMenu(String newName ,Game newGame)
    {
        super("MainMenuScreen",newGame);
        aStore.loadAndAddBitmap("Title","img/MainMenuImages/Title.PNG");
        aStore.loadAndAddBitmap("playBtn","img/MainMenuImages/playBtn.png");
        aStore.loadAndAddBitmap("optionsBtn","img/MainMenuImages/devBtn.png");
        aStore.loadAndAddSound("DISC5_02","music/DISC5_02.mp3");

    }

    @Override
    public void update(ElapsedTime elapsedTime)
    {
        Input input=mGame.getInput();
        List <TouchEvent> touchEvents=input.getTouchEvents();
        if(touchEvents.size() > 0)
        {
            TouchEvent touchEvent = touchEvents.get(0);

            if(boundPlayBtn.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0)
            {
              // aStore.getMusic("DISC5_02").pause();

                mGame.getScreenManager().removeScreen(this.getName());
                PickDeckScreen game = new PickDeckScreen(mGame);
                mGame.getScreenManager().addScreen(game);
            }

            if(boundOptionsBtn.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0) {
                mGame.getScreenManager().removeScreen(this.getName());
                OptionsScreen game = new OptionsScreen("", mGame);
                mGame.getScreenManager().addScreen(game);
            }
        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D iGraphics2D)
    {
        try
        {
            scaleScreenReso scale= new scaleScreenReso(iGraphics2D);
            Bitmap koqTitle=aStore.getBitmap("Title");
            Bitmap playGame=aStore.getBitmap("playBtn");
            Bitmap options=aStore.getBitmap("optionsBtn");


            if(boundPlayBtn==null || boundOptionsBtn == null || boundTitle == null)
            {

                int titleLeft= 0;
                int titleRight= iGraphics2D.getSurfaceWidth();
                int titletop= 60;//(iGraphics2D.getSurfaceHeight()/4)+(koqTitle.getHeight()/2);
                int titlebottom= 139;//(iGraphics2D.getSurfaceHeight()/4)-(koqTitle.getHeight()/2);

                boundTitle= new Rect(titleLeft,titletop,titleRight,titlebottom);

                int optionsLeft=(iGraphics2D.getSurfaceWidth()-options.getWidth());
                int optionsRight=optionsLeft+options.getWidth();
                int optionsTop=(iGraphics2D.getSurfaceHeight()/2);
                int optionsBottom= optionsTop+options.getHeight();

                boundOptionsBtn=new Rect(optionsLeft,optionsTop,optionsRight,optionsBottom);

                int playLeft=optionsLeft/2;
                int playRight=optionsRight/2;
                int playTop=(iGraphics2D.getSurfaceHeight()/2);
                int playBottom= playTop+playGame.getHeight();

                boundPlayBtn= new Rect(playLeft,playTop,playRight,playBottom);

            }
            iGraphics2D.clear(Color.rgb(255,255,255));
            iGraphics2D.drawBitmap(koqTitle,null,boundTitle,null);
            iGraphics2D.drawBitmap(playGame,null,boundPlayBtn,null);
            iGraphics2D.drawBitmap(options,null,boundOptionsBtn,null);

        }
        catch (Exception e)
        {
            String error = e.getMessage();
        }

    }
}
