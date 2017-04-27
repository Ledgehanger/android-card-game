package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;

/**
 * Created by ariftahir on 27/04/2017.
 */

public class HighState extends GameScreen{

    private Rect boundBackground, boundTitle, boundHeader, boundHigh1, boundHigh2, boundHigh3, boundHigh4, boundHigh5, boundBack;
    private Settings settings;
    AssetStore aStore = mGame.getAssetManager();

    public HighState(String name, Game game) {
        super(name, game);
        aStore.loadAndAddBitmap("background", "img/highstate/background.png");
        aStore.loadAndAddBitmap("title" , "img/highstate/title.png");
        aStore.loadAndAddBitmap("header", "img/highstate/header.png");
        aStore.loadAndAddBitmap("highwin", "img/highstate/win.png");
        aStore.loadAndAddBitmap("highlose", "img/highstate/lose.png");
    }

    @Override
    public void update(ElapsedTime elapsedTime) {
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {
            TouchEvent touchEvent = touchEvents.get(0);

            if (boundBack.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                aStore.getMusic("BGM").stop();
                mGame.getScreenManager().removeScreen(this.getName());
                MainMenu menu = new MainMenu("", mGame);
                mGame.getScreenManager().addScreen(menu);
            }
        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        try{
            Bitmap backGround = aStore.getBitmap("background");
            Bitmap title = aStore.getBitmap("title");
            Bitmap header = aStore.getBitmap("header");
            Bitmap highWin = aStore.getBitmap("highwin");
            Bitmap highLose = aStore.getBitmap("highlose");

            if(boundBackground == null || boundTitle == null || boundHeader == null || boundHigh1 == null || boundHigh2 == null || boundHigh3 == null ||
                    boundHigh4 == null || boundHigh5 == null || boundBack == null){
                int bgLeft = 0;
                int bgRight = graphics2D.getSurfaceWidth();
                int bgTop = 0;
                int bgBot = graphics2D.getSurfaceHeight();

                boundBackground = new Rect(bgLeft, bgTop, bgRight, bgBot);

                int titleLeft = 200;
                int titleRight = titleLeft + title.getWidth();
                int titletop = 60;//(iGraphics2D.getSurfaceHeight()/4)+(koqTitle.getHeight()/2);
                int titlebottom = 139;//(iGraphics2D.getSurfaceHeight()/4)-(koqTitle.getHeight()/2);

                //boundTitle= scale.scalarect(titleLeft,titletop,titleRight,titlebottom);

            }



        }catch(Exception e){
            e.printStackTrace();
        }

        }

    }