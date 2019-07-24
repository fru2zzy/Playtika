public interface Failable {

    boolean isFailed(ClusterItem clusterItem, int serverId, int nodeId) throws Exception;

    NodeItem findDisconnectedNode(ClusterItem clusterItem) throws Exception;
}
