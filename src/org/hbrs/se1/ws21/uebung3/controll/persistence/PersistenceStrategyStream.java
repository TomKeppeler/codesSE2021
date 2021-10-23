package org.hbrs.se1.ws21.uebung3.controll.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.List;

import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceException.ExceptionType;
import org.junit.jupiter.api.parallel.ExecutionMode;

public class PersistenceStrategyStream<Member> implements PersistenceStrategy<Member> {

    // URL of file, in which the objects are stored
    private String location = "out\\objects.ser";
    private ObjectOutputStream outputFile = null;
    private ObjectInputStream inputFile = null;

    // Backdoor method used only for testing purposes, if the location should be
    // changed in a Unit-Test
    // Example: Location is a directory (Streams do not like directories, so try
    // this out ;-)!
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    /**
     * Method for opening the connection to a stream (here: Input- and
     * Output-Stream) In case of having problems while opening the streams, leave
     * the code in methods load and save
     */
    public void openConnection() throws PersistenceException {
        try {
            outputFile = new ObjectOutputStream(new FileOutputStream(location));
            inputFile = new ObjectInputStream(new FileInputStream(location));
        } catch (IOException e) {
            throw new PersistenceException(ExceptionType.ConnectionNotAvailable, "Incorect file location.");
        }
    }

    @Override
    /**
     * Method for closing the connections to a stream
     */
    public void closeConnection() throws PersistenceException {
        try {
            outputFile.close();
            inputFile.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    /**
     * Method for saving a list of Member-objects to a disk (HDD)
     */
    public void save(List<Member> member) throws PersistenceException {
        try {
            outputFile.writeObject(member);
            outputFile.flush();
        } catch (IOException e) {
            throw new PersistenceException(ExceptionType.ConnectionNotAvailable, "File save error.");
        }
    }

    @Override
    /**
     * Method for loading a list of Member-objects from a disk (HDD) Some coding
     * examples come for free :-) Take also a look at the import statements above
     * ;-!
     */
    public List<Member> load() throws PersistenceException {
        // Some Coding hints ;-)
        // ObjectInputStream ois = null;
        // FileInputStream fis = null;
        // List<...> newListe = null;
        //
        // Initiating the Stream (can also be moved to method openConnection()... ;-)
        // fis = new FileInputStream( " a location to a file" );
        // ois = new ObjectInputStream(fis);

        // Reading and extracting the list (try .. catch ommitted here)
        // Object obj = ois.readObject();

        // if (obj instanceof List<?>) {
        // newListe = (List) obj;
        // return newListe

        // and finally close the streams (guess where this could be...?)
        
        return null;
    }
}
