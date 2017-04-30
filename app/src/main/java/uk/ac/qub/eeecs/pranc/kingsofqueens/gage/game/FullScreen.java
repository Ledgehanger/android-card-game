package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by ariftahir on 30/04/2017.
 */

public class FullScreen extends AppCompatActivity {
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
}
