package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;


import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Player;

/**
 * Author Mark McAleese (40177285) all methods
 */

public abstract class Ability {
    protected boolean HasAbility;
    protected int levelOne;
    protected int levelTwo;
    protected int levelThree;
    protected int effect;
    protected Player effectPlayer;

    public boolean getHasAbility(){return HasAbility;};

    public void effect(int effectLevel){

        switch(effectLevel) {
            case 3:
                effect= levelThree;
                break;
            case 2:
                effect = levelTwo;
                break;
            case 1:
                effect = levelOne;
                break;
            default:
                effect = 0;
                break;
        }
    }

    public void addEffectPlayer(Player pPlayer){
        this.effectPlayer = pPlayer;
    }
}

