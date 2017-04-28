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
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.scaleScreenReso;

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
    public float textSize = 22f;
    public float textSizeDesc = 10f;

    protected Rect cardRect;
    protected final int OFFSET = 133;
    protected AssetStore aStore;

    //Getters and Setters
    public void setType(String type){this.type=type;}
    public String getType(){return type;}
    public int getHP(){return hp;}
    public int getATK(){return atk;}
    public int getEv(){return ev;}
    public int getEvCost(){return evCost;}

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

    //FOR AI USE ONLY
    public int getWeight()
    {
        int weight=hp+atk+abilityLvl;
        return weight;
    }

    public void evolve() {
        try {
            id++;
            JSONArray array = aStore.getJson(type);
            for (int index = 0; index < array.length(); index++) {
                JSONObject object = array.getJSONObject(index);
                int id = object.getInt("_id");
                if (id == ev) {
                    name = object.getString("name");
                    atk = object.getInt("attack");
                    hp = object.getInt("defense");
                    String strAbility = object.getString("ability");
                    ability = AbilityFactory.getAbility(strAbility);
                    picture = object.getString("picture");
                    inDeck = object.getBoolean("inDeck");
                    desc = object.getString("desc");
                    abilityLvl = object.getInt("abilityLvl");
                    evCost = object.getInt("evCost");
                    ev = object.getInt("ev");
                    setUpCardBitmap(height, false);
                    break;
                }
            }
        } catch (JSONException e) {
            String p = e.toString();
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

    public void createCardRect(int bot, int left, int top, scaleScreenReso scalar)
    {
        int cardLeft=left;
        int cardRight= left + OFFSET;
        int cardTop=top;
        int cardBottom=bot;
        cardRect=scalar.scalarect(cardLeft,cardTop,cardRight,cardBottom);
    }
    public void setCardToNull()
    {
        cardRect = null;
    }


    public void drawCard(int bot, int left, int top, IGraphics2D iG2D, boolean drawBack,scaleScreenReso scalar)
    {
        if(isPicked){
            top -= 50;
            bot -= 50;
        }
            createCardRect(bot,left,top,scalar);


        if(cardImg == null){
            setUpCardBitmap(top,drawBack);
        }

        if(iG2D != null)
            iG2D.drawBitmap(cardImg,null,cardRect,null);

        if(!drawBack && iG2D != null){

            scalar.drawScalaText(iG2D,Integer.toString(hp),cardRect.right - 28,cardRect.bottom-10,textSize);
            scalar.drawScalaText(iG2D,Integer.toString(atk),cardRect.left + 10,cardRect.bottom-10,textSize);
            scalar.drawScalaText(iG2D,Integer.toString(evCost),cardRect.left + 15,cardRect.top+30,textSize);
            scalar.drawScalaText(iG2D,desc,cardRect.left +30,cardRect.bottom-50,textSizeDesc);
        }

    }
    public void drawCard(Rect spotRect, IGraphics2D iG2D, scaleScreenReso screenReso)
    {
        setUpCardBitmap(0,false);
        iG2D.drawBitmap(cardImg,null,spotRect,null);
        screenReso.drawScalaText(iG2D,Integer.toString(hp),spotRect.right - 26,spotRect.bottom-10,textSize);
        screenReso.drawScalaText(iG2D,Integer.toString(atk)   , spotRect.left + 10,spotRect.bottom-10,textSize);
        screenReso.drawScalaText(iG2D,Integer.toString(evCost), spotRect.left + 15,spotRect.top   +30,textSize);
        screenReso.drawScalaText(iG2D,desc                    , spotRect.left + 30,spotRect.bottom-50,textSizeDesc);

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

    public boolean dealDamageToCard(int totalAttack) {
        for (int i = 0; i < totalAttack; i++) {
          hp--;
          if(hp <= 0)
              break;
        }
        return hp > 0;
    }
}