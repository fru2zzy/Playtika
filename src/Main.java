import exceptions.DataNotFoundException;
import exceptions.NoDataException;
import search.FailSearchEngine;
import search.Result;
import servers.Cluster;

public class Main {

    public static void main(String[] args) {
        Cluster cluster = new Cluster(5, 3);

        cluster.sendMessage();
        System.out.println(cluster);

        FailSearchEngine searchEngine = new FailSearchEngine();

        Result result;
        try {
            result = searchEngine.findFailIterativelly(cluster); // result = searchEngine.findFail(cluster);
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
