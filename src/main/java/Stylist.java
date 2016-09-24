import java.util.List;
import org.sql2o.*;

public class Stylist {
  private int id;
  private String username;

  public Stylist(String username) {
    this.username = username;
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

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists (username) VALUES (:username);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("username", this.username)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET username = :username WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("username", this.username)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stylists WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists WHERE id = :id;";
      Stylist stylist = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
      return stylist;
    }
  }

  public static Stylist findByUsername(String username) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists WHERE username = :username;";
      Stylist stylist = con.createQuery(sql)
        .addParameter("username", username)
        .executeAndFetchFirst(Stylist.class);
      return stylist;
    }
  }

  public static List<Stylist> search(String search) {
    String sql = "SELECT * FROM stylists WHERE username ~* :search;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("search", ".*" + search + ".*")
        .executeAndFetch(Stylist.class);
    }
  }

  public static List<Stylist> all() {
    String sql = "SELECT * FROM stylists;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  @Override
    public boolean equals(Object other) {
      if (!(other instanceof Stylist)) {
        return false;
      } else {
        Stylist otherStylist = (Stylist) other;
        return this.getUsername().equals(otherStylist.getUsername()) &&
          this.getId() == otherStylist.getId();
      }
    }

}
