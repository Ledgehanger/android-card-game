package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.R;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.scaleScreenReso;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;

/**
 * Created by ariftahir on 19/04/2017.
 */

public class GameOverState extends GameScreen{
    //CREATED TO REPLACE GAMEOVERSCREEN, USING JAVA INSTEAD OF JAVA AND XML


    //Creates Rect which bound buttons
    private Rect boundBackground, boundTitle, boundMenuBtn, boundHighScore, boundScore, boundReplayBtn;


    //Set up AssetStore
    AssetStore aStore = mGame.getAssetManager();

    public GameOverState(String newName ,Game newGame)
    {
        super("GameOverState",newGame);
        aStore.loadAndAddBitmap("title","img/EndImages/GameOverTitle.PNG");
        aStore.loadAndAddBitmap("mainmenu","img/EndImages/endGameBtn.PNG");
        aStore.loadAndAddBitmap("highscore","img/EndImages/endHighBtn.png");
        aStore.loadAndAddBitmap("score","img/EndImages/scoreBtn.PNG");
        aStore.loadAndAddBitmap("replay","img/EndImages/replayBtn.PNG");
        aStore.loadAndAddBitmap("background", "img/EndImages/mmbg.jpg");

        aStore.loadAndAddMusic("BGM","music/DISC5_02.mp3");

    }

    @Override
    public void update(ElapsedTime elapsedTime)
    {
        Input input=mGame.getInput();
        List<TouchEvent> touchEvents=input.getTouchEvents();
        if(touchEvents.size() > 0)
        {
            TouchEvent touchEvent = touchEvents.get(0);

            if(boundMenuBtn.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0)
            {
                aStore.getMusic("BGM").stop();

                mGame.getScreenManager().removeScreen(this.getName());
                MainMenu menu = new MainMenu("", mGame);
                mGame.getScreenManager().addScreen(menu);
            }
            if(boundReplayBtn.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0) {
                aStore.getMusic("BGM").stop();
                mGame.getScreenManager().removeScreen(this.getName());
                PickDeckScreen game = new PickDeckScreen(mGame, mGame.getAssetManager());
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

            Bitmap overTitle =aStore.getBitmap("title");
            Bitmap menuBtn = aStore.getBitmap("mainmenu");
            Bitmap highScore = aStore.getBitmap("highscore");
            Bitmap score = aStore.getBitmap("score");
            Bitmap replay = aStore.getBitmap("replay");
            Bitmap backGround = aStore.getBitmap("background");


            if(boundBackground == null || boundMenuBtn == null || boundReplayBtn == null || boundHighScore == null || boundScore == null || boundTitle == null)
            {
                int bgLeft=0;
                int bgRight=iGraphics2D.getSurfaceWidth();
                int bgTop=0;
                int bgBot=iGraphics2D.getSurfaceHeight();

                boundBackground = new Rect(bgLeft,bgTop,bgRight,bgBot);

                int titleLeft= 200;
                int titleRight= titleLeft+overTitle.getWidth();
                int titletop= 60;
                int titlebottom= 139;

                boundTitle= scale.scalarect(titleLeft,titletop,titleRight,titlebottom);

                int menuLeft=375;
                int menuRight=menuLeft+menuBtn.getWidth();
                int menuTop=450;
                int menuBottom= menuTop+menuBtn.getHeight();

                boundMenuBtn = scale.scalarect(menuLeft,menuTop,menuRight,menuBottom);


                int highTop=250;
                int highBottom = highTop+highScore.getHeight();

                boundHighScore = scale.scalarect(menuLeft,highTop,menuRight,highBottom);

                int replayLeft = 0;
                int replayRight = replayLeft+replay.getWidth();
                int replayTop = 475;
                int replayBottom = replayTop+replay.getHeight();

                boundReplayBtn = scale.scalarect(replayLeft, replayTop, replayRight, replayBottom);

                int scoreLeft = 375;
                int scoreRight = scoreLeft + score.getWidth();
                int scoreTop = 350;
                int scoreBottom = scoreTop + score.getHeight();

            }
            aStore.getMusic("BGM").play();
            aStore.getMusic("BGM").setVolume(1);
            aStore.getMusic("BGM").setLopping(true);

            iGraphics2D.clear(Color.rgb(255,255,255));
            iGraphics2D.drawBitmap(backGround,null,boundBackground,null);
            iGraphics2D.drawBitmap(overTitle,null,boundTitle,null);
            iGraphics2D.drawBitmap(menuBtn,null,boundMenuBtn,null);
            iGraphics2D.drawBitmap(highScore,null,boundHighScore,null);
            iGraphics2D.drawBitmap(score,null,boundScore,null);
            iGraphics2D.drawBitmap(replay,null,boundReplayBtn,null);
        }
        catch (Exception e)
        {
            String error = e.getMessage();
        }

    }

    public class ActivityName extends Activity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // remove title
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_main);
        }
    }
}
