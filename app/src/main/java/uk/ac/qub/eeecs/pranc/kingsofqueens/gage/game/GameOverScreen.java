package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import uk.ac.qub.eeecs.pranc.kingsofqueens.R;

public class GameOverScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_screen);

        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView highScoreLabel = (TextView) findViewById(R.id.HighScoreLabel);


        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(score + "");

        SharedPreferences settings = getSharedPreferences("GAME_DATA", getApplicationContext().MODE_PRIVATE);
        int highScore = settings.getInt("HIGH SCORE", 0);

        if(score>highScore){
            highScoreLabel.setText("High Score : " + score);

            //SAVE SCORE
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH SCORE", score);
            editor.commit();

        }else{
            highScoreLabel.setText("High Score : " + highScore);
        }


        //SHOW RESULT - I THINK THIS HAS TO GO ON RENDER GAMESCREEN
        Intent intent = new Intent(getApplicationContext(), GameOverScreen.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
    }

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

    public void playAgain(View view){
        startActivity(new Intent(getApplicationContext(), RenderGameScreen.class));
    }
}
