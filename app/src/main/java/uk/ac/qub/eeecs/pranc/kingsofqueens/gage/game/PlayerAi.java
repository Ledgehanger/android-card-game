package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import java.util.Random;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
/**
 * Author Mark McAleese (40177285)
 */


public class PlayerAi extends Player {

    protected int playPos;
    protected int evPos;

    public int getPlayPos(){return playPos;}
    public int getEvPos(){return evPos;}


    //Carl
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

    //Author Mark McAleese
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
    //Carl
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
    //Carl
    public boolean checkFieldFree(Field playerAiField)
    {
        int loopCounter=0;

        boolean posAvailable=false;
        int sizeOfLoop=playerAiField.getSizeOfRow();
        Spot checkSpot;

        do{
            checkSpot=playerAiField.getSpotFromRow(0,loopCounter);
            if(!checkSpot.getCardPlaced())
                posAvailable=true;
            loopCounter++;

        }while(loopCounter<sizeOfLoop&&!posAvailable);

        return posAvailable;
    }
    //Carl
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
    //Carl
    public boolean checkHandFree()
    {
        if(playerHand.getMyHand().isEmpty()==true)
            return true;
        else
            return false;
    }
    //Carl
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
                   if (tempCardStorage.getEvCost() == 0)
                       canEvolve = false;
                   else if (currentEVPoints >= tempCardStorage.getEvCost()) {
                       canEvolve = true;
                       evPos=loopCounter;
                   }
               }
               loopCounter++;
           } while (loopCounter < playerAiField.getSizeOfRow() && canEvolve == false);
       }

       return canEvolve;
   }
    //Carl
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
    //Carl
   public void bestPlay(Field oppField)
   {
       Spot checkSpot;
       boolean playAvailable=false;
       int lowestWeight=Integer.MAX_VALUE;
       int checkWeight;
       Card checkCard;

       for(int i=0;i<oppField.getSizeOfRow();i++) {
           checkSpot=oppField.getSpotFromRow(0,i);
           if(checkSpot.getCardPlaced()){
               checkCard=checkSpot.getSpotCard();
               checkWeight=checkCard.getWeight();

               if(checkWeight<lowestWeight) {
                   if(checkSpotFree(i)) {
                       lowestWeight = checkWeight;
                       playAvailable=true;
                       playPos=i;
                   }

               }

           }
       }
       if(!playAvailable)
           findFirstAvailPos();
   }

}
