package org.hbrs.se1.ws21.uebung3.test;

import org.hbrs.se1.ws21.uebung2.controll.exceptions.ContainerException;
import org.hbrs.se1.ws21.uebung3.controll.Container;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung3.view.*;
public class App {
    public static void main(String[] args) {
       // testStore();
        testload();
       
    }
    private static void testload(){
        Container containerInstanc = Container.getInstance();
        try {
            containerInstanc.load();
        } catch (PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(containerInstanc.size());
    }
    private static void testStore(){
        Container containerInstanc = Container.getInstance();
        try {
            for (int i = 0; i < 100; i++) {
                containerInstanc.addMember(new AutoMitMember(2, 4, "Audi", "A3", "Max Mustermann", 110.4));
                containerInstanc.addMember(new AutoMitMember(2, 4, "Fiat", "500", "Lisa Mustermann", 80.47));
            }
        } catch (ContainerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            containerInstanc.store();
        } catch (PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
