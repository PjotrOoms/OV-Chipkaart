package P3;

public class Adres {
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reiziger_id;

    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, int reiziger_id) {
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger_id = reiziger_id;
    }

    public int getId() {
        return id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String pc) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public String toString(){
        return "   Adres: #" + id + " " + postcode + " " + straat + " " + huisnummer + " " + woonplaats + ")";
    }
}
