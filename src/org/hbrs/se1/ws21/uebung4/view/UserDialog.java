package org.hbrs.se1.ws21.uebung4.view;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.hbrs.se1.ws21.uebung4.controll.Container;
import org.hbrs.se1.ws21.uebung4.controll.Expertise;
import org.hbrs.se1.ws21.uebung4.controll.Mitarbeiter;
import org.hbrs.se1.ws21.uebung4.controll.MitarbeiterComperator;
import org.hbrs.se1.ws21.uebung4.controll.StoredPrintStream;
import org.hbrs.se1.ws21.uebung4.controll.exceptions.ContainerException;
import org.hbrs.se1.ws21.uebung4.controll.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung4.controll.persistence.PersistenceStrategyStream;

public class UserDialog {
    private final Container<Mitarbeiter> speicher;
    private StoredPrintStream stream = new StoredPrintStream(System.out);
    private Expertise expertise;
    public UserDialog() {
        this.speicher = Container.getInstance();
        this.speicher.setPersistenceStrategy(new PersistenceStrategyStream<>());
        this.expertise = new Expertise();
        this.expertise.setNewExpertise(1, "Beginner");
        this.expertise.setNewExpertise(2, "Experte");
        this.expertise.setNewExpertise(3, "Top-Performer");
    }

    public StoredPrintStream getStream() {
        return stream;
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
                    stream.println("Es muss eine Zahl angegeben werden.");
                    break;
                }
                String vname = scan.next();
                String name = scan.next();
                for (int i = 0; i < 10; i++) {
                    if (vname.contains(i + "") || name.contains(i + "")) {
                        stream.println("Der Name darf keine Zahl enthalten.");
                        break;
                    }
                }
                String rolle = scan.next();
                String abteil = scan.next();
                int expertiseLvl = 1;
                try {
                    expertiseLvl = scan.nextInt();
                    if (!(expertiseLvl > 0 && expertiseLvl < 4)) {
                        stream.println("Es muss eine Zahl zwischen 1 und 3 angegeben werden.");
                        break;
                    }
                } catch (NoSuchElementException e) {
                    stream.println("Es muss eine Zahl angegeben werden.");
                    break;
                }
                Mitarbeiter neueMitarbeiter = new Mitarbeiter(id, vname, name, rolle, abteil, expertiseLvl);
                try {
                    speicher.addMember(neueMitarbeiter);
                } catch (ContainerException e) {
                    stream.println("ID schon vorhanden.");
                    break;
                }
                stream.println(String.format("Mitarbeiter erfolgreich gespeichert.\n%s", neueMitarbeiter.toString()));
            }
                break;
            case "store":
                try {
                    this.speicher.store();
                } catch (PersistenceException e) {
                    stream.println("Beim Speichern ist ein Fehler aufgetreten.");
                    break;
                }
                stream.println("Erfolgreich gespeichert.");
                break;
            case "load": {
                String secoundInput = scan.next().toLowerCase();
                if (secoundInput.equals("merge")) {
                    try {
                        this.speicher.load(false);
                    } catch (PersistenceException e) {
                        stream.println("Laden nicht erfolgreich");
                        break;
                    }
                } else if (secoundInput.equals("force")) {
                    try {
                        this.speicher.load(true);
                    } catch (PersistenceException e) {
                        stream.println("Laden nicht erfolgreich");
                        break;
                    }
                } else {
                    stream.println("Falsche Eingabe: " + secoundInput);
                    break;
                }
                stream.println(String.format("Erfolgreich geladen. Mit dem %s modus", secoundInput));
            }
                break;
            case "dump":
                stream.println(dump());
                break;
            case "search": {
                String secoundInput = scan.next().toLowerCase();
                try {
                    stream.println(dump(Integer.valueOf(secoundInput)));
                } catch (NumberFormatException e) {
                    stream.println("Es muss nach einer Mitarbeite ID (eine Ganzzahl) gesucht werden.");
                    break;
                }
            }
                break;
            case "exit":
                ende = true;
                break;
            case "help":
                stream.println(getHelpMassage());
                break;
            default:
                stream.println("Es muss ein gültige eingabe angegben werden.");
            }
        } while (!ende);
        scan.close();
    }

    private String dump() {
        String out = String.format("%S%n|%-30S|%-30S|%-30S|%-30S|%-30S|%n%s%n",
                    "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|", "ID", "Vorname", "Nachname",
                    "Rolle", "Abteil",
                    "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|");
        this.speicher.getCurrentList().sort(new MitarbeiterComperator());
        for (Mitarbeiter mitarbeiter : this.speicher.getCurrentList()) {
            out += String.format("|%-30s|%-30s|%-30s|%-30s|%-30s|%n%s%n", mitarbeiter.getID(), mitarbeiter.getVorname(), mitarbeiter.getNachname(),
                    mitarbeiter.getRolle(), 
                    mitarbeiter.getAbteil(),
                    "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|");
        }
        return out.equals("") ? "Es wurden bisher keine Mitarbeiter eingetragen." : out;
    }

    private String dump(int searchKey) {
        String out = String.format("%S%n|%-30S|%-30S|%-30S|%-30S|%-30S|%-30S|%n%s%n",
                "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|",
                "ID", "Vorname", "Nachname", "Rolle", "Abteil", "Experties-Level",
                "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|");
        this.speicher.getCurrentList().sort(new MitarbeiterComperator());

        for (Mitarbeiter mitarbeiter : this.speicher.getCurrentList()) {
            if (mitarbeiter.getExperLvl() == searchKey) {
                out += String.format("|%-30s|%-30s|%-30s|%-30s|%-30s|%-30s|%n%s%n", mitarbeiter.getID(),
                        mitarbeiter.getVorname(), mitarbeiter.getNachname(), mitarbeiter.getRolle(),
                        mitarbeiter.getAbteil(), expertise.getExpertisName(mitarbeiter.getExperLvl()),
                        "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|");
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
