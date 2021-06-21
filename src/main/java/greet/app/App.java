package greet.app;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {

        port(4040);
        staticFiles.location("/public");

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
            if (request.queryParams("username").equals("")) {
                return "Dumela!";
            } else {
                return "Hello " + request.queryParams("username");
            }
        });
    } //main bracket
}
