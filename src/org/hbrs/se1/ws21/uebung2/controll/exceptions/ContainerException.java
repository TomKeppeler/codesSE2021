package org.hbrs.se1.ws21.uebung2.controll.exceptions;

public class ContainerException extends Exception {
    public ContainerException(){
        
    }
    public ContainerException(Integer id){//so kann im Stacktrace die Member id mit ausgegeben werden
        super(String.format("Das Member-Objekt mit der ID %d ist bereits vorhanden!", id));
    }
}
