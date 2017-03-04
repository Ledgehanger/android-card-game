package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;


/**
 * Created by markm on 25/11/2016.
 */


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.GenAlgorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.util.BoundingBox;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameObject;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.LayerViewport;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.ScreenViewport;


public class RenderGameScreen extends GameScreen {

    private final float LEVEL_WIDTH = 1000.0f;
    private final float LEVEL_HEIGHT = 1000.0f;
    private ScreenViewport mScreenViewport;
    private LayerViewport mLayerViewport;
    private GameObject mQueensBackground; //TO INCLUDE IMAGE OF LANYON
    private PlayerCards mPlayerCards; //PUT CARD IMAGES ON PLAYERCARDS CLASS
    private final int NUM_CARD_SPACES = 12; //NEED TO DRAW GRID FOR CARDS
    private List<PlayerCards> mCards; //TO IMPLEMENT CARD CLASS


    private Player player, playerAI;

    public RenderGameScreen(Game game, Deck playerDeck) throws Exception {
        super("RenderGameScreen", game);
        game.getAssetManager().loadAndAddBitmap("deckimg", "img/PlayerIcons/deckimg.png");




        playerAI = new PlayerAi("",game);
        player = new Player("", playerDeck);


        mScreenViewport = new ScreenViewport(0, 0, game.getScreenWidth(),
                game.getScreenHeight());

        if (mScreenViewport.width > mScreenViewport.height)
            mLayerViewport = new LayerViewport(240.0f, 240.0f
                    * mScreenViewport.height / mScreenViewport.width, 240,
                    240.0f * mScreenViewport.height / mScreenViewport.width);
        else
            mLayerViewport = new LayerViewport(240.0f * mScreenViewport.height
                    / mScreenViewport.width, 240.0f, 240.0f
                    * mScreenViewport.height / mScreenViewport.width, 240);

        AssetStore assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("QueensBackground", "GameScreenImages/QueensBackground.JPG");
        assetManager.loadAndAddBitmap("HealthMonitor", "GameScreenImages/HealthMonitor.png");
        assetManager.loadAndAddBitmap("PlayerPictureHolder", "img/pph.png");

        mQueensBackground = new GameObject(LEVEL_WIDTH / 2.0f,
                LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
                .getAssetManager().getBitmap("QueensBackground"), this);

        mPlayerCards = new PlayerCards(100, 200, this);
    }

    public PlayerCards getmPlayerCards() {

        return mPlayerCards;
    }

    public List<PlayerCards> getPlayerCards() {
        return mCards;
    }

    public void update(ElapsedTime elapsedTime) {





        // unknown code
        mPlayerCards.update(elapsedTime);

        BoundingBox playerBound = mPlayerCards.getBound();
        if (playerBound.getLeft() < 0)
            mPlayerCards.position.x -= playerBound.getLeft();
        else if (playerBound.getRight() > LEVEL_WIDTH)
            mPlayerCards.position.x -= (playerBound.getRight() - LEVEL_WIDTH);

        if (playerBound.getBottom() < 0)
            mPlayerCards.position.y -= playerBound.getBottom();
        else if (playerBound.getTop() > LEVEL_HEIGHT)
            mPlayerCards.position.y -= (playerBound.getTop() - LEVEL_HEIGHT);

        mLayerViewport.x = mPlayerCards.position.x;
        mLayerViewport.y = mPlayerCards.position.y;

        if (mLayerViewport.getLeft() < 0)
            mLayerViewport.x -= mLayerViewport.getLeft();
        else if (mLayerViewport.getRight() > LEVEL_WIDTH)
            mLayerViewport.x -= (mLayerViewport.getRight() - LEVEL_WIDTH);

        if (mLayerViewport.getBottom() < 0)
            mLayerViewport.y -= mLayerViewport.getBottom();
        else if (mLayerViewport.getTop() > LEVEL_HEIGHT)
            mLayerViewport.y -= (mLayerViewport.getTop() - LEVEL_HEIGHT);
    }

    public void draw(ElapsedTime elapsedTime, IGraphics2D iGraphics2D) {

        iGraphics2D.clear(Color.BLACK);
        iGraphics2D.clipRect(mScreenViewport.toRect());

        mQueensBackground.draw(elapsedTime, iGraphics2D, mLayerViewport, mScreenViewport);
        //Draw Deck
        Bitmap deck = mGame.getAssetManager().getBitmap("deckimg");
        Bitmap deck2 = mGame.getAssetManager().getBitmap("deckimg");

        Rect deckRect = player.playerDeck.drawDeck(GenAlgorithm.field.BOTTOM, iGraphics2D);
        iGraphics2D.drawBitmap(deck, null, deckRect, null);

        Rect deckRect2 = playerAI.playerDeck.drawDeck(GenAlgorithm.field.TOP, iGraphics2D);
        iGraphics2D.drawBitmap(deck2, null, deckRect2, null);


    }
}