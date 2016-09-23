import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  private static Client activeClient;
  private static Stylist activeStylist;
  private static Map<String, Object> model;

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";
    model = new HashMap<String, Object>();

    before((request, response) -> {
      model.clear();
      if(activeClient != null) {
        model.put("activeClient", activeClient);
      }
      if(activeStylist != null) {
        model.put("activeStylist", activeStylist);
      }
      String referer;
      if(request.headers("Referer") != null) {
        if(request.headers("Referer").contains("?")) {
          referer = request.headers("Referer").substring(0, request.headers("Referer").indexOf('?'));
        } else {
          referer = request.headers("Referer");
        }
        if(!referer.equals(request.session().attribute("Current"))) {
          request.session().attribute("Prev", request.session().attribute("Current"));
          request.session().attribute("Current", referer);
        }
      }
    });

    // Home
    get("/", (request, response) -> {
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // Stylists
    get("/stylists", (request, response) -> {
      //TODO
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/new", (request, response) -> {
      if (activeStylist != null) {
        response.redirect("/stylists/profile");
      } else if (request.queryParams("stylistexists") != null) {
        model.put("stylistexists", true);
      }
      model.put("template", "templates/stylists/new.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists", (request, response) -> {
      String name = request.queryParams("name");
      if (Stylist.findByName(name) != null) {
        response.redirect(request.session().attribute("Current") + "?stylistexists=true");
      } else {
        Stylist stylist = new Stylist(name);
        stylist.save();
        activeClient = null;
        activeStylist = stylist;
        response.redirect(request.session().attribute("Current"));
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/search", (request, response) -> {
      //TODO
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/signin", (request, response) -> {
      if (request.queryParams("notfound") != null) {
        model.put("notfound", true);
      }
      model.put("template", "templates/stylists/signin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/signin", (request, response) -> {
      String name = request.queryParams("name");
      if (Stylist.findByName(name) == null) {
        response.redirect("/stylists/signin?notfound");
      } else {
        activeStylist = Stylist.findByName(name);
        response.redirect("/stylists/profile");
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/signout", (request, response) -> {
      activeStylist = null;
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/profile", (request, response) -> {
      //TODO
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      //TODO
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // Clients
    get("/clients", (request, response) -> {
      //TODO
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/new", (request, response) -> {
      if (activeClient != null) {
        response.redirect("/clients/profile");
      } else if (request.queryParams("clientexists") != null) {
        model.put("clientexists", true);
      }
      model.put("template", "templates/clients/new.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients", (request, response) -> {
      String name = request.queryParams("name");
      if (Client.findByName(name) != null) {
        response.redirect(request.session().attribute("Current") + "?clientexists=true");
      } else {
        Client client = new Client(name);
        client.save();
        activeStylist = null;
        activeClient = client;
        response.redirect(request.session().attribute("Current"));
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/search", (request, response) -> {
      //TODO
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/signin", (request, response) -> {
      if (request.queryParams("notfound") != null) {
        model.put("notfound", true);
      }
      model.put("template", "templates/clients/signin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients/signin", (request, response) -> {
      String name = request.queryParams("name");
      if (Client.findByName(name) == null) {
        response.redirect("/clients/signin?notfound");
      } else {
        activeClient = Client.findByName(name);
        response.redirect("/clients/profile");
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/signout", (request, response) -> {
      activeClient = null;
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/profile", (request, response) -> {
      //TODO
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/:id", (request, response) -> {
      //TODO
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // Owner
    get("/owner", (request, response) -> {
      activeClient = null;
      activeStylist = null;
      model.put("template", "templates/owner.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
