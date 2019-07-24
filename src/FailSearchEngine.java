import java.util.List;

public class FailSearchEngine implements Failable {

    public NodeItem findDisconnectedNode(ClusterItem clusterItem) throws Exception {
        List<NodeItem> nodeItems = clusterItem.getNodeItems();
        if (nodeItems.isEmpty()) throw new Exception("Cannot perform search in empty array!");

        int midIndex = nodeItems.size() / 2;
        NodeItem midNode = nodeItems.get(midIndex);

        int iteration = 0, startIndex, endIndex;
        int previousMidIndex = nodeItems.size();
        boolean previousStatus = false;
        NodeItem previousItem = midNode;

        // Binary search
        while (iteration <= nodeItems.size()) {
            boolean isFailed;
            isFailed = isFailed(clusterItem, midNode.getParentId(), midNode.getId());

            if (isFailed) {
                startIndex = iteration > 1 ? midIndex : 0;
                endIndex = midIndex;
                previousMidIndex = midIndex;
                midIndex = (endIndex - startIndex) / 2;
            } else {
                startIndex = midIndex;
                endIndex = previousMidIndex;
                midIndex = startIndex + ((endIndex - startIndex) / 2);
            }

            // Boundary condition
            if (previousStatus && endIndex - startIndex == 2 && !isFailed) {
                midNode = nodeItems.get(midIndex);
                isFailed = isFailed(clusterItem, midNode.getParentId(), midNode.getId());
                if (!isFailed) return previousItem;
            }

            previousStatus = isFailed;
            previousItem = midNode;

            midNode = nodeItems.get(midIndex);
            isFailed = isFailed(clusterItem, midNode.getParentId(), midNode.getId());
            iteration++;

            boolean changedStatusOnBoundary = (!previousStatus && isFailed) || (previousStatus && !isFailed);
            if (changedStatusOnBoundary && endIndex - startIndex <= 1) {
                return previousItem; // We've found a node
            }

            if (iteration == nodeItems.size()) {
                if (endIndex - startIndex == 1) { // Reached the end of binary search without any match found
                    throw new Exception("Cannot find disconnected node, all nodes working fine");
                } else {
                    return nodeItems.get(0); // Case when all nodes are disconnected
                }
            }
        }
        throw new Exception("Cannot find disconnected node!");
    }

    public boolean isFailed(ClusterItem clusterItem, int serverId, int nodeId) throws Exception {
        return clusterItem.getServerItems().get(serverId).getNodeItemByID(nodeId).isDisconnected();
    }
}
