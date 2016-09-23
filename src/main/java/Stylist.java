import java.util.List;
import org.sql2o.*;

public class Stylist {
  private int id;
  private String name;

  public Stylist(String name) {
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET name = :name WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("name", this.name)
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

  public static Stylist findByName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists WHERE name = :name;";
      Stylist stylist = con.createQuery(sql)
        .addParameter("name", name)
        .executeAndFetchFirst(Stylist.class);
      return stylist;
    }
  }

  public static List<Stylist> search(String search) {
    String sql = "SELECT * FROM stylists WHERE name ~* :search;";
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
        return this.getName().equals(otherStylist.getName()) &&
          this.getId() == otherStylist.getId();
      }
    }

}
