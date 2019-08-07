package servers;

public class Node implements Failable {

    private int id;
    private int parentId;
    private boolean failed;

    Node(int parentId, int id) {
        this.parentId = parentId;
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    public Failable getInnerFallible(int number) {
        return null; // No inners
    }

    @Override
    public int getSize() {
        return 0;
    }

    void failNode() {
        this.failed = true;
    }

    @Override
    public String toString() {
        return (failed ? "\u001B[31m" : "") + "Node{" + "id=" + id + ", parentId=" + parentId + ", failed=" + failed + '}' + (failed ? "\u001B[0m" : "");
    }
}
