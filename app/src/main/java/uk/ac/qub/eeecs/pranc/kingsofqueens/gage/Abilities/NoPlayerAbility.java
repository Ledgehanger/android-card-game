package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;



/**
 * Created by mark on 10/04/2017.
 */

public abstract class NoPlayerAbility implements Ability{
    protected boolean hasAbility;
    public void effect(int effectLevel){}
    public boolean getHasAbility(){return hasAbility;}
}
