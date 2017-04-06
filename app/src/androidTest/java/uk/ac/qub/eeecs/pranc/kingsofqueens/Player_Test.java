package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Deck;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Player;

import static org.junit.Assert.assertEquals;

/**
 * Created by markm on 06/04/2017.
 */

public class Player_Test {

    AssetStore assetStore;
    private final String playerImage = "pImage";
    Player player;

    @Test
    public void playerSetUpDefault() {
        Player player = new Player();
        checkStartStats();
    }

    @Test
    public void playerSetUpWithStore(){
        setUpAssetStore();
        Deck deck = setDeck();
        Player player = new Player(playerImage, assetStore, deck);
        checkStartStats();
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

    private void checkStartStats(){
        assertEquals(20, player.getHp());
        assertEquals(0, player.getEvTotal());
        assertEquals(true, player.getIsAlive());
    }
    private Deck setDeck(){
        return new Deck(assetStore, "Psych", "Theology");
    }
}
