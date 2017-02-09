package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

/**
 * Created by Nicola on 22/11/2016.
 */

public class Player {
    int HP = 100;
    int ID;
    float x, y;
    String image;
    boolean isAlive;
    int evTotal;

    public boolean DamageTaken(int Totaldamage) {
        HP -= Totaldamage;
        return isAlive = HP > 0;
    }


    public Player(float x, int HP, float y, String image, boolean isAlive) {
        this.x = x;
        this.HP = HP;
        this.y = y;
        this.image = image;
        this.isAlive = isAlive;
        evTotal = 0;
    }


    public static boolean createWinner(int HP1, int HP2) {
        if (HP1 > HP2) {
            return true;
        } else {
            return false;
        }
    }
}


