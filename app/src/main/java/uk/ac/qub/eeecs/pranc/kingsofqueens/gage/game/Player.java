package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;


public class Player {

    protected int hp;
    protected int evTotal;
    protected String id;
    protected String playerImgFile;
    protected boolean isAlive;
    protected boolean handDrawCardBack = false;
    protected Bitmap playerIconBitmap;
    protected Bitmap playerHPBarBitmap;
    protected Paint playerPaint;

    protected Deck playerDeck;
    protected Hand playerHand;
    protected Field playerField;
    protected Rect playerRectIcon;
    protected Rect playerRectHp;
    protected genAlgorithm.field fieldLocation;

    protected float textSize = 25f;

    public final int STARTING_EV = 1;
    public final int STARTING_HP = 20;
    public final int CARDS_PER_TURN = 1;
    public final int STARTING_HAND_SIZE  = 3;

    public final String hpBarFileName = "HPBar";


    
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
        playerHand = new Hand(playerDeck.drawFromDeck(STARTING_HAND_SIZE));
        this.id = "Player";
        this.playerField = new Field();

    }
    public Player(String pImage, AssetStore assetStore,genAlgorithm.field fieldLocation){
        this.fieldLocation = fieldLocation;
        this.playerImgFile = pImage;
        this.hp = STARTING_HP;
        this.isAlive = true;
        this.evTotal = STARTING_EV;
        if(assetStore != null)
            setUpBitmap(assetStore);
        this.id = "AI";
        this.playerField = new Field();
    }

    public int  getEvTotal           ()            {
        return evTotal;
    }
    public int  getHp                ()                 {
        return this.hp;
    }
    public String getId              () {
        return id;
    }

    public void addToEvTotal   (int add){
        this.evTotal += add;
    }
    public void healPlayer     (int heal){
        for(int i = 0; i < heal; i++){
            if(hp < 20){
                hp++;
            }else
                break;
        }
    }
    public void playerStartTurn(){
        evTotal++;
        if(playerHand != null && playerDeck != null && playerDeck.getSize() > 0)
            playerHand.addToHand(playerDeck.drawFromDeck(CARDS_PER_TURN));
    }
    public void drawPlayer     (IGraphics2D iGraphics2D, AssetStore assetStore) {

        if (playerRectIcon == null || playerRectHp == null) {
            createPlayerRect(iGraphics2D, assetStore);
        }
        if(playerPaint == null)
            playerPaint = setUpPaint();

        iGraphics2D.drawBitmap(playerIconBitmap,null, playerRectIcon,null);
        iGraphics2D.drawBitmap(playerHPBarBitmap,null,playerRectHp,null);
        iGraphics2D.drawText(Integer.toString(hp),playerRectHp.centerX()-15,playerRectHp.centerY()+5,playerPaint);

        playerField.draw(fieldLocation,iGraphics2D,assetStore);
        playerDeck.drawDeck(fieldLocation,iGraphics2D);
        playerHand.drawHand(fieldLocation,iGraphics2D,assetStore,handDrawCardBack);


    }
    
    public boolean getIsAlive  (){return isAlive;}
    public Paint   setUpPaint  (){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        float textRatio = (float) playerRectHp.width()/ playerRectHp.height();
        paint.setTextSize(textSize*textRatio);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        return paint;
    }
    public boolean DamageTaken(int totalDamage) {
        if(!isAlive)
            return isAlive;

        for(int i = 0; i < totalDamage; i++){
            if(hp > 0)
                hp--;
            else{
                isAlive = false;
                break;
            }
        }

        return isAlive;
    }

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
    protected void setUpBitmap     (AssetStore pAssetManger) {

        pAssetManger.loadAndAddBitmap(hpBarFileName, "GameScreenImages/HealthMonitor.png");
        pAssetManger.loadAndAddBitmap(playerImgFile, "img/PlayerIcons/"+playerImgFile+".png");
        playerIconBitmap = pAssetManger.getBitmap(playerImgFile);
        playerHPBarBitmap = pAssetManger.getBitmap(hpBarFileName);
    }
}


