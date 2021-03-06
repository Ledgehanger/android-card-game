package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;
/**
 * Created by markm on 25/11/2016.
 */
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.scaleScreenReso;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;

import static android.content.ContentValues.TAG;

public class RenderGameScreen extends GameScreen {

    private final int LEVEL_WIDTH = 1184;
    private final int LEVEL_HEIGHT = 720;


    //Game Objects
    private GameTurn currentGame;
    private Player player;
    private PlayerAi playerAI;
    private boolean ignorePlayerInput = false;
    private boolean cardPlayedLimit = false;

    private Bitmap endTurnActive;
    private Bitmap endTurnDisable;
    private Rect endTurnRect;
    private Rect boundBackground;
    private AssetStore assetStore;
    public static final String END_TURN_ACTIVE = "EndTurnActive";
    public static final String END_TURN_DISABLE = "EndTurnDisable";

    scaleScreenReso scalar;

    public RenderGameScreen(Game game, Deck playerDeck, AssetStore aStore) throws Exception {
        super("RenderGameScreen", game);

        playerAI = new PlayerAi("PlayerAiIcon", aStore, genAlgorithm.field.TOP);
        player = new Player("PlayerIcon", aStore, playerDeck, genAlgorithm.field.BOTTOM);
        assetStore = aStore;
        setUpAssets();

        playerAI.playerDeck.setDeckImg(aStore.getBitmap("deckimg"));
        player.playerDeck.setDeckImg(aStore.getBitmap("deckimg"));


        currentGame = new GameTurn(player.getId(), playerAI.getId());
    }

    //Author Mark McAleese (40177285)
    private void setUpAssets() {
        assetStore.loadAndAddBitmap("deckimg", "img/PlayerIcons/deckimg.png");
        assetStore.loadAndAddBitmap("Hand", "img/PlayerIcons/HandCanvas.png");
        assetStore.loadAndAddBitmap("Row", "img/PlayerIcons/Row.PNG");
        assetStore.loadAndAddMusic("BGM", "music/Keeper_of_Lust.m4a");
        assetStore.loadAndAddBitmap("Spot", "img/PlayerIcons/extra.png");
        assetStore.loadAndAddBitmap("QueensBackground", "img/EndImages/mmbg.jpg");
        assetStore.loadAndAddBitmap("HealthMonitor", "GameScreenImages/HealthMonitor.png");
        assetStore.loadAndAddBitmap("PlayerPictureHolder", "img/PlayerIcons/PlayerIcon.png");
        assetStore.loadAndAddBitmap(END_TURN_ACTIVE, "img/EndTurnActive.png");
        assetStore.loadAndAddBitmap(END_TURN_DISABLE, "img/EndTurnDisable.png");


    }

    //Author Mark McAleese (40177285)
    public void update(ElapsedTime elapsedTime) {
        try {
            Input input = mGame.getInput();
            checkGameOver();
            if (currentGame.getCurrentPhase() == GameTurn.turnTypes.START_PHASE) {
                startPhase();
            } else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.PLAY_CARD) {
                if (input != null) {
                    List<TouchEvent> touchEvents = input.getTouchEvents();
                    placeCardPhase(elapsedTime, touchEvents);
                }
            } else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.ATTACK_PHASE) {
                if (!currentGame.isFirstTurn())
                    attackPhase();
                else
                    currentGame.getNextPhase();
            } else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.END_TURN) {
                if (currentGame.isFirstTurn())
                    currentGame.setFirstTurn(false);
                endTurnPhase();
            } else if (currentGame.getCurrentPhase() == GameTurn.turnTypes.GAME_OVER) {
                ignorePlayerInput = true;
                proceedEnd(elapsedTime);
            }
        }catch (Exception e){
            Log.e(TAG, "RenderGameScreen update: " + e.toString());
        }
    }

    //Author Mark McAleese (40177285)
    public void draw(ElapsedTime elapsedTime, IGraphics2D iGraphics2D) {
        try {
            scalar = new scaleScreenReso(iGraphics2D);
            iGraphics2D.clear(Color.BLACK);
            assetStore.getMusic("BGM").play();
            assetStore.getMusic("BGM").setVolume(1);
            assetStore.getMusic("BGM").setLopping(true);
            //Draw Background
            Bitmap bg = assetStore.getBitmap("QueensBackground");
            setupBackground(iGraphics2D);
            iGraphics2D.drawBitmap(bg, null, boundBackground, null);
            //Draw Player
            drawPlayer(LEVEL_WIDTH, LEVEL_HEIGHT, iGraphics2D);
            drawEndTurn(elapsedTime, iGraphics2D);
        }catch (Exception e){
            Log.e(TAG, "RenderGameScreen update: " + e.toString());
        }

    }

    public void setupBackground(IGraphics2D iGraphics2D) {
        int bgLeft = 0;
        int bgRight = LEVEL_WIDTH;
        int bgTop = 0;
        int bgBot = LEVEL_HEIGHT;
        boundBackground = scalar.scalarect(bgLeft, bgTop, bgRight, bgBot);
    }
    //Author Mark McAleese (40177285)
    public void drawPlayer(int surfaceWidth, int surfaceHeight, IGraphics2D iGraphics2D) {
        playerAI.drawPlayer(iGraphics2D, assetStore, surfaceHeight, surfaceWidth, scalar);
        player.drawPlayer(iGraphics2D, assetStore, surfaceHeight, surfaceWidth, scalar);
    }

    //Author Mark McAleese (40177285)
    public void drawEndTurn(ElapsedTime elapsedTime, IGraphics2D iGraphics2D) {
        if (endTurnDisable == null) {
            endTurnDisable = assetStore.getBitmap(END_TURN_DISABLE);
        }
        if (endTurnActive == null) {
            endTurnActive = assetStore.getBitmap(END_TURN_ACTIVE);
        }
        if (endTurnRect == null) {
            int top, bot, left, right;
            int mid = iGraphics2D.getSurfaceHeight() / 2;
            bot = mid + 50;
            top = mid - 50;
            right = iGraphics2D.getSurfaceWidth();
            left = right - 140;
            endTurnRect = new Rect(left, top, right, bot);
        }
        if (currentGame.getCurrentPlayerID() == player.getId())
            iGraphics2D.drawBitmap(endTurnActive, null, endTurnRect, null);
        else
            iGraphics2D.drawBitmap(endTurnDisable, null, endTurnRect, null);
    }
    //Author Mark McAleese (40177285)  & Carl AI part (else)
    private void placeCardPhase(ElapsedTime elapsedTime, List<TouchEvent> touchEvents) {
        if (ignorePlayerInput == false) {
            if (!touchEvents.isEmpty()) {
                TouchEvent touchEvent = touchEvents.get(0);
                if (genAlgorithm.hasTouchEventType0(touchEvent, endTurnRect)) {
                    currentGame.getNextPhase();
                }
            }
            playerPlaceCardPhase(elapsedTime, touchEvents);
        } else {
            //Carl
            boolean checkPlay=playerAI.checkFieldFree(playerAI.playerField);
            boolean checkEv=playerAI.checkEvolve(playerAI.playerField);
            int playPos;
            int evPos;

            if(checkPlay)
            {
                playerAI.bestPlay(player.playerField);
                playPos=playerAI.getPlayPos();
                playerAiPlayCard(playPos);
            }

            if(checkEv)
            {
                evPos=playerAI.getEvPos();
                playerAIEvolve(evPos);

            }
                currentGame.getNextPhase();

        }
    }


    //Author Mark McAleese (40177285)
    private void startPhase() {
        startPlayerTurn(currentGame.getCurrentPlayerID());
        setIgnorePlayer();
        currentGame.getNextPhase();
    }
    //Author Mark McAleese (40177285)
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
    //Author Mark McAleese (40177285)
    private void endTurnPhase() {
        cardPlayedLimit = false;
        if (!ignorePlayerInput)
            player.playerHand.endTurn();
        else
            playerAI.playerHand.endTurn();
        currentGame.getNextPhase();
    }
    //Author Mark McAleese (40177285)
    public void playerPlaceCardPhase(ElapsedTime elapsedTime, List<TouchEvent> touchEvents) {
        if (!touchEvents.isEmpty()) {
            playerEvolving(touchEvents);
            playingCard(elapsedTime, touchEvents);
        }
    }
    //Author Mark McAleese (40177285)
    private void playingCard(ElapsedTime elapsedTime, List<TouchEvent> touchEvents) {
        if (!cardPlayedLimit && !player.isEvolving() && checkHp()) {
            player.playerHand.update(elapsedTime, touchEvents);

            if (player.playerHand.cardPicked()) {
                player.playerField.update(elapsedTime, touchEvents, player.playerHand);
                if (player.playerHand.getCardPlayedThisTurn()) {
                    cardPlayedLimit = true;
                    Card newCard = player.playerHand.getLastCardPlayed();
                    checkGameOver();
                    genAlgorithm.useCardAbility(newCard, player, playerAI);
                }
            }
        }
    }
    //Carl
    private void playerAiPlayCard(int pos)
    {
        Spot placeCard = playerAI.playerField.getSpotFromRow(0,pos);
        int posPlay;
        posPlay=playerAI.pickCardToPlay(playerAI.playerHand);
        placeCard.setSpotCard(playerAI.playerHand.getCardFromHand(posPlay));
        playerAI.playerHand.setIndexOfPickedCard(posPlay);
        playerAI.playerHand.getPickedCardFromHand();

        Card newCard=playerAI.playerHand.getLastCardPlayed();
        checkGameOver();
        genAlgorithm.useCardAbility(newCard,playerAI,player);
    }
    //Carl
    private void playerAIEvolve(int evPos)
    {
        Spot evSpot=playerAI.playerField.getSpotFromRow(0,evPos);
        playerAI.evTotal-=evSpot.getEvolvingCost();
        evSpot.cardEvolving();
        evSpot.useCardAbility(playerAI,player);

    }

    private void playerEvolving(List<TouchEvent> touchEvents) {
        TouchEvent touchEvent = touchEvents.get(0);
        Rect evRect = player.getPlayerRectEv();
        if (genAlgorithm.hasTouchEventType0(touchEvent, evRect)) {
            if (player.getEvTotal() > 0)
                player.setEvolving();
        }
        if (player.isEvolving())
            player.evolving(touchEvent, playerAI);


    }

    //Author Mark McAleese (40177285)
    private void setIgnorePlayer() {
        if (currentGame.getCurrentPlayerID() == player.id)
            ignorePlayerInput = false;
        else
            ignorePlayerInput = true;
    }
    //Author Mark McAleese (40177285)
    private void startPlayerTurn(String currentTurnId) {
        if (currentTurnId == player.id)
            player.playerStartTurn();
        else
            playerAI.playerStartTurn();
    }
    //Author Mark McAleese (40177285)
    public void nextCurrentTurn() {
        currentGame.getNextPhase();
    }

    public void checkGameOver() {
        if (player.getHp() <= 0)
            currentGame.setGameOver(player.getId());
        if (playerAI.getHp() <= 0)
            currentGame.setGameOver(playerAI.getId());
    }

    public boolean checkHp() {
        return player.getHp() > 0 && playerAI.getHp() > 0;
    }


    @NonNull
    public void proceedEnd(ElapsedTime elapsedTime) {
        assetStore.getMusic("BGM").stop();
                //boolean won = currentGame.getLoseID() != player.getId();
                boolean winner = currentGame.getCurrentPlayerID() == player.getId();
                mGame.getScreenManager().removeScreen(this.getName());
                GameOverState over = new GameOverState("", mGame,winner);
                mGame.getScreenManager().addScreen(over);
/*            }
        }*/
    }
}