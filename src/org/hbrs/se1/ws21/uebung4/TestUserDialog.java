package org.hbrs.se1.ws21.uebung4;

import java.util.ArrayList;
import java.util.Scanner;

import org.hbrs.se1.ws21.uebung4.view.UserDialog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestUserDialog {
    UserDialog userDialog;
    ArrayList<String> enterCommands;

    //@BeforeEach
    public void setup(){
        userDialog = new UserDialog();
        enterCommands = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            enterCommands.add(String.format("enter %d Test%d Test%d Test%d Test%d %d\n", i, i, i, i, i, i));
        }
        enterCommands.add("dump\nsave\nexit\n");
    }

    //@Test
    public void enterTest(){
        for (String command : enterCommands) {
            userDialog.startDialog(new Scanner(command));
        }
        userDialog.getStream().getPrintList().forEach(a -> System.out.println(a));
    }
    public static void main(String[] args) {
        TestUserDialog t = new TestUserDialog();
        t.setup();
        t.enterTest();
        //t.enterCommands.forEach(a -> System.out.println(a));
    }
}