package org.hbrs.se1.ws21.uebung3.controll;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hbrs.se1.ws21.uebung2.controll.exceptions.ContainerException;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceException.ExceptionType;

public class Container {
    private static Container singleInstance = null;
    private ArrayList<Member> speicher; // Im speicher werden die Member Elemente gespeichert.
    private String fileLocation;

    private Container() { // Speicher wird erst ab aufruf des Konstruktor initialisieren .
        speicher = new ArrayList<>();
        this.fileLocation = "out\\ContainerData";
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public static Container getInstance() {
        if (singleInstance == null) {
            singleInstance = new Container();
        }
        return singleInstance;
    }

    public void store() throws PersistenceException {
        if (speicher == null) {
            throw new IllegalArgumentException();
        }
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(speicher);
            oos.flush();
            fos.close();
            oos.close();
        } catch (IOException e) {
            throw new PersistenceException(ExceptionType.ConnectionNotAvailable,
                    String.format("Location was not found. At %s", fileLocation));
        }
    }

    public void load() throws PersistenceException {
        FileInputStream fis;
        ObjectInputStream ois;
        Object fileContent;
        try {
            fis = new FileInputStream(fileLocation);
            ois = new ObjectInputStream(fis);
            fileContent = ois.readObject();
            fis.close();
            ois.close();
        } catch (FileNotFoundException e) {
            throw new PersistenceException(ExceptionType.ConnectionNotAvailable, "File was not found");
        } catch (IOException ioe) {
            throw new PersistenceException(ExceptionType.ConnectionNotAvailable, "File not readable");
        } catch (ClassNotFoundException e) {
            throw new PersistenceException(ExceptionType.ConnectionNotAvailable, "Class not readable");
        }

        if (fileContent instanceof List) {
            speicher = (ArrayList<Member>) fileContent;
        } else {
            throw new PersistenceException(ExceptionType.ConnectionNotAvailable, "Not a Class of LinkedList<Member>");
        }

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
