package servers;

import static utils.Const.TEXT_DEFAULT;
import static utils.Const.TEXT_RED;

public class Node implements Failable {

    private int id;
    private int parentId;
    private boolean failed;

    Node(int parentId, int id) {
        this.parentId = parentId;
        this.id = id;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
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
    public Failable getInnerFailable(int number) {
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
        return (failed ? TEXT_RED : "") + "Node{" + "id=" + id + ", parentId=" + parentId + ", failed=" + failed + '}' + (failed ? TEXT_DEFAULT : "");
    }
}
