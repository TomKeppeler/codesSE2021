package org.hbrs.se1.ws21.uebung4.test;

import java.util.Scanner;

import org.hbrs.se1.ws21.uebung4.controller.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestUserDialog {
    InputDialog userDialog = new InputDialog();
    String enterCommands;

    // @BeforeEach
    public void setup() {

    }

    public void helpTest() {
        userDialog.startDialog(new Scanner("help\nexit\n"));
    }

    // @Test
    public void enterDumpStoreTest() {
        enterCommands = "";
        for (int i = 1; i <= 100; i++) {
            enterCommands += String.format("enter %d Test Test Test%d Test%d%nnein%n", i,
                    /* t.translateNumber(i), t.translateNumber(i), */i, i);
        }
        enterCommands += "dump\nstore\nexit\n";
        userDialog.startDialog(new Scanner(enterCommands));
    }

    public void enterDumpStoreLoadForceTest() {
        enterCommands = "";
        for (int i = 1; i <= 100; i++) {
            enterCommands += String.format("load force%nenter %d Test Test Test%d Test%d%nnein%n", i+200,
                    /* t.translateNumber(i), t.translateNumber(i), */i, i);
        }
        enterCommands += "dump\nstore\nexit\n";
        userDialog.startDialog(new Scanner(enterCommands));
    }
    
    public void enterDumpStoreLoadMergeTest() {
        enterCommands = "";
        for (int i = 1; i <= 100; i++) {
            enterCommands += String.format("load merge%nenter %d Test Test Test%d Test%d%nnein%n", i+100,
                    /* t.translateNumber(i), t.translateNumber(i), */i+100, i+100);
        }
        enterCommands += "dump\nstore\nexit\n";
        userDialog.startDialog(new Scanner(enterCommands));
    }

    public void searchTest() {
        enterCommands = "";
        for (int i = 1; i <= 10; i++) {
            enterCommands += String.format("enter %d Test Test Test%d Test%d%nja%njava%n2%nnein%n", i,
                    /* t.translateNumber(i), t.translateNumber(i), */i, i);
        }
        enterCommands += "search java\nexit\n";
        userDialog.startDialog(new Scanner(enterCommands));
    }

    public void dumpSearchTest() {
        enterCommands = "";
        for (int i = 1; i <= 10; i++) {
            enterCommands += String.format("enter %d Test Test Test Test%d%nja%njava%n2%nnein%n", i,
                    /* t.translateNumber(i), t.translateNumber(i), */i);
        }
        enterCommands += "dump Test\nexit\n";
        userDialog.startDialog(new Scanner(enterCommands));
    }

    public static void main(String[] args) {
        TestUserDialog t = new TestUserDialog();
         //t.helpTest();
         //t.searchTest();
         //t.enterDumpStoreLoadForceTest();
         //t.enterDumpStoreLoadMergeTest();
    }
}