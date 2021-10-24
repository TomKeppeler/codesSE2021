package org.hbrs.se1.ws21.uebung3.controll.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.List;

import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceException.ExceptionType;

public class PersistenceStrategyStream<Member> implements PersistenceStrategy<Member> {

    // URL of file, in which the objects are stored
    private String location = "out\\objects.ser";
    private ObjectOutputStream outputFile = null;
    private ObjectInputStream inputFile = null;

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
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
            throw new PersistenceException(ExceptionType.ConnectionNotAvailable, "File save error.");
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
    public List<Member> load() throws PersistenceException {
        try{
            return (List<Member>)inputFile.readObject();
        }catch(IOException | ClassNotFoundException cfe){
            throw new PersistenceException(ExceptionType.ConnectionNotAvailable, "File load error.");
        }
    }
}
