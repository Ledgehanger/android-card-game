package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.FileIO;

/**
 * Created by ariftahir on 14/04/2017.
 */

//THIS CLASS WILL READ A FILE ON MEMORY WHICH STORES USERS SETTING, HIGH SCORES AND VOLUME E.G. GAME WAS MUTED ON LAST PLAY AND A HIGHSCORE WAS ACHIEVED

public class Settings {
    public static boolean soundEnabled = true;
    public static int[] highScores = new int[]{100, 80, 50, 30, 10};

    public static boolean isSoundEnabled() {
        return soundEnabled;
    }

    public static void setSoundEnabled(boolean soundEnabled) {
        Settings.soundEnabled = soundEnabled;
    }

    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(files.readFile("img/koq.txt")));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                highScores[i] = Integer.parseInt(in.readLine());
            }
        } catch (IOException e) {
            //load defaults
        } catch (NumberFormatException e) {
            //load defaults
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            }catch(IOException e){
                //load defaults
            }
        }
    }

    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile("img/koq.txt")));
            out.write(Boolean.toString((soundEnabled)));
            for(int i = 0; i < 5; i++){
                out.write(Integer.toString(highScores[i]));
            }
        }catch(IOException e){
        }finally {
            try{
                if(out!=null){
                    out.close();
                }
            }catch(IOException e){
                //load defaults
            }
        }
    }

    //determine if score is highest than in text file
    public static void addScore(int score){
        for(int i = 0; i < 5; i++){
            if(highScores[i] < score){
                for(int j = 4; j > i; j++)
                    highScores[j] = highScores[j + 1];
                highScores[i] = score;
                break;
            }
        }
    }
}