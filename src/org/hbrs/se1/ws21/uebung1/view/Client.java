package org.hbrs.se1.ws21.uebung1.view;

import org.hbrs.se1.ws21.uebung1.control.Translator;
import org.hbrs.se1.ws21.uebung1.control.factory.TranslatorFactory;

public class Client {

	/*
	 * Methode zur Ausgabe einer Zahl auf der Console
	 */
	public void display(int aNumber) {
		// In dieser Methode soll die Methode translateNumber
		// mit dem übergegebenen Wert der Variable aNumber
		// aufgerufen werden.
		//
		// Strenge Implementierung gegen das Interface Translator gewuenscht!

		Translator tl = TranslatorFactory.createGermanTranslator();
		System.out.println("Das Ergebnis der Berechnung: " 
		+ tl.translateNumber(aNumber) + "");
	}
}
