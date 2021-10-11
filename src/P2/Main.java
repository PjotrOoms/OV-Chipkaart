package P2;

import P3.Adres;
import P3.AdresDAO;
import P3.AdresDAOPsql;
import P4.OVChipkaart;
import P4.OVChipkaartDAO;
import P4.OVChipkaartDAOPsql;
import P5.Product;
import P5.ProductDAO;
import P5.ProductDAOPsql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        getConnection();
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        AdresDAO adao = new AdresDAOPsql(connection);
        OVChipkaartDAO odao = new OVChipkaartDAOPsql(connection);
        ProductDAOPsql pdao = new ProductDAOPsql(connection);
        rdao.setAdao(adao);
        rdao.setOdoa(odao);
//        testReizigerDAO(rdao);
//        testAdresDAO(rdao, adao);
//        testOVChipkaartDAO(rdao, adao, odao);
        testProductDAO(pdao);
        closeConnection();
    }


    private static void getConnection(){
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=admin";

        try {
            connection = DriverManager.getConnection(url);

    } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void closeConnection() throws SQLException {
        connection.close();
    }

    private static void testReizigerDAO(ReizigerDAO rdao) {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(6, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Zoeken op geboortedatum
        String gbdatum2 = "1998-08-11";
        List<Reiziger> reizigersgb = rdao.findByGbdatum(java.sql.Date.valueOf(gbdatum2));
        System.out.println("[Test] ReizigerDAO.findByGbdatum() geeft de volgende reizigers:");
        for (Reiziger r2 : reizigersgb) {
            System.out.println(r2);
        }

        // Update een reiziger
        Reiziger sietske2 = new Reiziger(6, "S", "", "Boeren", java.sql.Date.valueOf(gbdatum));
        rdao.update(sietske2);
        System.out.println("[Test] ReizigerDAO.save() heeft de gegevens aangepast en opgeslagen.");

        // Delete een reiziger
//        rdao.delete(sietske2);
//        System.out.println("[Test] ReizigerDAO.save() heeft de reiziger verwijderd.");

        // Zoeken op ID
        System.out.println("[Test] ReizigerDAO.findById() heeft de volgende reiziger gevonden:");
        Reiziger r3 = rdao.findById(5);
        System.out.println(r3);
    }

    private static void testAdresDAO(ReizigerDAO rdao, AdresDAO adao) {
        System.out.println("\n---------- Test AdresDAO -------------");


        // Alle adressen ophalen uit de DB
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Reiziger toevoegen
        Reiziger pjotr = new Reiziger(8, "P", "", "Ooms", java.sql.Date.valueOf("1999-09-09"));

        // Adres aanmaken
        Adres adres1 = new Adres(8, "4208 BJ", "90", "Ruigenhoek", "Gorinchem", 8);

        // Reiziger + adres setten
        pjotr.setAdres(adres1);

        // Adres opslaan
        List<Reiziger>reizigers = rdao.findAll();
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(pjotr);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Adres updaten
        Adres adres2 = new Adres(8, "4208 BJ", "80", "Ruigenhoek", "Gorinchem", 8);
        adao.update(adres2);
        System.out.println("[Test] AdresDAO.update() heeft het adres aangepast.");

        // Adres zoeken dmv id van reiziger
        System.out.println("[Test] AdresDAO.findById() heeft het volgende adres gevonden:");
        Adres adres3 = adao.findByReiziger(pjotr);
        System.out.println(adres3);

        // Reiziger deleten
        rdao.delete(pjotr);
        System.out.println("[Test] ReizgerDAO.delete() heeft de reiziger en het adres verwijderd.");
    }

    private static void testOVChipkaartDAO(ReizigerDAO rdao, AdresDAO adao, OVChipkaartDAO odao){
        System.out.println("\n---------- Test OVChipkaartDAO -------------");


        // Reiziger toevoegen
        Reiziger piet = new Reiziger(9, "P", "", "Jansen", java.sql.Date.valueOf("1999-09-09"));

        // Adres aanmaken
        Adres adres1 = new Adres(9, "4208 BJ", "90", "Ruigenhoek", "Gorinchem", 9);

        // OVC lijst aanmaken
        List<OVChipkaart> ovChipkaartlijst = new ArrayList<>();

        // OVChipkaart aanmaken
        OVChipkaart ovChipkaart = new OVChipkaart(35263, java.sql.Date.valueOf("2021-09-09"), 2, 25.50, 9);
        ovChipkaartlijst.add(ovChipkaart);

        // Reiziger + adres + ovchipkaart setten
        piet.setAdres(adres1);
        piet.setOvChipkaart(ovChipkaartlijst);

        // Opslaan
        List<Reiziger>reizigers = rdao.findAll();
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(piet);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // OVChipkaarten van reiziger zoeken
        System.out.println("[Test] OVChipkaartDAO.findByReiziger() heeft de volgende OV Chipkaarten gevonden:");
        List<OVChipkaart> ovChipkaartZoeken = odao.findByReiziger(piet);
        System.out.println(ovChipkaartZoeken);

        // Reiziger (en bijbehorende adres en ov_chipkaarten deleten)
        rdao.delete(piet);
        System.out.println("[Test] ReizgerDAO.delete() heeft de reiziger, het adres en de ov chipkaarten verwijderd.");
    }

    private static void testProductDAO(ProductDAO pdao) {
        System.out.println("\n---------- Test ProductDAO -------------");

        // Product toevoegen
        Product p1 = new Product(7, "Weekkaart", "Een hele week onberperkt reizen met heet openbaar vervoer", 420.00);

        // OVC lijst aanmaken
        ArrayList<OVChipkaart> ovChipkaartlijst = new ArrayList<>();

        // OVChipkaart aanmaken
        OVChipkaart ovChipkaart = new OVChipkaart(35283, java.sql.Date.valueOf("2018-05-31"), 2, 25.50, 2);
        ovChipkaartlijst.add(ovChipkaart);

        // OVChipkaart + product setten
        p1.setOvChipkaarten(ovChipkaartlijst);

        // Opslaan
        List<Product>producten = pdao.findAll();
        System.out.print("[Test] Eerst " + producten.size() + " producten, na ProductDAO.save() ");
        pdao.save(p1);
        producten = pdao.findAll();
        System.out.println(producten.size() + " producten\n");

        // Update
        Product p2 = new Product(7, "Weekkaart", "Een hele week onberperkt reizen met het openbaar vervoer", 200.00);
        pdao.update(p2);
        System.out.println("[Test] ProductDAO.update() heeft het product aangepast.");

        // Alle producten van 1 ovchipkaart zoeken
        System.out.println("[Test] ProductDAO.findByOVChipkaart() heeft de volgende producten gevonden:");
        List<Product> productenzoeken = pdao.findByOVChipkaart(ovChipkaart);
        System.out.println(productenzoeken);

        // Product delete
        pdao.delete(p2);
        System.out.println("[Test] ProductDAO.delete() heeft het product verwijderd.");

        // Alle producten ophalen uit de DB
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product pr : producten) {
            System.out.println(pr);
        }
        System.out.println();
    }
}
