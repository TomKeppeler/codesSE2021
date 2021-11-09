package org.hbrs.se1.ws21.uebung4.view;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.hbrs.se1.ws21.uebung4.model.Container;
import org.hbrs.se1.ws21.uebung4.model.mitarbeiter.Mitarbeiter;
import org.hbrs.se1.ws21.uebung4.model.mitarbeiter.MitarbeiterComperator;

public class EmployeeView extends PrintStream {

    private ArrayList<String> printList;

    public EmployeeView(PrintStream out) {
        super(out);
        printList = new ArrayList<>();
    }

    public void search(String searchKey, Container<Mitarbeiter> speicher) {
        boolean keyExists = false;
        String line = "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|";
        String out = String.format("%S%n|%-30S|%-30S|%-30S|%-30S|%-30S|%-30S|%n%s%n", line, "ID", "Vorname", "Nachname",
                "Rolle", "Abteil", "Experties-Level",
                "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|");
        speicher.getCurrentList().sort(new MitarbeiterComperator());
        for (Mitarbeiter mitarbeiter : speicher.getCurrentList()) {
            for (String expBezeichnung : mitarbeiter.getExpertise()) {
                if (expBezeichnung.equals(searchKey)) {
                    out += String.format("|%-30s|%-30s|%-30s|%-30s|%-30s|%-30s|%n%s%n", mitarbeiter.getID(),
                            mitarbeiter.getVorname(), mitarbeiter.getNachname(), mitarbeiter.getRolle(),
                            mitarbeiter.getAbteil(), mitarbeiter.getExpertise().getExpertisLvl(searchKey), line);
                    keyExists = true;
                }
            }

        }
        if (keyExists) {
            this.println(out);
        } else {
            this.println(String.format("Es gibt die Suche %s nicht", searchKey));
        }
    }

    public void dump(Container<Mitarbeiter> speicher, String searchKey, String coloumn) {
        switch (coloumn.toLowerCase()) {
        case "abteil": {
            ArrayList<Mitarbeiter> ret = new ArrayList<>();
            speicher.getCurrentList().sort(new MitarbeiterComperator());
            for (Mitarbeiter mitarbeiter : speicher.getCurrentList()) {
                if (mitarbeiter.getAbteil().equalsIgnoreCase(searchKey)) {
                    ret.add(mitarbeiter);
                }
            }
            this.println(this.getTable(ret));
        }
            break;
        case "rolle": {
            ArrayList<Mitarbeiter> ret = new ArrayList<>();
            speicher.getCurrentList().sort(new MitarbeiterComperator());
            for (Mitarbeiter mitarbeiter : speicher.getCurrentList()) {
                if (mitarbeiter.getRolle().equalsIgnoreCase(searchKey)) {
                    ret.add(mitarbeiter);
                }
            }
            this.println(this.getTable(ret));
        }
            break;
        case "nachname": {
            ArrayList<Mitarbeiter> ret = new ArrayList<>();
            speicher.getCurrentList().sort(new MitarbeiterComperator());
            for (Mitarbeiter mitarbeiter : speicher.getCurrentList()) {
                if (mitarbeiter.getNachname().equalsIgnoreCase(searchKey)) {
                    ret.add(mitarbeiter);
                }
            }
            this.println(this.getTable(ret));
        }
            break;
        case "vorname": {
            ArrayList<Mitarbeiter> ret = new ArrayList<>();
            speicher.getCurrentList().sort(new MitarbeiterComperator());
            for (Mitarbeiter mitarbeiter : speicher.getCurrentList()) {
                if (mitarbeiter.getVorname().equalsIgnoreCase(searchKey)) {
                    ret.add(mitarbeiter);
                }
            }
            this.println(this.getTable(ret));
        }
            break;
        case "id": {
            ArrayList<Mitarbeiter> ret = new ArrayList<>();
            speicher.getCurrentList().sort(new MitarbeiterComperator());
            try {
                for (Mitarbeiter mitarbeiter : speicher.getCurrentList()) {
                    if (mitarbeiter.getID() == Integer.parseInt(searchKey)) {
                        ret.add(mitarbeiter);
                    }
                }
            } catch (NumberFormatException e) {
                this.println("ID muss eine Ganzzahl sein");
                break;
            }
            this.println(this.getTable(ret));
        }

            break;
        default:
            this.println("");
            break;
        }
        speicher.getCurrentList().sort(new MitarbeiterComperator());
        this.println(this.getTable((ArrayList<Mitarbeiter>) speicher.getCurrentList()));
    }

    private String getTable(ArrayList<Mitarbeiter> list) {
        String out = String.format("%S%n|%-30S|%-30S|%-30S|%-30S|%-30S|%n%s%n",
                "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|",
                "ID", "Vorname", "Nachname", "Rolle", "Abteil",
                "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|");
        for (Mitarbeiter mitarbeiter : list) {
            out += String.format("|%-30s|%-30s|%-30s|%-30s|%-30s|%n%s%n", mitarbeiter.getID(), mitarbeiter.getVorname(),
                    mitarbeiter.getNachname(), mitarbeiter.getRolle(), mitarbeiter.getAbteil(),
                    "|------------------------------|------------------------------|------------------------------|------------------------------|------------------------------|");
        }
        return out;
    }

    public void dump(Container<Mitarbeiter> speicher) {
        speicher.getCurrentList().sort(new MitarbeiterComperator());
        this.println(this.getTable((ArrayList<Mitarbeiter>) speicher.getCurrentList()));
    }

    public void getHelpMessage() {
        String line = "|------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|";
        String out = line + "\n";
        out += String.format(
                "|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n|%-90s|%-100s|%n%s%n",

                "ENTER [ID] [VORNAME] [NACHNAME] [ROLLE] [ABTEIL]",
                "Neuen Mitarbeiter eintragen (Falls kein Abteil eingetragen werden soll gib null an).", line, "STORE",
                "Speichert die Eintraege", line, "LOAD [MERGE/FORCE]",
                "MERGE: fuegt die geladenden eintrage an die vorhanden an. FORCE: Ueberschreibt die Eintraege", line,
                "DUMP [SPALTE] [SUCHEWORT]", "Eintraege werden nach ID sortiert ausgegeben (ohne Expertise)", line,
                "SEARCH [EXPERTISEN-BEZEICHNUNG]", "Ausgabe ist in Einfacher übersicht", line, "EXIT",
                "Beendet die Anwendung", line, "HELP", "Gibt beschreibung der befehle aus", line);

        this.println(out);
    }

    @Override
    public void println(String line) {
        printList.add(line);
        super.println(line);
    }

    @Override
    public void print(String line) {
        printList.add(line);
        super.print(line);
    }

    public List<String> getPrintList() {
        return printList;
    }
}
