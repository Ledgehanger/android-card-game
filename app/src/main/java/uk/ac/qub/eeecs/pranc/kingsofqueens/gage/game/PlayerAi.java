package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import java.util.Random;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;

/**
 * Created by markm on 04/03/2017.
 */

//TODO: Debug and Fix Evolve

public class PlayerAi extends Player {

    public int playPos;
    public int evPos;

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

    public boolean checkSpotFree(int spotPos)
    {
        boolean posFree=false;

        Spot checkSpot=playerField.getSpotFromRow(0,spotPos);

        if (!checkSpot.getCardPlaced())
        {
            posFree = true;
            playPos=spotPos;
        }

        return posFree;
    }

    public boolean checkOpponentField(Field oppPlayerField)
    {
        int loopCounter=0;

        boolean aiPosAvailable=false;
        int sizeOfLoop=oppPlayerField.getSizeOfRow();

        do {
            Spot checkSpot=oppPlayerField.getSpotFromRow(0,loopCounter);

            if(checkSpot.getCardPlaced())
            {
               aiPosAvailable=checkSpotFree(loopCounter);
            }

            loopCounter++;

        }while(loopCounter<sizeOfLoop&&!aiPosAvailable);

        if (!aiPosAvailable)
            aiPosAvailable=findFirstAvailPos();

        return aiPosAvailable;
    }

    public boolean findFirstAvailPos()
    {
        int loopCounter=0;
        boolean posFree=false;

        do
        {
            posFree=checkSpotFree(loopCounter);
            loopCounter++;

        }while(loopCounter<playerField.getSizeOfRow()&&!posFree);

        return posFree;
    }

    public boolean checkHandFree()
    {
        if(playerHand.getMyHand().isEmpty()==true)
            return true;
        else
            return false;
    }

   public boolean checkEvolve(Field playerAiField)
   {
       boolean canEvolve=false;
       int loopCounter=0;
       Card tempCardStorage;

       int currentEVPoints=getEvTotal();

       if(currentEVPoints>=3)
       {
           do {
               Spot currentSpot = playerAiField.getSpotFromRow(0, loopCounter);

               if (currentSpot.getCardPlaced()) {
                   tempCardStorage = currentSpot.getSpotCard();
                   if (tempCardStorage.getEv() == 0)
                       canEvolve = false;
                   else if (currentEVPoints >= tempCardStorage.getEv()) {
                       canEvolve = true;
                       evPos=loopCounter;
                   }
               }
               loopCounter++;
           } while (loopCounter < playerAiField.getSizeOfRow() && canEvolve == false);
       }

       return canEvolve;
   }

   public int pickCardToPlay(Hand playerHand)
   {
       int posInHand=0;
       int currentWeight;
       int largeWeight=-1;
       Card checkCard;

       for(int i=0;i<playerHand.getHandSize();i++)
       {
           checkCard=playerHand.getCardFromHand(i);
           currentWeight=checkCard.getWeight();

           if(currentWeight>largeWeight)
           {
               largeWeight = currentWeight;
               posInHand=i;
           }
       }
       return posInHand;
   }


}
