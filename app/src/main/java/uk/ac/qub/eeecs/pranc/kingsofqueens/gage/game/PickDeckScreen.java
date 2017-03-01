package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.DeckSelection.DeckPickerRect;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.DeckSelection.DeckSelection;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;

/**
 * Created by markm on 22/11/2016.
 */

public class PickDeckScreen extends GameScreen {



    private AssetStore aStore;
    HashMap<String,DeckSelection> DeckHashMap = new HashMap<String,DeckSelection>();
    private Rect DeckButton,Left,Right,Play;
    boolean deck1Picked = false, deck2Picked = false;
    private int index = 0;
    private String currentDeck;
    DeckPickerRect Deck1 = new DeckPickerRect(), Deck2 = new DeckPickerRect();

    public PickDeckScreen(Game newGame)
    {
        super("PickDeckScreen",newGame);
        newGame.getAssetManager().loadAndAddJson("Decks", "Decks/deckTypes.json");
        newGame.getAssetManager().loadAndAddBitmap("Left","img/LeftArrow.png");
        newGame.getAssetManager().loadAndAddBitmap("Right","img/RightArrow.png");
        newGame.getAssetManager().loadAndAddBitmap("Play","img/MainMenuImages/playBtn.png");

        aStore = newGame.getAssetManager();
    }


    @Override
    public void update(ElapsedTime elapsedTime) {
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();

        if(!touchEvents.isEmpty()){
            TouchEvent touchEvent = touchEvents.get(0);

            if(DeckButton.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0){
                if(deck1Picked == false && !Deck2.deckName.equals(currentDeck)){
                    DeckSelection test = DeckHashMap.get(currentDeck);
                    Deck1 = new DeckPickerRect(new Rect(300,31,600,120), test.getBitImage(),currentDeck);
                    deck1Picked = true;
                }
                else if(deck2Picked == false && deck1Picked == true && !Deck1.deckName.equals(currentDeck)){
                    DeckSelection test = DeckHashMap.get(currentDeck);
                    Deck2 = new DeckPickerRect(new Rect(600,31,900,120), test.getBitImage(),currentDeck);
                    deck2Picked = true;
                }

            }

            deck1Picked = checkInputDeckChoices(touchEvent, Deck1, deck1Picked);
            deck2Picked = checkInputDeckChoices(touchEvent, Deck2, deck2Picked);

            if(Left.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0){
                    index--;
            }
            if(Right.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0){
                    index++;
            }


            if(deck1Picked && deck2Picked){
                if(Play.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type==0){
                    //replace with the game
                    mGame.getScreenManager().removeScreen(mGame.getScreenManager().getCurrentScreen().getName());
                    //pass down the decks they have picked
                    Deck playerDeck = new Deck();
                    playerDeck.loadDecksIntoAssestManger(mGame,Deck1.deckName, Deck2.deckName);
                    playerDeck.setDeckUp(mGame.getAssetManager(), Deck1.deckName, Deck2.deckName);

                    try {
                        RenderGameScreen gameScreen = new RenderGameScreen(mGame,playerDeck);
                        mGame.getScreenManager().addScreen(gameScreen);
                    }catch(Exception e)   {
                        Log.d(e.toString(), "update: ");
                    }
                    }
            }


        }

    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D iGraphics2D) {
        DeckSelection[] decks = aStore.jsonToDeckCollection("Decks");

        try
        {
            Bitmap rightArrow = aStore.getBitmap("Right");
            Bitmap leftArrow = aStore.getBitmap("Left");

            if(DeckHashMap.isEmpty()) {
                //Create bitmaps for every object in decks and add it to the hashmap
                for (DeckSelection deck : decks) {
                    if (!DeckHashMap.containsKey(deck.getName())) {
                        aStore.loadAndAddBitmap(deck.getImgPath(),deck.getImgPath());
                        deck.setBitImage(aStore.getBitmap(deck.getImgPath()));
                        DeckHashMap.put(deck.getName(), deck);
                    }
                }

                int spacingX = mGame.getScreenWidth() / 6;
                int spacingY = mGame.getScreenHeight() / 3;

                //Create the Rect buttons
                DeckButton = new Rect(2 * spacingX, spacingY, 4 * spacingX, 2 * spacingY);
                Left = new Rect(spacingX, spacingY, 2 * spacingX, 2 * spacingY);
                Right = new Rect(4 * spacingX, spacingY, 5 * spacingX, 2 * spacingY);
                Play = new Rect(350,500,850,650);
            }

            iGraphics2D.clear(Color.rgb(255,255,255));

            // Used to manage the going throw decks
            if(index >= decks.length) index = 0;
            if(index < 0) index = decks.length - 1;

            //store the current deck name for lookup in the hashmap
            currentDeck = decks[index].getName();

            //check if bitmap is null and if it is create that bitmap
            if(DeckHashMap.get(decks[index].getName()).getBitImage() ==  null)
                DeckHashMap.get(decks[index].getName()).setBitImage(aStore.getBitmap(decks[index].getImgPath()));

            //Draw the images
            iGraphics2D.drawBitmap(leftArrow,null,Left,null);
            iGraphics2D.drawBitmap(DeckHashMap.get(decks[index].getName()).getBitImage(),null,
                            DeckButton,null);
            iGraphics2D.drawBitmap(rightArrow,null,Right,null);
            //Check if we should draw Deck Choices and play button
            if(deck1Picked){
                iGraphics2D.drawBitmap(Deck1.Image,null, Deck1.button,null);
            }
            if(deck2Picked){
                iGraphics2D.drawBitmap(Deck2.Image,null, Deck2.button,null);
            }
            if(deck1Picked && deck2Picked){
                Bitmap playButton = aStore.getBitmap("Play");

                iGraphics2D.drawBitmap(playButton, null, Play, null);
            }

        }
        catch (Exception e)
        {
            String msg = e.getMessage();
            Log.d(msg, "draw: ");
        }
    }


    private boolean checkInputDeckChoices(TouchEvent touchEvent, DeckPickerRect deck, boolean valid) {
        if (valid == true) {
            if (deck.button.contains((int) touchEvent.x, (int) touchEvent.y)
                    && touchEvent.type == 0) {
                valid = false;
                deck.deckName = "";
            }
        }
        return valid;
    }

    }



