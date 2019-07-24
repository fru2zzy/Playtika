import java.util.List;

class ServerItem {
    private int id;
    private List<NodeItem> nodeItems;

    List<NodeItem> getNodeItems() {
        return nodeItems;
    }

    ServerItem(int id, List<NodeItem> nodeItems) {
        this.id = id;
        this.nodeItems = nodeItems;
    }

    NodeItem getNodeItemByID(int nodeId) throws Exception {
        for (NodeItem node : nodeItems) {
            if (node.getId() == nodeId) return node;
        }
        throw new Exception("Cannot find nodeId " + nodeId + " in requested list of node IDs: " + nodeItems);
    }

    @Override
    public String toString() {
        return "ServerItem{" + "id=" + id + ", nodeItems=" + nodeItems + '}';
    }
}
