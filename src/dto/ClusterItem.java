package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ClusterItem {

    private ClusterItem clusterItem;
    private List<Optional<ServerItem>> serverItems;
    private int CURRENT_SERVER_ID;
    private int CURRENT_NODE_ID;
    private static final Random RANDOM = new Random();


    private ClusterItem() {
    }

    public ClusterItem(int serversCount, int nodesCount) {
        initializeCluster(serversCount, nodesCount);
    }

    private List<Optional<ServerItem>> getServerItems() {
        return serverItems;
    }

    private void setServerItems(List<Optional<ServerItem>> serverItems) {
        this.serverItems = serverItems;
    }

    private void initializeCluster(int serversCount, int nodesCount) {
        clusterItem = new ClusterItem();
        serverItems = new ArrayList<>();

        for (int i = 0; i < serversCount; i++) {
            List<Optional<NodeItem>> nodes = new ArrayList<>();
            for (int j = 0; j < nodesCount; j++) {
                if (RANDOM.nextBoolean()) {
                    NodeItem node = new NodeItem(CURRENT_NODE_ID, CURRENT_SERVER_ID);
                    nodes.add(Optional.of(node));
                } else {
                    nodes.add(null);
                }
                CURRENT_NODE_ID++;
            }
            ServerItem server = new ServerItem(CURRENT_SERVER_ID, nodes);
            serverItems.add(Optional.of(server));
            CURRENT_SERVER_ID++;
        }
        clusterItem.setServerItems(serverItems);
    }

    public List<Optional<NodeItem>> getNodeItems() {
        List<Optional<NodeItem>> nodeItems = new ArrayList<>();

        for (Optional<ServerItem> serverItem : getServerItems()) {
            serverItem.ifPresent(serverItem1 -> nodeItems.addAll(serverItem1.getNodeItems()));
        }
        return nodeItems;
    }

    @Override
    public String toString() {
        return "ClusterItem {" + "serverItems=" + serverItems + '}';
    }
}
