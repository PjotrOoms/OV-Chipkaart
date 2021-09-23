package P2;

import P3.Adres;
import P3.AdresDAO;
import P3.AdresDAOPsql;

import java.sql.*;
import java.util.List;

public class Main {

    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        getConnection();
        ReizigerDAO rdao = new ReizigerDAOPsql(connection);
        AdresDAO adao = new AdresDAOPsql(connection);
        testReizigerDAO(rdao);
        testAdresDAO(adao);

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

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
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

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
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

    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Alle adressen ophalen uit de DB
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende reizigers:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Adres aanmaken en opslaan
        Adres adres1 = new Adres(6, "4208 BJ", "90", "Ruigenhoek", "Gorinchem", 6);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(adres1);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
        System.out.println("[Test] AdresDAO.save() heeft het adres toegevoegd.");

        // Adres updaten
        Adres adres2 = new Adres(6, "4208 BJ", "80", "Ruigenhoek", "Gorinchem", 6);
        adao.update(adres2);
        System.out.println("[Test] AdresDAO.update() heeft het adres aangepast.");

        // Adres deleten
//        adao.delete(adres2);
//        System.out.println("[Test] AdresDAO.save() heeft het adres verwijderd.");

        // Adres zoeken dmv id van reiziger
        System.out.println("[Test] AdresDAO.findById() heeft het volgende adres gevonden:");
        String gbdatum = "1981-03-14";
        Reiziger sietske3 = new Reiziger(6, "S", "", "Boeren", java.sql.Date.valueOf(gbdatum));
        Adres adres3 = adao.findByReiziger(sietske3);
        System.out.println(adres3);
    }
}
