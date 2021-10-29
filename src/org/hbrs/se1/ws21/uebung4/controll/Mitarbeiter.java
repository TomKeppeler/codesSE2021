package org.hbrs.se1.ws21.uebung4.controll;

public class Mitarbeiter implements Member {
    int id;
    String vorname;
    String nachname;
    String rolle;
    String abteil;
    int experLvl;

    public Mitarbeiter(int id, String vorname, String nachname, String rolle, String abteil, int experLvl) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.rolle = rolle;
        this.abteil = abteil;
        this.experLvl = experLvl;
    }

    @Override
    public Integer getID() {
        return id;
    }

    public String getVorname() {
        return this.vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return this.nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getRolle() {
        return this.rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public String getAbteil() {
        return this.abteil;
    }

    public void setAbteil(String abteil) {
        this.abteil = abteil;
    }

    public int getExperLvl() {
        return this.experLvl;
    }

    public void setExperLvl(int experLvl) {
        this.experLvl = experLvl;
    }
}
