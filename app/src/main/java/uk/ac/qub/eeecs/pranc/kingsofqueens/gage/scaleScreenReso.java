package uk.ac.qub.eeecs.pranc.kingsofqueens.gage;
/**
 * Created by Carl on 09/04/2017.
 * DEV NOTE: DESIGN EVERYTHING WITH 1184*720 IN MIND
 * CLASS IS USED TO SCALE TO OTHER DEVICE RESOLUTION'S
 */
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import android.graphics.Rect;

public class scaleScreenReso {
    protected static double horiScale;
    protected static double vertScale;
    protected static final int DEVRESOVERT=720;
    protected static final int DEVRESOHORI=1184;

    protected IGraphics2D iG2D;

    public scaleScreenReso(IGraphics2D iG2D)
    {
        this.iG2D=iG2D;

        double hori=iG2D.getSurfaceWidth();
        double vert=iG2D.getSurfaceHeight();

        horiScale=hori/DEVRESOHORI;
        vertScale=vert/DEVRESOVERT;
    }
    public scaleScreenReso(int width, int height)
    {
        this.iG2D=iG2D;

        double hori= (double) width;
        double vert= (double)height;

        horiScale=hori/DEVRESOHORI;
        vertScale=vert/DEVRESOVERT;
    }

    public void setHoriScale(int horiScale){this.horiScale=horiScale;}
    public void setVertScale(int vertScale){this.vertScale=vertScale;}

    public Rect scaleRect(int left,int top,int right,int bot)
    {
            Rect scaleRect;
            int tleft = (int) (left * horiScale);
            int tright = (int)(right * horiScale);
            int ttop = (int) (top * vertScale);
            int tbot = (int) (bot * vertScale);
            scaleRect=new Rect((int)(left*horiScale),(int)(top*vertScale),(int)(right*horiScale),(int)(bot*vertScale));
            return scaleRect;
    }

    public int scaleHoriVar(int var)
    {
        var*=horiScale;
        return var;
    }

    public int scaleVertVar(int var)
    {
        var*=vertScale;
        return var;
    }
}
