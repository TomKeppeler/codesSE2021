package org.hbrs.se1.ws21.uebung3.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

import org.hbrs.se1.ws21.uebung3.controll.*;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceStrategyMongoDB;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceException.ExceptionType;
import org.hbrs.se1.ws21.uebung3.view.AutoMitMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestContainer {
    private Container testContainer;
    private Member[] testCases = new Member[100];

    @BeforeEach
    public void setup() {
        testContainer = Container.getInstance();
        for (int i = 0; i < testCases.length; i++) {
            testCases[i] = new AutoMitMember(2, 4, "Audi", "A3", "Max Mustermann", 110.4);
        }
    }

    @Test
    public void noPersistenceStrategySetTest() {
        PersistenceException thrownException = assertThrows(PersistenceException.class, () -> testContainer.store());
        assertEquals(ExceptionType.NoStrategyIsSet, thrownException.getExceptionTypeType());
    }

    @Test
    public void setPersistenceStrategyMongoDBTest() {
        testContainer.setPersistenceStrategy(new PersistenceStrategyMongoDB<Member>());
        assertThrows(UnsupportedOperationException.class, () -> testContainer.load());
    }

    @Test
    public void incorrectFileLocationTest() {
        try {
            testContainer.store();
        } catch (PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PersistenceStrategyStream test = (PersistenceStrategyStream) testContainer.getPersistenceStrategy();
        test.setLocation("Object" + new Random().nextInt() + ".ser");
        PersistenceException exc = assertThrows(PersistenceException.class, () -> testContainer.load());
        assertEquals(ExceptionType.ConnectionNotAvailable, exc.getExceptionTypeType());
    }

    @Test
    public void useCaseSenarioTest() {
        assertEquals(100, testContainer.size());
        try {
            testContainer.store();
        } catch (PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(100, testContainer.size());
        try {
            testContainer.load();
        } catch (PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int i = 99; i > -1; i--) {
            assertEquals(i+1, testContainer.size());
            testContainer.deleteMember(testCases[i].getID());
            assertEquals(i+1, testContainer.size());
            try {
                testContainer.store();
            } catch (PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            assertEquals(i+1, testContainer.size());
            try {
                testContainer.load();
            } catch (PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            assertEquals(i+1, testContainer.size());
        }
    }
}
