package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.GameObjects;
/**
 * Author Mark McAleese (40177285) all methods
 */

public class GameTurn {


    public enum turnTypes {
        START_PHASE,
        PLAY_CARD,
        ATTACK_PHASE,
        END_TURN,
        GAME_OVER
    }

    private String playerOneID;
    private String playerTwoID;
    private String loseID;
    private boolean playerOneTurn;
    private boolean playerTwoTurn;
    private boolean firstTurn;

    private turnTypes currentPhase;

    public GameTurn(String playerOneID, String playerTwoID){
        this.playerOneID = playerOneID;
        this.playerOneTurn = true;
        this.playerTwoID = playerTwoID;
        this.playerTwoTurn = false;
        currentPhase = turnTypes.START_PHASE;
        firstTurn = true;
    }

    public turnTypes getNextPhase(){

        switch(currentPhase){
            case START_PHASE:
                currentPhase = turnTypes.PLAY_CARD;
                break;
            case PLAY_CARD:
                currentPhase = turnTypes.ATTACK_PHASE;
                break;
            case ATTACK_PHASE:
                currentPhase = turnTypes.END_TURN;
                break;
            case END_TURN:
                firstTurn = false;
                endTurn();
                currentPhase = turnTypes.START_PHASE;
                break;
            default:
                currentPhase = turnTypes.START_PHASE;
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
        currentPhase = turnTypes.GAME_OVER;
    }

    public boolean isFirstTurn() {
        return firstTurn;
    }

    public void setFirstTurn(boolean firstTurn) {
        this.firstTurn = firstTurn;
    }

    public String getLoseID() {
        return loseID;
    }
}
