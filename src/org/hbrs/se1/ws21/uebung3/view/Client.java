package org.hbrs.se1.ws21.uebung3.view;

import org.hbrs.se1.ws21.uebung2.control.exceptions.ContainerException;
import org.hbrs.se1.ws21.uebung3.control.Container;

public class Client {
    public static void main(String[] args) {
        Container c = Container.getInstance();
        for (int i = 0; i < 200; i++) {
            try {
                c.addMember(new AutoMitMember());
            } catch (ContainerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        MemberView.dump(c.getCurrentList());
    }
}
