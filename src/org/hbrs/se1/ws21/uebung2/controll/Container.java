package org.hbrs.se1.ws21.uebung2.controll;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.hbrs.se1.ws21.uebung2.controll.exceptions.ContainerException;

public class Container {

    private List<Member> speicher; // Im speicher werden die Member Elemente gespeichert.

    public Container() { // Speicher wird erst ab aufruf des Konstruktor initialisieren .
        speicher = new LinkedList<>();
    }

    public void addMember(Member member) throws ContainerException {
        if(member == null){// NullPointerException abfangen.
            throw new IllegalArgumentException();
        }
        if (speicher.contains(member)) {// Wenn das Member Element schon im speicher vorhanden ist wird die
                                        // ConatinerException geworfen.
            throw new ContainerException(member.getID());
        }
        speicher.add(member);// Member Element wird zum speicher hinzugefühgt.
    }

    public String deleteMember(Integer id) {
        for (Member member : speicher) {
            if (Objects.equals(member.getID(), id)) {// Suche nach dem Element im speicher.
                speicher.remove(member);// Loeschung des Elements aus dem Speicher.
                return String.format("Geloescht:[%s]", member.toString());
            }// eine Exception zu werfen wäre natürlich eindeutiger da sie auch abgefangen werden kann.
        }
        return String.format("Member Element (%d) nicht vorhanden", id);// kein zu löschendes Element im speicher
                                                                        // gefunden.
    }

    public void dump(){
        for (Member member : speicher) {
            System.out.println(member.toString());
        }
    }

    public int size(){// Anzahl der Inhalte im Speicher.
        return speicher.size();
    }
}
