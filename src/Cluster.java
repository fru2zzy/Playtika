import dto.ClusterItem;
import dto.NodeItem;

import java.util.List;
import java.util.Optional;

class Cluster {

    void sendMessage(ClusterItem clusterItem) {
        List<Optional<NodeItem>> nodeItems = clusterItem.getNodeItems();
        int randomNodeIndex = (int) (Math.random() * nodeItems.size());

        for (int i = randomNodeIndex; i < nodeItems.size(); i++) {
            Optional<NodeItem> nodeToDisconnect = nodeItems.get(i);

            // Set random node as disconnected
            if (Optional.ofNullable(nodeToDisconnect).isPresent()) { // WTF ? nodeToDisconnect.isPresent causes NPE  http://prntscr.com/oq8a0p
                nodeToDisconnect.get().setDisconnected();
                System.out.println("Node with id: '" + nodeToDisconnect.get().getId() + "' is Disconnected!");
            }
        }
    }
}
