import exceptions.DataNotFoundException;
import exceptions.NoDataException;
import search.FailSearchEngine;
import search.Result;
import servers.Cluster;
import utils.GsonSerializer;

import java.io.IOException;

import static utils.Const.DEFAULT_NODES_COUNT;
import static utils.Const.DEFAULT_SERVERS_COUNT;

public class Main {

    public static void main(String[] args) throws IOException {
        Cluster originalCluster = new Cluster(DEFAULT_SERVERS_COUNT, DEFAULT_NODES_COUNT);

        // Write our cluster into json file
        GsonSerializer serializer = new GsonSerializer();
        serializer.writeClusterToJSON(originalCluster);

        // Read our cluster from json file
        Cluster cluster = serializer.readClusterFromJSON();

        cluster.sendMessage();
        System.out.println(cluster);

        FailSearchEngine searchEngine = new FailSearchEngine();

        Result result;
        try {
            result = searchEngine.findFail(cluster);
        } catch (NoDataException noDataException) {
            System.out.println("Warning, no data provided, initialize by default values: serversCount = " +
                    DEFAULT_SERVERS_COUNT + ", nodesCount = " + DEFAULT_NODES_COUNT);

            cluster = new Cluster(DEFAULT_SERVERS_COUNT, DEFAULT_NODES_COUNT);

            cluster.sendMessage();
            result = searchEngine.findFail(cluster);
        } catch (DataNotFoundException dataNotFoundException) {
            result = searchEngine.findFailIterativelly(cluster);
        }
        System.out.println(result);
    }
}
