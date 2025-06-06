import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FirewallQuery {
    public static String processPacket(Packet packet) {
        StringBuilder result = new StringBuilder();

        if (StateInspect.isEstablishedSession(packet)) {
            result.append("Packet allowed (session is already established).");
            return result.toString();
        }

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Connect to DB
            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "your username", "password");

            // Prepare query
            String sql = "SELECT * FROM firewall_rules WHERE " +
                "(source_ip = ? OR source_ip = '-') AND " +
                "(destination_ip = ? OR destination_ip = '-') AND " +
                "(port = ? OR port IS NULL) AND " +
                "(protocol = ? OR protocol = '-') AND " +
                "(direction = ? OR direction = '-')";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, packet.getSourceIP());
            stmt.setString(2, packet.getDestIP());
            stmt.setInt(3, packet.getPort());
            stmt.setString(4, packet.getProtocol());
            stmt.setString(5, packet.getDirection());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String action = rs.getString("action");
                result.append("Packet is ").append(action.toUpperCase()).append("ED by firewall rule.\n");

                // Promote session
                StateInspect.insertNewSession(packet);
                StateInspect.promoteToEstablished(packet);
            } else {
                result.append("Packet is BLOCKED (no matching rule).\n");
            }

            conn.close();
        } catch (Exception e) {
            result.append("Error: ").append(e.getMessage());
            e.printStackTrace();
        }

        return result.toString();
    }
}
