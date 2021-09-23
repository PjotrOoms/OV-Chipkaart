package P2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{

    private Connection conn;

    public ReizigerDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            String query = "INSERT INTO reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) " + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, reiziger.getId());
            pstmt.setString(2, reiziger.getVoorletters());
            pstmt.setString(3, reiziger.getTussenvoegsels());
            pstmt.setString(4, reiziger.getAchternaam());
            pstmt.setDate(5, reiziger.getGeboortedatum());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            String query = "UPDATE reiziger " + "SET achternaam = ? " + "WHERE reiziger_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, reiziger.getAchternaam());
            pstmt.setInt(2, reiziger.getId());

            pstmt.executeUpdate();

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            String query = "DELETE FROM reiziger " + "WHERE reiziger_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, reiziger.getId());

            pstmt.executeUpdate();

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        try {
            String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int reiziger_id = rs.getInt("reiziger_id");
                String voorletters = rs.getString("voorletters");
                String tussenvoegsels = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                String gebdat = rs.getString("geboortedatum");
                Reiziger r;
                r = new Reiziger(reiziger_id, voorletters, tussenvoegsels, achternaam, java.sql.Date.valueOf(gebdat));
                return r;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

        @Override
    public List<Reiziger> findByGbdatum(Date datum) {
        try {
            // Query + resulaten
            String query = "SELECT * FROM reiziger WHERE geboortedatum = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, datum);
            ResultSet rs = pstmt.executeQuery();

            // Lijst van reizigers
            List<Reiziger> reizigers = new ArrayList<>();

            while (rs.next()) {
                int reiziger_id = rs.getInt("reiziger_id");
                String voorletters = rs.getString("voorletters");
                String tussenvoegsels = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                String gebdat = rs.getString("geboortedatum");

                Reiziger r;
                r = new Reiziger(reiziger_id, voorletters, tussenvoegsels, achternaam, java.sql.Date.valueOf(gebdat));
                reizigers.add(r);
                rs.close();
                pstmt.close();
                return reizigers;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            // Query + resulaten
            Statement st = conn.createStatement();
            String query = "SELECT * FROM reiziger";
            ResultSet rs = st.executeQuery(query);

            // Lijst van reizigers
            List<Reiziger> reizigers = new ArrayList<>();

            // Gegevens van elke reiziger opslaan
            while (rs.next()) {
                int reiziger_id = rs.getInt("reiziger_id");
                String voorletters = rs.getString("voorletters");
                String tussenvoegsels = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                String gebdat = rs.getString("geboortedatum");

                if (tussenvoegsels == null) {
                    tussenvoegsels = "";
                }

                // Reizigers ophalen uit db en toevoegen aan de lijst
                Reiziger r;
                r = new Reiziger(reiziger_id, voorletters, tussenvoegsels, achternaam, java.sql.Date.valueOf(gebdat));
                reizigers.add(r);
            }

            rs.close();
            st.close();
            return reizigers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
