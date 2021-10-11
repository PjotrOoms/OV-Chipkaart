package P5;

import P4.OVChipkaart;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private ArrayList<OVChipkaart> ovChipkaarten = new ArrayList<>();

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public ArrayList<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(ArrayList<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public void voegOvChipkaartToe(OVChipkaart ovChipkaart) {
        ovChipkaarten.add(ovChipkaart);
    }

    public void verwijderOvChipkaartToe(OVChipkaart ovChipkaart) {
        ovChipkaarten.remove(ovChipkaart);
    }

    @Override
    public String toString() {
        String ovChipkaart = "";
        for (OVChipkaart ovc : ovChipkaarten) {
            ovChipkaart += ovc.getKaart_nummer() + " - ";
        }
        return "Product: #" + product_nummer + " naam: " + naam + ", beschrijving: " + beschrijving + ", prijs = " + prijs + ", OV-Chipkaarten = " + ovChipkaart;
    }
}
