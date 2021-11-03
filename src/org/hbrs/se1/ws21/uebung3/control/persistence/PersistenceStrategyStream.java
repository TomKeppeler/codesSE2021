package org.hbrs.se1.ws21.uebung3.control.persistence;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hbrs.se1.ws21.uebung3.control.Member;
import org.hbrs.se1.ws21.uebung3.control.persistence.PersistenceException.ExceptionType;

public class PersistenceStrategyStream implements PersistenceStrategy<Member> {

    // URL of file, in which the objects are stored
    private String location = "objects.ser";

    /*
     * private ObjectOutputStream outputFile = null; private ObjectInputStream
     * inputFile = null; 
     * private FileInputStream fis = null;
     * 
     * private ByteArrayOutputStream baos = null;
     */
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void openConnection() throws PersistenceException {
        /*
         * if (!connection) { try { File localFile = new File(location);
         * if(!localFile.exists()){ ObjectOutputStream oos = new ObjectOutputStream(new
         * FileOutputStream(localFile)); oos.writeObject(new ArrayList<>());
         * oos.close(); } baos = new ByteArrayOutputStream(); outputFile = new
         * ObjectOutputStream(baos); fis = new FileInputStream(localFile); inputFile =
         * new ObjectInputStream(fis); } catch (IOException e) { throw new
         * PersistenceException(ExceptionType.ConnectionNotAvailable,
         * "Incorect file location."); } connection = true; }
         */
    }

    @Override
    public void closeConnection() throws PersistenceException {
        /*
         * if (connection) { try { outputFile.close(); baos.close(); inputFile.close();
         * fis.close(); } catch (IOException e) { throw new
         * PersistenceException(ExceptionType.ConnectionNotAvailable,
         * "File save error."); } connection = false; }
         */
    }

    @Override
    public void save(List<Member> member) throws PersistenceException {
        try {
            FileOutputStream fos = new FileOutputStream(location);
            ObjectOutputStream outputFile = new ObjectOutputStream(fos);
            outputFile.writeObject(member);
            outputFile.flush();
            outputFile.close();
            fos.close();
        } catch (IOException e) {
            throw new PersistenceException(ExceptionType.ConnectionNotAvailable, e.getMessage());
        }
    }

    @Override
    public List<Member> load() throws PersistenceException {
        try {
            FileInputStream fis = new FileInputStream(location);
            ObjectInputStream inputFile = new ObjectInputStream(fis);
            Object obj = inputFile.readObject();
            inputFile.close();
            fis.close();
            if (obj instanceof List<?>) {
                return (List<Member>) obj;
            }
        } catch (IOException | ClassNotFoundException cfe) {
            throw new PersistenceException(ExceptionType.ConnectionNotAvailable, cfe.getMessage());
        }
        return new ArrayList<>();
    }
}
