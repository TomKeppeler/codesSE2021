package org.hbrs.se1.ws21.uebung4.view;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.hbrs.se1.ws21.uebung4.control.Container;
import org.hbrs.se1.ws21.uebung4.control.Expertise;
import org.hbrs.se1.ws21.uebung4.control.Mitarbeiter;
import org.hbrs.se1.ws21.uebung4.control.MitarbeiterComperator;
import org.hbrs.se1.ws21.uebung4.control.StoredPrintStream;
import org.hbrs.se1.ws21.uebung4.control.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung4.control.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws21.uebung4.control.persistence.exceptions.ContainerException;

public class UserDialog {
    private final Container<Mitarbeiter> speicher;
    private StoredPrintStream stream = new StoredPrintStream(System.out);

    public UserDialog() {
        this.speicher = Container.getInstance();
        this.speicher.setPersistenceStrategy(new PersistenceStrategyStream<>());
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
                } catch (NumberFormatException e) {
                    stream.println("Es muss eine Zahl angegeben werden.");
                    break;
                }

                for (int i = 0; i < 10; i++) {
                    if (userInput[2].contains(i + "") || userInput[3].contains(i + "")) {
                        stream.println("Der Name darf keine Zahl enthalten.");
                        break;
                    }
                }

                if (userInput[5].equals("null")) {
                    userInput[5] = "";
                }
                boolean endeExpEingabe = false;
                int i = 1, lvl = 0;
                String bez = "";
                String antwort = "";
                Expertise expertise = new Expertise();
                stream.println("Wollen sie Expertisen zum Mitarbeiter festlegen?\n('ja' oder 'nein' eingeben)");
                antwort = scan.next();
                if (antwort.equals("nein")) {
                    endeExpEingabe = true;
                }
                while (!endeExpEingabe) {
                    
                    stream.println("Geben Sie einen Expertisen-Bezeichnung an.");
                    bez = scan.next();
                    stream.println("Geben Sie das Level an was der Mitarbeiter in dieser Expertise hat.");
                    try {
                        lvl = scan.nextInt();
                    } catch (Exception e) {
                        stream.println("Es muss eine Zahl angegeben werden.");
                        continue;
                    }
                    if(!(lvl > 0 && lvl < 4)){
                        stream.println("Das Level muss zwischen  1 und 3 sein.");
                        continue;
                    }
                    expertise.setNewExpertise(lvl, bez);
                    i++;
                    if (i > 3) {
                        endeExpEingabe = true;
                    }
                    
                    stream.println("Wollen sie Expertisen zum Mitarbeiter festlegen?\n('ja' oder 'nein' eingeben)");
                    antwort = scan.next();
                    if (antwort.equals("nein")) {
                        endeExpEingabe = true;
                    }
                }

                Mitarbeiter neueMitarbeiter = new Mitarbeiter(id, userInput[2], userInput[3], userInput[4],
                        userInput[5], expertise);
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
                if (this.speicher.size() < 1) {
                    this.stream.println("Es wurden bisher keine Mitarbeiter eingetragen.");
                } else {
                    stream.println(dump(userInput[1].toLowerCase()));
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

    private String dump(String searchKey) {
        String line = "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|";
        String out = String.format("%S%n|%-30S|%-30S|%-30S|%-30S|%-30S|%-30S|%n%s%n", line, "ID", "Vorname", "Nachname",
                "Rolle", "Abteil", "Experties-Level",
                "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|");
        this.speicher.getCurrentList().sort(new MitarbeiterComperator());
        for (Mitarbeiter mitarbeiter : this.speicher.getCurrentList()) {
            for (String expBezeichnung : mitarbeiter.getExpertise()) {
                if (expBezeichnung.equals(searchKey)) {
                    out += String.format("|%-30s|%-30s|%-30s|%-30s|%-30s|%-30s|%n%s%n", mitarbeiter.getID(),
                            mitarbeiter.getVorname(), mitarbeiter.getNachname(), mitarbeiter.getRolle(),
                            mitarbeiter.getAbteil(), mitarbeiter.getExpertise().getExpertisLvl(searchKey), line);
                }
            }

        }
        return out;
    }

    private String getHelpMassage() {
        String line = "|------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|";
        String out = line + "\n";
        out += String.format(
                "|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n",
                
                "ENTER [ID] [VORNAME] [NACHNAME] [ROLLE] [ABTEIL]",
                "Neuen Mitarbeiter eintragen (Falls kein Abteil eingetragen werden soll gib null an).", line,
                "STORE", "Speichert die Eintraege", line, 
                "LOAD [MERGE/FORCE]", "MERGE: fuegt die geladenden eintrage an die vorhanden an. FORCE: Ueberschreibt die Eintraege", line,
                "DUMP", "Eintraege werden nach ID sortiert ausgegeben (ohne Expertise)", line,
                "SEARCH [EXPERTISEN-BEZEICHNUNG]", "Ausgabe ist in Einfacher übersicht", line,
                "EXIT", "Beendet die Anwendung", line,
                "HELP", "Gibt beschreibung der befehle aus", line);
        
        return out;
    }
}
