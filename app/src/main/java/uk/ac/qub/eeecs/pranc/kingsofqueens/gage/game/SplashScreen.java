package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

/**
 * Created by Arif_Tahir on 11/04/2017.
 */

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.ac.qub.eeecs.pranc.kingsofqueens.R;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.MainActivity;

public class SplashScreen extends AppCompatActivity {

    //TIME TO DISPLAY SPLASH
    private static int SPLAH_TIME_OUT = 3000;

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
        },SPLAH_TIME_OUT);
    }
}
