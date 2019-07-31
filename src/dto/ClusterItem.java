package dto;

import java.util.ArrayList;
import java.util.List;

public class ClusterItem {

    private ClusterItem clusterItem;
    private List<ServerItem> serverItems;
    private int CURRENT_SERVER_ID;
    private int CURRENT_NODE_ID;


    ClusterItem() {
    }

    public ClusterItem(int serversCount, int nodesCount) {
        initializeCluster(serversCount, nodesCount);
    }

    public List<ServerItem> getServerItems() {
        return serverItems;
    }

    private void setServerItems(List<ServerItem> serverItems) {
        this.serverItems = serverItems;
    }

    private void initializeCluster(int serversCount, int nodesCount) {
        clusterItem = new ClusterItem();
        serverItems = new ArrayList<>();

        for (int i = 0; i < serversCount; i++) {
            List<NodeItem> nodes = new ArrayList<>();
            for (int j = 0; j < nodesCount; j++) {
                nodes.add(new NodeItem(CURRENT_NODE_ID, CURRENT_SERVER_ID));
                CURRENT_NODE_ID++;
            }
            serverItems.add(new ServerItem(CURRENT_SERVER_ID, nodes));
            CURRENT_SERVER_ID++;
        }
        clusterItem.setServerItems(serverItems);
    }

    public List<NodeItem> getNodeItems() {
        List<NodeItem> nodeItems = new ArrayList<>();

        for (ServerItem serverItem : getServerItems()) {
            nodeItems.addAll(serverItem.getNodeItems());
        }
        return nodeItems;
    }

    @Override
    public String toString() {
        return "ClusterItem {" + "serverItems=" + serverItems + '}';
    }
}
