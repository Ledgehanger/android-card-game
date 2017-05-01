package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

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

    private Rect boundBackground, boundTitle, boundHeader, boundHigh1, boundHigh11, boundHigh2, boundHigh22, boundHigh3, boundHigh33, boundHigh4, boundHigh44 ,boundHigh5, boundHigh55, boundBack;
    private Settings settings;
    AssetStore aStore = mGame.getAssetManager();

    public HighState(String name, Game game) {
        super(name, game);
        aStore.loadAndAddBitmap("background", "img/mmbg.jpg");
        aStore.loadAndAddBitmap("title", "img/highstate/Title.PNG");
        aStore.loadAndAddBitmap("header", "img/highstate/scoreboard.png");
        aStore.loadAndAddBitmap("highwin", "img/highstate/winner.png");
        aStore.loadAndAddBitmap("highlose", "img/highstate/loser.png");
        aStore.loadAndAddBitmap("back", "img/highstate/back.png");
        aStore.loadAndAddBitmap("one", "img/highstate/1.png");
        aStore.loadAndAddBitmap("two", "img/highstate/2.png");
        aStore.loadAndAddBitmap("three", "img/highstate/3.png");
        aStore.loadAndAddBitmap("four", "img/highstate/4.png");
        aStore.loadAndAddBitmap("five", "img/highstate/5.png");
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

            Bitmap background = aStore.getBitmap("background");
            Bitmap title = aStore.getBitmap("title");
            Bitmap header = aStore.getBitmap("header");
            Bitmap highWin = aStore.getBitmap("highwin");
            Bitmap highLose = aStore.getBitmap("highlose");
            Bitmap back = aStore.getBitmap("back");
            Bitmap one = aStore.getBitmap("one");
            Bitmap two = aStore.getBitmap("two");
            Bitmap three = aStore.getBitmap("three");
            Bitmap four = aStore.getBitmap("four");
            Bitmap five = aStore.getBitmap("five");

            if(boundBackground == null || boundTitle == null || boundHeader == null || boundHigh1 == null || boundHigh2 == null || boundHigh3 == null ||
                    boundHigh4 == null || boundHigh5 == null || boundBack == null
                    || boundHigh11 == null || boundHigh22 == null || boundHigh33 == null || boundHigh44 == null || boundHigh55 == null){
                int bgLeft = 0;
                int bgRight = graphics2D.getSurfaceWidth();
                int bgTop = 0;
                int bgBot = graphics2D.getSurfaceHeight();

                boundBackground = new Rect(bgLeft, bgTop, bgRight, bgBot);

                int titleLeft = 200;
                int titleRight = titleLeft + title.getWidth();
                int titletop = 30;
                int titlebottom = titletop + title.getHeight();

                boundTitle = scale.scalarect(titleLeft,titletop,titleRight,titlebottom);

                int headerLeft = 380;
                int headerRight = headerLeft + header.getWidth();
                int headerTop = 240;
                int headerBottom = headerTop + header.getHeight();

                boundHeader = new Rect(headerLeft, headerTop, headerRight, headerBottom);

                int high1Left = 200;
                int high1Right = high1Left + one.getWidth();
                int high1Top = 180;
                int high1Bottom = high1Top + one.getHeight();

                boundHigh1 = scale.scalarect(high1Left, high1Top, high1Right, high1Bottom);

                int high11Left = 300;
                int high11Right = high11Left + highWin.getWidth();
                int high11Top = 180;
                int high11Bottom = high11Top + highWin.getHeight();

                boundHigh11 = scale.scalarect(high11Left, high11Top, high11Right, high11Bottom);

                int high2Left = 200;
                int high2Right = high2Left + one.getWidth();
                int high2Top = 280;
                int high2Bottom = high2Top + one.getHeight();

                boundHigh2 = scale.scalarect(high2Left, high2Top, high2Right, high2Bottom);

                int high22Left = 300;
                int high22Right = high22Left + highWin.getWidth();
                int high22Top = 280;
                int high22Bottom = high22Top + highWin.getHeight();

                boundHigh22 = scale.scalarect(high22Left, high22Top, high22Right, high22Bottom);

                int high3Left = 200;
                int high3Right = high3Left + one.getWidth();
                int high3Top = 380;
                int high3Bottom = high3Top + one.getHeight();

                boundHigh3 = scale.scalarect(high3Left, high3Top, high3Right, high3Bottom);

                int high33Left = 300;
                int high33Right = high33Left + highWin.getWidth();
                int high33Top = 380;
                int high33Bottom = high33Top + highWin.getHeight();

                boundHigh33 = scale.scalarect(high33Left, high33Top, high33Right, high33Bottom);

                int high4Left = 200;
                int high4Right = high4Left + one.getWidth();
                int high4Top = 480;
                int high4Bottom = high4Top + one.getHeight();

                boundHigh4 = scale.scalarect(high4Left, high4Top, high4Right, high4Bottom);

                int high44Left = 300;
                int high44Right = high44Left + highWin.getWidth();
                int high44Top = 480;
                int high44Bottom = high44Top + highWin.getHeight();

                boundHigh44 = scale.scalarect(high44Left, high44Top, high44Right, high44Bottom);

                int high5Left = 200;
                int high5Right = high5Left + one.getWidth();
                int high5Top = 580;
                int high5Bottom = high5Top + one.getHeight();

                boundHigh5 = scale.scalarect(high5Left, high5Top, high5Right, high5Bottom);

                int high55Left = 300;
                int high55Right = high55Left + highWin.getWidth();
                int high55Top = 580;
                int high55Bottom = high55Top + highWin.getHeight();

                boundHigh55 = scale.scalarect(high55Left, high55Top, high55Right, high55Bottom);

                int backLeft = 10;
                int backRight = backLeft + back.getWidth();
                int backTop = 640;
                int backBottom = backTop + back.getHeight();

                boundBack = scale.scalarect(backLeft, backTop, backRight, backBottom);
            }

            graphics2D.clear(Color.rgb(255, 255, 255));
            graphics2D.drawBitmap(background, null, boundBackground, null);
            graphics2D.drawBitmap(title, null, boundTitle, null);
            graphics2D.drawBitmap(header, null, boundHeader, null);
            graphics2D.drawBitmap(back, null, boundBack, null);
            graphics2D.drawBitmap(one, null, boundHigh1, null);
            graphics2D.drawBitmap(two, null, boundHigh2, null);
            graphics2D.drawBitmap(three, null, boundHigh3, null);
            graphics2D.drawBitmap(four, null, boundHigh4, null);
            graphics2D.drawBitmap(five, null, boundHigh5, null);

            File file = new File("img/koq.txt");
            Scanner input = null;
            try {
                input = new Scanner(file);
                if(input.nextLine().equals(Integer.parseInt("1"))){
                    graphics2D.drawBitmap(highWin, null, boundHigh11, null);
                }else{
                    graphics2D.drawBitmap(highLose, null, boundHigh11, null);
                }

                if(input.nextLine().equals(Integer.parseInt("1"))){
                    graphics2D.drawBitmap(highWin, null, boundHigh22, null);
                }else{
                    graphics2D.drawBitmap(highLose, null, boundHigh22, null);
                }

                if(input.nextLine().equals(Integer.parseInt("1"))){
                    graphics2D.drawBitmap(highWin, null, boundHigh33, null);
                }else{
                    graphics2D.drawBitmap(highLose, null, boundHigh33, null);
                }

                if(input.nextLine().equals(Integer.parseInt("1"))){
                    graphics2D.drawBitmap(highWin, null, boundHigh44, null);
                }else{
                    graphics2D.drawBitmap(highLose, null, boundHigh44, null);
                }

                if(input.nextLine().equals(Integer.parseInt("1"))){
                    graphics2D.drawBitmap(highWin, null, boundHigh55, null);
                }else{
                    graphics2D.drawBitmap(highLose, null, boundHigh55, null);
                }

                


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        }


    }