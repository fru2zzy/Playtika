package search;

public class Result {

    private int failedServer;
    private int failedNode;
    private int iterationsCount;
    private boolean isEmpty = true;

    Result() {
    }

    void setFailedServer(int failedServer) {
        isEmpty = false;
        this.failedServer = failedServer;
    }

    void setFailedNode(int failedNode) {
        isEmpty = false;
        this.failedNode = failedNode;
    }

    void setIterationsCount(int iterationsCount) {
        this.iterationsCount = iterationsCount;
    }

    boolean isEmpty() {
        return isEmpty;
    }

    int getFailedServer() {
        return failedServer;
    }

    int getFailedNode() {
        return failedNode;
    }

    int getIterationsCount() {
        return iterationsCount;
    }

    @Override
    public String toString() {
        return "Failed items were: {failedServer=" + failedServer + ", failedNode=" + failedNode + '}';
    }
}
