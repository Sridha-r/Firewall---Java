import java.sql.*;
public class StateInspect {
    public static boolean isEstablishedSession(Packet packet) {
        boolean found = false;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "system", "dbms123"
            );

            String query = "SELECT * FROM session_state WHERE source_ip = ? AND destination_ip = ? " +
                           "AND protocol = ? AND port = ? AND direction = ? AND state = 'ESTABLISHED'";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, packet.getSourceIP());
            stmt.setString(2, packet.getDestIP());
            stmt.setString(3, packet.getProtocol());
            stmt.setInt(4, packet.getPort());
            stmt.setString(5, packet.getDirection());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Packet is part of an ESTABLISHED session.");
                found = true;
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return found;
    }

    public static void insertNewSession(Packet packet) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "system", "dbms123"
            );

            String insert = "INSERT INTO session_state (session_id,source_ip, destination_ip, protocol, port, state, direction) " +
                            "VALUES (session_seq.NEXTVAL, ?, ?, ? ,?, 'NEW', ?)";
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setString(1, packet.getSourceIP());
            stmt.setString(2, packet.getDestIP());
            stmt.setString(3, packet.getProtocol());
            stmt.setInt(4, packet.getPort());
            stmt.setString(5, packet.getDirection());

            stmt.executeUpdate();

            System.out.println("New session inserted as 'NEW'.");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void promoteToEstablished(Packet packet) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "system", "dbms123"
            );

            String update = "UPDATE session_state SET state = 'ESTABLISHED', last_updated = CURRENT_TIMESTAMP " +
                            "WHERE source_ip = ? AND destination_ip = ? AND protocol = ? AND port = ? AND direction = ?";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, packet.getSourceIP());
            stmt.setString(2, packet.getDestIP());
            stmt.setString(3, packet.getProtocol());
            stmt.setInt(4, packet.getPort());
            stmt.setString(5, packet.getDirection());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("\n Session promoted to ESTABLISHED.");
            } else {
                System.out.println("\n Session not found to promote.");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  public static void showEstablishedConnections() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "system", "dbms123");
    
            String sql = "SELECT * FROM session_state WHERE state = 'ESTABLISHED'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
    
            System.out.println("Established Connections:");
            while (rs.next()) {
                System.out.println("Session ID: " + rs.getInt("session_id"));
                System.out.println("Source IP: " + rs.getString("source_ip"));
                System.out.println("Destination IP: " + rs.getString("destination_ip"));
                System.out.println("Protocol: " + rs.getString("protocol"));
                System.out.println("Port: " + rs.getInt("port"));
                System.out.println("Direction: " + rs.getString("direction"));
                System.out.println("Last Updated: " + rs.getTimestamp("last_updated"));
                System.out.println("----");
            }
    
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
