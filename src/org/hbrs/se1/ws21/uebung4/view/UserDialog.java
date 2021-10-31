package org.hbrs.se1.ws21.uebung4.view;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.hbrs.se1.ws21.uebung4.controll.Container;
import org.hbrs.se1.ws21.uebung4.controll.Mitarbeiter;
import org.hbrs.se1.ws21.uebung4.controll.MitarbeiterComperator;
import org.hbrs.se1.ws21.uebung4.controll.exceptions.ContainerException;
import org.hbrs.se1.ws21.uebung4.controll.persistence.PersistenceException;

public class UserDialog {
    private final Container<Mitarbeiter> speicher;

    public UserDialog() {
        this.speicher = Container.getInstance();
    }

    public Container<Mitarbeiter> getSpeicher() {
        return speicher;
    }

    public void startDialog(Scanner scan) {
        boolean ende = false;
        String userInput = "";
        do {
            userInput = scan.next().toLowerCase();
            switch (userInput) {
            case "enter": {
                int id = 0;
                try {
                    id = scan.nextInt();
                } catch (NoSuchElementException e) {
                    System.out.println("Es muss eine Zahl angegeben werden.");
                    break;
                }
                String vname = scan.next();
                String name = scan.next();
                for (int i = 0; i < 10; i++) {
                    if (vname.contains(i + "") || name.contains(i + "")) {
                        System.out.println("Der Name darf keine Zahl enthalten.");
                        break;
                    }
                }
                String rolle = scan.next();
                String abteil = scan.next();
                int expertiseLvl = 1;
                try {
                    expertiseLvl = scan.nextInt();
                } catch (NoSuchElementException e) {
                    System.out.println("Es muss eine Zahl zwischen 1 und 3 angegeben werden.");
                    break;
                }
                Mitarbeiter neueMitarbeiter = new Mitarbeiter(id, vname, name, rolle, abteil, expertiseLvl);
                try {
                    speicher.addMember(neueMitarbeiter);
                } catch (ContainerException e) {
                    System.out.println("Doppelte ID ist nicht erlaubt");
                    break;
                }
                System.out
                        .println(String.format("Mitarbeiter erfolgreich gespeichert.\n%s", neueMitarbeiter.toString()));
            }
                break;
            case "store":
                try {
                    this.speicher.store();
                } catch (PersistenceException e) {
                    System.out.println("Beim Speichern ist ein Fehler aufgetreten.");
                    break;
                }
                System.out.println("Erfolgreich gespeichert.");
                break;
            case "load": {
                String secoundInput = scan.next().toLowerCase();
                if (secoundInput.equals("merge")) {
                    try {
                        this.speicher.load(false);
                    } catch (PersistenceException e) {
                        System.out.println("Laden nicht erfolgreich");
                        break;
                    }
                } else if (secoundInput.equals("force")) {
                    try {
                        this.speicher.load(true);
                    } catch (PersistenceException e) {
                        System.out.println("Laden nicht erfolgreich");
                        break;
                    }
                } else {
                    System.out.println("Falsche Eingabe: " + secoundInput);
                }
                System.out.println(String.format("Erfolgreich geladen. Mit dem %s modus", secoundInput));
            }
                break;
            case "dump":
                System.out.println(dump());
                break;
            case "search": {
                String secoundInput = scan.next().toLowerCase();
                try {
                    System.out.println(dump(Integer.valueOf(secoundInput)));
                } catch (NumberFormatException e) {
                    System.out.println("Es muss nach einer Mitarbeite ID (eine Ganzzahl) gesucht werden.");
                    break;
                }
            }
                break;
            case "exit":
                ende = true;
                break;
            case "help":
                System.out.println(getHelpMassage());
                break;
            default:
                System.out.println("Es muss ein gültige eingabe angegben werden.");
            }
        } while (!ende);
        scan.close();
    }

    private String dump() {
        String out = "";
        this.speicher.getCurrentList().sort(new MitarbeiterComperator());
        for (Mitarbeiter mitarbeiter : this.speicher.getCurrentList()) {
            out += String.format(
                    "ID\t\tVorname\t\t\tNachname\t\tRolle\t\t\tAbteil%n%d\t\t%s\t\t\t%s\t\t%s\t\t\t%s%n---------------------------------------------------------------------------------------------------%n",
                    mitarbeiter.getID(), mitarbeiter.getVorname(), mitarbeiter.getNachname(), mitarbeiter.getRolle(),
                    mitarbeiter.getAbteil());
        }
        return out.equals("") ? "Es wurden bisher keine Mitarbeiter eingetragen." : out;
    }

    private String dump(int searchKey) {
        String out = "";
        this.speicher.getCurrentList().sort(new MitarbeiterComperator());
        for (Mitarbeiter mitarbeiter : this.speicher.getCurrentList()) {
            if (mitarbeiter.getExperLvl() == searchKey) {
                out += String.format(
                        "ID\t\tVorname\t\t\tNachname\t\tRolle\t\t\tAbteil\t\t\tExperties-Level%n%d\t\t%s\t\t\t%s\t\t%s\t\t\t%s\t\t%s%n--------------------------------------------------------------------------------------------------------------------------------------------------%n",
                        mitarbeiter.getID(), mitarbeiter.getVorname(), mitarbeiter.getNachname(),
                        mitarbeiter.getRolle(), mitarbeiter.getAbteil(), mitarbeiter.getExperLvl());
            }
        }
        return out.equals("") ? String.format("Expertise-Level %d nicht vorhanden.", searchKey) : out;
    }

    private String getHelpMassage() {
        return """
                    ENTER [ID] [VORNAME] [NACHNAME] [ROLLE] [ABTEIL] [EXPERTISEN-LEVEL(Zahl von 1 bis 3)]\tNeuen Mitarbeiter eintragen

                    STORE\t\t\t\t\t\t\t\t\t\t\tSpeichert die Eintraege

                    LOAD [MERGE/FORCE]\t\t\t\t\t\t\t\t\t\tMERGE: fuegt die geladenden eintrage an die vorhanden an. FORCE: Ueberschreibt die Eintraege

                    DUMP\t\t\t\t\t\t\t\t\t\t\tEintraege werden nach ID sortiert ausgegeben (ohne Expertise)

                    SEARCH [EXPERTISEN-LEVEL(Zahl von 1 bis 3)]\t\t\t\t\t\t\tAusgabe ist in Einfacher übersicht

                    EXIT\t\t\t\t\t\t\t\t\t\t\tBeendet die Anwendung

                    HELP\t\t\t\t\t\t\t\t\t\t\tGibt beschreibung der befehle aus
                """;
    }
}
