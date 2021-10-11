package P5;

import P4.OVChipkaart;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private Connection conn;

    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) {
        try {
            String query = "INSERT INTO product(product_nummer, naam, beschrijving, prijs) " + "VALUES (?, ?, ?, ?)";
            String query2 = "INSERT INTO ov_chipkaart_product(kaart_nummer, product_nummer, status, last_update) " + "VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, product.getProduct_nummer());
            pstmt.setString(2, product.getNaam());
            pstmt.setString(3, product.getBeschrijving());
            pstmt.setDouble(4, product.getPrijs());

            pstmt.executeUpdate();

            for (OVChipkaart ovchip : product.getOvChipkaarten()) {
                PreparedStatement pstmt2 = conn.prepareStatement(query2);
                pstmt2.setInt(1, ovchip.getKaart_nummer());
                pstmt2.setInt(2, product.getProduct_nummer());
                pstmt2.setString(3, "actief");
                pstmt2.setDate(4, Date.valueOf(LocalDate.now()));
                pstmt2.executeUpdate();
            }

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        try {
            String query = "UPDATE product " + "SET prijs = ? " + "WHERE product_nummer = ?";
            String query2 = "UPDATE ov_chipkaart_product " + "SET last_update = ? " + "WHERE product_nummer = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            PreparedStatement pstmt2 = conn.prepareStatement(query2);

            pstmt.setDouble(1, product.getPrijs());
            pstmt.setInt(2, product.getProduct_nummer());

            pstmt2.setDate(1, Date.valueOf(LocalDate.now()));
            pstmt2.setInt(2, product.getProduct_nummer());

            pstmt.executeUpdate();
            pstmt2.executeUpdate();

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Product product) {
        try {
            String query = "DELETE FROM product " + "WHERE product_nummer = ?";
            String query2 = "DELETE FROM ov_chipkaart_product " + "WHERE product_nummer = ?";

            PreparedStatement pstmt = conn.prepareStatement(query);
            PreparedStatement pstmt2 = conn.prepareStatement(query2);

            pstmt.setInt(1, product.getProduct_nummer());
            pstmt2.setInt(1, product.getProduct_nummer());

            pstmt2.executeUpdate();
            pstmt.executeUpdate();

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            String query = "SELECT ov_chipkaart_product.kaart_nummer, ov_chipkaart_product.product_nummer, product.naam, product.beschrijving, product.prijs " +
                    "FROM product " +
                    "JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer " +
                    "WHERE ov_chipkaart_product.kaart_nummer = ? " +
                    "ORDER BY kaart_nummer, product_nummer;";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ovChipkaart.getKaart_nummer());
            ResultSet rs = pstmt.executeQuery();

            // Lijst van producten
            List<Product> producten = new ArrayList<>();

            while (rs.next()) {
                int product_nummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");
                Product product = new Product(product_nummer, naam, beschrijving, prijs);
                product.voegOvChipkaartToe(ovChipkaart);
                producten.add(product);
            }

            rs.close();
            pstmt.close();
            return producten;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        try {
            String query = "SELECT ov_chipkaart_product.kaart_nummer, product.naam, product.beschrijving, product.prijs, ov_chipkaart_product.product_nummer, ov_chipkaart.geldig_tot, ov_chipkaart.klasse, ov_chipkaart.saldo, ov_chipkaart.reiziger_id " +
                    "FROM product " +
                    "JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer " +
                    "JOIN ov_chipkaart ON ov_chipkaart_product.kaart_nummer = ov_chipkaart.kaart_nummer " +
                    "ORDER BY kaart_nummer, product_nummer;";

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            // Lijst van producten
            List<Product> producten = new ArrayList<>();

            while (rs.next()) {
                int product_nummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");
                Product product = new Product(product_nummer, naam, beschrijving, prijs);

                int kaart_nummer = rs.getInt("kaart_nummer");
                Date geldig_tot = rs.getDate("geldig_tot");
                int klasse = rs.getInt("klasse");
                float saldo = rs.getFloat("saldo");
                int reiziger_id = rs.getInt("reiziger_id");
                OVChipkaart ovc = new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id);

                for (Product pr : producten) {
                    if (pr.getProduct_nummer() == pr.getProduct_nummer()) {
                        pr.voegOvChipkaartToe(ovc);
                        break;
                    }
                }
                product.voegOvChipkaartToe(ovc);
                producten.add(product);
            }

            rs.close();
            pstmt.close();
            return producten;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
