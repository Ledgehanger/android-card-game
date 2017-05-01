/**
 * Created by Carl on 13/11/2016.
 */
package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.ExtraGameScreens;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;

public abstract class screen {

    //Properties

    //Screen Name
    protected String sName;
    //Screen that game belongs to
    protected Game sGame;

    //Get and set Methods

    public String getSName(){ return sName;}
    public void setSName(String sName){this.sName=sName;}

    public Game getsGame(){return sGame;}
    public void setsGame(Game sGame){this.sGame=sGame;}

    //Constructor
    public screen(String sName,Game sGame)
    {
        this.sName=sName;
        this.sGame=sGame;
    }

    //Update and Draw Methods

    public abstract void update(ElapsedTime elapsedTime);
    public abstract void draw(ElapsedTime elapsedTime, IGraphics2D iGraphics2D);

    //Used by app when it is paused
    public void pause()
    {

    }

    //Used by app when it is resumed
    public void resume()
    {

    }

    //Used by app when it is disposed
    public void dispose()
    {

    }





}
