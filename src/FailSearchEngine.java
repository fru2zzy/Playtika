import customExceptions.DataNotFoundException;
import dto.ClusterItem;
import dto.NodeItem;

import java.util.List;
import java.util.Optional;

class FailSearchEngine {

    Optional<NodeItem> findDisconnectedNodeIteratively(ClusterItem clusterItem) {
        List<Optional<NodeItem>> nodeItems = clusterItem.getNodeItems();
        for (int i = 0; i < nodeItems.size(); i++) {
            Optional<NodeItem> node = nodeItems.get(i);
            if (Optional.ofNullable(node).isPresent() && node.get().isDisconnected()) { // WTF ? node.isPresent causes NPE  http://prntscr.com/oq8a0p
                System.out.println("\nWe've found a disconnected node in " + i + " iteration(s)");
                return node;
            }
        }
        throw new DataNotFoundException("Cannot find disconnected node even in iteratively way! Maybe no nodes were disconnected?");
    }
}
