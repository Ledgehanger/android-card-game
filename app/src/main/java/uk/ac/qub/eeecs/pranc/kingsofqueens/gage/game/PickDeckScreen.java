package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.HashMap;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;

/**
 * Created by markm on 22/11/2016.
 */

public class PickDeckScreen extends GameScreen {

    private AssetStore aStore;
    HashMap<String,DeckSelection> DeckHashMap = new HashMap<String,DeckSelection>();
    private Rect CS,Engineering,Law,Medical,Neutral,Psych,Theology;

    public PickDeckScreen(Game newGame)
    {
        super("PickDeckScreen",newGame);
        newGame.getAssetManager().loadAndAddJson("Decks", "Decks/deckTypes.json");
        aStore = newGame.getAssetManager();

    }
    @Override
    public void update(ElapsedTime elapsedTime) {

    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D iGraphics2D) {
        DeckSelection [] decks = aStore.jsonToDeckCollection("Decks");

        try
        {
            Bitmap koqTitle = aStore.getBitmap("Title");
            Bitmap playGame = aStore.getBitmap("playBtn");
            Bitmap options  = aStore.getBitmap("optionsBtn");

            if(DeckHashMap.isEmpty()) {
                for (DeckSelection deck : decks) {
                    if (!DeckHashMap.containsKey(deck.name)) {
                        aStore.loadAndAddBitmap(deck.imgPath,deck.imgPath);
                        deck.setBitImage(aStore.getBitmap(deck.imgPath));
                        int deckLeft = 0;
                        int deckRight = deckLeft + deck.getBitImage().getWidth();
                        int deckTop = (iGraphics2D.getSurfaceHeight()/2);
                        int deckBot = deckTop + deck.getBitImage().getHeight();
                        deck.setButton(new Rect(deckLeft,deckRight,deckTop,deckBot));
                        DeckHashMap.put(deck.name, deck);
                    }
                }
            }

            iGraphics2D.clear(Color.rgb(255,255,255));
            iGraphics2D.drawBitmap(DeckHashMap.get("CS").getBitImage(),null,DeckHashMap.get("CS").getButton(),null);
            /*int index = 0;
            for (DeckSelection deck : decks ) {
               iGraphics2D.drawBitmap(deck.getBitImage(),null,deck.getButton(),null);
                index++;
            }*/

        }
        catch (Exception e)
        {
            String msg = e.getMessage();
        }


    }


}
