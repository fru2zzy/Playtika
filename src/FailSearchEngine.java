import java.util.List;

public class FailSearchEngine implements Failable {

    public NodeItem findDisconnectedNode(ClusterItem clusterItem) throws Exception {
        List<NodeItem> nodeItems = clusterItem.getNodeItems();
        if (nodeItems.isEmpty()) throw new Exception("Cannot perform search in empty array!");


        int iteration = 0, startIndex, endIndex;
        int previousStartIndex = 0, previousEndIndex = nodeItems.size();
        int lastDisconnectedNodeIndex = 0;
        boolean previousStatus;
        NodeItem previousItem, lastDisconnectedNode = null;

        int midIndex = nodeItems.size() / 2;
        int previousMidIndex = midIndex;
        NodeItem midNode = nodeItems.get(midIndex);

        // Binary search
        boolean isFailed;
        isFailed = isFailed(clusterItem, midNode.getParentId(), midNode.getId());
        iteration++;

        while (iteration <= nodeItems.size()) {
            if (isFailed) {
                lastDisconnectedNodeIndex = previousMidIndex;
                lastDisconnectedNode = midNode;

                startIndex = previousStartIndex;
                endIndex = midIndex;
                midIndex = startIndex + (endIndex - startIndex) / 2;
            } else {
                startIndex = midIndex;
                endIndex = previousEndIndex;
                midIndex = startIndex + ((endIndex - startIndex) / 2);
            }

            // Remember previous state to use it later
            previousMidIndex = midIndex;
            previousStartIndex = startIndex;
            previousEndIndex = endIndex;
            previousItem = midNode;
            previousStatus = isFailed;

            // Get state of a new mid index node
            midNode = nodeItems.get(midIndex);
            isFailed = isFailed(clusterItem, midNode.getParentId(), midNode.getId());
            iteration++;

            // Case when our first mid index = searched node
            if (!isFailed && Math.abs(lastDisconnectedNodeIndex - midIndex) <= 1) {
                System.out.println("\nWe've found a disconnected node in " + iteration + " iteration(s)");
                return lastDisconnectedNode;
            }

            boolean changedStatusOnBoundary = (!previousStatus && isFailed) || (previousStatus && !isFailed);
            if (changedStatusOnBoundary) {
                if (!isFailed && Math.abs(previousEndIndex - midIndex) <= 1) { // Distance between nodes <= 1 node
                    System.out.println("\nWe've found a disconnected node in " + iteration + " iteration(s)");
                    return previousItem;
                }
            }

            if (iteration == nodeItems.size()) {
                if (endIndex - startIndex == 1) { // Reached the end of binary search without any match found
                    throw new Exception("Cannot find disconnected node, all nodes working fine");
                } else {
                    System.out.println("\nWe've found a disconnected node in " + iteration + " iteration(s)");
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
