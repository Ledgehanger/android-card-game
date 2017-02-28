package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;
/**
 * Created by Carl on 20/11/2016.
 * Class used for card objects in game
 * Will Contain various methods which relate to the use of card in the game
 * Methods desc to come later
 */
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.Ability;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Gen_Algorithm;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.CanvasGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.app.AlertDialog;
import java.util.ArrayList;
import java.util.List;
public class Card{
    /* Initial properties
    * id - Gets card information from JSON file(Ability,Initial Health and Attack, name etc.)
    * type - defines what deck a card comes from (Comp - 0, Psych - 1, Theo - 2, Law - 3, Med - 4, Eng - 5, GenB - 6)
    * hp - How much health a card has. Can be added to or reduced
    * atk- How much attack power a card has. Can be added to or reduced
    * evolveState - Defines which evolve state card is in (Stage 1 - 1, Stage 2-2, State 3-3)
    * canEvolve - Used as precaution since some cards cant evolve e.g Building card.
    *
    * x - X position of card on screen(Positive x goes right across screen, Start pos - top left)
    * y - Y position of card on screen(Positive y goes down screen, Start pos - top left)
    * width - Width of card (Will be scaled when not in use)
    * height - Height of card (Will be scaled when not in use)
    * cardImg - Image of card
    * */
    Rect cardArea;
    public int id;
    public String type;
    public int hp;
    public int atk;
    public int evolveLevel;
    public int evCost;
    public boolean inDeck;
    public float x;
    public float y;
    public float width;
    public float height;
    public Bitmap cardImg;
    public String imgPath;
    public Ability ability;
    Game sGame;
    // TODO: 25/11/2016 Assgin this where will break unit tests, either figure out how to get Game in tests or figure out a different way here 
    AssetStore aStore; 
    /* =sGame.getAssetManager(); Doing this breaks the testable of the deck class comment
    out for now to have a quick fix
     */
    AssetManager aManager;
    CanvasGraphics2D cg2D = new CanvasGraphics2D(aManager);
    //Getters and Setters
    public void setID(int id){this.id=id;}
    public int getID(){return id;}
    public void setType(String type){this.type=type;}
    public String getType(){return type;}
    public void setHP(int hp){this.hp=hp;}
    public int getHP(){return hp;}
    public void setATK(int atk){this.atk=atk;}
    public int getATK(){return atk;}
    public void setEvolveLevel(int evolveLevel){this.evolveLevel=evolveLevel;}
    public int getEvolveLevel(){return evolveLevel;}
    public void setEvCost(int evCost){this.evCost=evCost;}
    public int getEvCost(){return evCost;}
    public void setInDeck(boolean inDeck){this.inDeck=inDeck;}
    public boolean getInDeck(){return inDeck;}
    public void setX(float x){this.x=x;}
    public float getX(){return x;}
    public void setY(float y){this.y=y;}
    public float getY(){return y;}
    public void setWidth(float width){this.width=width;}
    public float getWidth(){return width;}
    public void setHeight(float height){this.height=height;}
    public float getHeight(){return height;}
    public void setCardImg(Bitmap cardImg){this.cardImg=cardImg;}
    public Bitmap getCardImg(){return cardImg;}
    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    //Constructor
    public Card(int id, String type, int hp, int atk, int evolveLevel, boolean inDeck, int evCost, float x, float y, float height,
                float width, Bitmap cardImg)
    {
        this.id=id;
        this.type=type;
        this.hp=hp;
        this.atk=atk;
        this.evolveLevel=evolveLevel;
        this.evCost=evCost;
        this.x=x;
        this.y=y;
        this.height=height;
        this.width=width;
        this.cardImg=cardImg;
    }
    public Card(int id, String type, int hp, int atk, int evCost, String imgPath) {
        this.id = id;
        this.type = type;
        this.hp = hp;
        this.atk = atk;
        this.evCost = evCost;
        this.imgPath = imgPath;
    }
    public Card()
    {
        id=0;
        type=null;
        hp=0;
        atk=0;
        evolveLevel=0;
        evCost=0;
        x=0;
        y=0;
        height=0;
        width=0;
    }

    public Card(int id, String type, int hp, int atk, int evolveLevel, int evCost, boolean inDeck, String imgPath,String Ability) {
        this.id = id;
        this.type = type;
        this.hp = hp;
        this.atk = atk;
        this.evolveLevel = evolveLevel;
        this.evCost = evCost;
        this.inDeck = inDeck;
        this.imgPath = imgPath;
        this.ability = Gen_Algorithm.findAbility(Ability);
    }




    //Methods
    public void drawCard(Card card,int hp, int atk)
    {
        Paint text=new Paint();
        int id = card.getID();
        String holdImgID=Integer.toString(id);
        String holdHP=Integer.toString(hp);
        String holdATK=Integer.toString(atk);
        Bitmap cardImg= sGame.getAssetManager().getBitmap(holdImgID);
        int cardLeft=cardImg.getWidth();
        int cardRight=cardLeft+cardImg.getWidth();
        int cardTop=(cg2D.getSurfaceHeight()/2);
        int cardBottom= cardTop+cardImg.getHeight();
        cardArea=new Rect(cardLeft,cardTop,cardRight,cardBottom);
        cg2D.drawBitmap(cardImg,null,cardArea,null);
        cg2D.drawText(holdHP,cardLeft,cardBottom,text);
        cg2D.drawText(holdATK,cardRight,cardBottom,text);
    }
    //Method used to modify HP card depending on if it gets damaged or healed
    public int modHP(Card card,int modHP)
    {
        int hp = card.getHP();
        int atk=card.getATK();
        hp=hp+modHP;
        if(hp<=0)
        {
            destroyCard(card);
        }
        else
        {
            drawCard(card,hp,atk);
        }
        return hp;
    }
    //Method used to modify ATK card depending on it when building ability activates
    public int modATK(Card card ,int modATK)
    {
        int atk=card.getATK();
        atk=modATK+atk;
        int hp=card.getHP();
        drawCard(card,hp,atk);
        return atk;
    }
    //Method used to evolve card to next level
    public void evolve(Card cardToEv, int id, int evCost,int totEvGained,int evolveLevel)
    {
        Card evolve=new Card();
        try
        {
            String JsonFileName = type;
            JSONArray array = sGame.getAssetManager().getJson(JsonFileName);
            int evID = 0;
            int goThrough = 0;
            do {
                JSONObject findID = array.getJSONObject(goThrough);
                evID = findID.getInt("_id");
                goThrough++;
            }
            while (id != evID);
            JSONObject evCard = array.getJSONObject(goThrough);
            evID=evCard.getInt("_id");
            String name=evCard.getString("name");
            int evATK= evCard.getInt("attack");
            int evHP=evCard.getInt("defense");
            String ability=evCard.getString("ability");
            String imgFile=evCard.getString("picture");
            int ev = evCard.getInt("ev");
            int newEVCost = evCard.getInt("evCost");
            String type = evCard.getString("type");
            Card sendCard = new Card(evID,type,evHP,evATK,newEVCost,imgFile);
            evolve.setID(evID);
            evolve.setType(type);
            evolve.setHP(evHP);
            evolve.setATK(evATK);
            evolve.setEvCost(newEVCost);
            evolve.setImgPath(imgFile);
        }
        catch(JSONException e)
        {
            String p=e.toString();
        }
    }
    //Method used to destroy card when HP is 0 or less
    public void destroyCard(Card card)
    {
    }
    /*Ability Methods
     * Comp - Disable
      * Psych - Control
      * Theo - EV Gain
      * Med - Heal*/
}