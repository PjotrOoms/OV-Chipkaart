package P2;

import P3.Adres;
import P4.OVChipkaart;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsels;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> ovChipkaart;

    public Reiziger(int id, String voorletters, String tussenvoegsels, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsels() {
        return tussenvoegsels;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public java.sql.Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Adres getAdres() {
        return adres;
    }

    public List<OVChipkaart> getOvChipkaart() {
        return ovChipkaart;
    }

    public void setOvChipkaart(List<OVChipkaart> ovChipkaart) {
        this.ovChipkaart = ovChipkaart;
    }

    public String toString() {
        return "Reiziger: #" + id + " " + voorletters + " " + tussenvoegsels + " " + achternaam + " (" + geboortedatum + ")" + adres;
    }
}
