package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.Ability;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.AbilityFactory;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.EnemyEffectedAbility;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.OwnerEffectedAbility;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.Player;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by markm on 22/02/2017.
 */


@RunWith(AndroidJUnit4.class)
public class Ability_Test {
    Player test;
    Ability ability;
    String AddPath          = "AddEvAbility"     ;
    String HealPath         = "HealAbility"      ;
    String DealDamagePath   = "DealDamageAbility";
    int StartingHp          = 20;

    @Test
    public void effectAddEv(){
        ability = AbilityFactory.getAbility(AddPath);
        if(ability.getHasAbility()) {

            test = new Player();
            if(ability instanceof OwnerEffectedAbility) {
                ((OwnerEffectedAbility) ability).addEffectPlayer(test);
            }
            ability.effect(1);
            assertEquals(2, test.getEvTotal());

            test = new Player();
            if(ability instanceof OwnerEffectedAbility) {
                ((OwnerEffectedAbility) ability).addEffectPlayer(test);
            }
            ability.effect(2);
            assertEquals(3, test.getEvTotal());

            test = new Player();
            if(ability instanceof OwnerEffectedAbility) {
                ((OwnerEffectedAbility) ability).addEffectPlayer(test);
            }
            ability.effect(3);
            assertEquals(4, test.getEvTotal());
        }
    }
    @Test
    public void DealDmg(){

        Player enemy = new Player();
        test = new Player();
        DealDamageTest(enemy,test,1,1);

        enemy = new Player();
        test = new Player();
        DealDamageTest(enemy,test,2,2);

        enemy = new Player();
        test = new Player();
        DealDamageTest(enemy,test,3,3);

    }

    public void DealDamageTest(Player pPlayer, Player pEnemyPlayer, int pLevel, int dmg){


        ability = AbilityFactory.getAbility(DealDamagePath);
        if(ability instanceof EnemyEffectedAbility) {
            ((EnemyEffectedAbility) ability).addEffectPlayer(pEnemyPlayer);
        }
        ability.effect(pLevel);

        assertEquals(StartingHp - dmg ,pEnemyPlayer.getHp());
        assertEquals(StartingHp, pPlayer.getHp());
    }

    @Test
    public void healEffectOnMaxHp(){

        test = new Player();
        test.DamageTaken(3);
        ability = AbilityFactory.getAbility(HealPath);
        if(ability instanceof OwnerEffectedAbility) {
            ((OwnerEffectedAbility) ability).addEffectPlayer(test);
        }

        ability.effect(3);
        assertEquals(20,test.getHp());
    }

    @Test
    public void healEffectLevel1(){

       // test = new Player(18,18,20,"18",true);

        test = new Player();
        test.DamageTaken(2);

        ability = AbilityFactory.getAbility(HealPath);
        if(ability instanceof OwnerEffectedAbility) {
            ((OwnerEffectedAbility) ability).addEffectPlayer(test);
        }

        ability.effect(1);
        assertEquals(19,test.getHp());
    }
    @Test
    public void healEffectLevel2(){

        test = new Player();
        test.DamageTaken(4);
        ability = AbilityFactory.getAbility(HealPath);
        if(ability instanceof OwnerEffectedAbility) {
            ((OwnerEffectedAbility) ability).addEffectPlayer(test);
        }

        ability.effect(2);
        assertEquals(19,test.getHp());
    }
    @Test
    public void healEffectLevel3(){

        test = new Player();
        test.DamageTaken(6
        );
        ability = AbilityFactory.getAbility(HealPath);
        if(ability instanceof OwnerEffectedAbility) {
            ((OwnerEffectedAbility) ability).addEffectPlayer(test);
        }

        ability.effect(3);
        assertEquals(19,test.getHp());
    }


    @Test
    public void nullEffect(){

        test = new Player();
        ability = AbilityFactory.getAbility("Fake");
        assertNull(ability);

    }
}
