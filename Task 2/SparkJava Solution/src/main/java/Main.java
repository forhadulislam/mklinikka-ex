import org.json.JSONObject;

import static spark.Spark.*;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


public class Main {
    public static void main(String[] args) {

        post("/hash", (request, response) ->  {
            //response.type("application/json");

            String fiOutput, sha256Output;

            try {

                JSONObject inputJson = new JSONObject(
                    request.body()
                );
                fiOutput = String.valueOf(inputJson.get("text"));

                // Creating SHA256 hash from the input text
                MessageDigest mdigest = MessageDigest.getInstance("SHA-256");
                byte[] bdigest = mdigest.digest(fiOutput.getBytes(StandardCharsets.UTF_8));
                sha256Output = DatatypeConverter.printHexBinary(bdigest).toLowerCase();

                // Creating the JSON response for output
                JSONObject output = new JSONObject();
                output.put("hash", sha256Output);

                // Setting response to 200 OK and sending the response
                response.status(200);
                return output;

            }catch (Exception e) {
                JSONObject err = new JSONObject();
                err.put("error", "Wrong data format!");

                // Setting response to 400 Bad Request and sending the response
                response.status(400);
                return err;
            }
        });

        get("/", (request, response) -> "Api root");

    }
}