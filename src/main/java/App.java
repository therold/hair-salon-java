import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  private static Client activeClient;
  private static Stylist activeStylist;

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    before((request, response) -> {
      String referer;
      if(request.headers("Referer") != null) {
        if(request.headers("Referer").contains("?")) {
          referer = request.headers("Referer").substring(0, request.headers("Referer").indexOf('?'));
        } else {
          referer = request.headers("Referer");
        }
        if(request.requestMethod().equals("GET") && !referer.equals(request.session().attribute("Current"))) {
          request.session().attribute("Prev", request.session().attribute("Current"));
          request.session().attribute("Current", referer);
        }
      }
    });

    // Home
    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // Stylists
    get("/stylists", (request, response) -> {
      //TODO
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/new", (request, response) -> {
      //TODO
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/stylists/new.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists", (request, response) -> {
      //TODO
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      if (Stylist.findByName(name) != null) {
        response.redirect(request.session().attribute("Current") + "?stylistexists=true");
      } else {
        Stylist stylist = new Stylist(name);
        stylist.save();
        activeClient = null;
        activeStylist = stylist;
        response.redirect(request.session().attribute("Prev"));
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/search", (request, response) -> {
      //TODO
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/signin", (request, response) -> {
      //TODO
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/signout", (request, response) -> {
      //TODO
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      //TODO
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // Clients
    get("/clients", (request, response) -> {
      //TODO
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/clients/new.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      if (Client.findByName(name) != null) {
        response.redirect(request.session().attribute("Current") + "?clientexists=true");
      } else {
        Client client = new Client(name);
        client.save();
        activeStylist = null;
        activeClient = client;
        response.redirect(request.session().attribute("Prev"));
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/search", (request, response) -> {
      //TODO
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/signin", (request, response) -> {
      //TODO
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/signout", (request, response) -> {
      //TODO
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/:id", (request, response) -> {
      //TODO
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // Owner
    get("/owner", (request, response) -> {
      //TODO
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
