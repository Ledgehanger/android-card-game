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
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;


public class RenderGameScreen extends GameScreen {

    private final float LEVEL_WIDTH  = 1000.0f;
    private final float LEVEL_HEIGHT = 1000.0f;



    //Game Objects
    private GameTurn currentGame;
    private Player player, playerAI;
    private boolean ignorePlayerInput = false;
    private boolean cardPlayedLimit   = false;

    private Bitmap endTurnActive;
    private Bitmap endTurnDisable;
    private Rect endTurnRect;
    private AssetStore assetStore;
    public static final String END_TURN_ACTIVE = "EndTurnActive";
    public static final String END_TURN_DISABLE = "EndTurnDisable";
    public RenderGameScreen(Game game, Deck playerDeck, AssetStore aStore) throws Exception {
        super("RenderGameScreen", game);

        playerAI = new PlayerAi("PlayerAiIcon",aStore,genAlgorithm.field.TOP);
        player   = new Player  ("PlayerIcon"  ,aStore, playerDeck, genAlgorithm.field.BOTTOM);
        assetStore = aStore;
        setUpAssets();

        playerAI.playerDeck.setDeckImg(aStore.getBitmap("deckimg"));
        player.playerDeck  .setDeckImg(aStore.getBitmap("deckimg"));



        currentGame = new GameTurn(player.getId(),playerAI.getId());
    }

    private void setUpAssets( ) {
        assetStore.loadAndAddBitmap("deckimg", "img/PlayerIcons/deckimg.png");
        assetStore.loadAndAddBitmap("Hand", "img/PlayerIcons/HandCanvas.png");
        assetStore.loadAndAddBitmap("Row", "img/PlayerIcons/Row.PNG");
        assetStore.loadAndAddMusic("BGM","music/Keeper_of_Lust.m4a");
        assetStore.loadAndAddBitmap("Spot", "img/PlayerIcons/Spot.PNG");
        assetStore.loadAndAddBitmap("QueensBackground", "GameScreenImages/QueensBackground.JPG");
        assetStore.loadAndAddBitmap("HealthMonitor", "GameScreenImages/HealthMonitor.png");
        assetStore.loadAndAddBitmap("PlayerPictureHolder", "img/PlayerIcons/PlayerIcon.png");
        assetStore.loadAndAddBitmap(END_TURN_ACTIVE, "img/EndTurnActive.png");
        assetStore.loadAndAddBitmap(END_TURN_DISABLE, "img/EndTurnDisable.png");
    }





    public void update(ElapsedTime elapsedTime) {
        Input input = mGame.getInput();
        if (currentGame.getCurrentPhase() == GameTurn.turnTypes.startPhase) {
            startPhase();
        } else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.placeCard) {
            if (input != null) {
                List<TouchEvent> touchEvents = input.getTouchEvents();
                placeCardPhase(elapsedTime, touchEvents);
            }
        } else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.attackPhase) {
            attackPhase();
        } else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.endTurn) {
            endTurnPhase();
        } else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.gameOver) {
            //TODO
        }

    }


    public void draw(ElapsedTime elapsedTime, IGraphics2D iGraphics2D) {

        iGraphics2D.clear(Color.BLACK);
        assetStore.getMusic("BGM").play();
        assetStore.getMusic("BGM").setVolume(1);
        //Draw Background
       // mQueensBackground.draw(elapsedTime, iGraphics2D, mLayerViewport, mScreenViewport);
        //Draw Player
        drawPlayer(iGraphics2D.getSurfaceWidth(),iGraphics2D.getSurfaceHeight(),iGraphics2D );

        drawEndTurn(elapsedTime,iGraphics2D);

    }

    public void drawPlayer( int surfaceWidth, int surfaceHeight,IGraphics2D iGraphics2D ) {
        playerAI.drawPlayer(iGraphics2D,assetStore, surfaceHeight, surfaceWidth);
        player.drawPlayer(iGraphics2D,assetStore, surfaceHeight, surfaceWidth);
    }


    public void drawEndTurn(ElapsedTime elapsedTime, IGraphics2D iGraphics2D){
        if(endTurnDisable == null){
            endTurnDisable  = assetStore.getBitmap(END_TURN_DISABLE);
        }
        if(endTurnActive == null) {
            endTurnActive  = assetStore.getBitmap(END_TURN_ACTIVE);
        }
        if(endTurnRect == null){
            int top, bot, left, right;
            int mid = iGraphics2D.getSurfaceHeight() / 2;
            bot = mid + 50;
            top = mid - 50;
            right = iGraphics2D.getSurfaceWidth();
            left = right - 140;
            endTurnRect = new Rect(left,top,right,bot);
        }
        if(currentGame.getCurrentPlayerID() == player.getId())
            iGraphics2D.drawBitmap(endTurnActive ,null,endTurnRect,null);
        else
            iGraphics2D.drawBitmap(endTurnDisable ,null,endTurnRect,null);
    }

    private void placeCardPhase(ElapsedTime elapsedTime, List<TouchEvent> touchEvents) {
        if (ignorePlayerInput == false) {
            if (!touchEvents.isEmpty()) {
                TouchEvent touchEvent = touchEvents.get(0);
                if (endTurnRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                    currentGame.getNextPhase();
                }
            }
            playerPlaceCardPhase(elapsedTime, touchEvents);
        } else {
            //TODO AI TURN
            currentGame.getNextPhase();
        }
    }

    private void startPhase() {
        startPlayerTurn(currentGame.getCurrentPlayerID());
        setIgnorePlayer();
        currentGame.getNextPhase();
    }

    private void attackPhase() {
        if (currentGame.isFirstTurn())
            currentGame.getNextPhase();
        else {
            if (ignorePlayerInput)
                playerAI.playerAttackPhase(player);
            else
                player.playerAttackPhase(playerAI);

            currentGame.getNextPhase();
        }
    }

    private void endTurnPhase() {
        cardPlayedLimit = false;
        if(!ignorePlayerInput)
            player.playerHand.endTurn();
        else
            playerAI.playerHand.endTurn();
        currentGame.getNextPhase();
    }

    public void playerPlaceCardPhase(ElapsedTime elapsedTime, List<TouchEvent> touchEvents) {

        if(!touchEvents.isEmpty()) {
            playerEvolving(touchEvents);
            playingCard(elapsedTime, touchEvents);
        }
    }

    private void playingCard(ElapsedTime elapsedTime, List<TouchEvent> touchEvents) {
        if (!cardPlayedLimit && !player.isEvolving()) {
            player.playerHand.update(elapsedTime, touchEvents);

            if (player.playerHand.cardPicked()) {
                player.playerField.update(elapsedTime, touchEvents, player.playerHand);
                if (player.playerHand.getCardPlayedThisTurn()) {
                    cardPlayedLimit = true;
                    Card newCard = player.playerHand.getLastCardPlayed();
                    useCardAbility(newCard);
                }
            }
        }
    }

    private void playerEvolving(List<TouchEvent> touchEvents) {
        TouchEvent touchEvent = touchEvents.get(0);
        Rect evRect = player.getPlayerRectEv();
        if(evRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0) {
            if(player.getEvTotal() > 0)
                player.setEvolving();
        }
        if(player.isEvolving())
            player.evolving(touchEvent,playerAI);


    }

    private void useCardAbility(Card c) {
        if(c.ability.getHasAbility()){
            if(c.ability instanceof OwnerEffectedAbility){
                ((OwnerEffectedAbility) c.ability).addEffectPlayer(player);
            }
            c.ability.effect(c.abilityLvl);
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

    public void nextCurrentTurn(){
        currentGame.getNextPhase();
    }




}