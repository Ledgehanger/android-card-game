package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import java.util.Random;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;

/**
 * Created by markm on 04/03/2017.
 */

public class PlayerAi extends Player {




    public int spotPos;
    public int evLvl;


    public PlayerAi(String aiImage, AssetStore aStore,genAlgorithm.field fieldLocation)
    {
        this.fieldLocation=fieldLocation;
        this.playerImgFile=aiImage;
        this.hp=STARTING_HP;
        this.isAlive=true;
        this.evTotal=STARTING_EV;
        generateAIDeck(aStore);

        playerHand=new Hand(playerDeck.drawFromDeck(STARTING_HAND_SIZE));
        id="AI";
        playerField=new Field();

        handDrawCardBack=true;
    }

    //Move to AI Class
    public void generateAIDeck(AssetStore aStore){

        int firstChoice = -1;
        String [] pickedDecks = new String[2];
        String [] deckNames =
                {"Psych","Engineering", "Theology","Medical","CS", "Law"
                };
        Random rand = new Random();

        for(int i = 0; i < 2; i++){
            int randomNumber = firstChoice;
            while(randomNumber == firstChoice){
                randomNumber = rand.nextInt(deckNames.length);
            }
            firstChoice = randomNumber;
            pickedDecks[i] = deckNames[randomNumber];
        }

        playerDeck = new Deck(aStore,pickedDecks[0], pickedDecks[1]);

    }

    public boolean checkFieldFree()
    {
        boolean fieldFree=false;
        spotPos=0;

        while(spotPos<playerField.getSizeOfRow()||fieldFree==true)
        {
            if (playerField.getSpotFromRow(0, spotPos) == null)
                fieldFree = true;
            spotPos++;
        }

        return fieldFree;
    }

    public boolean checkHandFree()
    {
        if(playerHand.getMyHand().isEmpty()==true)
            return true;
        else
            return false;
    }

    public void checkAIEv()
    {
        if(evTotal==5) {
            evLvl = 2;
        }

        if(evTotal==3)
        {
            evLvl=1;
        }

        else
        {
            evLvl=0;
        }

    }

    public boolean checkCardEvolve(Card card)
    {
        if(card.getEv()==0)
            return false;
        else
            return true;
    }

}
