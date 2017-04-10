package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;


/**
 * Created by markm on 25/11/2016.
 */


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.OwnerEffectedAbility;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
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

    //Game Objects
    private GameTurn currentGame;
    private Player player, playerAI;
    private String ButtonText = "End phase";
    private boolean ingorePlayerInput = false;

    public RenderGameScreen(Game game, Deck playerDeck) throws Exception {
        super("RenderGameScreen", game);

        playerAI = new PlayerAi("PlayerAiIcon",game,genAlgorithm.field.TOP);
        player = new Player("PlayerIcon", game.getAssetManager(), playerDeck, genAlgorithm.field.BOTTOM);

        game.getAssetManager().loadAndAddBitmap("deckimg", "img/PlayerIcons/deckimg.png");
        game.getAssetManager().loadAndAddBitmap("Hand", "img/PlayerIcons/HandCanvas.png");


        playerAI.playerDeck.setDeckImg(mGame.getAssetManager().getBitmap("deckimg"));
        player.playerDeck.setDeckImg(mGame.getAssetManager().getBitmap("deckimg"));

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
        assetManager.loadAndAddBitmap("PlayerPictureHolder", "img/PlayerIcons/PlayerIcon.png");

        mQueensBackground = new GameObject(LEVEL_WIDTH / 2.0f,
                LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
                .getAssetManager().getBitmap("QueensBackground"), this);

        mPlayerCards = new PlayerCards(100, 200, this);

        currentGame = new GameTurn(player.getId(),playerAI.getId());
    }

    public PlayerCards getmPlayerCards() {

        return mPlayerCards;
    }

    public List<PlayerCards> getPlayerCards() {
        return mCards;
    }

    public void update(ElapsedTime elapsedTime) {
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();

        setIngorePlayer();

        if(ingorePlayerInput) {
            //do ai turn
            currentGame.getNextPhase();
        }else{
            //do player turn
            if (currentGame.getCurrentPhase() == GameTurn.turnTypes.startPhase) {
                startPlayerTurn(currentGame.getCurrentPlayerID());
                currentGame.getNextPhase();

            } else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.placeCard) {
                    player.playerHand.update(elapsedTime, touchEvents);

                    if(player.playerHand.cardPicked()){
                        //How Ability are going to work atm
                        Card c = player.playerHand.getPickedCardFromHand();

                        if(c.ability instanceof OwnerEffectedAbility){
                            ((OwnerEffectedAbility) c.ability).addEffectPlayer(player);
                        }
                        if(c.ability.getHasAbility() == true){
                            c.ability.effect("level1");
                        }
                        currentGame.getNextPhase();
                    }


            } else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.choseALane) {
                if (currentGame.isFirstTurn())
                    currentGame.getNextPhase();


            } else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.endTurn) {

                currentGame.getNextPhase();
            } else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.gameOver) {

            }
        }
        }

    private void setIngorePlayer() {
        if(currentGame.getCurrentPlayerID() == player.id)
            ingorePlayerInput = false;
        else
            ingorePlayerInput = true;
    }


    private void startPlayerTurn(String currentTurnId){
        if(currentTurnId == player.id)
            player.playerStartTurn();
        else
            playerAI.playerStartTurn();
    }




    public void draw(ElapsedTime elapsedTime, IGraphics2D iGraphics2D) {

       iGraphics2D.clear(Color.BLACK);
       iGraphics2D.clipRect(mScreenViewport.toRect());
       //Draw Background
       mQueensBackground.draw(elapsedTime, iGraphics2D, mLayerViewport, mScreenViewport);
       //Draw Player
       playerAI.drawPlayer(iGraphics2D,getGame().getAssetManager());
       player.drawPlayer(iGraphics2D,getGame().getAssetManager());

    }
}