package org.hbrs.se1.ws21.uebung3.control;

import java.io.Serializable;

public interface Member extends Serializable{ //Serializable interface wird gebraucht damit es in der Datei als Objeckt gespeichert werden kann

    // ID ist über einen Konstruktor einer abgeleiteten Klasse
    // explizit außerhalb der Container-Klasse zu belegen
    // --> Primärschlüssel zur Unterscheidung aller Member-Objekte
    Integer getID();

    public String toString();

}
