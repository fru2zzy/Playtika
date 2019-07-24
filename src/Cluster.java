import java.util.List;

class Cluster {

    void sendMessage(ClusterItem clusterItem) {
        List<NodeItem> nodeItems = clusterItem.getNodeItems();
        int randomNodeIndex = (int) (Math.random() * nodeItems.size());

        for (int i = randomNodeIndex; i < nodeItems.size(); i++) {
            NodeItem nodeToDisconnect = nodeItems.get(i);

            // Set random node as disconnected
            nodeToDisconnect.setDisconnected();
            System.out.println("Node with id: '" + nodeToDisconnect.getId() + "' is Disconnected!");
        }
    }
}
