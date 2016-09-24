import java.util.List;
import org.sql2o.*;

public class Client {
  private int id;
  private String username;
  private Integer stylistId;

  public Client(String username) {
    this.username = username;
    this.stylistId = null;
  }

  public int getId() {
    return this.id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getStylistId() {
    return this.stylistId;
  }

  public void setStylistId(Integer stylistId) {
    this.stylistId = stylistId;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (username, stylistId) VALUES (:username, :stylistId);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("username", this.username)
        .addParameter("stylistId", this.stylistId)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET username = :username, stylistId = :stylistId WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("username", this.username)
        .addParameter("stylistId", this.stylistId)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE id = :id;";
      Client client = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
      return client;
    }
  }

  public static Client findByUsername(String username) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE username = :username;";
      Client client = con.createQuery(sql)
        .addParameter("username", username)
        .executeAndFetchFirst(Client.class);
      return client;
    }
  }

  public static List<Client> withStylistId(int stylistId) {
    String sql = "SELECT * FROM clients WHERE stylistId = :stylistId;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("stylistId", stylistId)
        .executeAndFetch(Client.class);
    }
  }

  public static List<Client> search(String search) {
    String sql = "SELECT * FROM clients WHERE username ~* :search;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("search", ".*" + search + ".*")
        .executeAndFetch(Client.class);
    }
  }

  public static List<Client> all() {
    String sql = "SELECT * FROM clients;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  @Override
    public boolean equals(Object other) {
      if (!(other instanceof Client)) {
        return false;
      } else {
        Client otherClient = (Client) other;
        return this.getUsername().equals(otherClient.getUsername()) &&
          this.getStylistId() == otherClient.getStylistId() &&
          this.getId() == otherClient.getId();
      }
    }

}
