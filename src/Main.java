import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.DataNotFoundException;
import exceptions.NoDataException;
import search.FailSearchEngine;
import search.Result;
import servers.Cluster;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Main {

    public static void main(String[] args) throws IOException {
        Cluster originalCluster = new Cluster(5, 3);
        Gson gson = new GsonBuilder().create();

        // Write our cluster into json file
        Writer writer = new FileWriter("C:\\cluster.json");
        gson.toJson(originalCluster, writer);
        writer.flush();
        writer.close();

        // Read our cluster from json file
        FileReader reader = new FileReader("C:\\cluster.json");
        Cluster cluster = gson.fromJson(reader, Cluster.class);
        reader.close();

        cluster.sendMessage();
        System.out.println(cluster);

        FailSearchEngine searchEngine = new FailSearchEngine();

        Result result;
        try {
            result = searchEngine.findFail(cluster);
        } catch (NoDataException noDataException) {
            System.out.println("Warning, no data provided, initialize by default values: serversCount = 3, nodesCount = 5");

            cluster = new Cluster(3, 5);

            cluster.sendMessage();
            result = searchEngine.findFail(cluster);
        } catch (DataNotFoundException dataNotFoundException) {
            result = searchEngine.findFailIterativelly(cluster);
        }
        System.out.println(result);
    }
}
