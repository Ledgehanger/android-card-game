package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.scaleScreenReso;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;

import android.graphics.Rect;
import android.graphics.Bitmap;
import android.graphics.Paint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;



/**
 * Created by Carl on 26/04/2017.
 */

/*Display Player Stats from CSV
  Column 1 - Wins
  Column 2 - Loses
  Column 3 - EXP
  Column 4 - Module Points(MP)-used to buy things from store coming soon
 */

public class PlayerStatsScreen extends GameScreen {
    AssetStore aStore=mGame.getAssetManager();
    private Rect boundPlayerAvatar,boundBackground,boundBackArrow;
    private String wins;
    private String loses;
    private String exp;
    private String mp;

    public PlayerStatsScreen(String name,Game game)
    {
        super("Player Stats Screen",game);

        //Set Up Assets
        aStore.loadAndAddBitmap("Background","img/mmbg.jpg");
        aStore.loadAndAddBitmap("Player Avatar","img/PlayerAvatar.png");
        aStore.loadAndAddBitmap("Back Arrow","img/OptionsScreenImages/back.png");
        aStore.loadAndAddMusic("BGM","music/Butterfly Kiss.m4a");

    }

    public void update(ElapsedTime elapsedTime)
    {
        Input input=mGame.getInput();
        List<TouchEvent> touchEvents=input.getTouchEvents();
        if(touchEvents.size()>0)
        {
            TouchEvent touchEvent=touchEvents.get(0);
            if (boundBackArrow.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                aStore.getMusic("BGM").stop();
                mGame.getScreenManager().removeScreen(this.getName());
                MainMenu menu = new MainMenu("", mGame);
                mGame.getScreenManager().addScreen(menu);
            }
        }

    }

    public void draw(ElapsedTime elapsedTime, IGraphics2D iGraphics2D)
    {
        try
        {
            scaleScreenReso scaler=new scaleScreenReso(iGraphics2D);
            Bitmap bg=aStore.getBitmap("Background");
            Bitmap pa=aStore.getBitmap("Player Avatar");
            Bitmap ba=aStore.getBitmap("Back Arrow");


            if(boundBackground==null||boundPlayerAvatar==null||boundBackArrow==null)
            {
                int bLeft=0;
                int bRight=iGraphics2D.getSurfaceWidth();
                int bTop=0;
                int bBot=iGraphics2D.getSurfaceHeight();

                boundBackground=new Rect(bLeft,bTop,bRight,bBot);

                int paLeft=1000;
                int paRight=paLeft+pa.getWidth()/2;
                int paTop=25;
                int paBot=paTop+pa.getHeight()/2;

                boundPlayerAvatar=scaler.scalarect(paLeft,paTop,paRight,paBot);

                int baLeft=50;
                int baRight=baLeft+ba.getWidth();
                int baTop=50;
                int baBot=baTop+ba.getHeight();

                boundBackArrow=scaler.scalarect(baLeft,baTop,baRight,baBot);
            }

            aStore.getMusic("BGM").play();
            aStore.getMusic("BGM").setVolume(1);
            aStore.getMusic("BGM").setLopping(true);

            iGraphics2D.drawBitmap(bg,null,boundBackground,null);
            iGraphics2D.drawBitmap(pa,null,boundPlayerAvatar,null);
            iGraphics2D.drawBitmap(ba,null,boundBackArrow,null);

            scaler.drawScalaText(iGraphics2D,"Player Stats",500,100,50f);

            scaler.drawScalaText(iGraphics2D,"Wins:",100,250,50f);
            scaler.drawScalaText(iGraphics2D,"Loses:",100,400,50f);
            scaler.drawScalaText(iGraphics2D,"Win/Loss Ratio:",100,550,50f);
            scaler.drawScalaText(iGraphics2D,"Total EXP earned:",100,700,50f);

            //readCSV();

            scaler.drawScalaText(iGraphics2D,wins,640,250,50f);
            scaler.drawScalaText(iGraphics2D,loses,640,400,50f);
            scaler.drawScalaText(iGraphics2D,exp,640,550,50f);
            scaler.drawScalaText(iGraphics2D,mp,640,700,50f);

        }

        catch(Exception e)
        {
            String error=e.getMessage();

        }
    }



}
