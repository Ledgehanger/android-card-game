package uk.ac.qub.eeecs.pranc.kingsofqueens.gage;
/**
 * Created by Carl on 09/04/2017.
 * DEV NOTE: DESIGN EVERYTHING WITH 1184*720 IN MIND
 * CLASS IS USED TO SCALE TO OTHER DEVICE RESOLUTION'S
 */
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class scaleScreenReso {
    protected static double horiScale;
    protected static double vertScale;
    protected static final int DEVRESOVERT=720;
    protected static final int DEVRESOHORI=1184;
    Paint paint;

    protected IGraphics2D iG2D;

    //Carl
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

    //Carl
    public void setHoriScale(int horiScale){this.horiScale=horiScale;}
    //Carl
    public void setVertScale(int vertScale){this.vertScale=vertScale;}


    //Carl
    public Rect scalarect(int left,int top,int right,int bot)
    {
            Rect scalarect;
            scalarect=new Rect((int)(left*horiScale),(int)(top*vertScale),(int)(right*horiScale),(int)(bot*vertScale));
            return scalarect;
    }

    //Carl
    public int scaleHoriVar(int var)
    {
        var*=horiScale;
        return var;
    }


    //Carl
    public int scaleVertVar(int var)
    {
        var*=vertScale;
        return var;
    }
    //Author Mark McAleese (40177285)
    public void drawScalaText(IGraphics2D pIGraphics2D, String text, int x, int y, float textSize) {
        pIGraphics2D.drawText(text, x, y, setUpPaint(textSize));
    }

    public Paint setUpPaint  (float textSize){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize((int)(textSize*vertScale));
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        return paint;
    }
}
