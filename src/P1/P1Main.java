package P1;

import java.sql.*;

public class P1Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=mibmab99";

        try {
            Connection conn = DriverManager.getConnection(url);

            Statement st = conn.createStatement();
            String query = "SELECT * FROM reiziger";

            ResultSet rs = st.executeQuery(query);

            int id;
            String voorletter;
            String tussenvoegsel;
            String achternaam;
            Date gebdat;

            System.out.println("Alle reizigers:");
            while (rs.next()) {
                id = rs.getInt("reiziger_id");
                voorletter = rs.getString("voorletters");
                tussenvoegsel = rs.getString("tussenvoegsel");
                if (tussenvoegsel == null) {
                    tussenvoegsel = "";
                }
                achternaam = rs.getString("achternaam");
                gebdat = rs.getDate("geboortedatum");
                System.out.println("#" + id + ": "+ voorletter + " " + tussenvoegsel + " " + achternaam + " (" + gebdat + ")");
            }

            rs.close();
            st.close();

        } catch (SQLException sqlex) {
            System.err.println("[SQLExceeption] Reizigeroverzicht kon niet worden opgehaald: " + sqlex.getMessage());
        }
    }
}

