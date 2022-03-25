package org.hbrs.se1.ws21.vorbereitung.ub3_3;
public class HRAdapterImpl {

    private SAPSuccessFactor alt = new SAPSuccessFactor();

    public void addContact(Contact c) throws AddException{
        alt.addContact(this.converIN(c));
    }

    public OData converIN(Contact c){
        return new OData();
    }
}