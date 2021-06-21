package greet.app;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        get("/greet", (request, response) -> "Dumela!");

        get("/greet/:username", (request, response) -> {
            return "Dumela: " + request.params(":username");
        });

        get("/greet/:username/language/:language", (request, response) -> {
            return "Dumela: " + request.params(":username");
        });
    }
}
