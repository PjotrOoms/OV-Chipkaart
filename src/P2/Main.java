package P2;

import java.sql.*;
import java.util.List;

public class Main {

    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        getConnection();
        ReizigerDAO rdao = new ReizigerDAOPsql(connection);
        testReizigerDAO(rdao);
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

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
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
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
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
        Reiziger sietske2 = new Reiziger(77, "S", "", "Boeren", java.sql.Date.valueOf(gbdatum));
        rdao.update(sietske2);
        System.out.println("[Test] ReizigerDAO.save() heeft de gegevens aangepast en opgeslagen.");

        // Delete een reiziger
        rdao.delete(sietske2);
        System.out.println("[Test] ReizigerDAO.save() heeft de reiziger verwijderd.");

        // Zoeken op ID
        System.out.println("[Test] ReizigerDAO.findById() heeft de volgende reiziger gevonden:");
        Reiziger r3 = rdao.findById(5);
        System.out.println(r3);

    }
}
