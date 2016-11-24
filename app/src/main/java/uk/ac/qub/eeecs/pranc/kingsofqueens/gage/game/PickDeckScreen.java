package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
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
    private Rect DeckButton,Left,Right,choice1,choice2;
    boolean choice1Check = false, choice2Check = false;
    private int index = 0;
    private String currentDeck;


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
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();

        if(!touchEvents.isEmpty()){
            TouchEvent touchEvent = touchEvents.get(0);

            if(DeckButton.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0){
                if(choice1Check == false){
                    DeckSelection test = DeckHashMap.get(currentDeck);


                }
                if(choice2Check == false && choice1Check == true){

                }
            }

            if(Left.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0){
                    index--;
            }

            if(Right.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0){
                    index++;
            }
        }

    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D iGraphics2D) {
        DeckSelection [] decks = aStore.jsonToDeckCollection("Decks");

        try
        {
            Bitmap rightArrow = aStore.getBitmap("Right");
            Bitmap leftArrow = aStore.getBitmap("Left");

            if(DeckHashMap.isEmpty()) {
                //Create bitmaps for every object in decks and add it to the hashmap
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

                //Create the Rect buttons
                DeckButton = new Rect(2 * spacingX, spacingY, 4 * spacingX, 2 * spacingY);
                Left = new Rect(spacingX, spacingY, 2 * spacingX, 2 * spacingY);
                Right = new Rect(4 * spacingX, spacingY, 5 * spacingX, 2 * spacingY);
            }

            iGraphics2D.clear(Color.rgb(255,255,255));

            // Used to manage the going throw decks
            if(index >= decks.length) index = 0;
            if(index < 0) index = decks.length - 1;
            //store the current deck name for lookup in the hashmap
            currentDeck = decks[index].name;

            //check if bitmap is null and if it is create that bitmap
            if(DeckHashMap.get(decks[index].name).getBitImage() ==  null)
                DeckHashMap.get(decks[index].name).setBitImage(aStore.getBitmap(decks[index].imgPath));

            //Draw the images
            iGraphics2D.drawBitmap(leftArrow,null,Left,null);
            iGraphics2D.drawBitmap(DeckHashMap.get(decks[index].name).getBitImage(),null,
                            DeckButton,null);
            iGraphics2D.drawBitmap(rightArrow,null,Right,null);
        }
        catch (Exception e)
        {
            String msg = e.getMessage();
            Log.d(msg, "draw: ");
        }


    }


}
