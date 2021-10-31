package org.hbrs.se1.ws21.uebung4;

import java.util.ArrayList;
import java.util.Scanner;

import org.hbrs.se1.ws21.uebung4.view.UserDialog;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestUserDialog {
    UserDialog userDialog;
    ArrayList<String> enterCommands;

    @BeforeEach
    public void setup(){
        userDialog = new UserDialog();
        enterCommands = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            enterCommands.add(String.format("enter %d Test%d Test%d Test%d Test%d %d ", i, i, i, i, i, i));
        }
        enterCommands.add("save");
        enterCommands.add("exit");
    }

    @Test
    public void enterTest(){
        enterCommands.forEach(a -> userDialog.startDialog(new Scanner(a)));
    }
}
