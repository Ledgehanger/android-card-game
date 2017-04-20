package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import java.util.Random;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;

/**
 * Created by markm on 04/03/2017.
 */

public class PlayerAi extends Player {

    public int playPos;
    public int evPos;
    public int evLvl;

    public int getPlayPos(){return playPos;}
    public int getEvPos(){return evPos;}


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

    public boolean checkSpotFree(int oppSpotPos)
    {
        boolean fieldFree=false;

        if (playerField.getSpotFromRow(0, oppSpotPos) != null)
        {
            fieldFree = true;
        }

        return fieldFree;
    }

    public boolean checkOpponentField(Field oppPlayerField)
    {
        int playPos=-1;

        boolean aiPosAvailable=false;
        int sizeOfLoop=oppPlayerField.getSizeOfRow()-1;

        do {
            playPos++;
            if(oppPlayerField.getSpotFromRow(0,playPos)!=null)
            {
               aiPosAvailable=checkSpotFree(playPos);
            }

        }while(playPos<=sizeOfLoop&&!aiPosAvailable);

        return aiPosAvailable;
    }

    public boolean checkHandFree()
    {
        if(playerHand.getMyHand().isEmpty()==true)
            return true;
        else
            return false;
    }

   public boolean checkEvolve(int currentEVPoints,Field playerAiField)
   {
       boolean canEvolve=false;
       evPos=-1;
       Card tempCardStorage;

       if(currentEVPoints>=3)
       {
           do {
               evPos++;
               Spot currentSpot = playerAiField.getSpotFromRow(0, evPos);

               if (currentSpot.getCardPlaced()) {
                   tempCardStorage = currentSpot.getSpotCard();
                   if (currentEVPoints >= tempCardStorage.getEv())
                       canEvolve = true;
                   if (tempCardStorage.getEv() == 0)
                       canEvolve = false;
               }
           } while (evPos < playerAiField.getSizeOfRow() || canEvolve == false);
       }

       return canEvolve;
   }

   public void playCard()
   {

   }
}
