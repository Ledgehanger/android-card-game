package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;
/**
 * Created by Carl on 20/11/2016.
 */
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.AbilityFactory;
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
    public String desc;
    public int  abilityLvl;

    public boolean inDeck;

    public Bitmap cardImg;
    public Rect cardTextRect;
    protected Rect cardRect;
    public Paint textPaint;
    public Paint textDesc;

    public float textSize=9f;

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


    public boolean isPicked = false;


    public Card(String name ,int id, String type, int hp, int atk, int ev, int evCost, boolean inDeck, String cardDraw,String Ability,String desc,int abilityLvl, AssetStore aStore) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.hp = hp;
        this.atk = atk;
        this.ev = ev;
        this.evCost = evCost;
        this.ability = AbilityFactory.getAbility(Ability);

        this.desc=desc;
        this.abilityLvl=abilityLvl;

        this.inDeck = inDeck;
        this.picture = cardDraw;

        this.aStore  = aStore;

    }

    public Card(int id,String type, AssetStore aStore)
    {
        this.id=id;
        this.type=type;
        this.aStore=aStore;
        cardJSON(id,type);
    }

    public void cardJSON(int id,String type)
    {
        try {
            aStore.loadAndAddJson(type,type+".json");
            JSONArray array = aStore.getJson(type);
            JSONObject card = array.getJSONObject(id);

            name=card.getString("name");
            atk=card.getInt("attack");
            hp=card.getInt("defense");
            String strAbility=card.getString("ability");
            ability=AbilityFactory.getAbility(strAbility);
            picture=card.getString("picture");
            inDeck=card.getBoolean("inDeck");
            desc=card.getString("desc");
            abilityLvl=card.getInt("abilityLvl");
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
    public void setCardToNull()
    {
        cardRect = null;
    }

    public Paint formatText(float textR)
    {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        float textRatio = textR;
        paint.setTextSize(textRatio);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        return paint;

    }

    public void drawCard(int bot, int left, int top, IGraphics2D iG2D,boolean drawBack)
    {
        if(isPicked){
            top -= 50;
            bot -= 50;
        }
            createCardRect(bot,left,top);
        if (textPaint==null)
            textPaint=formatText(26.5F);
        if (textDesc==null)
            textDesc=formatText(10F);
        if(cardImg == null){
            setUpCardBitmap(top,drawBack);
        }


        iG2D.drawBitmap(cardImg,null,cardRect,null);

        if(!drawBack){
            iG2D.drawText(Integer.toString(hp),(left+width)-22,bot-10,textPaint);
            iG2D.drawText(Integer.toString(atk),left+15,bot - 10,textPaint);
            iG2D.drawText(Integer.toString(evCost),left+15,top+30,textPaint);
            iG2D.drawText(desc,left+30,bot-50,textDesc);
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