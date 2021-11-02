package org.hbrs.se1.ws21.uebung4.view;

import java.util.Scanner;

public class ScrumPlanung {
    public static void main(String[] args) {
        UserDialog userDialog = new UserDialog();
        System.out.println("Eingabe der Mitarbeiter:");
        userDialog.startDialog(new Scanner(System.in));
        System.out.println("Eingabe der Mitarbeiter Beendet");
        /*
         * enter 1 NeuTest NeuTest NeuTest NeuTest 2
         */
    }
}