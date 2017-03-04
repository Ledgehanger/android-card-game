package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Rect;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;

/**
 * Created by Nicola on 22/11/2016.
 */

public class Player {

    int hp = 100;
    int ID;
    float x, y;
    Bitmap bitmapImage;
    boolean isAlive;


    int evTotal;
    Deck playerDeck;
    Hand playerHand;
    Rect playerIcon, playerHp;

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

    private void setUpBitmap(String pImage, AssetStore pAssetManger){
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



}


