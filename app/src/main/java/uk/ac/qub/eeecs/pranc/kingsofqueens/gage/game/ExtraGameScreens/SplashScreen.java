package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.ExtraGameScreens;

/**
 * Created by Arif_Tahir on 11/04/2017.
 */

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import uk.ac.qub.eeecs.pranc.kingsofqueens.R;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.MainActivity;

public class SplashScreen extends AppCompatActivity {


    //TIME TO DISPLAY SPLASH
    private static int SPLAH_TIME_OUT = 3000;

    //HIDE SOFT KEYS AND ENBALE FULL SCREEN
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                finish();
            }
            //SHOULD EXECUTE DELAYED SPLASH
        },SPLAH_TIME_OUT);
    }
}
