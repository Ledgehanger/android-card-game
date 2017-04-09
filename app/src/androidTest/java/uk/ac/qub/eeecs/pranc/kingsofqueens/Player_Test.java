package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Deck;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Player;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;

import static org.junit.Assert.assertEquals;

/**
 * Created by markm on 06/04/2017.
 */

public class Player_Test {

    AssetStore assetStore;
    private final String playerImage = "pImage";
    private final int FullHp = 20;
    Player playerWithAssetStore;
    Player defaultPlayer;

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
        playerAddingEvwithEndTurn(defaultPlayer);
        playerAddingEvwithEndTurn(playerWithAssetStore);
    }

    @Test
    public void playerAddEv() {
        setUpPlayers();
        playerAddEv(defaultPlayer,10);
        playerAddEv(playerWithAssetStore,5);
    }


    private void setUpPlayers(){
        setUpAssetStore();
        Deck deck = setDeck();
        playerWithAssetStore = new Player(playerImage, assetStore, deck, genAlgorithm.field.BOTTOM);
        defaultPlayer = new Player();
    }
    private void playerAddingEvwithEndTurn(Player pPlayer){
        pPlayer.playerEndTurn();
        assertEquals(1, pPlayer.getEvTotal());
        pPlayer.playerEndTurn();
        assertEquals(2, pPlayer.getEvTotal());
    }
    private void playerDead(Player pPlayer){
        pPlayer.DamageTaken(1000);
        assertEquals(false,pPlayer.getIsAlive());

    }
    private void playerTakingDmg(Player pPlayer, int pDmg){
        pPlayer. DamageTaken(pDmg);
        assertEquals((FullHp - pDmg),pPlayer.getHp());
        assertEquals(true, pPlayer.getIsAlive());
    }
    private void playerAddEv(Player pPlayer, int total) {
        pPlayer.addToEvTotal(total);
        assertEquals(total, pPlayer.getEvTotal());
    }
    private void setUpAssetStore(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        assetStore = new AssetStore(new FileIO(appContext));
    }
    private void checkStartStats(Player pPlayer){
        assertEquals(20, pPlayer.getHp());
        assertEquals(0, pPlayer.getEvTotal());
        assertEquals(true, pPlayer.getIsAlive());
    }
    private Deck setDeck(){
        return new Deck(assetStore, "Psych", "Theology");
    }
}
