package dto;

import customExceptions.DataNotFoundException;

import java.util.List;

public class ServerItem {
    private int id;
    private List<NodeItem> nodeItems;

    public List<NodeItem> getNodeItems() {
        return nodeItems;
    }

    public ServerItem(int id, List<NodeItem> nodeItems) {
        this.id = id;
        this.nodeItems = nodeItems;
    }

    public NodeItem getNodeItemByID(int nodeId) throws Exception {
        for (NodeItem node : nodeItems) {
            if (node.getId() == nodeId) return node;
        }
        throw new DataNotFoundException("Cannot find nodeId " + nodeId + " in requested list of node IDs: " + nodeItems);
    }

    @Override
    public String toString() {
        return "ServerItem {" + "id=" + id + ", nodeItems=" + nodeItems + '}';
    }
}
