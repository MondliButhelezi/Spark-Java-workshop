package greet.app;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {

        port(4040);
        get("/greet", (request, response) -> "Dumela! Welcome to my first Java web App ^_^ ");

        get("/greet/:username", (request, response) -> {
            return "Dumela, " + request.params(":username");
        });

//        get("/greet/:username/language/:language", (request, response) -> {
//            return "Dumela: " + request.params(":username");
//        });
        post("/greet", (request, response) -> {
            if (request.queryParams("username").equals("username")) {
                return request.queryParams("username");
            } else {
                return "Dumela!";
            }

        });

    }
}
