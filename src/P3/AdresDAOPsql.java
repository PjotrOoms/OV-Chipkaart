package P3;

import P2.Reiziger;
import P2.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO{

    private Connection conn;
    private ReizigerDAO rdao;

    public AdresDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            String query = "INSERT INTO adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) " + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, adres.getId());
            pstmt.setString(2, adres.getPostcode());
            pstmt.setString(3, adres.getHuisnummer());
            pstmt.setString(4, adres.getStraat());
            pstmt.setString(5, adres.getWoonplaats());
            pstmt.setInt(6, adres.getReiziger_id());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Adres adres) {
        try {
            String query = "UPDATE adres " + "SET huisnummer = ? " + "WHERE adres_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, adres.getHuisnummer());
            pstmt.setInt(2, adres.getId());

            pstmt.executeUpdate();

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            String query = "DELETE FROM adres " + "WHERE adres_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, adres.getId());

            pstmt.executeUpdate();

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String query = "SELECT * FROM adres WHERE reiziger_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reiziger.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int adres_id = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");
                int reiziger_id = rs.getInt("reiziger_id");
                Adres a;
                a = new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id);
                return a;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        try {
            // Query + resulaten
            Statement st = conn.createStatement();
            String query = "SELECT * FROM adres";
            ResultSet rs = st.executeQuery(query);

            // Lijst van adressen
            List<Adres> adressen = new ArrayList<>();

            // Gegevens van elk adres opslaan
            while (rs.next()) {
                int adres_id = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");
                int reiziger_id = rs.getInt("reiziger_id");

            // Reizigers ophalen uit db en toevoegen aan de lijst
                Adres a;
                a = new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id);
                adressen.add(a);
            }

            rs.close();
            st.close();
            return adressen;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
