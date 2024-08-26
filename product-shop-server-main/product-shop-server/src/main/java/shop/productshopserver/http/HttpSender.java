package shop.productshopserver.http;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import shop.productshopserver.session.Status;
//per comunicare con session con un microservizio che si occupa di gestire le sessioni
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpSender {
    private String uri = "http://product-shop-session:51235/session";

    public Status getSession(String id) {
        String newUri = uri + "/" + id;
        HttpRequest req = HttpRequest.newBuilder().GET().header("accept", "application/json").uri(URI.create(newUri)).build();

        HttpClient client = HttpClient.newBuilder().build(); // Crea un'istanza di HttpClient

        try {
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper(); // Crea un'istanza di ObjectMapper per deserializzare JSON
            mapper.registerModule(new JavaTimeModule()); // Registra un modulo per gestire i tipi di data e ora
            Status status = mapper.readValue(resp.body(), new TypeReference<Status>(){}); //Deserializza il corpo della risposta in un oggetto stat
            return status; // Restituisce l'oggetto Status

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSession(String id, Status status){
        String newUri = uri + "/" + id;

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {

            String map = mapper.writeValueAsString(status);
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(newUri)).POST(HttpRequest.BodyPublishers.ofString(map)).version(java.net.http.HttpClient.Version.HTTP_2).build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse resp = client.send(req, HttpResponse.BodyHandlers.ofString());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

}
