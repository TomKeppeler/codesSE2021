package org.hbrs.se1.ws21.uebung4.controll.persistence;

import java.util.List;

public class PersistenceStrategyMongoDB<Member> implements PersistenceStrategy<Member> {

    @Override
    public void openConnection() throws PersistenceException {
        throw new java.lang.UnsupportedOperationException("Not implemented!");
    }

    @Override
    public void closeConnection() throws PersistenceException {
        throw new java.lang.UnsupportedOperationException("Not implemented!");
    }

    @Override
    public void save(List<Member> member) {
        throw new java.lang.UnsupportedOperationException("Not implemented!");
    }

    @Override
    public List<Member> load() {
        throw new java.lang.UnsupportedOperationException("Not implemented!");
    }
}
