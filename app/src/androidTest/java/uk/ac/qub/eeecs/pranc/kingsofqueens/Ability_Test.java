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
        //test = new Player(20,20,20,"test",true);

        ability = genAlgorithm.findAbility(AddPath);
        ability.effect(test,"level1");
        assertEquals(1,test.getEvTotal());

        //ability.effect(test,"level2");
        assertEquals(2,test.getEvTotal());

        //test = new Player(20,20,20,"test",true);
        ability.effect(test,"level3");
        assertEquals(3,test.getEvTotal());

    }

    @Test
    public void healEffectOnMaxHp(){
       // test = new Player(20,20,20,"20",true);
        ability = genAlgorithm.findAbility(HealPath);
        ability.effect(test,"level3");
        assertEquals(20,test.getHp());
    }

    @Test
    public void healEffectLevel1(){
       // test = new Player(18,18,20,"18",true);
        ability = genAlgorithm.findAbility(HealPath);
        ability.effect(test,"level1");
        assertEquals(19,test.getHp());
    }
    public void healEffectLevel2(){
       // test = new Player(17,17,20,"17",true);
        ability = genAlgorithm.findAbility(HealPath);
        ability.effect(test,"level2");
        assertEquals(19,test.getHp());
    }

    public void healEffectLevel3(){
        //test = new Player(16,16,20,"16",true);
        ability = genAlgorithm.findAbility(HealPath);
        ability.effect(test,"level3");
        assertEquals(19,test.getHp());
    }


    @Test
    public void nullEffect(){
        //test = new Player(20,20,20,"test",true);
        ability = genAlgorithm.findAbility("Fake");
        assertNull(ability);

    }
}
