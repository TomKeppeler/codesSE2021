package org.hbrs.se1.ws21.uebung4.controller;

import java.util.Scanner;

import org.hbrs.se1.ws21.uebung4.model.Container;
import org.hbrs.se1.ws21.uebung4.model.exceptions.ContainerException;
import org.hbrs.se1.ws21.uebung4.model.mitarbeiter.*;
import org.hbrs.se1.ws21.uebung4.model.persistence.*;
import org.hbrs.se1.ws21.uebung4.view.EmployeeView;

public class InputDialog {
    private EmployeeView stream;

    public InputDialog() {
        Container.getInstance().setPersistenceStrategy(new PersistenceStrategyStream<>());
        stream = new EmployeeView(System.out);
    }

    public Container<Mitarbeiter> getSpeicher() {
        return Container.getInstance();
    }

    public void startDialog(Scanner scan) {
        boolean ende = false;
        String[] userInput;
        do {
            try {
                userInput = scan.nextLine().split(" ");
                switch (userInput[0].toLowerCase()) {
                case "enter": {
                    int id = 0;
                    int noIDinput = 4;
                    if (userInput.length == 6) {
                        try {
                            id = Integer.parseInt(userInput[1]);
                        } catch (NumberFormatException e) {
                            stream.println("Es muss eine Zahl angegeben werden.");
                            break;
                        }
                        noIDinput = 5;
                    } else {
                        id = Mitarbeiter.getUniqueID();
                    }
                    for (int i = 0; i < 10; i++) {
                        if (userInput[2].contains(i + "") || userInput[3].contains(i + "")) {
                            stream.println("Der Name darf keine Zahl enthalten.");
                            break;
                        }
                    }

                    if (userInput[noIDinput].equals("null")) {
                        userInput[noIDinput] = "";
                    }
                    boolean endeExpEingabe = false;
                    int i = 1, lvl = 0;
                    String bez = "";
                    String tmp = "";
                    Expertise expertise = new Expertise();
                    stream.println("Wollen sie Expertisen zum Mitarbeiter festlegen?\n('ja' oder 'nein' eingeben)");
                    if (scan.next().trim().equals("nein")) {
                        endeExpEingabe = true;
                    }
                    boolean isNumber = false;
                    while (!endeExpEingabe) {

                        stream.println("Geben Sie einen Expertisen-Bezeichnung an.");
                        bez = scan.next();
                        stream.println("Geben Sie das Level an was der Mitarbeiter in dieser Expertise hat.");
                        do {
                            tmp = scan.next();
                            try {
                                lvl = Integer.parseInt(tmp);
                            } catch (NumberFormatException e) {
                                stream.println("Es muss eine Zahl angegeben werden.");
                                continue;
                            }
                            if (!(lvl > 0 && lvl < 4)) {
                                stream.println("Das Level muss zwischen  1 und 3 sein.");
                                continue;
                            }
                            isNumber = true;
                        } while (!isNumber);
                        expertise.setNewExpertise(lvl, bez);
                        i++;
                        if (i > 3) {
                            endeExpEingabe = true;
                        }

                        stream.println("Wollen sie Expertisen zum Mitarbeiter festlegen?\n('ja' oder 'nein' eingeben)");

                        if (scan.next().trim().equals("nein")) {
                            endeExpEingabe = true;
                        }
                    }

                    Mitarbeiter neueMitarbeiter = new Mitarbeiter(id, userInput[2], userInput[3], userInput[4],
                            userInput[noIDinput], expertise);
                    try {
                        Container.getInstance().addMember(neueMitarbeiter);
                    } catch (ContainerException e) {
                        stream.println("ID schon vorhanden.");
                        break;
                    }
                    stream.println(
                            String.format("Mitarbeiter erfolgreich gespeichert.\n%s", neueMitarbeiter.toString()));
                }
                    break;
                case "store":
                    try {
                        Container.getInstance().store();
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
                            Container.getInstance().load(false);
                        } catch (PersistenceException e) {
                            stream.println("Laden nicht erfolgreich");
                            break;
                        }
                    } else if (secoundInput.equals("force")) {
                        try {
                            Container.getInstance().load(true);
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
                    if (Container.getInstance().size() < 1) {
                        this.stream.println("Es wurden bisher keine Mitarbeiter eingetragen.");
                    } else {
                        if (userInput.length > 1) {
                            this.stream.dump(Container.getInstance(), userInput[2], userInput[1]);
                        } else {
                            this.stream.dump(Container.getInstance());
                        }

                    }
                    break;
                case "search": {
                    if (Container.getInstance().size() < 1) {
                        this.stream.println("Es wurden bisher keine Mitarbeiter eingetragen.");
                    } else {
                        this.stream.search(userInput[1].toLowerCase(), Container.getInstance());
                    }
                }
                    break;
                case "exit":
                    ende = true;
                    break;
                case "help":
                    this.stream.getHelpMassage();
                    break;
                default:
                    stream.println("Es muss ein gültige eingabe angegben werden.");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                stream.println("Es muessen mehr Parameter Angegeben werden.");
            }
        } while (!ende);
        scan.close();
    }

    public EmployeeView getStream() {
        return stream;
    }
}
