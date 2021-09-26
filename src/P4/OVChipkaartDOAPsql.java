package P4;

import P2.Reiziger;
import P3.Adres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OVChipkaartDOAPsql implements OVChipkaartDAO{

    private Connection conn;

    public OVChipkaartDOAPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            String query = "INSERT INTO ov_chipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) " + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, ovChipkaart.getKaart_nummer());
            pstmt.setDate(2, ovChipkaart.getGeldig_tot());
            pstmt.setInt(3, ovChipkaart.getKlasse());
            pstmt.setDouble(4, ovChipkaart.getSaldo());
            pstmt.setInt(5, ovChipkaart.getReiziger_id());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            String query = "UPDATE ov_chipkaart " + "SET klasse = ? " + "WHERE kaart_nummer = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, ovChipkaart.getKlasse());
            pstmt.setInt(2, ovChipkaart.getKaart_nummer());

            pstmt.executeUpdate();

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            String query = "DELETE FROM ov_chipkaart " + "WHERE kaart_nummer = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, ovChipkaart.getKaart_nummer());

            pstmt.executeUpdate();

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reiziger.getId());
            ResultSet rs = pstmt.executeQuery();

            // Lijst van ov_chipkaarten
            List<OVChipkaart> ovChipkaarten = new ArrayList<>();

            while (rs.next()) {
                int kaart_nummer = rs.getInt("kaart_nummer");
                String geldig_tot = rs.getString("geldig_tot");
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");
                int reiziger_id = rs.getInt("reiziger_id");
                OVChipkaart a = new OVChipkaart(kaart_nummer, java.sql.Date.valueOf(geldig_tot), klasse, saldo, reiziger_id);
                ovChipkaarten.add(a);
            }

            rs.close();
            pstmt.close();
            return ovChipkaarten;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
