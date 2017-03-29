package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Rect;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;

/**
 * Created by Nicola on 22/11/2016.
 */

public class Player {

    protected int hp = 100;
    protected int ID;
    protected float x, y;
    protected Bitmap bitmapImage;
    protected boolean isAlive;
    protected int evTotal;
    protected Deck playerDeck;
    protected Hand playerHand;
    protected Rect playerIcon, playerHp;


    public boolean DamageTaken(int Totaldamage) {
        hp -= Totaldamage;
        return isAlive = hp > 0;
    }


    public Player(float x, int HP, float y, String image, boolean isAlive) {
        this.x = x;
        this.hp = HP;
        this.y = y;
        this.isAlive = isAlive;
        evTotal = 0;
    }
    public Player(String pImage, Game pGame, Deck playerDeck ) {

        this.hp = 20;
        this.isAlive = true;
        this.evTotal = 0;
        this.playerDeck = playerDeck;
        setUpBitmap(pImage,pGame.getAssetManager());
}
    public Player(String pImage, Game pGame){
        this.hp = 20;
        this.isAlive = true;
        this.evTotal = 0;
        setUpBitmap(pImage,pGame.getAssetManager());
    }

    protected void setUpBitmap(String pImage, AssetStore pAssetManger){
        pAssetManger.loadAndAddBitmap(pImage, "img/PlayerIcons/"+pImage+".png");
        bitmapImage = pAssetManger.getBitmap(pImage);
    }

    public static boolean createWinner(int HP1, int HP2) {
        if (HP1 > HP2) {
            return true;
        } else {
            return false;
        }
    }

    public int getEvTotal() {
        return evTotal;
    }

    public void setEvTotal(int evTotal) {
        this.evTotal = evTotal;
    }

    public void addToEvTotal(int add){
        this.evTotal += add;
    }

    public void healPlayer(int heal){
        for(int i = 0; i < heal; i++){
            if(hp < 20){
                hp++;
            }else
                break;
        }
    }

    public int getHp(){
        return this.hp;
    }

    public Bitmap getBitmapImage() {
        return bitmapImage;
    }

    public void drawPlayer(genAlgorithm.field side, IGraphics2D iGraphics2D) {
        float top;
        float bot;
        float leftSide;

        int left;
        int right;
        int topI;
        int botI;

        if (playerIcon == null) {
            if (side == genAlgorithm.field.TOP) {
                top = 0;
                bot = iGraphics2D.getSurfaceHeight();

                leftSide = iGraphics2D.getSurfaceWidth();
                left = (int) leftSide - 100;
                right = (int) leftSide;
                topI = (int) top;
                botI = (int) ((bot) - (bot / 1.5)) - 75;
                playerIcon = new Rect(left, topI, right, botI);

            } else {
                top = iGraphics2D.getSurfaceHeight() / 2;
                bot = iGraphics2D.getSurfaceHeight();

                leftSide = iGraphics2D.getSurfaceWidth();
                left = (int) leftSide - 100;
                right = (int) leftSide;
                topI = (int) ((top) + (top / 4) + 105);
                botI = (int) bot;
                playerIcon = new Rect(left, topI, right, botI);
            }
        }
        iGraphics2D.drawBitmap(getBitmapImage(),null,playerIcon,null);

    }


}


