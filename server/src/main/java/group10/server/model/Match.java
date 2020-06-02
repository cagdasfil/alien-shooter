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

    /**
     * Getter method for id attribute of match.
     * @return id   id of the match
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for id attribute of match. Sets id with given parameter.
     * @param id   the id to set
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Getter method for serverUsername attribute of match.
     * @return serverUsername    of the match
     */
    public String getServerUsername() {
        return serverUsername;
    }

    /**
     * Setter method for serverUsername attribute of match. Sets serverUsername with given parameter.
     * @param serverUsername   the serverUsername to set
     */
    public void setServerUsername(String serverUsername) {
        this.serverUsername = serverUsername;
    }

    /**
     * Getter method for serverIP attribute of match.
     * @return serverIP   serverIP of the match
     */
    public String getServerIP() {
        return serverIP;
    }

    /**
     * Setter method for serverIP attribute of match. Sets serverIP with given parameter.
     * @param serverIP   the serverIP to set
     */
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    /**
     * Getter method for serverPort attribute of match.
     * @return serverPort   serverPort of the match
     */
    public String getServerPort() {
        return serverPort;
    }

    /**
     * Setter method for serverPort attribute of match. Sets serverPort with given parameter.
     * @param serverPort   the serverPort to set
     */
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * Getter method for serverStatus attribute of match.
     * @return serverStatus   serverStatus of the match
     */
    public String getServerStatus() {
        return serverStatus;
    }

    /**
     * Setter method for serverStatus attribute of match. Sets serverStatus with given parameter.
     * @param serverStatus   the serverStatus to set
     */
    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    /**
     * Getter method for clientUsername attribute of match.
     * @return clientUsername   clientUsername of the match
     */
    public String getClientUsername() {
        return clientUsername;
    }

    /**
     * Setter method for clientUsername attribute of match. Sets clientUsername with given parameter.
     * @param clientUsername   the clientUsername to set
     */
    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    /**
     * Getter method for clientStatus attribute of match.
     * @return clientStatus   clientStatus of the match
     */
    public String getClientStatus() {
        return clientStatus;
    }

    /**
     * Setter method for clientStatus attribute of match. Sets clientStatus with given parameter.
     * @param clientStatus   the clientStatus to set
     */
    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }
}
