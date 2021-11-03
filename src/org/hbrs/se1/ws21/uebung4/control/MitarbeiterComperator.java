package org.hbrs.se1.ws21.uebung4.control;

import java.util.Comparator;

public class MitarbeiterComperator implements Comparator<Mitarbeiter> {

    @Override
    public int compare(Mitarbeiter o1, Mitarbeiter o2) {
        return o1.getID() - o2.getID();
    }
    
}
