package org.hbrs.se1.ws21.uebung4;

import java.util.ArrayList;
import java.util.Scanner;

import org.hbrs.se1.ws21.uebung1.control.GermanTranslator;
import org.hbrs.se1.ws21.uebung1.control.Translator;
import org.hbrs.se1.ws21.uebung4.view.UserDialog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestUserDialog {
    UserDialog userDialog;
    String enterCommands;

    //@BeforeEach
    public void setup(){
        userDialog = new UserDialog();
        enterCommands = "";
        Translator t = new GermanTranslator();
        for (int i = 1; i <= 10; i++) {
            enterCommands += String.format("enter %d Test Test Test%d Test%d %d%n", i, /*t.translateNumber(i), t.translateNumber(i),*/i, i, 2);
        }
        enterCommands += "dump\nstore\nexit\n";
    }

    //@Test
    public void enterTest(){
        userDialog.startDialog(new Scanner(enterCommands));
        //userDialog.getStream().getPrintList().forEach(a -> System.out.println(a));
    }
    public static void main(String[] args) {
        TestUserDialog t = new TestUserDialog();
        t.setup();
        t.enterTest();
    }
}