package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Color;
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
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.scaleScreenReso;

/**
 * Created by ariftahir on 27/04/2017.
 */

public class HighState extends GameScreen {

    private Rect boundBackground, boundTitle, boundHeader, boundHigh1, boundHigh2, boundHigh3, boundHigh4, boundHigh5, boundBack;
    private Settings settings;
    AssetStore aStore = mGame.getAssetManager();

    public HighState(String name, Game game) {
        super(name, game);
        aStore.loadAndAddBitmap("background", "img/highstate/background.png");
        aStore.loadAndAddBitmap("title", "img/highstate/title.png");
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
            scaleScreenReso scale = new scaleScreenReso(graphics2D);

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
                int titletop = 60;
                int titlebottom = 139;

                boundTitle = scale.scalarect(titleLeft,titletop,titleRight,titlebottom);

                int headerLeft = 200;
                int headerRight = headerLeft + header.getWidth();
                int headerTop = 120;
                int headerBottom = headerTop + header.getHeight();

                boundHeader = new Rect(headerLeft, headerTop, headerRight, headerBottom);

                int high1Left = 200;
                int high1Right = high1Left + highWin.getWidth();
                int high1Top = 150;
                int high1Bottom = high1Top + highWin.getHeight();

                boundHigh1 = scale.scalarect(high1Left, high1Top, high1Right, high1Bottom);

                int high2Left = 200;
                int high2Right = high2Left + highWin.getWidth();
                int high2Top = 150;
                int high2Bottom = high2Top + highWin.getHeight();

                boundHigh2 = scale.scalarect(high2Left, high2Top, high2Right, high2Bottom);

                int high3Left = 200;
                int high3Right = high3Left + highWin.getWidth();
                int high3Top = 150;
                int high3Bottom = high3Top + highWin.getHeight();

                boundHigh3 = scale.scalarect(high3Left, high3Top, high3Right, high3Bottom);

                int high4Left = 200;
                int high4Right = high4Left + highWin.getWidth();
                int high4Top = 150;
                int high4Bottom = high4Top + highWin.getHeight();

                boundHigh4 = scale.scalarect(high4Left, high4Top, high4Right, high4Bottom);

                int high5Left = 200;
                int high5Right = high5Left + highWin.getWidth();
                int high5Top = 150;
                int high5Bottom = high5Top + highWin.getHeight();

                boundHigh5 = scale.scalarect(high5Left, high5Top, high5Right, high5Bottom);

            }

            graphics2D.clear(Color.rgb(255, 255, 255));
            graphics2D.drawBitmap(backGround, null, boundBackground, null);

        }catch(Exception e){
            e.printStackTrace();
        }

        }

    }