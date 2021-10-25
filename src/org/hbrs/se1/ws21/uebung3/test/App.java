package org.hbrs.se1.ws21.uebung3.test;

import org.hbrs.se1.ws21.uebung2.controll.exceptions.ContainerException;
import org.hbrs.se1.ws21.uebung3.controll.Container;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws21.uebung3.view.AutoMitMember;

public class App {
    public static void main(String[] args) {
        Container c = Container.getInstance();
        c.setPersistenceStrategy(new PersistenceStrategyStream());
        for (int i = 0; i < 100; i++) {
            try {
                c.addMember(new AutoMitMember());
            } catch (ContainerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            c.store();
            System.out.println(c.size());
        } catch (PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    } 
}
