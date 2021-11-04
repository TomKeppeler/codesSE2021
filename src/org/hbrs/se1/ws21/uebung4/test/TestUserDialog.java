package org.hbrs.se1.ws21.uebung4.test;

import java.util.Scanner;

import org.hbrs.se1.ws21.uebung1.control.GermanTranslator;
import org.hbrs.se1.ws21.uebung1.control.Translator;
import org.hbrs.se1.ws21.uebung4.view.UserDialog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestUserDialog {
    UserDialog userDialog = new UserDialog();
    String enterCommands;

    //@BeforeEach
    public void setup(){

    }
    public void helpTest(){
        userDialog.startDialog(new Scanner("help\nexit\n"));
    }
    //@Test
    public void enterTest(){
        enterCommands = "";
        Translator t = new GermanTranslator();
        for (int i = 1; i <= 100; i++) {
            enterCommands += String.format("enter %d Test Test Test%d Test%d %d%n", i,
                    /* t.translateNumber(i), t.translateNumber(i), */i, i, 2);
        }
        enterCommands += "dump\nstore\nexit\n";
        userDialog.startDialog(new Scanner(enterCommands));
        //userDialog.getStream().getPrintList().forEach(a -> System.out.println(a));
    }
    public static void main(String[] args) {
        TestUserDialog t = new TestUserDialog();
        //t.enterTest();
        t.helpTest();
    }
}