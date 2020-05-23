package group10.client.model.server;

import java.util.Set;

public class Match {

    private Long id;
    private String server_player;
    private String client_player;
    private String status;;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServer_player() {
        return server_player;
    }

    public void setServer_player(String server_player) {
        this.server_player = server_player;
    }

    public String getClient_player() {
        return client_player;
    }

    public void setClient_player(String client_player) {
        this.client_player = client_player;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
