package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import java.util.Random;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;

/**
 * Created by markm on 04/03/2017.
 */

public class PlayerAi extends Player {

    public PlayerAi(String image, Game pGame)
    {
        super(image,pGame);
        generateAIDeck(pGame);
    }

    //Move to AI Class
    public void generateAIDeck(Game game){

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

        playerDeck = new Deck(game,pickedDecks[0], pickedDecks[1]);

    }

}
