package org.hbrs.se1.ws21.uebung1.control;

import java.util.HashMap;
public class GermanTranslator implements Translator {

	public String date = "Okt/2021"; // Default-Wert
	HashMap<Integer, String> uebersetzteZahlen = new HashMap<>();
	public GermanTranslator(){
		uebersetzteZahlen.put(1, "eins");
		uebersetzteZahlen.put(2, "zwei");
		uebersetzteZahlen.put(3, "drei");
		uebersetzteZahlen.put(4, "vier");
		uebersetzteZahlen.put(5, "fünf");
		uebersetzteZahlen.put(6, "sech");
		uebersetzteZahlen.put(7, "sieben");
		uebersetzteZahlen.put(8, "acht");
		uebersetzteZahlen.put(9, "neun");
		uebersetzteZahlen.put(10, "zehn");
	}
	/**
	 * Methode zur Übersetzung einer Zahl in eine String-Repraesentation
	 */
	public String translateNumber( int number ) {
		// [ihr Source Code aus Übung 1-2]
		return uebersetzteZahlen.getOrDefault(number, String.format("Übersetzung der Zahl %d nicht möglich (%.1f)", number, version));
	}

	/**
	 * Objektmethode der Klasse GermanTranslator zur Ausgabe einer Info.
	 */
	public void printInfo(){
		System.out.println( "GermanTranslator v1.9, erzeugt am " + this.date );
	}

	/**
	 * Setzen des Datums, wann der Uebersetzer erzeugt wurde (Format: Monat/Jahr (Beispiel: Okt/2021))
	 * Das Datum sollte system-intern gesetzt werden und nicht von externen View-Klassen
	 */
	public void setDate( String date ) {
		this.date = date;
	}
}