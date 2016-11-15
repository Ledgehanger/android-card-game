/**
 * Created by Carl on 14/11/2016.
 */
package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

public class MainMenu extends screen
{

    //Creates Rect which bound buttons
    private Rect boundPlayBtn,boundOptionsBtn,boundTitle;


    //Set up AssetStore
    AssetStore aStore = sGame.getAssetManager();

    public MainMenu(String newName ,Game newGame)
    {
        super("MainMenuScreen",newGame);

    }

    @Override
    public void update(ElapsedTime elapsedTime)
    {
        Input input=sGame.getInput();
        List <TouchEvent> touchEvents=input.getTouchEvents();

        if(touchEvents.size()<0)
        {
            TouchEvent touchEvent = touchEvents.get(0);

            if(boundPlayBtn.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0)
            {
               aStore.getMusic("DISC5_02").pause();

                sGame.getScreenManager().removeScreen(this.getSName());
                //sGame.getScreenManager().addScreen(BuildDeckScreen);
            }

            if(boundPlayBtn.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0)
            {
                sGame.getScreenManager().removeScreen(this.getSName());
                //sGame.getScreenManager().addScreen(BuildOptionsScreen);
            }
        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D iGraphics2D)
    {
        try
        {
            Bitmap koqTitle=aStore.getBitmap("Title");
            Bitmap playGame=aStore.getBitmap("playBtn");
            Bitmap options=aStore.getBitmap("optionsBtn");

            aStore.getMusic("DISC5_02").play();
            aStore.getMusic("DISC5_02").setVolume(2);
            aStore.getMusic("DISC5_02").setLopping(true);

            if(boundPlayBtn==null)
            {
                int titleLeft=0;
                int titleRight= iGraphics2D.getSurfaceWidth();
                int titletop=(iGraphics2D.getSurfaceHeight()/4)+(koqTitle.getHeight()/2);
                int titlebottom=(iGraphics2D.getSurfaceHeight()/4)-(koqTitle.getHeight()/2);

                boundTitle= new Rect(titleLeft,titletop,titleRight,titlebottom);

                int playLeft=(iGraphics2D.getSurfaceWidth()-playGame.getWidth());
                int playRight=playLeft+playGame.getWidth();
                int playTop=(iGraphics2D.getSurfaceHeight()/2);
                int playBottom= playTop+playGame.getHeight();

                boundPlayBtn= new Rect(playLeft,playTop,playRight,playBottom);

                int optionsLeft=(iGraphics2D.getSurfaceWidth()-options.getWidth());
                int optionsRight=optionsLeft+options.getWidth();
                int optionsTop=(iGraphics2D.getSurfaceHeight()/2);
                int optionsBottom= optionsTop+options.getHeight();

                boundOptionsBtn= new Rect(optionsLeft,optionsTop,optionsRight,optionsBottom);

            }
            iGraphics2D.clear(Color.rgb(255,255,255));
            iGraphics2D.drawBitmap(koqTitle,null,boundTitle,null);
            iGraphics2D.drawBitmap(playGame,null,boundPlayBtn,null);
            iGraphics2D.drawBitmap(options,null,boundOptionsBtn,null);

        }
        catch (Exception e)
        {

        }

    }
}
