package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
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
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.scaleScreenReso;

/**
 * Created by markm on 22/11/2016.
 */

public class PickDeckScreen extends GameScreen {

    private AssetStore aStore;
    HashMap<String,DeckSelection> DeckHashMap = new HashMap<String,DeckSelection>();
    private Rect DeckButton,Left,Right,Play,bg;

    boolean deck1Picked = false, deck2Picked = false;
    private int index = 0;
    private String currentDeck;
    DeckPickerRect Deck1 = new DeckPickerRect(), Deck2 = new DeckPickerRect();
    scaleScreenReso scale;

    public PickDeckScreen(Game newGame, AssetStore assetStore)
    {
        super("PickDeckScreen",newGame);
        this.aStore = assetStore;
        aStore.loadAndAddJson("Decks", "Decks/deckTypes.json");
        aStore.loadAndAddBitmap("Left","img/LeftArrow.png");
        aStore.loadAndAddBitmap("Right","img/RightArrow.png");
        aStore.loadAndAddBitmap("Play","img/MainMenuImages/playBtn.png");
        aStore.loadAndAddBitmap("BG","img/mmbg.jpg");
        aStore.loadAndAddMusic("BGM","music/Layer Cake.m4a");


    }


    @Override
    public void update(ElapsedTime elapsedTime) {
        Input input = mGame.getInput();
        if(input != null) {
            List<TouchEvent> touchEvents = input.getTouchEvents();
            updateTouchEvents(touchEvents);
        }

    }

    public void updateTouchEvents(List<TouchEvent> touchEvents) {
        if (!touchEvents.isEmpty()) {
            TouchEvent touchEvent = touchEvents.get(0);
            if(genAlgorithm.hasTouchEvent(touchEvent,DeckButton)){
                mangeDeckSelection();
            }
            deck1Picked = checkInputDeckChoices(touchEvent, Deck1, deck1Picked);
            deck2Picked = checkInputDeckChoices(touchEvent, Deck2, deck2Picked);

            if (genAlgorithm.hasTouchEvent(touchEvent,Left)) {
                index--;
            }
            if (genAlgorithm.hasTouchEvent(touchEvent,Right)) {
                index++;
            }

            checkPlayButtonPressed(touchEvent);
        }
    }

    private void mangeDeckSelection() {
        if(deck1Picked == false && !Deck2.getDeckName().equals(currentDeck)){
            DeckSelection test = DeckHashMap.get(currentDeck);
            Deck1 = new DeckPickerRect(scale.scalarect(300,31,600,120), test.getBitImage(),currentDeck);
            deck1Picked = true;
        }
        else if(deck2Picked == false && deck1Picked == true && !Deck1.getDeckName().equals(currentDeck)){
            DeckSelection test = DeckHashMap.get(currentDeck);
            Deck2 = new DeckPickerRect(scale.scalarect(600,31,900,120), test.getBitImage(),currentDeck);
            deck2Picked = true;
        }
    }

    private void checkPlayButtonPressed(TouchEvent touchEvent) {
        try {
            if (deck1Picked && deck2Picked) {
                if (genAlgorithm.hasTouchEvent(touchEvent,Play)) {
                    Deck playerDeck = setupForRenderGame();
                    RenderGameScreen gameScreen = new RenderGameScreen(mGame, playerDeck,mGame.getAssetManager());
                    mGame.getScreenManager().addScreen(gameScreen);

                }
            }
        }
        catch(Exception e)   {
                Log.d(e.toString(), "update: ");
            }
    }

    @NonNull
    private Deck setupForRenderGame() {
        aStore.getMusic("BGM").stop();
        //replace with the game
        mGame.getScreenManager().removeScreen(mGame.getScreenManager().getCurrentScreen().getName());
        //pass down the decks they have picked
        return new Deck(mGame.getAssetManager(),Deck1.getDeckName(), Deck2.getDeckName());
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D iGraphics2D) {
        DeckSelection[] decks = aStore.jsonToDeckCollection("Decks");
         scale=new scaleScreenReso(iGraphics2D);

        try
        {
            Bitmap bgImg=aStore.getBitmap("BG");
            Bitmap rightArrow = aStore.getBitmap("Right");
            Bitmap leftArrow = aStore.getBitmap("Left");

            if(DeckHashMap.isEmpty()) {
                //Create bitmaps for every object in decks and add it to the hashmap
                for (DeckSelection deck : decks) {
                    if (!DeckHashMap.containsKey(deck.getName())) {
                        setupDeckTypeButtons(deck);
                    }
                }

                int spacingX = mGame.getScreenWidth() / 6;
                int spacingY = mGame.getScreenHeight() / 3;

                //Create the Rect buttons
                DeckButton = new Rect(2 * spacingX, spacingY, 4 * spacingX, 2 * spacingY);
                Left = new Rect(spacingX, spacingY, 2 * spacingX, 2 * spacingY);
                Right = new Rect(4 * spacingX, spacingY, 5 * spacingX, 2 * spacingY);
                Play = scale.scalarect(350,500,850,650);

            }
            if(iGraphics2D != null)
                iGraphics2D.clear(Color.rgb(255,255,255));

            aStore.getMusic("BGM").play();
            aStore.getMusic("BGM").setVolume(1);
            aStore.getMusic("BGM").setLopping(true);


            mangeIndexs(decks);

            //store the current deck name for lookup in the hashmap
            currentDeck = decks[index].getName();

            //check if bitmap is null and if it is create that bitmap
            if(DeckHashMap.get(decks[index].getName()).getBitImage() ==  null)
                DeckHashMap.get(decks[index].getName()).setBitImage(aStore.getBitmap(decks[index].getImgPath()));

            //Draw the images
            if(iGraphics2D != null) {
                draw(iGraphics2D, decks[index], bgImg, rightArrow, leftArrow);
            }
        }
        catch (Exception e)
        {
            String msg = e.getMessage();
            Log.d(msg, "draw: ");
        }
    }

    private void draw(IGraphics2D iGraphics2D, DeckSelection deck, Bitmap bgImg, Bitmap rightArrow, Bitmap leftArrow) {
        int bgRight = iGraphics2D.getSurfaceWidth();
        int bgBot = iGraphics2D.getSurfaceHeight();
        bg = new Rect(0, 0, bgRight, bgBot);
        iGraphics2D.drawBitmap(bgImg, null, bg, null);
        iGraphics2D.drawBitmap(leftArrow, null, Left, null);
        iGraphics2D.drawBitmap(DeckHashMap.get(deck.getName()).getBitImage(), null,
                DeckButton, null);
        iGraphics2D.drawBitmap(rightArrow, null, Right, null);
        //Check if we should draw Deck Choices and play button
        if (deck1Picked) {
            iGraphics2D.drawBitmap(Deck1.getImage(), null, Deck1.getButton(), null);
        }
        if (deck2Picked) {
            iGraphics2D.drawBitmap(Deck2.getImage(), null, Deck2.getButton(), null);
        }
        if (deck1Picked && deck2Picked) {
            Bitmap playButton = aStore.getBitmap("Play");

            iGraphics2D.drawBitmap(playButton, null, Play, null);
        }
    }

    private void mangeIndexs(DeckSelection[] decks) {
        if(index >= decks.length)
            index = 0;
        if(index < 0)
            index = decks.length - 1;
    }

    private void setupDeckTypeButtons(DeckSelection deck) {
        aStore.loadAndAddBitmap(deck.getImgPath(),deck.getImgPath());
        deck.setBitImage(aStore.getBitmap(deck.getImgPath()));
        DeckHashMap.put(deck.getName(), deck);
    }


    private boolean checkInputDeckChoices(TouchEvent touchEvent, DeckPickerRect deck, boolean valid) {
        if (valid == true) {
            if (genAlgorithm.hasTouchEvent(touchEvent,deck.getButton())){
                valid = false;
                deck.setDeckName("");
            }
        }
        return valid;
    }

    }



