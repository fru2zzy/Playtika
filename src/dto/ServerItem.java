package dto;

import customExceptions.DataNotFoundException;

import java.util.List;
import java.util.Optional;

public class ServerItem {
    private int id;
    private List<Optional<NodeItem>> nodeItems;

    List<Optional<NodeItem>> getNodeItems() {
        return nodeItems;
    }

    ServerItem(int id, List<Optional<NodeItem>> nodeItems) {
        this.id = id;
        this.nodeItems = nodeItems;
    }

    public Optional<NodeItem> getNodeItemByID(int nodeId) {
        for (Optional<NodeItem> node : nodeItems) {
            if (node.get().getId() == nodeId) return node;
        }
        throw new DataNotFoundException("Cannot find nodeId " + nodeId + " in requested list of node IDs: " + nodeItems);
    }

    @Override
    public String toString() {
        return "ServerItem {" + "id=" + id + ", nodeItems=" + nodeItems + '}';
    }
}
