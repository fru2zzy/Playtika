package search;

public class Result {

    private int failedServer;
    private int failedNode;
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

    boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public String toString() {
        return "Failed items were: {failedServer=" + failedServer + ", failedNode=" + failedNode + '}';
    }
}
