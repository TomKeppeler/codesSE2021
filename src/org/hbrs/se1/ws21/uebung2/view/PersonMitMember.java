package org.hbrs.se1.ws21.uebung2.view;

import java.util.Date;
import java.util.Random;

public class PersonMitMember implements org.hbrs.se1.ws21.uebung2.controll.Member{
    private String name;
    private String vorname;
    private Date gebDate;
    private String hobby;
    private Double groesse;
    private Geschlecht geschlecht;

    public PersonMitMember(String name, String vorname, Date gebDate, String hobby, Double groesse, Geschlecht geschlecht) {
        this.name = name;
        this.vorname = vorname;
        this.gebDate = gebDate;
        this.hobby = hobby;
        this.groesse = groesse;
        this.geschlecht = geschlecht;
    }

    public Geschlecht getGeschlecht() {
        return this.geschlecht;
    }

    public void setGeschlecht(Geschlecht geschlecht) {
        this.geschlecht = geschlecht;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return this.vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public Date getGebDate() {
        return this.gebDate;
    }

    public void setGebDate(Date gebDate) {
        this.gebDate = gebDate;
    }

    public String getHobby() {
        return this.hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Double getGroesse() {
        return this.groesse;
    }

    public void setGroesse(Double groesse) {
        this.groesse = groesse;
    }

    @Override
    public Integer getID() {
        return new Random().nextInt(999999);
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", vorname='" + getVorname() + "'" +
            ", gebDate='" + getGebDate() + "'" +
            ", hobby='" + getHobby() + "'" +
            ", groesse='" + getGroesse() + "'" +
            ", id='" + getID() + "'" +
            "}";
    }
    
}
