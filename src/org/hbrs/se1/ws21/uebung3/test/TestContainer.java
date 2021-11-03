package org.hbrs.se1.ws21.uebung3.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.hbrs.se1.ws21.uebung2.control.exceptions.ContainerException;
import org.hbrs.se1.ws21.uebung3.control.*;
import org.hbrs.se1.ws21.uebung3.control.persistence.*;
import org.hbrs.se1.ws21.uebung3.control.persistence.PersistenceException.ExceptionType;
import org.hbrs.se1.ws21.uebung3.view.AutoMitMember;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestContainer {
    private Container testContainer;
    private Member[] testCases = new Member[100];

    @BeforeAll
    public void setup() {
        testContainer = Container.getInstance();
        for (int i = 0; i < 100; i++) {
            testCases[i] = new AutoMitMember(2, 4, "Audi", "A3", "Max Mustermann", 110.4);
        }
    }

    @Test
    public void noPersistenceStrategySetTest() {
        testContainer.setPersistenceStrategy(null); 
        assertThrows(PersistenceException.class, () -> testContainer.store());
    }

    @Test
    public void setPersistenceStrategyMongoDBTest() {
        testContainer.setPersistenceStrategy(new PersistenceStrategyMongoDB<Member>());
        assertThrows(UnsupportedOperationException.class, () -> testContainer.load());
    }

    @Test
    public void incorrectFileLocationTest() {
        testContainer.setPersistenceStrategy(new PersistenceStrategyStream());
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
        testContainer.setPersistenceStrategy(new PersistenceStrategyStream());
        for (Member member : testCases) {
            try {
                testContainer.addMember(member);
            } catch (ContainerException e) {
                // TODO Auto-generated catch block
                assertTrue(false);
            }            
        }
        assertEquals(100, testContainer.size());
        try {
            testContainer.store();
        } catch (PersistenceException e) {
            // TODO Auto-generated catch block
            assertTrue(false);
        }
        assertEquals(100, testContainer.size());
        try {
            testContainer.load();
        } catch (PersistenceException e) {
            // TODO Auto-generated catch block
            assertTrue(false);
        }
        assertEquals(100, testContainer.size());

        for (int i = 99; i > -1; i--) {
            assertEquals(i+1, testContainer.size());
            testContainer.deleteMember(testCases[i].getID());
            assertEquals(i, testContainer.size());
            try {
                testContainer.store();
            } catch (PersistenceException e) {
                // TODO Auto-generated catch block
                assertTrue(false);
            }
            assertEquals(i, testContainer.size());
            try {
                testContainer.load();
            } catch (PersistenceException e) {
                // TODO Auto-generated catch block
                assertTrue(false);
            }
            assertEquals(i, testContainer.size());
        }
    }
}
