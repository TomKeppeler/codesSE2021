package org.hbrs.se1.ws21.vorbereitung.HandyTarifProvider.model;

public class Tarif {
    private String bezeichnung;
    private String art;
    private int freiSMS;
    private int freiTelefoni;
    private int datenvolume; // in MB angegeben

    public Tarif(String bezeichnung, String art, int freiSMS, int freiTelefoni, int datenvolume) {
        this.bezeichnung = bezeichnung;
        this.art = art;
        this.freiSMS = freiSMS;
        this.freiTelefoni = freiTelefoni;
        this.datenvolume = datenvolume;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public String getArt() {
        return art;
    }

    public int getFreiSMS() {
        return freiSMS;
    }

    public int getFreiTelefoni() {
        return freiTelefoni;
    }

    public int getDatenvolume() {
        return datenvolume;
    }
}