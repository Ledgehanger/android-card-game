package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;



/**
 * Created by mark on 09/04/2017.
 */

public class GameTurn {

    public GameTurn(String playerOneID, String playerTwoID){
        this.playerOneID = playerOneID;
        this.playerOneTurn = true;
        this.playerTwoID = playerTwoID;
        this.playerTwoTurn = false;
        currentPhase = turnTypes.startPhase;
        firstTurn = true;
    }
    public enum turnTypes {
                startPhase,
                placeCard,
                attackPhase,
                endTurn,
                gameOver
    }

    private String playerOneID;
    private String playerTwoID;
    private String loseID;
    private boolean playerOneTurn;
    private boolean playerTwoTurn;
    private boolean firstTurn;

    private turnTypes currentPhase;


    public turnTypes getNextPhase(){

        switch(currentPhase){
            case startPhase:
                currentPhase = turnTypes.placeCard;
                break;
            case placeCard:
                currentPhase = turnTypes.attackPhase;
                break;
            case attackPhase:
                currentPhase = turnTypes.endTurn;
                break;
            case endTurn:
                firstTurn = false;
                endTurn();
                currentPhase = turnTypes.startPhase;
            default:
                currentPhase = turnTypes.startPhase;
                break;
        }
        return currentPhase;
    }

    private void endTurn(){
        playerOneTurn = !playerOneTurn;
        playerTwoTurn = !playerTwoTurn;
    }

    public turnTypes getCurrentPhase(){
        return currentPhase;
    }

    public String getCurrentPlayerID(){
        if(playerOneTurn)
            return playerOneID;
        else
            return playerTwoID;
    }

    public void setGameOver(String loserID){
        this.loseID = loserID;
        currentPhase = turnTypes.gameOver;
    }

    public boolean isFirstTurn() {
        return firstTurn;
    }

    public void setFirstTurn(boolean firstTurn) {
        this.firstTurn = firstTurn;
    }
}
