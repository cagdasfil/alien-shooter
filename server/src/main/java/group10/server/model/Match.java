package group10.server.model;

import javax.persistence.*;

@Entity
@Table(name="matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name ="server_username", nullable = false, unique = true, updatable = false)
    private String serverUsername;

    @Column(name="server_ip", nullable = false)
    private String serverIP;

    @Column(name ="server_port", nullable = false)
    private String serverPort;

    @Column(name="server_status", nullable = false)
    private String serverStatus;

    @Column(name ="client_username", nullable = false)
    private String clientUsername;

    @Column(name="client_status", nullable = false)
    private String clientStatus;

    /**
     * Empty class constructor for Match class.
     */
    public Match() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerUsername() {
        return serverUsername;
    }

    public void setServerUsername(String serverUsername) {
        this.serverUsername = serverUsername;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }
}
