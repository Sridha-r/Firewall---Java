import java.util.Scanner;

public class Packet {
    private String sourceIP;
    private String destIP;
    private int port;
    private String protocol;
    private String direction;

    public Packet(String sourceIP, String destIP, int port, String protocol,String direction) {
        this.sourceIP = sourceIP;
        this.destIP = destIP;
        this.port = port;
        this.protocol = protocol;
        this.direction= direction;
    }
    public String getSourceIP() { return sourceIP; }
    public String getDestIP() { return destIP; }
    public int getPort() { return port; }
    public String getProtocol() { return protocol; }
    public String getDirection() { return direction; }


    // For testing/debugging
    public void displayPacket() {
        System.out.println(" Packet Info:");
        System.out.println("Source IP: " + sourceIP);
        System.out.println("Destination IP: " + destIP);
        System.out.println("Port: " + port);
        System.out.println("Protocol: " + protocol);
        System.out.println("Direction " + direction);
}

}



class Main{
public static void main(String[] args) {
    

Scanner scanner = new Scanner(System.in);

System.out.println("Enter Source IP: ");
String sourceIP = scanner.nextLine();

System.out.print("Enter Destination IP: ");
String destIP = scanner.nextLine();

System.out.print("Enter Port: ");
int port = scanner.nextInt();
scanner.nextLine(); // consume leftover newline

System.out.print("Enter Protocol (TCP/UDP): ");
String protocol = scanner.nextLine();

System.out.print("Enter Direction (INBOUND/OUTBOUND)) ");
String direction = scanner.nextLine();

Packet packet = new Packet(sourceIP, destIP, port, protocol,direction);

FirewallQuery.processPacket(packet);
StateInspect.showEstablishedConnections();
}
    }
