package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;
/**
 * Created by markm on 25/11/2016.
 */
import android.graphics.Color;

import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.OwnerEffectedAbility;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameObject;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.LayerViewport;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.ScreenViewport;


public class RenderGameScreen extends GameScreen {

    private final float LEVEL_WIDTH  = 1000.0f;
    private final float LEVEL_HEIGHT = 1000.0f;
    private ScreenViewport mScreenViewport;
    private LayerViewport  mLayerViewport;
    
    private GameObject     mQueensBackground; //TO INCLUDE IMAGE OF LANYON
    private PlayerCards    mPlayerCards; //PUT CARD IMAGES ON PLAYERCARDS CLASS
    private final int      NUM_CARD_SPACES = 12; //NEED TO DRAW GRID FOR CARDS
    
    private List<PlayerCards> mCards; //TO IMPLEMENT CARD CLASS

    //Game Objects
    private GameTurn currentGame;
    private Player player, playerAI;
    private boolean ignorePlayerInput = false;
    private boolean cardPlayedLimit   = false;

    public RenderGameScreen(Game game, Deck playerDeck) throws Exception {
        super("RenderGameScreen", game);

        playerAI = new PlayerAi("PlayerAiIcon",game,genAlgorithm.field.TOP);
        player   = new Player  ("PlayerIcon"  ,game.getAssetManager(), playerDeck, genAlgorithm.field.BOTTOM);

        setUpAssets(game.getAssetManager());

        playerAI.playerDeck.setDeckImg(mGame.getAssetManager().getBitmap("deckimg"));
        player.playerDeck  .setDeckImg(mGame.getAssetManager().getBitmap("deckimg"));

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


        mQueensBackground = new GameObject(LEVEL_WIDTH / 2.0f,
                LEVEL_HEIGHT / 2.0f, LEVEL_WIDTH, LEVEL_HEIGHT, getGame()
                .getAssetManager().getBitmap("QueensBackground"), this);

        mPlayerCards = new PlayerCards(100, 200, this);

        currentGame = new GameTurn(player.getId(),playerAI.getId());
    }

    private void setUpAssets(AssetStore assetManager) {
        assetManager.loadAndAddBitmap("deckimg", "img/PlayerIcons/deckimg.png");
        assetManager.loadAndAddBitmap("Hand", "img/PlayerIcons/HandCanvas.png");
        assetManager.loadAndAddBitmap("Row", "img/PlayerIcons/Row.PNG");
        assetManager.loadAndAddMusic("BGM","music/Keeper_of_Lust.m4a");
        assetManager.loadAndAddBitmap("Spot", "img/PlayerIcons/Spot.PNG");
        assetManager.loadAndAddBitmap("QueensBackground", "GameScreenImages/QueensBackground.JPG");
        assetManager.loadAndAddBitmap("HealthMonitor", "GameScreenImages/HealthMonitor.png");
        assetManager.loadAndAddBitmap("PlayerPictureHolder", "img/PlayerIcons/PlayerIcon.png");
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
        setIgnorePlayer();

        if (currentGame.getCurrentPhase() == GameTurn.turnTypes.startPhase) {
            startPlayerTurn(currentGame.getCurrentPlayerID());
            currentGame.getNextPhase();
        }
        else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.placeCard) {
            if(!ignorePlayerInput)
                placeCardPhase(elapsedTime, touchEvents);
             else
                 currentGame.getNextPhase();
                // AI turn
        }
        else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.attackPhase) {
            //  if (currentGame.isFirstTurn()
            //TODO
            currentGame.getNextPhase();
        }
        else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.endTurn) {
            cardPlayedLimit = false;
            player.playerHand.endTurn();
            currentGame.getNextPhase();
        }
        else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.endTurn) {
            cardPlayedLimit = false;
            endPlayerTurn(currentGame.getCurrentPlayerID());
            currentGame.getNextPhase();
        }
        else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.gameOver) {
        //TODO
        }
    }

    private void placeCardPhase(ElapsedTime elapsedTime, List<TouchEvent> touchEvents) {
        if(!cardPlayedLimit) {
            player.playerHand.update(elapsedTime, touchEvents);

            if (player.playerHand.cardPicked()) {
                player.playerField.update(elapsedTime, touchEvents, player.playerHand);

                if (player.playerHand.getCardPlayedThisTurn()) {
                    cardPlayedLimit = true;
                    Card newCard = player.playerHand.getLastCardPlayed();
                    useCardAbility(newCard);
                    currentGame.getNextPhase();
                }
            }
        }
    }

    private void useCardAbility(Card c) {
        if(c.ability.getHasAbility()){
            if(c.ability instanceof OwnerEffectedAbility){
                ((OwnerEffectedAbility) c.ability).addEffectPlayer(player);
            }
            c.ability.effect("level1");
        }
    }

    private void setIgnorePlayer() {
        if(currentGame.getCurrentPlayerID() == player.id)
            ignorePlayerInput = false;
        else
            ignorePlayerInput = true;
    }


    private void startPlayerTurn(String currentTurnId){
        if(currentTurnId == player.id)
            player.playerStartTurn();
        else
            playerAI.playerStartTurn();
    }
    private void endPlayerTurn(String currentTurnId){
        if(currentTurnId == player.id)
            player.playerHand.endTurn();
        else
            playerAI.playerHand.endTurn();
    }

    public void draw(ElapsedTime elapsedTime, IGraphics2D iGraphics2D) {

       iGraphics2D.clear(Color.BLACK);
       iGraphics2D.clipRect(mScreenViewport.toRect());
       getGame().getAssetManager().getMusic("BGM").play();
       getGame().getAssetManager().getMusic("BGM").setVolume(1);
       //Draw Background
       mQueensBackground.draw(elapsedTime, iGraphics2D, mLayerViewport, mScreenViewport);
       //Draw Player
       playerAI.drawPlayer(iGraphics2D,getGame().getAssetManager());
       player.drawPlayer(iGraphics2D,getGame().getAssetManager());

    }

}