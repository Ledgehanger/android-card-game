package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.CanvasGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Deck;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Player;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.scaleScreenReso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by markm on 06/04/2017.
 */
@RunWith(AndroidJUnit4.class)
public class Player_Test {
    private final int LEVEL_WIDTH  = 1200;
    private final int LEVEL_HEIGHT = 720;

    private final String    playerImage = "pImage";
    private final int       FullHp = 20;

    Player                  playerWithAssetStore;
    Player                  defaultPlayer;
    AssetStore              assetStore;
    AssetManager            assetManager;
    CanvasGraphics2D        canvasGraphics2D;
    scaleScreenReso         scaler;

    @Before
    public void setUp() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        assetManager = appContext.getAssets();
        assetStore = new AssetStore(new FileIO(appContext));
        canvasGraphics2D = new CanvasGraphics2D(assetManager);
        Canvas n = new Canvas();
        canvasGraphics2D.setCanvas(n);
        assetStore.loadAndAddBitmap("PlayerPictureHolder", "img/PlayerIcons/PlayerIcon.png");
        assetStore.loadAndAddBitmap("Spot", "img/PlayerIcons/Spot.PNG");
        assetStore.loadAndAddBitmap("deckimg", "img/PlayerIcons/deckimg.png");
        assetStore.loadAndAddBitmap("Hand", "img/PlayerIcons/HandCanvas.png");
        assetStore.loadAndAddBitmap("Row", "img/PlayerIcons/Row.PNG");
        scaler = new scaleScreenReso(LEVEL_WIDTH,LEVEL_HEIGHT);

    }


    @Test
    public void playerSetUp(){
        setUpPlayers();
        checkStartStats(defaultPlayer);
        checkStartStats(playerWithAssetStore);
    }

    @Test
    public void playerTakingXDmgTest(){
        setUpPlayers();
        playerTakingDmg(playerWithAssetStore,10);
        playerTakingDmg(defaultPlayer,5);
    }

    @Test
    public void playerDies(){
        setUpPlayers();
        playerDead(defaultPlayer);
        playerDead(playerWithAssetStore);
    }

    @Test
    public void playerAddingOneEVWithEndTurn(){
        setUpPlayers();
        playerAddingEvEndTurn(defaultPlayer);
        playerAddingEvEndTurn(playerWithAssetStore);
    }

    @Test
    public void playerAddEv() {
        setUpPlayers();
        playerAddEv(defaultPlayer,11);
        playerAddEv(playerWithAssetStore,6);
    }


    private void setUpPlayers(){
        setUpAssetStore();
        Deck deck = setDeck();
        playerWithAssetStore = new Player(playerImage, assetStore, deck, genAlgorithm.field.BOTTOM);
        defaultPlayer = new Player();
    }
    private void playerAddingEvEndTurn(Player pPlayer){
        pPlayer.playerStartTurn();
        assertEquals(2, pPlayer.getEvTotal());
        pPlayer.playerStartTurn();
        assertEquals(3, pPlayer.getEvTotal());
    }
    private void playerDead(Player pPlayer){
        pPlayer.DamageTaken(1000);
        assertEquals(false,pPlayer.getIsAlive());
        assertEquals(false,pPlayer.DamageTaken(1));

    }
    private void playerTakingDmg(Player pPlayer, int pDmg){
        pPlayer. DamageTaken(pDmg);
        assertEquals((FullHp - pDmg),pPlayer.getHp());
        assertEquals(true, pPlayer.getIsAlive());
    }
    private void playerAddEv(Player pPlayer, int total) {
        pPlayer.addToEvTotal(total);
        assertEquals(total + 1, pPlayer.getEvTotal());
    }
    private void setUpAssetStore(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        assetStore = new AssetStore(new FileIO(appContext));
    }
    private void checkStartStats(Player pPlayer){
        assertEquals(20, pPlayer.getHp());
        assertEquals(1, pPlayer.getEvTotal());
        assertEquals(true, pPlayer.getIsAlive());
    }
    private Deck setDeck() {
        return new Deck(assetStore, "Psych", "Theology");
    }

    @Test
    public void testPlayers(){
        Player player = new Player("ai",null, genAlgorithm.field.BOTTOM);
        assertEquals("AI",player.getId());
        AssetStore as = getAssetStore();
        player.drawPlayer(null,as,100,100,scaler);
    }
    @Test
    public void playerAttackPhase(){
        Player player = new Player(playerImage, assetStore, genAlgorithm.field.BOTTOM);
        player.playerAttackPhase(playerWithAssetStore);

    }

    @Test
    public void testDrawPlayersWithNullCanvas(){
        Player player = new Player("ai",null, genAlgorithm.field.BOTTOM);
        Player player2 = new Player("ei",null, genAlgorithm.field.TOP);
        assertEquals("AI",player.getId());
        AssetStore as = getAssetStore();
        player.drawPlayer (null,as,100,100,scaler);
        player.drawPlayer (null,as,100,100,scaler);
        player2.drawPlayer(null,as,100,100,scaler);
        player2.drawPlayer(null,as,100,100,scaler);
    }
    @Test
    public void testDrawPlayersWithCanvas(){
        Deck player1d = new Deck();
        Deck player2d = new Deck();
        assetStore.loadAndAddJson("Psych", "Decks/Psych.json");
        assetStore.loadAndAddJson("Neutral","Decks/Neutral.json");

        player1d.setDeckUp(assetStore, "Psych", "Psych");
        player2d.setDeckUp(assetStore, "Psych", "Psych");

        Player player =     new Player("PlayerPictureHolder",assetStore, new Deck(), genAlgorithm.field.BOTTOM);
        Player player2 =    new Player("PlayerPictureHolder",assetStore, new Deck(), genAlgorithm.field.TOP   );
        assertEquals("Player",player.getId());

        player.drawPlayer (canvasGraphics2D,assetStore,100,100,scaler);
        player.drawPlayer (canvasGraphics2D,assetStore,100,100,scaler);
        player2.drawPlayer(canvasGraphics2D,assetStore,100,100,scaler);
        player2.drawPlayer(canvasGraphics2D,assetStore,100,100,scaler);
    }

    @Test
    public void testPaint(){
        Player player = new Player("ai",null, genAlgorithm.field.BOTTOM);

        AssetStore as = getAssetStore();
        player.drawPlayer(null,as,100,100,scaler);
        Paint e = player.setUpPaint();
        assertNotNull(e);
    }

    @NonNull
    private AssetStore getAssetStore() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        return new AssetStore(new FileIO(appContext));
    }


}
