package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.scaleScreenReso;


public class Player {

    public final int     STARTING_EV         = 1;
    public final int     STARTING_HP         = 20;
    public final int     CARDS_PER_TURN      = 1;
    public final int     STARTING_HAND_SIZE  = 3;
    public final int     HP_DEATH            = 0;
    public final String  HP_BAR_FILE_NAME    = "HPBar";
    public final String  EV_BAR_FILE_NAME    = "EVBar";
    public final String  EV_CHECKED_BAR_FILE_NAME = "EVBarChecked";

    protected boolean    isAlive;
    protected boolean    handDrawCardBack = false;
    protected boolean      evolving         = false;
    protected float      textSize = 25f;

    protected int        hp;
    protected int        evTotal;
    protected String     id;
    protected String     playerImgFile;

    protected Bitmap     playerIconBitmap;
    protected Bitmap     playerHPBarBitmap;
    protected Bitmap     playerNotCheckedEVBarBitmap;
    protected Bitmap     playerCheckEVBarBitmap;


    protected Rect       playerRectIcon;
    protected Rect       playerRectHp;
    protected Rect       playerRectEv;

    protected Paint      playerPaint;

    protected Deck       playerDeck;
    protected Hand       playerHand;
    protected Field      playerField;

    protected genAlgorithm.field fieldLocation;

    public Player(){
        this.hp      = STARTING_HP;
        this.evTotal = STARTING_EV;
        this.isAlive = true;
    }
    public Player(String pImage, AssetStore assetStore, Deck playerDeck,genAlgorithm.field fieldLocation ) {
        this.fieldLocation      = fieldLocation;
        this.playerImgFile      = pImage;
        this.hp                 = STARTING_HP;
        this.isAlive            = true;
        this.evTotal            = STARTING_EV;
        this.playerDeck         = playerDeck;

        if (assetStore != null)
            setUpBitmap(assetStore);

        this.playerHand     = new Hand(playerDeck.drawFromDeck(STARTING_HAND_SIZE));
        this.id             = "Player";
        this.playerField    = new Field();

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

    public int     getEvTotal  ()            {
        return evTotal;
    }
    public int     getHp       ()                 {
        return this.hp;
    }
    public String  getId       () {
        return id;
    }
    public boolean getIsAlive  (){return isAlive;}
    public void setHandDrawCardBack(boolean handDrawCardBack){this.handDrawCardBack=handDrawCardBack;}

    public void addToEvTotal   (int add){
        this.evTotal += add;
    }
    public void healPlayer     (int heal){
        for(int i = 0; i < heal; i++){
            if(hp < STARTING_HP)
                hp++;
            else
                break;
        }
    }
    public void playerStartTurn(){
        evTotal++;
        if(playerHand != null && playerDeck != null && playerDeck.getSize() > 0)
            playerHand.addToHand(playerDeck.drawFromDeck(CARDS_PER_TURN));
    }
    public void drawPlayer(IGraphics2D pIGraphics2D, AssetStore pAssetStore, int pSurfaceHeight, int pSurfaceWidth,
                           scaleScreenReso pScalar) {

        if (playerRectIcon == null || playerRectHp == null) {
            createPlayerRect(pAssetStore, pSurfaceHeight,pSurfaceWidth,pScalar);
        }
        if(playerPaint == null)
            playerPaint = setUpPaint();

        int xOffsetHp = 15;
        int yOffset   = 5;
        int xOffsetEv = 40;

        if(pIGraphics2D != null) {
            pIGraphics2D.drawBitmap(playerIconBitmap , null, playerRectIcon, null);
            pIGraphics2D.drawBitmap(playerHPBarBitmap, null, playerRectHp  , null);

            drawEvolving(pIGraphics2D);
            pIGraphics2D.drawText(Integer.toString(hp), playerRectHp.centerX() - xOffsetHp,
                    playerRectHp.centerY() + yOffset, playerPaint);
            String ev = "EV: " + evTotal;
            pIGraphics2D.drawText(ev, playerRectEv.centerX() - xOffsetEv,
                    playerRectEv.centerY() + yOffset, playerPaint);

            playerField.draw   (fieldLocation, pIGraphics2D, pAssetStore, pSurfaceHeight,
                    pSurfaceWidth,pScalar);
            playerDeck.drawDeck(fieldLocation, pIGraphics2D, pSurfaceHeight,pScalar);
            playerHand.drawHand(fieldLocation, pIGraphics2D, pAssetStore, handDrawCardBack,
                    pSurfaceHeight, pSurfaceWidth,pScalar);

        }

    }

    public void drawEvolving(IGraphics2D pIGraphics2D) {
        if(!evolving)
            pIGraphics2D.drawBitmap(playerNotCheckedEVBarBitmap, null, playerRectEv, null);
        else
            pIGraphics2D.drawBitmap(playerCheckEVBarBitmap, null, playerRectEv, null);
    }


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
            if(hp > HP_DEATH) {
                hp--;
            }else{
                isAlive = false;
                break;
            }
        }
        return isAlive;
    }

    protected void createPlayerRect(AssetStore assetStore, int surfaceHeight, int surfaceWidth, scaleScreenReso scalar) {
        float top , bot, leftSide;
        int left, right, topPlayerIcon, botPlayerIcon;
        int evDrawingOffset = 100;
        if(playerIconBitmap == null || playerHPBarBitmap == null)
            setUpBitmap(assetStore);

        if (fieldLocation == genAlgorithm.field.TOP) {
            top = 0;
            bot = surfaceHeight;

            leftSide = surfaceWidth;
            left = (int) leftSide - 100;
            right = (int) leftSide;
            topPlayerIcon = (int) top;
            botPlayerIcon = (int) ((bot) - (bot / 1.5)) - 75;
            int topHp = botPlayerIcon + 10;
            int botHp = topHp + 100;

            playerRectIcon = scalar.scalarect(left, topPlayerIcon, right, botPlayerIcon);
            playerRectHp   = scalar.scalarect(left,topHp,right,botHp);
            playerRectEv   = scalar.scalarect(left-100,topHp,right-100,botHp);

        } else {
            top = surfaceHeight / 2;
            bot = surfaceHeight;

            leftSide = surfaceWidth;
            left = (int) leftSide - 100;
            right = (int) leftSide;
            topPlayerIcon = (int) ((top) + (top / 4) + 105);
            botPlayerIcon = (int) bot;
            int topHp = topPlayerIcon - 100;
            int botHp = topPlayerIcon - 10;

            playerRectIcon = scalar.scalarect(left, topPlayerIcon, right, botPlayerIcon);
            playerRectHp   = scalar.scalarect(left,topHp,right,botHp);
            playerRectEv   = scalar.scalarect(left-evDrawingOffset,topHp,right-evDrawingOffset,botHp);

        }
    }
    protected void setUpBitmap     (AssetStore pAssetManger) {

        pAssetManger.loadAndAddBitmap(HP_BAR_FILE_NAME, "GameScreenImages/HealthMonitor.png");
        pAssetManger.loadAndAddBitmap(EV_BAR_FILE_NAME, "GameScreenImages/EvBar.png");
        pAssetManger.loadAndAddBitmap(EV_CHECKED_BAR_FILE_NAME, "GameScreenImages/EvBarPicked.png");
        pAssetManger.loadAndAddBitmap(playerImgFile, "img/PlayerIcons/"+playerImgFile+".png");
        playerIconBitmap  = pAssetManger.getBitmap(playerImgFile);
        playerHPBarBitmap = pAssetManger.getBitmap(HP_BAR_FILE_NAME);
        playerNotCheckedEVBarBitmap = pAssetManger.getBitmap(EV_BAR_FILE_NAME);
        playerCheckEVBarBitmap = pAssetManger.getBitmap(EV_CHECKED_BAR_FILE_NAME);
    }

    public void playerAttackPhase(Player enemyPlayer){
        for(int row = 0; row < playerField.ROWS_PER_FIELD; row++) {
            for (int column = 0; column < playerField.getSizeOfRow(); column++) {
                Spot currentSpot = playerField.getSpotFromRow(row, column);
                if (currentSpot.getCardPlaced()) {
                    Spot enemyPlayerSpot = enemyPlayer.playerField.getSpotFromRow(row, column);

                    if (enemyPlayerSpot.getCardPlaced())
                        enemyPlayerSpot.dealDamageToCurrentCard(currentSpot.getCardAttack());
                    else
                        enemyPlayer.DamageTaken(currentSpot.getCardAttack());
                }
            }
        }
    }

    public Rect getPlayerRectEv() {
        return playerRectEv;
    }

    public void setEvolving() {
        this.evolving = !evolving;
    }

    public boolean isEvolving() {
        return evolving;
    }
    
    public void evolving(TouchEvent touchEvent, Player enemy){
        for(int row = 0; row < playerField.ROWS_PER_FIELD; row++) {
            for (int column = 0; column < playerField.getSizeOfRow(); column++) {
                Spot currentSpot = playerField.getSpotFromRow(row, column);
                if (currentSpot.getCardPlaced()) {
                    if(genAlgorithm.hasTouchEvent(touchEvent,currentSpot.getSpotRect())){
                        if(evTotal >= currentSpot.getEvolvingCost() && currentSpot.getEvolvingCost() > 0){
                            evTotal -= currentSpot.getEvolvingCost();
                            currentSpot.cardEvolving();
                            evolving = false;
                            currentSpot.useCardAbility(this,enemy);
                        }
                    }
                }
            }
        }
    }
}


