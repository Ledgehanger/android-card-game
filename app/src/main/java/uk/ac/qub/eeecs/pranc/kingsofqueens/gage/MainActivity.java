package uk.ac.qub.eeecs.pranc.kingsofqueens.gage;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import uk.ac.qub.eeecs.pranc.kingsofqueens.R;



/**
 * Main game activity
 *
 * @version 1.0
 */
public class MainActivity extends Activity {

    /**
     * Game fragment instance
     */
    private Game mGame;

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the window as suitable for a game, namely: full screen
        // with no title and a request to keep the screen on. The changes
        // are made before any content is inflated.

        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Set the content view to use a simple frame layout
        setContentView(R.layout.activity_main);

        // Add in the main game fragment
        FragmentManager fm = getFragmentManager();
        mGame = (Game) fm.findFragmentById(R.id.activity_main_id);

    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        // If the fragment does not consume the back event then
        // trigger the default behaviour
        if (!mGame.onBackPressed())
            super.onBackPressed();
    }
}