package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

public class RenderGameScreen extends GameScreen {

      private Player player;
    private ArrayList<Card> cards;

    private Typeface scoreFont;
    private int playerScore = 0;

    private static final int CARD_HEIGHT = 50;
    private static final int CARD_WIDTH = 20;
    private int cardSpeed = -200;

    private static final int PLAYER_WIDTH = 66;
    private static final int PLAYER_HEIGHT = 92;

    public void update(){ //elapsedTime??
        if(!player.isAlive()){
            setCurrentState(new MainMenu(playerScore / 100));
        }
        playerScore += 1;
        //update image methods and pass elapsedTime??
    }
    
    private void renderScore(GraphicsHelper g){
        
        
    }