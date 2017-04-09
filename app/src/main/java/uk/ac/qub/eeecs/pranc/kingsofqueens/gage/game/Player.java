package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;

/**
 * Created by Nicola on 22/11/2016.
 */

public class Player {

    protected int hp;
    protected int ID;
    protected float x, y;
    protected Bitmap playerIconBitmap,playerHPBarBitmap;
    protected boolean isAlive;
    protected int evTotal;
    protected String playerImgFile;

    protected Deck playerDeck;
    protected Hand playerHand;
    protected Rect playerRectIcon, playerRectHp;
    protected Paint playerPaint;
    protected float textSize = 25f;
    protected String hpBarFileName = "HPBar";

    protected final int STARTING_EV = 1;
    protected final int STARTING_HP = 20;
    protected final int CARDS_PER_TURN = 1;
    protected genAlgorithm.field fieldLocation;
    protected boolean handDrawCardBack = false;

    public boolean DamageTaken(int totalDamage) {
        hp -= totalDamage;
        isAlive = hp > 0;
        return isAlive;
    }
    
    public Player(){
        this.hp = STARTING_HP;
        this.evTotal = STARTING_EV;
        this.isAlive = true;
    }
    
    public Player(String pImage, AssetStore assetStore, Deck playerDeck,genAlgorithm.field fieldLocation ) {
        this.fieldLocation = fieldLocation;
        this.playerImgFile = pImage;
        this.hp = STARTING_HP;
        this.isAlive = true;
        this.evTotal = STARTING_EV;
        this.playerDeck = playerDeck;
        if (assetStore != null)
            setUpBitmap( assetStore);
        playerHand = new Hand(playerDeck.drawFromDeck(playerHand.STARTING_HAND_SIZE),assetStore);

    }

    /// Used in PlayerAi
    public Player(String pImage, AssetStore assetStore,genAlgorithm.field fieldLocation){
        this.fieldLocation = fieldLocation;
        this.playerImgFile = pImage;
        this.hp = STARTING_HP;
        this.isAlive = true;
        this.evTotal = STARTING_EV;
        if(assetStore != null)
            setUpBitmap(assetStore);
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

    public void drawPlayer(IGraphics2D iGraphics2D, AssetStore assetStore) {

        if (playerRectIcon == null || playerRectHp == null) {
            createPlayerRect(iGraphics2D, assetStore);
        }
        if(playerPaint == null)
            playerPaint = setUpPaint();

        iGraphics2D.drawBitmap(playerIconBitmap,null, playerRectIcon,null);
        iGraphics2D.drawBitmap(playerHPBarBitmap,null,playerRectHp,null);
        iGraphics2D.drawText(Integer.toString(hp),playerRectHp.centerX()-15,playerRectHp.centerY()+5,playerPaint);

        playerDeck.drawDeck(fieldLocation,iGraphics2D);
        playerHand.drawHand(fieldLocation,iGraphics2D,assetStore,handDrawCardBack);


    }

    public Paint setUpPaint(){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        float textRatio = (float) playerRectHp.width()/ playerRectHp.height();
        paint.setTextSize(textSize*textRatio);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        return paint;
    }

    public void playerEndTurn(){
        evTotal++;
        if(playerHand != null && playerDeck != null && playerDeck.getSize() > 0)
            playerHand.AddToHand(playerDeck.drawFromDeck(CARDS_PER_TURN));
    }

    public boolean getIsAlive(){return isAlive;}

    protected void createPlayerRect( IGraphics2D iGraphics2D, AssetStore assetStore) {
        float top , bot, leftSide;
        int left, right, topPlayerIcon, botPlayerIcon;

        if(playerIconBitmap == null || playerHPBarBitmap == null)
            setUpBitmap(assetStore);
        if (fieldLocation == genAlgorithm.field.TOP) {
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

    protected void setUpBitmap(AssetStore pAssetManger) {

        //pAssetManger.loadAndAddBitmap(hpBarFileName, "img/PlayerIcons/"+hpBarFileName+".png");
        pAssetManger.loadAndAddBitmap(hpBarFileName, "GameScreenImages/HealthMonitor.png");
        pAssetManger.loadAndAddBitmap(playerImgFile, "img/PlayerIcons/"+playerImgFile+".png");
        playerIconBitmap = pAssetManger.getBitmap(playerImgFile);
        playerHPBarBitmap = pAssetManger.getBitmap(hpBarFileName);
    }
}


