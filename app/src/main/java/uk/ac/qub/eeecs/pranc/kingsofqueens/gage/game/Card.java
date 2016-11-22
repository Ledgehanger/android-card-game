package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

/**
 * Created by markm on 20/11/2016.
 */

public class Card {

    public int id;
    String name;
    int attack;
    int defense;
    String ability;
    String imgFile;
    int EV;
    boolean inDeck;
    int handPosition = 0;

    public Card(int id, String name, int attack, int defense,
                String ability, String imgFile, int EV,boolean inDeck) {
        this.id = id;
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.ability = ability;
        this.imgFile = imgFile;
        this.EV = EV;
        this.inDeck = inDeck;
    }
}
