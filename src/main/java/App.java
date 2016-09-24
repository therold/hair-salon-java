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
      model.put("stylists", Stylist.all());
      model.put("template", "templates/stylists/index.vtl");
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

    get("/stylists/edit/:id", (request, response) -> {
      Stylist stylist = tryFindStylist(request.params(":id"));
      if(stylist != null) {
        model.put("stylist", stylist);
      } else {
        response.redirect(request.session().attribute("Prev"));
      }
      model.put("template", "templates/stylists/edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/delete/:id", (request, response) -> {
      Stylist stylist = tryFindStylist(request.params(":id"));
      if(stylist != null) {
        model.put("stylist", stylist);
      }
      model.put("template", "templates/stylists/delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/search", (request, response) -> {
      if(request.queryParams("s") != null) {
        String search = request.queryParams("s");
        model.put("stylists", Stylist.search(search));
        model.put("search", search);
      }
      model.put("template", "templates/stylists/search.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/assign", (request, response) -> {
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("template", "templates/stylists/assign.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/signin", (request, response) -> {
      if (request.queryParams("notfound") != null) {
        model.put("notfound", true);
      }
      model.put("template", "templates/stylists/signin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/signout", (request, response) -> {
      activeStylist = null;
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/profile", (request, response) -> {
      if (activeStylist == null) {
        response.redirect("/");
      }
      model.put("template", "templates/stylists/profile.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      Stylist stylist = tryFindStylist(request.params(":id"));
      if(stylist != null) {
        model.put("stylist", stylist);
      }
      model.put("template", "templates/stylists/view.vtl");
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

    post("/stylists/edit", (request, response) -> {
      Stylist stylist = tryFindStylist(request.queryParams("stylistId"));
      if(stylist != null) {
        stylist.setName(request.queryParams("name"));
        stylist.update();
        response.redirect(request.session().attribute("Prev"));
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/assign", (request, response) -> {
      Stylist stylist = tryFindStylist(request.queryParams("stylistId"));
      Client client = tryFindClient(request.queryParams("clientId"));
      if(stylist != null && client != null) {
        client.setStylistId(stylist.getId());
        client.update();
        response.redirect(request.session().attribute("Prev"));
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/delete", (request, response) -> {
      Stylist stylist = tryFindStylist(request.queryParams("stylistId"));
      if(stylist != null) {
        if(activeStylist != null && activeStylist.equals(stylist)) {
          activeStylist = null;
        }
        stylist.delete();
        response.redirect(request.session().attribute("Prev"));
      }
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

    // Clients
    get("/clients", (request, response) -> {
      model.put("clients", Client.all());
      model.put("template", "templates/clients/index.vtl");
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

    get("/clients/edit/:id", (request, response) -> {
      Client client = tryFindClient(request.params(":id"));
      if(client != null) {
        model.put("client", client);
      } else {
        response.redirect(request.session().attribute("Prev"));
      }
      model.put("template", "templates/clients/edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/delete/:id", (request, response) -> {
      Client client = tryFindClient(request.params(":id"));
      if(client != null) {
        model.put("client", client);
      }
      model.put("template", "templates/clients/delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/search", (request, response) -> {
      if(request.queryParams("s") != null) {
        String search = request.queryParams("s");
        model.put("clients", Client.search(search));
        model.put("search", search);
      }
      model.put("template", "templates/clients/search.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/signin", (request, response) -> {
      if (request.queryParams("notfound") != null) {
        model.put("notfound", true);
      }
      model.put("template", "templates/clients/signin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/signout", (request, response) -> {
      activeClient = null;
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/profile", (request, response) -> {
      if (activeClient == null) {
        response.redirect("/");
      }
      model.put("template", "templates/clients/profile.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/:id", (request, response) -> {
      Client client = tryFindClient(request.params(":id"));
      if(client != null) {
        model.put("client", client);
      }
      model.put("template", "templates/clients/view.vtl");
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

    post("/clients/edit", (request, response) -> {
      Client client = tryFindClient(request.queryParams("clientId"));
      if(client != null) {
        client.setName(request.queryParams("name"));
        client.update();
        response.redirect(request.session().attribute("Prev"));
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients/delete", (request, response) -> {
      Client client = tryFindClient(request.queryParams("clientId"));
      if(client != null) {
        if(activeClient != null && activeClient.equals(client)) {
          activeClient = null;
        }
        client.delete();
        response.redirect(request.session().attribute("Prev"));
      }
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

    // Owner
    get("/owner", (request, response) -> {
      activeClient = null;
      activeStylist = null;
      model.remove("activeClient");
      model.remove("activeStylist");
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("template", "templates/owner.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }

  private static Stylist tryFindStylist(String id) {
    Integer stylistId = tryParseInt(id);
    if(stylistId != null && Stylist.find(stylistId) != null) {
      return Stylist.find(stylistId);
    } else {
      return null;
    }
  }

  private static Client tryFindClient(String id) {
    Integer clientId = tryParseInt(id);
    if(clientId != null && Client.find(clientId) != null) {
      return Client.find(clientId);
    } else {
      return null;
    }
  }

  private static Integer tryParseInt(String toParse) {
    Integer number = null;
    try {
      number = Integer.parseInt(toParse);
    } catch (NumberFormatException e) {
      System.out.println("Error parsing integer: " + e.getMessage());
    }
    return number;
  }

}
