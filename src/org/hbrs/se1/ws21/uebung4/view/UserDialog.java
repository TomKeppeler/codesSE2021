package org.hbrs.se1.ws21.uebung4.view;

import java.io.InputStream;
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

    public void startDialog(InputStream in) {
        Scanner scan = new Scanner(in);
        boolean ende = false;
        String userInput = "";
        System.out.println("Eingabe der Mitarbeiter:");
        do {
            userInput = scan.next().toLowerCase();
            switch (userInput) {
            case "enter":
                {   
                    
                    try{
                        Integer.valueOf(i);
                    }catch(NumberFormatException e){
                        e.printStackTrace();
                    }
                }
                // ENTER [ID] [VORNAME] [NACHNAME] [ROLLE] [ABTEIL] [EXPERTISEN-LEVEL(Zahl von 1
                // bis 3)]
                break;
            case "store":
                try {
                    this.speicher.store();
                } catch (PersistenceException e) {
                    e.printStackTrace();
                }
                break;
            case "load": {
                String secoundInput = scan.next().toLowerCase();
                if (secoundInput.equals("merge")) {
                    try {
                        this.speicher.load(false);
                    } catch (PersistenceException e) {
                        e.printStackTrace();
                    }
                } else if (secoundInput.equals("force")) {
                    try {
                        this.speicher.load(true);
                    } catch (PersistenceException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Falsche Eingabe: " + secoundInput);
                }
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
                    e.printStackTrace();
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
                scan.close();
                throw new IllegalArgumentException();
            }
        } while (!ende);
        scan.close();
        System.out.println("Eingabe der Mitarbeiter Beendet");
    }

    private String dump() {
        String out = "";
        this.speicher.getCurrentList().sort(new MitarbeiterComperator());
        for (Mitarbeiter mitarbeiter : this.speicher.getCurrentList()) {
            out += String.format(
                    "ID\tVorname\t\tNachname\tRolle\tAbteil\tExperties-Level%n%d\t%s\t%s\t%s\t%s\t%s%n-------------------------------------------------------------------------%n",
                    mitarbeiter.getID(), mitarbeiter.getVorname(), mitarbeiter.getNachname(), mitarbeiter.getRolle(),
                    mitarbeiter.getAbteil(), mitarbeiter.getExperLvl());
        }
        return out;
    }

    private String dump(int searchKey) {
        String out = "";
        this.speicher.getCurrentList().sort(new MitarbeiterComperator());
        for (Mitarbeiter mitarbeiter : this.speicher.getCurrentList()) {
            if (mitarbeiter.getExperLvl() == searchKey) {
                out += String.format(
                        "ID\tVorname\t\tNachname\tRolle\tAbteil\tExperties-Level%n%d\t%s\t%s\t%s\t%s\t%s%n-------------------------------------------------------------------------%n",
                        mitarbeiter.getID(), mitarbeiter.getVorname(), mitarbeiter.getNachname(),
                        mitarbeiter.getRolle(), mitarbeiter.getAbteil(), mitarbeiter.getExperLvl());
            }
        }
        return out;
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

    public static void main(String[] args) {
        UserDialog ud = new UserDialog();
        for (int i = 0; i < 10; i++) {
            try {
                ud.speicher.addMember(new Mitarbeiter(i, "Vorname" + i, "Nachname" + i, "Rolle" + i, "Abteil" + i, 1));
            } catch (ContainerException e) {
                e.printStackTrace();
            }
        }
        System.out.println(ud.dump());
    }
}
