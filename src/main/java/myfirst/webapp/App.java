package myfirst.webapp;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {

        port(4040);
        staticFiles.location("/public"); // linking static files index and hello.handlebars.

        get("/greet", (request, response) -> "Dumela! Welcome to my first Java web App ^_^ ");

        get("/greet/:username", (request, response) -> {
            return "Dumela, " + request.params(":username");
        });

        get("/greet/:username/language/:language", (request, response) -> {
            if (request.params("language").equals("english")) {
                return "Hello " +  request.params("username");
            } else if (request.params("language").equals("sotho")) {
                return "Dumela " + request.params("username");
            } else if (request.params("language").equals("zulu")) {
                return "Sawubona " + request.params("username");
            }else if (request.params("language").equals("xhosa")) {
                return "Molo " + request.params("username");
            } else {
                return "Dumela, " + request.params(":username");
            }
        });

        post("/greet", (request, response) -> {
            if (request.queryParams("username").isEmpty()) {
                return "Dumela!";
            } else {
                return "Dumela " + request.queryParams("username");
            }
        });

        get("/hello", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "hello.handlebars");
        }, new HandlebarsTemplateEngine());


        post("/hello", (request, response) -> {
            Map<String, Object> map = new HashMap<>();

            String message = new String();
            String greeting =  request.queryParams("username");
            String language = request.queryParams("language");
//
            if (language.equalsIgnoreCase("ENGLISH")) {
                message = "Hello, " + greeting;
            } else if (language.equalsIgnoreCase("SOTHO")) {
                message = "Dumela, " + greeting;
            } else if (language.equalsIgnoreCase("ZULU")) {
                message = "Sawubona, " + greeting;
            } else if (language.equalsIgnoreCase("XHOSA")){
                message = "Molo, " + greeting;
            }
            map.put("greeting", message);

            return new ModelAndView(map,"hello.handlebars");
        }, new HandlebarsTemplateEngine());
    } //main bracket


}
