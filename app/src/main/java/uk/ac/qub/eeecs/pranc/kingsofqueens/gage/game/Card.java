package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;
/**
 * Created by Carl on 20/11/2016.
 */
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.Ability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Paint;
import android.graphics.Typeface;

public class Card{

    public int id;
    public String name;
    public String type;
    public Ability ability;
    public String picture;
    public int hp;
    public int atk;
    public int ev;
    public int evCost;
    public int width;
    public int height;

    public boolean inDeck;

    public Bitmap cardImg;
    public Rect cardTextRect;
    protected Rect cardRect;
    public Paint textPaint;

    public float textSize=10f;

    protected final int OFFSET = 133;
    Game newGame;
    // TODO: 25/11/2016 Assgin this where will break unit tests, either figure out how to get Game in tests or figure out a different way here 
    AssetStore aStore;
    /* =sGame.getAssetManager(); Doing this breaks the testable of the deck class comment
    out for now to have a quick fix
     */
    private AssetManager aManager;

    //Getters and Setters
    public void setID(int id){this.id=id;}
    public int getID(){return id;}
    public void setType(String type){this.type=type;}
    public String getType(){return type;}
    public void setHP(int hp){this.hp=hp;}
    public int getHP(){return hp;}
    public void setATK(int atk){this.atk=atk;}
    public int getATK(){return atk;}
    public void setEv(int ev){this.ev=ev;}
    public int getEv(){return ev;}
    public void setEvCost(int evCost){this.evCost=evCost;}
    public int getEvCost(){return evCost;}
    public void setCardImg(Bitmap cardImg){this.cardImg=cardImg;}
    public Bitmap getCardImg(){return cardImg;}


    public Card(String name ,int id, String type, int hp, int atk, int ev, int evCost, boolean inDeck, String cardDraw,String Ability, AssetStore aStore) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.hp = hp;
        this.atk = atk;
        this.ev = ev;
        this.evCost = evCost;
        this.ability = genAlgorithm.findAbility(Ability);
        this.inDeck = inDeck;
        this.picture = cardDraw;
        this.aStore  = aStore;

    }

    public Card(int id,String type)
    {
        this.id=id;
        this.type=type;
        cardJSON(id,type);
    }

    public void cardJSON(int id,String type)
    {
        try {
            JSONArray array = newGame.getAssetManager().getJson(type);
            JSONObject card = array.getJSONObject(id);

            name=card.getString("name");
            atk=card.getInt("attack");
            hp=card.getInt("defense");
            String strAbility=card.getString("ability");
            ability=genAlgorithm.findAbility(strAbility);
            picture=card.getString("picture");
            inDeck=card.getBoolean("inDeck");
            evCost=card.getInt("evCost");
            ev=card.getInt("ev");
        }

        catch(JSONException e)
        {
            String p=e.toString();
        }

    }

    public void evolve()
    {
        try{
            id++;
            JSONArray array= newGame.getAssetManager().getJson(type);
            JSONObject evCard=array.getJSONObject(id);

            ev=evCard.getInt("ev");
            if(ev==0)
            {
                return;
            }
            else
            {
                cardJSON(id,type);
            }
        }
        catch(JSONException e){
            String p=e.toString();
        }
    }

    public void setUpCardBitmap(int top, boolean drawBack)
    {
            if(drawBack){
                aStore.loadAndAddBitmap("deckimg", "img/PlayerIcons/deckimg.png");
                cardImg = aStore.getBitmap("deckimg");
            }else {
                aStore.loadAndAddBitmap(name, picture);
                cardImg = aStore.getBitmap(name);
            }
            width = OFFSET;
            height = top;

    }

    public void createCardRect(int bot, int left, int top)
    {
        int cardLeft=left;
        int cardRight= left + OFFSET;
        int cardTop=top;
        int cardBottom=bot;

        cardRect=new Rect(cardLeft,cardTop,cardRight,cardBottom);
    }

    public Paint formatText()
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        float textRatio = 26.6F;
        paint.setTextSize(textRatio);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        return paint;

    }

    public void drawCard(int bot, int left, int top, IGraphics2D iG2D,boolean drawBack)
    {
        if(cardRect==null)
            createCardRect(bot,left,top);
        if (textPaint==null)
            textPaint=formatText();
        if(cardImg == null){
            setUpCardBitmap(top,drawBack);
        }


        iG2D.drawBitmap(cardImg,null,cardRect,null);

        if(!drawBack){
            iG2D.drawText(Integer.toString(hp),(left+width) - 20,bot - 13,textPaint);
            iG2D.drawText(Integer.toString(atk),left+15,bot - 13,textPaint);
        }

    }

    public int modHP(Card card,int modHP)
    {
        int hp = card.getHP();
        int atk=card.getATK();
        hp=hp+modHP;
        if(hp<=0)
        {

        }
        else
        {
            //drawCardImage(card,hp,atk);
        }
        return hp;
    }
}