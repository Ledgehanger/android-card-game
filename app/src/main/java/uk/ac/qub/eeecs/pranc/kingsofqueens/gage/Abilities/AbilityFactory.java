package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities;

/**
 * Author Mark McAleese (40177285) all methods
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
