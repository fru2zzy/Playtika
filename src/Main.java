import customExceptions.DataNotFoundException;
import customExceptions.NoDataException;
import customExceptions.SuccessException;
import dto.ClusterItem;
import dto.NodeItem;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {
        Cluster cluster = new Cluster();

        Random random = new Random();
        int randomServerCount = random.nextInt(3);
        int randomNodesCount = random.nextInt(5);

        ClusterItem clusterItem = new ClusterItem(randomServerCount, randomNodesCount);

        cluster.sendMessage(clusterItem);

        FailSearchEngine searchEngine = new FailSearchEngine();
        NodeItem disconnectedNode = null;
        try {
            disconnectedNode = searchEngine.findDisconnectedNode(clusterItem);
        } catch (NoDataException noDataException) {
            System.out.println("Warning, no data provided, initialize by default values: serversCount = 3, nodesCount = 5");
            clusterItem = new ClusterItem(3, 5);
            cluster.sendMessage(clusterItem);
            disconnectedNode = searchEngine.findDisconnectedNode(clusterItem);
        } catch (DataNotFoundException dataNotFoundException) {
            disconnectedNode = searchEngine.findDisconnectedNodeIteratively(clusterItem);
        } catch (SuccessException successException) {
            System.out.println(successException.getMessage());
        }

        if (disconnectedNode != null) System.out.println("Our disconnected node is: " + disconnectedNode);
    }
}
