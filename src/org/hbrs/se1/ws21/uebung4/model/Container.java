package org.hbrs.se1.ws21.uebung4.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hbrs.se1.ws21.uebung4.model.exceptions.ContainerException;
import org.hbrs.se1.ws21.uebung4.model.persistence.*;
import org.hbrs.se1.ws21.uebung4.model.persistence.PersistenceException.ExceptionType;

public class Container<E extends Member> {
    private static Container singleInstance = null;
    private PersistenceStrategy<E> persistenceStrategy = null;

    private ArrayList<E> speicher; // Im speicher werden die Member Elemente gespeichert.

    private Container() { // Speicher wird erst ab aufruf des Konstruktor initialisieren.
        speicher = new ArrayList<>();
    }

    public void setPersistenceStrategy(PersistenceStrategy<E> persistenceStrategy) {
        this.persistenceStrategy = persistenceStrategy;
    }

    public PersistenceStrategy<E> getPersistenceStrategy() {
        return persistenceStrategy;
    }

    public static <E extends Member> Container<E> getInstance() {
        if (singleInstance == null) {
            singleInstance = new Container<E>();
        }
        return singleInstance;
    }

    public void store() throws PersistenceException {
        if (!(persistenceStrategy instanceof PersistenceStrategy)) {
            throw new PersistenceException(ExceptionType.NoStrategyIsSet, "Persistence strategy was never set.");
        }
        persistenceStrategy.openConnection();
        persistenceStrategy.save(speicher);
        persistenceStrategy.closeConnection();
    }

    public void load(boolean force) throws PersistenceException {
        if (!(persistenceStrategy instanceof PersistenceStrategy)) {
            throw new PersistenceException(ExceptionType.NoStrategyIsSet, "Persistence strategy was never set.");
        }
        persistenceStrategy.openConnection();
        if(force){
            speicher = (ArrayList<E>) this.persistenceStrategy.load();
        }else{
            ArrayList<E> newList = (ArrayList<E>) this.persistenceStrategy.load();
            for (E member : newList) {
                try {
                    this.addMember(member);
                }catch (ContainerException e){}
            }
        }
        persistenceStrategy.closeConnection();
    }
    public void load() throws PersistenceException{
        speicher = (ArrayList<E>) this.persistenceStrategy.load();
    }
    public void addMember(E member) throws ContainerException {
        if (member == null) {// NullPointerException abfangen.
            throw new IllegalArgumentException();
        }
        for (E e : speicher) {
            if(e.getID().equals(member.getID())){// Wenn das Member Element schon im speicher vorhanden ist wird die
                                            // ConatinerException geworfen.
                throw new ContainerException(member.getID());
            }
        }
        speicher.add(member);// Member Element wird zum speicher hinzugefühgt.
    }

    public String deleteMember(Integer id) {
        for (E member : speicher) {
            if (Objects.equals(member.getID(), id)) {// Suche nach dem Element im speicher.
                speicher.remove(member);// Loeschung des Elements aus dem Speicher.
                return String.format("Geloescht:[%s]", member.getID());
            } // eine Exception zu werfen wäre natürlich eindeutiger da sie auch abgefangen
              // werden kann.
        }
        return String.format("Member Element (%d) nicht vorhanden", id);// kein zu löschendes Element im speicher
                                                                        // gefunden.
    }

    public List<E> getCurrentList() {
        return speicher;
    }

    public int size() {// Anzahl der Inhalte im Speicher.
        return speicher.size();
    }
}