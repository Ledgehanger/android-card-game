package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.HashMap;
import java.util.Map;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.util.GraphicsHelper;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.LayerViewport;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.ScreenViewport;

/**
 * Created by markm on 22/11/2016.
 */

public class PickDeckScreen extends GameScreen {



    private AssetStore aStore;
    HashMap<String,DeckSelection> DeckHashMap = new HashMap<String,DeckSelection>();
    private Rect DeckButton,Left,Right;
    private int index = 0;
    public PickDeckScreen(Game newGame)
    {
        super("PickDeckScreen",newGame);
        newGame.getAssetManager().loadAndAddJson("Decks", "Decks/deckTypes.json");
        newGame.getAssetManager().loadAndAddBitmap("Left","img/LeftArrow.png");
        newGame.getAssetManager().loadAndAddBitmap("Right","img/RightArrow.png");
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
            Bitmap rightArrow = aStore.getBitmap("Right");
            Bitmap leftArrow = aStore.getBitmap("Left");

            if(DeckHashMap.isEmpty()) {
                for (DeckSelection deck : decks) {
                    if (!DeckHashMap.containsKey(deck.name)) {
                        aStore.loadAndAddBitmap(deck.imgPath,deck.imgPath);
                        deck.setBitImage(aStore.getBitmap(deck.imgPath));
                        //deck.setButton(new Rect(deckLeft,deckRight,deckTop,deckBot));
                        DeckHashMap.put(deck.name, deck);
                    }
                }

                int spacingX = mGame.getScreenWidth() / 6;
                int spacingY = mGame.getScreenHeight() / 3;

                DeckButton = new Rect(2 * spacingX, spacingY, 4 * spacingX, 2 * spacingY);
                Left = new Rect(spacingX, spacingY, 2 * spacingX, 2 * spacingY);
                Right = new Rect(4 * spacingX, spacingY, 5 * spacingX, 2 * spacingY);
            }

            iGraphics2D.clear(Color.rgb(255,255,255));


            if(index > decks.length) index = 0;
            if(index < 0) index = decks.length;

            iGraphics2D.drawBitmap(leftArrow,null,Left,null);
            iGraphics2D.drawBitmap(DeckHashMap.get(decks[index].name).getBitImage(),null,
                            DeckButton,null);
            iGraphics2D.drawBitmap(rightArrow,null,Right,null);
        }
        catch (Exception e)
        {
            String msg = e.getMessage();
        }


    }


}
