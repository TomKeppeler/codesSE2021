package org.hbrs.se1.ws21.uebung4.view;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.hbrs.se1.ws21.uebung4.control.Container;
import org.hbrs.se1.ws21.uebung4.control.Expertise;
import org.hbrs.se1.ws21.uebung4.control.Mitarbeiter;
import org.hbrs.se1.ws21.uebung4.control.MitarbeiterComperator;
import org.hbrs.se1.ws21.uebung4.control.StoredPrintStream;
import org.hbrs.se1.ws21.uebung4.control.exceptions.ContainerException;
import org.hbrs.se1.ws21.uebung4.control.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung4.control.persistence.PersistenceStrategyStream;

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
        String[] userInput;
        do {
            userInput = scan.nextLine().split(" ");
            switch (userInput[0].toLowerCase()) {
            case "enter": {
                int id = 0;
                try {
                    id = Integer.parseInt(userInput[1]);
                } catch (NoSuchElementException e) {
                    stream.println("Es muss eine Zahl angegeben werden.");
                    break;
                }
                String vname = userInput[2];
                String name = userInput[3];
                for (int i = 0; i < 10; i++) {
                    if (vname.contains(i + "") || name.contains(i + "")) {
                        stream.println("Der Name darf keine Zahl enthalten.");
                        break;
                    }
                }
                String rolle = userInput[4];
                String abteil = userInput[5];
                int expertiseLvl = 1;
                try {
                    expertiseLvl = Integer.parseInt(userInput[6]);
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
                String secoundInput = userInput[1];
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
                if (this.speicher.size() < 1) {
                    this.stream.println("Es wurden bisher keine Mitarbeiter eingetragen.");
                } else {
                    stream.println(dump());
                }
                break;
            case "search": {
                String secoundInput = userInput[1].toLowerCase();
                if (this.speicher.size() < 1) {
                    this.stream.println("Es wurden bisher keine Mitarbeiter eingetragen.");
                } else {
                    try {
                        stream.println(dump(Integer.valueOf(secoundInput)));
                    } catch (NumberFormatException e) {
                        stream.println("Es muss nach einer Mitarbeite ID (eine Ganzzahl) gesucht werden.");
                        break;
                    }
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
                "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|",
                "ID", "Vorname", "Nachname", "Rolle", "Abteil",
                "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|");
        this.speicher.getCurrentList().sort(new MitarbeiterComperator());
        for (Mitarbeiter mitarbeiter : this.speicher.getCurrentList()) {
            out += String.format("|%-30s|%-30s|%-30s|%-30s|%-30s|%n%s%n", mitarbeiter.getID(), mitarbeiter.getVorname(),
                    mitarbeiter.getNachname(), mitarbeiter.getRolle(), mitarbeiter.getAbteil(),
                    "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|");
        }
        return out;
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
        return out;
    }

    private String getHelpMassage() {
        String line = "|------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|";
        String out = line + "\n";
        out += String.format(
                        "|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n",
                        "ENTER [ID] [VORNAME] [NACHNAME] [ROLLE] [ABTEIL] [EXPERTISEN-LEVEL(Zahl von 1 bis 3)]",
                        "Neuen Mitarbeiter eintragen", line,
                        "STORE", "Speichert die Eintraege", line,
                         "LOAD [MERGE/FORCE]", "MERGE: fuegt die geladenden eintrage an die vorhanden an. FORCE: Ueberschreibt die Eintraege", line,
                        "DUMP", "Eintraege werden nach ID sortiert ausgegeben (ohne Expertise)", line,
                        "SEARCH [EXPERTISEN-LEVEL(Zahl von 1 bis 3)]", "Ausgabe ist in Einfacher übersicht", line,
                        "EXIT", "Beendet die Anwendung", line,
                        "HELP", "Gibt beschreibung der befehle aus", line);
        return out;
    }
}
