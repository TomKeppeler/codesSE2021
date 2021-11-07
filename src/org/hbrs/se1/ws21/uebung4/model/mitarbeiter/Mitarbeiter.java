package org.hbrs.se1.ws21.uebung4.model.mitarbeiter;

import org.hbrs.se1.ws21.uebung4.model.Member;

public class Mitarbeiter implements Member {
    private Integer id;
    private String vorname;
    private String nachname;
    private String rolle;
    private String abteil;
    private Expertise expertise;

    public Mitarbeiter(Integer id, String vorname, String nachname, String rolle, String abteil, Expertise expertise) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.rolle = rolle;
        this.abteil = abteil;
        this.expertise = expertise;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getID() + "'" +
            ", vorname='" + getVorname() + "'" +
            ", nachname='" + getNachname() + "'" +
            ", rolle='" + getRolle() + "'" +
            ", abteil='" + getAbteil() + "'" +
            ", expertise='" + getExpertise() + "'" +
            "}";
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

    public Expertise getExpertise() {
        return this.expertise;
    }

    public void setExpertise(Expertise expertise) {
        this.expertise = expertise;
    }
}
