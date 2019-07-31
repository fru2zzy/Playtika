package dto;

public class NodeItem {

    private int id;
    private int parentId;
    private boolean isDisconnected;


    NodeItem(int id, int parentId) {
        this.id = id;
        this.parentId = parentId;
        this.isDisconnected = false;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setDisconnected() {
        isDisconnected = true;
    }

    public boolean isDisconnected() {
        return isDisconnected;
    }

    @Override
    public String toString() {
        return "NodeItem {" + "id=" + id + ", parentId=" + parentId + ", isDisconnected=" + isDisconnected + '}';
    }
}
