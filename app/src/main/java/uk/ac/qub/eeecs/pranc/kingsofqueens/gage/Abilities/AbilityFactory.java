package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Player;

/**
 * Created by mark on 10/04/2017.
 */

public class AbilityFactory {

    public static Ability getAbility(String abilityType){
        if(abilityType == null || abilityType == "")
            return null;

        if(abilityType.equalsIgnoreCase("AddEvAbility")){
            return new AddEvAbility();
        }
        if(abilityType.equalsIgnoreCase("Default")){
            return new Default();
        }
        if(abilityType.equalsIgnoreCase("HealAbility")){
            return new HealAbility();
        }
        if(abilityType.equalsIgnoreCase("DealDamageAbility")){
            return new DealDamageAbility();
        }
        return null;
    }
}
