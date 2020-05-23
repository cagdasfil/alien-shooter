package group10.server.model;

import javax.persistence.*;

@Entity
@Table(name="matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name ="server_player", nullable = false, unique = true, updatable = false)
    private String server_player;

    @Column(name="client_player", nullable = false)
    private String client_player;

    @Column(name="status", nullable = false)
    private String status;


    /**
     * Empty class constructor for User class.
     */
    public Match() {

    }


    public Match(Match match) {
        this.server_player = match.server_player;
        this.client_player = match.client_player;
        this.status = match.status;
    }

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
