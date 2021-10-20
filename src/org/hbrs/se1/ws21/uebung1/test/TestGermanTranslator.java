package org.hbrs.se1.ws21.uebung1.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hbrs.se1.ws21.uebung1.control.GermanTranslator;
import org.hbrs.se1.ws21.uebung1.control.Translator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestGermanTranslator {
    Translator t1;
    String[] zahlenUebersetzt = {"eins", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun", "zehn"};
    @BeforeEach
    public void setup(){
        t1 = new GermanTranslator();
    }
    @Test
    public void testGermanTranslator(){
        assertEquals("Übersetzung der Zahl -1 nicht möglich (1,0)", t1.translateNumber(-1));
        assertEquals("Übersetzung der Zahl 11 nicht möglich (" + Translator.version + ")", t1.translateNumber(11));
        for (int i = 1; i < 11; i++) {
            assertEquals(zahlenUebersetzt[i-1], t1.translateNumber(i));
        }
        assertEquals("Übersetzung der Zahl 0 nicht möglich (1,0)", t1.translateNumber(0));
    }
}