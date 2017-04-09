package uk.ac.qub.eeecs.pranc.kingsofqueens;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.Ability;
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
    String AddPath  = "uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.AddEvAbility";
    String HealPath = "uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.HealAbility";


    @Test
    public void effectAddEv(){
        ability = genAlgorithm.findAbility(AddPath);
        if(ability.getAbility()) {
            test = new Player();
            ability.effect(test, "level1");
            assertEquals(2, test.getEvTotal());

            test = new Player();
            ability.effect(test, "level2");
            assertEquals(3, test.getEvTotal());

            test = new Player();
            ability.effect(test, "level3");
            assertEquals(4, test.getEvTotal());
        }
    }

    @Test
    public void healEffectOnMaxHp(){

        test = new Player();

        ability = genAlgorithm.findAbility(HealPath);
        ability.effect(test,"level3");
        assertEquals(20,test.getHp());
    }

    @Test
    public void healEffectLevel1(){

       // test = new Player(18,18,20,"18",true);

        test = new Player();
        test.DamageTaken(2);

        ability = genAlgorithm.findAbility(HealPath);
        ability.effect(test,"level1");
        assertEquals(19,test.getHp());
    }
    public void healEffectLevel2(){

        test = new Player();

        ability = genAlgorithm.findAbility(HealPath);
        ability.effect(test,"level2");
        assertEquals(19,test.getHp());
    }

    public void healEffectLevel3(){

        test = new Player();

        ability = genAlgorithm.findAbility(HealPath);
        ability.effect(test,"level3");
        assertEquals(19,test.getHp());
    }


    @Test
    public void nullEffect(){

        test = new Player();
        ability = genAlgorithm.findAbility("Fake");
        assertNull(ability);

    }
}
