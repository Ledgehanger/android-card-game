package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

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
    protected Bitmap playerIconBitmap,playerHPBarBitmap;
    protected boolean isAlive;
    protected int evTotal;
    protected Deck playerDeck;
    protected Hand playerHand;
    protected Rect playerRectIcon, playerRectHp;
    protected Paint playerPaint;
    protected float textSize = 25f;
    protected String hpBarFileName = "HPBar";

    public boolean DamageTaken(int Totaldamage) {
        hp -= Totaldamage;
        return isAlive = hp > 0;
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
        //pAssetManger.loadAndAddBitmap(hpBarFileName, "img/PlayerIcons/"+hpBarFileName+".png");
        pAssetManger.loadAndAddBitmap(hpBarFileName, "GameScreenImages/HealthMonitor.png");

        playerIconBitmap = pAssetManger.getBitmap(pImage);
        playerHPBarBitmap = pAssetManger.getBitmap(hpBarFileName);
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

    public Bitmap getPlayerIconBitmap() {
        return playerIconBitmap;
    }

    public void drawPlayer(genAlgorithm.field side, IGraphics2D iGraphics2D) {
        float top;
        float bot;
        float leftSide;

        int left;
        int right;
        int topI;
        int botI;

        if (playerRectIcon == null || playerRectHp == null) {
            createPlayerRect(side, iGraphics2D);

        }

        if(playerPaint == null)
            playerPaint = setUpPaint();




        iGraphics2D.drawBitmap(playerIconBitmap,null, playerRectIcon,null);
        iGraphics2D.drawBitmap(playerHPBarBitmap,null,playerRectHp,null);
        iGraphics2D.drawText(Integer.toString(hp),playerRectHp.centerX()-15,playerRectHp.centerY()+5,playerPaint);

    }

    public Paint setUpPaint(){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        float textRatio = (float) playerRectHp.width()/ playerRectHp.height();
        paint.setTextSize(textSize*textRatio);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        return paint;
    }
    protected void createPlayerRect(genAlgorithm.field side, IGraphics2D iGraphics2D) {
        float top , bot, leftSide;
        int left, right, topPlayerIcon, botPlayerIcon;

        if (side == genAlgorithm.field.TOP) {
            top = 0;
            bot = iGraphics2D.getSurfaceHeight();

            leftSide = iGraphics2D.getSurfaceWidth();
            left = (int) leftSide - 100;
            right = (int) leftSide;
            topPlayerIcon = (int) top;
            botPlayerIcon = (int) ((bot) - (bot / 1.5)) - 75;
            int topHp = (int)botPlayerIcon + 10;
            int botHp = topHp + 100;

            playerRectIcon = new Rect(left, topPlayerIcon, right, botPlayerIcon);
            playerRectHp   = new Rect(left,topHp,right,botHp);

        } else {
            top = iGraphics2D.getSurfaceHeight() / 2;
            bot = iGraphics2D.getSurfaceHeight();

            leftSide = iGraphics2D.getSurfaceWidth();
            left = (int) leftSide - 100;
            right = (int) leftSide;
            topPlayerIcon = (int) ((top) + (top / 4) + 105);
            botPlayerIcon = (int) bot;
            int topHp = (int)topPlayerIcon - 100;
            int botHp = topPlayerIcon - 10;


            playerRectIcon = new Rect(left, topPlayerIcon, right, botPlayerIcon);
            playerRectHp   = new Rect(left,topHp,right,botHp);

        }
    }


}


