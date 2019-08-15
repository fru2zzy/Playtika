package servers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server implements Failable {

    private int id;
    private int parentId;
    private boolean failed;
    private final List<Node> nodes;
    private final Random random = new Random();

    Server(int id, int parentId, int nodesCount) {
        this.id = id;
        this.parentId = parentId;
        nodes = new ArrayList<>(nodesCount);
        for (int i = 0; i < nodesCount; i++) {
            nodes.add(new Node(id, i));
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getParentId() {
        return parentId;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    public Failable getInnerFailable(int number) {
        return nodes.get(number);
    }

    @Override
    public int getSize() {
        return nodes.size();
    }

    void failRandomNode() {
        this.failed = true;
        int randomNode = random.nextInt(getSize());
        for (int i = randomNode; i < getSize(); i++) {
            nodes.get(i).failNode();
        }
    }

    void failAllNodes() {
        this.failed = true;
        for (int i = 0; i < getSize(); i++) {
            nodes.get(i).failNode();
        }
    }

    @Override
    public String toString() {
        return "\nServer{" + "id=" + id + ", failed=" + failed + ", nodes=" + nodes + '}';
    }
}
