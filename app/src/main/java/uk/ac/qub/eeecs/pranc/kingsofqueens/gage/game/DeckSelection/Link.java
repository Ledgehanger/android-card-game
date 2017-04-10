package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.DeckSelection;

/**
 * Created by Paddy_Lenovo on 06/04/2017.
 * The class handles the holds the cards that are drawn into the hand and the hand enacts functions on the cards
 */

public class Link {

    public Link next;
    public Link(){

    }

    public void display(){
    //drawing the cards to the field

    }
}

class LinkList {

    public Link firstLink;
    LinkList() {//constructor

        firstLink = null;
    }

    public boolean isEmpty() {

        return (firstLink == null);//call to draw

    }
    //value needs to be added to Card so it can interact with the list as it enters the hand, replace newCard
    public void insertFirstLink() {

        Link newLink = new Link();
        newLink.next = firstLink;
        firstLink = newLink;
    }

    public Link removeFirst() {
        Link linkReference = firstLink;
        if (!isEmpty()) {
            firstLink = firstLink.next;
        } else {
            //call new card
        }
        return linkReference;
    }

    public void display() {

        Link theLink = firstLink;
        while (theLink != null) {

            theLink.display();//exchanged for draw..
            theLink = theLink.next;

        }
    }
    public void removeLink() {

        Link currentLink = firstLink;
        Link previousLink = firstLink;

    }
}