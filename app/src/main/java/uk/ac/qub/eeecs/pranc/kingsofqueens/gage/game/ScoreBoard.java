/*
package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

*/
/**
 * Created by ariftahir on 13/04/2017.
 *//*


public class ScoreBoard extends  AppCompatActivity{

        class RenderView extends View {
             
            Paint paint;
             
            Typeface font;
             
            Rect bounds = new Rect();
             
            Player player;
             
            GameOverState gameOver;
              


            public RenderView(Context context) { 
                super(context); 
                paint = new Paint(); 
                font = Typeface.createFromAsset(context.getAssets(), "font.ttf"); 
            }  

            protected void drawScore(Canvas canvas){ 
                canvas.drawRGB(0,0,0); 
                paint.setTypeface(font); 
                paint.setTextSize(28); 
                paint.setTextAlign(Paint.Align.CENTER); 
                canvas.drawText("SCORE: ", canvas.getWidth()/2, 100, paint);  
                if(player.isAlive && player.DamageTaken(0)){ 
                    player.evTotal += 10; 
                    canvas.drawText("SCORE: " + player.evTotal, canvas.getWidth()/2, 100, paint); 
                }else if(player.isAlive && !player.DamageTaken(0)){ 
                    player.evTotal -= 10; 
                    canvas.drawText("SCORE: " + player.evTotal, canvas.getWidth()/2, 100, paint); 
                }else if(!player.isAlive){ 
                    //gameOver(); 
                    canvas.drawText("GameOver", canvas.getWidth()/2, 100, paint);
                }  

            } 

        } 
        public void onCreate(Bundle savedInstanceState) { 
            super.onCreate(savedInstanceState); 
            requestWindowFeature(Window.FEATURE_NO_TITLE); 
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
            setContentView(new RenderView(this)); 
        } 

    }
*/
