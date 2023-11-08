package controller;

import domain.StarshipRequest;
import domain.XMLResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.function.Consumer;

@Path("/")
public class StarshipController {

    private String spaceMarineServiceUrl = "https://localhost:28081/api/v1/starships";
    private XMLParser<XMLResponse> xmlResponseParser = new XMLParser();
    private XMLParser<StarshipRequest> xmlStarshipParser = new XMLParser();
    private HttpsURLConnection connection;

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }
    private HttpResponse httpsPutRequest(String inputUrl) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(inputUrl);
        put.setHeader("Content-Type", "application/xml");
        HttpResponse response = client.execute(put);
        return response;
    }

    private HttpResponse httpsPostRequest(String inputUrl) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(inputUrl);
        post.setHeader("Content-Type", "application/xml");
        HttpResponse response = client.execute(post);
        return response;
    }
    private HttpResponse httpsGetRequest(String inputUrl) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(inputUrl);
        get.setHeader("Content-Type", "application/xml");
        HttpResponse response = client.execute(get);
        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/starship/create/{id}/{name}")
    public Response createStarship(@PathParam("id") Integer id, @PathParam("name") String name, StarshipRequest starshipRequest) throws JAXBException, InterruptedException {
        String responseEntity;
        Integer responseHttpStatusCode;
        try {
            HttpResponse response = httpsPostRequest(spaceMarineServiceUrl + "/" + id.toString() + "/" + name);
            responseHttpStatusCode = response.getStatusLine().getStatusCode();
            responseEntity = new String(response.getEntity().getContent().readAllBytes());
        } catch (IOException e){
            responseHttpStatusCode = 503;
            responseEntity = "Service is not ready to handle requests.";
        }
        return Response
                .status(responseHttpStatusCode)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "Origin, Content-Type, Accept, Authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity(responseEntity)
                .build();
    }

    @GET
    @Path("/starships")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Response getStarships() throws IOException, InterruptedException {
        String responseEntity;
        Integer responseHttpStatusCode;
        try{
            HttpResponse response = httpsGetRequest(spaceMarineServiceUrl);
            responseHttpStatusCode = response.getStatusLine().getStatusCode();
            responseEntity = new String(response.getEntity().getContent().readAllBytes());
        } catch (IOException e){
            responseHttpStatusCode = 503;
            responseEntity = "Service is not ready to handle requests.";
        }
        return Response
                .status(responseHttpStatusCode)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "Origin, Content-Type, Accept, Authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity(responseEntity)
                .build();
    }
    @OPTIONS
    @Path("/starship/{starshipId}/unload/{spaceMarineId}")
    public Response blyadskayaHuinya() {
        return Response.ok("")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }

    //@POST
    @PUT
    @Path("/starship/{starshipId}/unload/{spaceMarineId}")
    @Produces(MediaType.APPLICATION_XML)
    public Response unload(@PathParam("starshipId") Integer starshipId, @PathParam("spaceMarineId") Integer spaceMarineId) throws IOException, InterruptedException {
        //BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"));
        //writer.write("test request " + starshipId + ' ' + spaceMarineId);
        //writer.close();
        //return Response.status(200).entity("Ok").build();
        String responseEntity;
        Integer responseHttpStatusCode;
        try{
            HttpResponse response = httpsPutRequest(spaceMarineServiceUrl + "/" + starshipId.toString() + "/unload/" + spaceMarineId.toString());
            responseHttpStatusCode = response.getStatusLine().getStatusCode();
            if (responseHttpStatusCode != 204){
                responseEntity = new String(response.getEntity().getContent().readAllBytes());
                return Response
                        .status(responseHttpStatusCode)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers",
                               "Origin, Content-Type, Accept, Authorization")
                        .header("Access-Control-Allow-Methods",
                                "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .entity("Space marine" + spaceMarineId + " was not on starship " + starshipId)
                        .build();
            }
        } catch (IOException e){
            responseHttpStatusCode = 503;
            responseEntity = "Service is not ready to handle requests.";
            return Response
                    .status(responseHttpStatusCode)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Headers",
                            "Origin, Content-Type, Accept, Authorization")
                    .header("Access-Control-Allow-Methods",
                            "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .entity(responseEntity)
                    .build();
        }
        return Response
                .status(responseHttpStatusCode)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers",
                        "Origin, Content-Type, Accept, Authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity("Ok")
                .build();
    }
}
