package group10.client.model.server;

public class Match {

    private Long id;
    private String serverUsername;
    private String serverIP;
    private String serverPort;
    private String serverStatus;
    private String clientUsername;
    private String clientStatus;

    /**
     * Getter method for id attribute of match.
     * @return id   id of the match
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for id attribute of match.
     * @param id   id of the match
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for serverUsername attribute of match.
     * @return serverUsername   serverUsername of the match
     */
    public String getServerUsername() {
        return serverUsername;
    }

    /**
     * Setter method for serverUsername attribute of match.
     * @param serverUsername   serverUsername of the match
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
     * Setter method for serverIP attribute of match.
     * @param serverIP   serverIP of the match
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
     * Setter method for serverPort attribute of match.
     * @param serverPort   serverPort of the match
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
     * Setter method for serverStatus attribute of match.
     * @param serverStatus   serverStatus of the match
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
     * Setter method for clientUsername attribute of match.
     * @param clientUsername   clientUsername of the match
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
     * Setter method for clientStatus attribute of match.
     * @param clientStatus   clientStatus of the match
     */
    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }
}
