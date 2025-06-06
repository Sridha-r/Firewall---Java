import java.util.Scanner;

public class Packet {
    private String sourceIP;
    private String destIP;
    private int port;
    private String protocol;
    private String direction;

    public Packet() {
        // Optional: Initialize with defaults
        this.sourceIP = "";
        this.destIP = "";
        this.protocol = "";
        this.port = 0;
        this.direction = "";
    }
    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    public void setDestIP(String destIP) {
        this.destIP = destIP;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    
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
