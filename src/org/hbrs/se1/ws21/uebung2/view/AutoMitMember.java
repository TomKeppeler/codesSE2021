package org.hbrs.se1.ws21.uebung2.view;

import java.util.Random;

import org.hbrs.se1.ws21.uebung2.controll.Member;

public class AutoMitMember implements Member{
    private Integer anzahlAchsen;
    private Integer anzahlReifen;
    private String marke;
    private String typ;
    private String besitzer;
    private Double leistung;
    public AutoMitMember(Integer anzahlAchsen, Integer anzahlReifen, String marke, String typ, String besitzer, Double leistung) {
        this.anzahlAchsen = anzahlAchsen;
        this.anzahlReifen = anzahlReifen;
        this.marke = marke;
        this.typ = typ;
        this.besitzer = besitzer;
        this.leistung = leistung;
    }

    @Override
    public Integer getID() {
        return new Random().nextInt(999999);
    }
    @Override
    public String toString() {
        return "{" +
            " anzahlAchsen='" + getAnzahlAchsen() + "'" +
            ", anzahlReifen='" + getAnzahlReifen() + "'" +
            ", marke='" + getMarke() + "'" +
            ", typ='" + getTyp() + "'" +
            ", besitzer='" + getBesitzer() + "'" +
            ", leistung='" + getLeistung() + "'" +
            ", id='" + getID() + "'" +
            "}";
    }

    public Integer getAnzahlAchsen() {
        return this.anzahlAchsen;
    }

    public void setAnzahlAchsen(Integer anzahlAchsen) {
        this.anzahlAchsen = anzahlAchsen;
    }

    public Integer getAnzahlReifen() {
        return this.anzahlReifen;
    }

    public void setAnzahlReifen(Integer anzahlReifen) {
        this.anzahlReifen = anzahlReifen;
    }

    public String getMarke() {
        return this.marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public String getTyp() {
        return this.typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getBesitzer() {
        return this.besitzer;
    }

    public void setBesitzer(String besitzer) {
        this.besitzer = besitzer;
    }

    public Double getLeistung() {
        return this.leistung;
    }

    public void setLeistung(Double leistung) {
        this.leistung = leistung;
    }

}
