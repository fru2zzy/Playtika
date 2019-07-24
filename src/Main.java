public class Main {

    public static void main(String[] args) throws Exception {
        Cluster cluster = new Cluster();
        ClusterItem clusterItem = new ClusterItem(3, 5);

        cluster.sendMessage(clusterItem);

        FailSearchEngine searchEngine = new FailSearchEngine();
        NodeItem disconnectedNode = searchEngine.findDisconnectedNode(clusterItem);

        System.out.println("Our disconnected node is: " + disconnectedNode);
    }
}
