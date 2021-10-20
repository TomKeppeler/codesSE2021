package org.hbrs.se1.ws21.uebung2.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import org.hbrs.se1.ws21.uebung2.controll.Container;
import org.hbrs.se1.ws21.uebung2.controll.Member;
import org.hbrs.se1.ws21.uebung2.controll.exceptions.ContainerException;
import org.hbrs.se1.ws21.uebung2.view.*;

public class TestContainer {
    Container container;
    Member[] testCases = new Member[4];

    @BeforeEach
    public void setup() { // Wird vor jeder Testmethode ausgeführt.
        container = new Container();
        testCases[0] = new AutoMitMember(2, 4, "Audi", "A3", "Max Mustermann", 110.4);
        testCases[1] = new AutoMitMember(2, 4, "Fiat", "500", "Lisa Mustermann", 80.47);
        testCases[2] = new PersonMitMember("Mustermann", "Max", new Date(1995, 10, 10), "Golf", 1.8,
                Geschlecht.MAENLICH);
        testCases[3] = new PersonMitMember("Mustermann", "Lisa", new Date(1995, 10, 10), "Schwimmen", 1.64,
                Geschlecht.WEIBLICH);
    }

    @Test
    public void addDeleteMemberAek1() {
        for (int i = 0; i < testCases.length; i++) {
            assertEquals(i, container.size());
            try {
                container.addMember(testCases[i]);
            } catch (ContainerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (int i = 0; i < testCases.length; i++) {
            assertEquals(String.format("Geloescht:[%s]", testCases[i].getID()),
                    container.deleteMember(testCases[i].getID()));
            assertEquals(container.size() - i, container.size());
        }
        try {
            container.addMember(testCases[0]);
        } catch (ContainerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // Test auf Doppeltes einfüghen
        assertThrows(ContainerException.class, () -> container.addMember(testCases[0]));
    }

    @Test
    public void addMemberAek2und3() {
        try {
            container.addMember(null);
        } catch (ContainerException e) {
            assertTrue(true);
        }
        assertEquals(1, 2);
    }
}