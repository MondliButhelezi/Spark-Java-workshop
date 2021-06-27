package myfirst.webapp;
import jdk.internal.org.objectweb.asm.Handle;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static spark.Spark.*;

public class App {

    static Map<String, Object> map = new HashMap<>();
    static Map<String, Integer> countGreet = new HashMap<>();

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4050;//return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {

        // connecting to maven
        String dbDiskURL = "jdbc:h2:file:./greetdb";
        // String dbMemoryURL = "jdbc:h2:mem:greetdb";

        Jdbi jdbi = Jdbi.create(dbDiskURL, "sa", "");

        // get a handle to the database
        Handle handle = jdbi.open();

        // create the table if needed
        handle.execute("create table if not exists greet ( id integer identity, name varchar(50), counter int )");

        port(getHerokuAssignedPort());
        staticFiles.location("/public"); // linking static files index and hello.handlebars.

        //1ST PART
        get("/greet", (request, response) -> "Dumela! Welcome to my first Java web App ^_^ ");

        get("/greet/:username", (request, response) -> {
            return "Dumela, " + request.params(":username");
        });

        // 2ND PART
        get("/greet/:username/language/:language", (request, response) -> {
            String names = request.params(":username");
            names = names.substring(0, 1).toUpperCase() + names.substring(1).toLowerCase();

            if (request.params("language").equals("english")) {
                return "Hello " + names;
            } else if (request.params("language").equals("sotho")) {
                return "Dumela " + names;
            } else if (request.params("language").equals("zulu")) {
                return "Sawubona " + names;
            }else if (request.params("language").equals("xhosa")) {
                return "Molo " + names;
            } else {
                return "Dumela, " + names;
            }
        });

        // 3RD PART
        post("/greet", (request, response) -> {
            String username = request.queryParams("username");
            username = username.substring(0, 1).toUpperCase();

            if (request.queryParams("username").isEmpty()) {
                return "Dumela!";
            } else {
                return "Dumela " + username;
            }
        });

        get("/hello", (request, response) -> {
            return new ModelAndView(map, "hello.handlebars");
        }, new HandlebarsTemplateEngine());

        // 4TH PART
        post("/hello", (request, response) -> {

            String message = new String();
            String greeting =  request.queryParams("username");
            greeting = greeting.substring(0,1).toUpperCase() + greeting.substring(1).toLowerCase();
            String language = request.queryParams("language");

            if (language.equalsIgnoreCase("ENGLISH")) {
                message = "Hello, " + greeting;
            } else if (language.equalsIgnoreCase("SOTHO")) {
                message = "Dumela, " + greeting;
            } else if (language.equalsIgnoreCase("ZULU")) {
                message = "Sawubona, " + greeting;
            } else if (language.equalsIgnoreCase("XHOSA")){
                message = "Molo, " + greeting;
            }

            if (!countGreet.containsKey(greeting)) {
                countGreet.put(greeting,1);
            } else if (countGreet.containsKey(greeting)) {
                countGreet.put(greeting, countGreet.get(greeting) + 1);
            } int totalCount = countGreet.size();

            map.put("greeting", message);
            map.put("totalCount", totalCount);

            return new ModelAndView(map,"hello.handlebars");
        }, new HandlebarsTemplateEngine());

        // 5TH PART
        get("/greeted", (req, res) -> {
            ArrayList<String> naming = new ArrayList<>();

            for (Map.Entry<String, Integer> names : countGreet.entrySet()) {
                String availableNames = names.getKey();
                naming.add(availableNames);
            }
            map.put("getNames",naming);

            return new ModelAndView(map, "greeted.handlebars");
        }, new HandlebarsTemplateEngine());


        post("/greeted", (request, response) -> {

            Map<String, String> map = new HashMap<>();
            String username = request.queryParams("username");

            map.put("greetedUsers", username);

            for (Map.Entry<String, String> entrySet : map.entrySet()){
                System.out.println(entrySet);
                map.put("greetedUsers", entrySet.getValue());
            }

            return new ModelAndView(map, "greeted.handlebars");
        });
    } //main bracket


}