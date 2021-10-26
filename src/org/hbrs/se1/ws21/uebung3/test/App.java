package org.hbrs.se1.ws21.uebung3.test;

import org.hbrs.se1.ws21.uebung2.controll.exceptions.ContainerException;
import org.hbrs.se1.ws21.uebung3.controll.Container;
import org.hbrs.se1.ws21.uebung3.controll.Member;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung3.controll.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws21.uebung3.view.AutoMitMember;

public class App {
    public static void main(String[] args) {
        Member[] m = new Member[100];
        for (int i = 0; i < m.length; i++) {
            m[i] = new AutoMitMember(2, 4, "Audi", "A3", "Max Mustermann", 110.4);
        }
        Container c = Container.getInstance();
        c.setPersistenceStrategy(new PersistenceStrategyStream());
        for (int i = 0; i < 100; i++) {
            try {
                c.addMember(m[i]);
            } catch (ContainerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            c.store();
            System.out.println(c.size());
            c.load();
            System.out.println(c.size());
        } catch (PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) {
            c.deleteMember(m[i].getID());
        }
        System.out.println("Größe nach Löschung von allen Elementen: " + c.size());
        try {
            c.store();
            c.load();
            System.out.println("Größe nach erneuten laden: " + c.size());
        } catch (PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } 
}
