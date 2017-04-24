package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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


    private int endscore = 0;
    public Canvas canvas;

    //Creates Rect which bound buttons
    private Rect boundPlayerScore, boundBackground, boundTitle, boundMenuBtn, boundHighScore, boundScore, boundReplayBtn;


    //Set up AssetStore
    AssetStore aStore = mGame.getAssetManager();

    Player player;
    PlayerAi playerAi;

    public GameOverState(String newName ,Game newGame)
    {
        super("GameOverState",newGame);

        //IMAGES and BUTTONS
        aStore.loadAndAddBitmap("winner", "img/EndImages/winner.png");
        aStore.loadAndAddBitmap("loser", "img/EndImages/loser.png");

        aStore.loadAndAddBitmap("title","img/EndImages/GameOverTitle.PNG");
        aStore.loadAndAddBitmap("mainmenu","img/EndImages/endGameBtn.PNG");
        aStore.loadAndAddBitmap("highScore","img/EndImages/endHighBtn.PNG");
        aStore.loadAndAddBitmap("score","img/EndImages/scoreBtn.PNG");
        aStore.loadAndAddBitmap("replay","img/EndImages/replayBtn.PNG");
        aStore.loadAndAddBitmap("background", "img/EndImages/mmbg.jpg");

        //SCORE NUMBERS
        aStore.loadAndAddBitmap("1","img/EndImages/1.png");
        aStore.loadAndAddBitmap("2","img/EndImages/2.png");
        aStore.loadAndAddBitmap("3","img/EndImages/3.png");
        aStore.loadAndAddBitmap("4","img/EndImages/4.png");
        aStore.loadAndAddBitmap("5","img/EndImages/5.png");
        aStore.loadAndAddBitmap("6","img/EndImages/6.png");
        aStore.loadAndAddBitmap("7","img/EndImages/7.png");
        aStore.loadAndAddBitmap("8","img/EndImages/8.png");
        aStore.loadAndAddBitmap("9","img/EndImages/9.png");
        aStore.loadAndAddBitmap("0","img/EndImages/0.png");

        //MUSIC
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

            Bitmap win = aStore.getBitmap("winner");
            Bitmap lose = aStore.getBitmap("loser");

            Bitmap overTitle =aStore.getBitmap("title");
            Bitmap menuBtn = aStore.getBitmap("mainmenu");
            Bitmap highScore = aStore.getBitmap("highScore");
            Bitmap score = aStore.getBitmap("score");
            Bitmap replay = aStore.getBitmap("replay");
            Bitmap backGround = aStore.getBitmap("background");

            Bitmap one = aStore.getBitmap("1");
            Bitmap two = aStore.getBitmap("2");
            Bitmap three = aStore.getBitmap("3");
            Bitmap four = aStore.getBitmap("4");
            Bitmap five = aStore.getBitmap("5");
            Bitmap six = aStore.getBitmap("6");
            Bitmap seven = aStore.getBitmap("7");
            Bitmap eight = aStore.getBitmap("8");
            Bitmap nine = aStore.getBitmap("9");
            Bitmap zero = aStore.getBitmap("0");


            if(boundPlayerScore == null || boundBackground == null || boundMenuBtn == null || boundReplayBtn == null || boundHighScore == null || boundScore == null || boundTitle == null)
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

                int menuLeft=620;
                int menuRight=menuLeft+menuBtn.getWidth();
                int menuTop=500;
                int menuBottom= menuTop+menuBtn.getHeight();


                boundTitle= scale.scalarect(titleLeft,titletop,titleRight,titlebottom);

                boundMenuBtn = scale.scalarect(menuLeft,menuTop,menuRight,menuBottom);

                int highTop=380;
                int highLeft = 375;
                int highRight = highLeft + highScore.getWidth();
                int highBottom = highTop+highScore.getHeight();

                boundHighScore = scale.scalarect(highLeft,highTop,highRight,highBottom);

                int replayLeft = 140;
                int replayRight = replayLeft+replay.getWidth();
                int replayTop = 500;
                int replayBottom = replayTop+replay.getHeight();

                boundReplayBtn = scale.scalarect(replayLeft, replayTop, replayRight, replayBottom);

                int scoreLeft = 375;
                int scoreRight = scoreLeft + score.getWidth();
                int scoreTop = 250;
                int scoreBottom = scoreTop + score.getHeight();

                boundScore = scale.scalarect(scoreLeft, scoreTop, scoreRight, scoreBottom);

                int pScoreLeft = 140;
                int pScoreRight = pScoreLeft + win.getWidth();
                int pScoreTop = 200;
                int pScoreBottom = pScoreTop + win.getHeight();

                boundPlayerScore = scale.scalarect(pScoreLeft, pScoreRight, pScoreTop, pScoreBottom);
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

            //MARK 
            if(player.getHp() > playerAi.getHp()){
                iGraphics2D.drawBitmap(win, null, boundPlayerScore, null);
            }else{
                iGraphics2D.drawBitmap(lose, null, boundPlayerScore, null);
            }


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
