public class NodeItem {

    private int id;
    private int parentId;
    private boolean isDisconnected;
    static int CURRENT_NODE_ID;


    NodeItem(int id, int parentId) {
        this.id = id;
        this.parentId = parentId;
        this.isDisconnected = false;
    }

    int getId() {
        return id;
    }

    int getParentId() {
        return parentId;
    }

    void setDisconnected() {
        isDisconnected = true;
    }

    boolean isDisconnected() {
        return isDisconnected;
    }

    @Override
    public String toString() {
        return "NodeItem{" + "id=" + id + ", parentId=" + parentId + ", isDisconnected=" + isDisconnected + '}';
    }
}
