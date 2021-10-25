package org.hbrs.se1.ws21.uebung3.controll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hbrs.se1.ws21.uebung2.controll.exceptions.ContainerException;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceStrategy;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceException.ExceptionType;

public class Container {
    private static Container singleInstance = null;
    private ArrayList<Member> speicher; // Im speicher werden die Member Elemente gespeichert.
    private PersistenceStrategy<Member> persistenceStrategy = null;

    private Container() { // Speicher wird erst ab aufruf des Konstruktor initialisieren .
        speicher = new ArrayList<>();
    }

    public void setPersistenceStrategy(PersistenceStrategy<Member> persistenceStrategy) {
        this.persistenceStrategy = persistenceStrategy;
    }

    public static Container getInstance() {
        if (singleInstance == null) {
            singleInstance = new Container();
        }
        return singleInstance;
    }

    public void store() throws PersistenceException {
        if(persistenceStrategy == null){
            throw new PersistenceException(ExceptionType.NoStrategyIsSet, "Persistence strategy was never set.");
        }
        persistenceStrategy.openConnection();
        persistenceStrategy.save(speicher);
        persistenceStrategy.closeConnection();
    }

    public void load() throws PersistenceException {
        if(persistenceStrategy == null){
            throw new PersistenceException(ExceptionType.NoStrategyIsSet, "Persistence strategy was never set.");
        }
        persistenceStrategy.openConnection();
        speicher = (ArrayList<Member>) persistenceStrategy.load();
        persistenceStrategy.closeConnection();
    }

    public void addMember(Member member) throws ContainerException {
        if (member == null) {// NullPointerException abfangen.
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
            } // eine Exception zu werfen wäre natürlich eindeutiger da sie auch abgefangen
              // werden kann.
        }
        return String.format("Member Element (%d) nicht vorhanden", id);// kein zu löschendes Element im speicher
                                                                        // gefunden.
    }

    public List<Member> getCurrentList() {
        return speicher;
    }

    public int size() {// Anzahl der Inhalte im Speicher.
        return speicher.size();
    }
}
